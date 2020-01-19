package com.lgy.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.base.domain.Shop;
import com.lgy.base.service.IShopService;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.enums.strategy.DownloadOrderInterfaceEnum;
import com.lgy.oms.enums.strategy.DownloadOrderStrategyEnum;
import com.lgy.oms.service.IShopInterfacesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 店铺接口设置Controller
 *
 * @author lgy
 * @date 2019-10-11
 */
@Controller
@RequestMapping("/oms/shopInterfaces")
public class ShopInterfacesController extends BaseController {
    private String prefix = "oms/shopInterfaces";

    @Autowired
    private IShopInterfacesService shopInterfacesService;
    @Autowired
    private IShopService shopService;

    @RequiresPermissions("oms:shopInterfaces:view")
    @GetMapping()
    public String interfaces() {

        return prefix + "/shopInterfaces";
    }

    /**
     * 查询店铺接口设置列表
     */
    @RequiresPermissions("oms:shopInterfaces:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ShopInterfaces shopInterfaces) {
        QueryWrapper<ShopInterfaces> queryWrapper = getShopInterfacesQueryWrapper(shopInterfaces);
        startPage();
        return getDataTable(shopInterfacesService.list(queryWrapper));
    }

    /**
     * 查询店铺接口参数
     *
     * @param shopInterfaces 店铺接口参数
     * @return
     */
    private QueryWrapper<ShopInterfaces> getShopInterfacesQueryWrapper(ShopInterfaces shopInterfaces) {
        QueryWrapper<ShopInterfaces> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(shopInterfaces.getPlatform())) {
            queryWrapper.like("platform", shopInterfaces.getPlatform());
        }
        if (StringUtils.isNotEmpty(shopInterfaces.getAppk())) {
            queryWrapper.like("appk", shopInterfaces.getAppk());
        }
        if (StringUtils.isNotEmpty(shopInterfaces.getStatus())) {
            queryWrapper.like("status", shopInterfaces.getStatus());
        }
        return queryWrapper;
    }

    /**
     * 导出店铺接口设置列表
     */
    @RequiresPermissions("oms:shopInterfaces:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ShopInterfaces shopInterfaces) {
        QueryWrapper<ShopInterfaces> queryWrapper = getShopInterfacesQueryWrapper(shopInterfaces);
        List<ShopInterfaces> list = shopInterfacesService.list(queryWrapper);
        ExcelUtil<ShopInterfaces> util = new ExcelUtil<>(ShopInterfaces.class);
        return util.exportExcel(list, "shopInterfaces");
    }

    /**
     * 新增店铺接口设置
     */
    @GetMapping("/add")
    public String add(Model model) {
        //下载订单策略
        model.addAttribute("strategyList", DownloadOrderStrategyEnum.getList());
        //下单订单接口
        model.addAttribute("interfaceList", DownloadOrderInterfaceEnum.getList());
        //下单订单Json
        model.addAttribute("interfaceJson", DownloadOrderInterfaceEnum.toJson());
        return prefix + "/add";
    }

    /**
     * 新增保存店铺接口设置
     */
    @RequiresPermissions("oms:shopInterfaces:add")
    @Log(title = "店铺接口设置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ShopInterfaces shopInterfaces) {

        if (StringUtils.isEmpty(shopInterfaces.getShop())) {
            return error("店铺不能为空");
        }

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop", shopInterfaces.getShop());
        ShopInterfaces one = shopInterfacesService.getOne(queryWrapper);
        if (one != null) {
            return error("店铺已存在店铺接口,请选择编辑设置");
        }

        //设置店铺平台、货主
        QueryWrapper shopWrapper = new QueryWrapper<>();
        shopWrapper.eq("gco", shopInterfaces.getShop());
        Shop shop = shopService.getOne(shopWrapper);
        shopInterfaces.setPlatform(shop.getPlatform());
        shopInterfaces.setOwner(shop.getOwner());
        return toAjax(shopInterfacesService.save(shopInterfaces));
    }

    /**
     * 修改店铺接口设置
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("shopInterfaces", shopInterfacesService.getById(id));
        //下载订单策略
        mmap.put("strategyList", DownloadOrderStrategyEnum.getList());
        //下单订单接口
        mmap.put("interfaceList", DownloadOrderInterfaceEnum.getList());
        return prefix + "/edit";
    }

    /**
     * 修改保存店铺接口设置
     */
    @RequiresPermissions("oms:shopInterfaces:edit")
    @Log(title = "店铺接口设置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ShopInterfaces shopInterfaces) {
        return toAjax(shopInterfacesService.updateById(shopInterfaces));
    }

    /**
     * 删除店铺接口设置
     */
    @RequiresPermissions("oms:shopInterfaces:remove")
    @Log(title = "店铺接口设置", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(shopInterfacesService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
