package com.lgy.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.base.domain.Shop;
import com.lgy.base.service.IShopService;
import com.lgy.common.annotation.Log;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.domain.ShopCommodity;
import com.lgy.oms.service.IShopCommodityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 铺货关系Controller
 *
 * @author lgy
 * @date 2019-10-22
 */
@Controller
@RequestMapping("/oms/commodity")
public class ShopCommodityController extends BaseController {

    private String prefix = "oms/shopcommodity";

    @Autowired
    private IShopCommodityService shopCommodityService;

    @Autowired
    private IShopService shopService;

    @RequiresPermissions("oms:commodity:view")
    @GetMapping()
    public String commodity() {
        return prefix + "/shopcommodity";
    }

    /**
     * 查询铺货关系列表
     */
    @RequiresPermissions("oms:commodity:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ShopCommodity shopCommodity) {
        QueryWrapper<ShopCommodity> queryWrapper = getShopCommodityQueryWrapper(shopCommodity);
        startPage();
        TableDataInfo dataTable = getDataTable(shopCommodityService.list(queryWrapper));
        return dataTable;
    }

    /**
     * 导出铺货关系列表
     */
    @RequiresPermissions("oms:commodity:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ShopCommodity shopCommodity) {
        QueryWrapper<ShopCommodity> queryWrapper = getShopCommodityQueryWrapper(shopCommodity);
        List<ShopCommodity> list = shopCommodityService.list(queryWrapper);
        ExcelUtil<ShopCommodity> util = new ExcelUtil<>(ShopCommodity.class);
        return util.exportExcel(list, "commodity");
    }

    private QueryWrapper<ShopCommodity> getShopCommodityQueryWrapper(ShopCommodity shopCommodity) {
        QueryWrapper<ShopCommodity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(shopCommodity.getShop())) {
            queryWrapper.lambda().eq(ShopCommodity::getShop, shopCommodity.getShop());
        }
        if (StringUtils.isNotEmpty(shopCommodity.getCommodity())) {
            queryWrapper.lambda().eq(ShopCommodity::getCommodity, shopCommodity.getCommodity());
        }
        if (StringUtils.isNotEmpty(shopCommodity.getNumIid())) {
            queryWrapper.lambda().eq(ShopCommodity::getNumIid, shopCommodity.getNumIid());
        }
        if (StringUtils.isNotEmpty(shopCommodity.getSkuId())) {
            queryWrapper.lambda().eq(ShopCommodity::getSkuId, shopCommodity.getSkuId());
        }
        if (StringUtils.isNotEmpty(shopCommodity.getStatus())) {
            queryWrapper.lambda().eq(ShopCommodity::getStatus, shopCommodity.getStatus());
        }
        return queryWrapper;
    }

    /**
     * 新增铺货关系
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存铺货关系
     */
    @RequiresPermissions("oms:commodity:add")
    @Log(title = "铺货关系", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ShopCommodity shopCommodity) {
        if (StringUtils.isNotEmpty(shopCommodity.getShop())) {
            QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("gco", shopCommodity.getShop());
            Shop shop = shopService.getOne(queryWrapper);
            if (shop != null) {
                //设置货主
                shopCommodity.setOwner(shop.getOwner());
            }
        }
        return toAjax(shopCommodityService.save(shopCommodity));
    }

    /**
     * 修改铺货关系
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ShopCommodity shopCommodity = shopCommodityService.getById(id);
        mmap.put("shopCommodity", shopCommodity);
        return prefix + "/edit";
    }

    /**
     * 修改保存铺货关系
     */
    @RequiresPermissions("oms:commodity:edit")
    @Log(title = "铺货关系", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ShopCommodity shopCommodity) {
        if (StringUtils.isNotEmpty(shopCommodity.getShop())) {
            QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Shop::getGco, shopCommodity.getShop());
            Shop shop = shopService.getOne(queryWrapper);
            if (shop != null) {
                //设置货主
                shopCommodity.setOwner(shop.getOwner());
            }
        }
        return toAjax(shopCommodityService.updateById(shopCommodity));
    }

    /**
     * 删除铺货关系
     */
    @RequiresPermissions("oms:commodity:remove")
    @Log(title = "铺货关系", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(shopCommodityService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    @RequiresPermissions("oms:commodity:add")
    @Log(title = "铺货关系", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<ShopCommodity> util = new ExcelUtil<>(ShopCommodity.class);
        List<ShopCommodity> shopCommodities = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        CommonResponse<String> response = shopCommodityService.importShopCommodity(shopCommodities, updateSupport, operName);
        if (Constants.SUCCESS.equals(response.getCode())) {
            return AjaxResult.success(response.getMsg());
        }
        return AjaxResult.error(response.getMsg());
    }

    @RequiresPermissions("oms:commodity:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<ShopCommodity> util = new ExcelUtil<>(ShopCommodity.class);
        return util.importTemplateExcel("铺货关系数据");
    }

}
