package com.lgy.web.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.base.domain.Shop;
import com.lgy.base.service.IShopService;
import com.lgy.common.annotation.Log;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 店铺档案Controller
 *
 * @author lgy
 * @date 2019-10-10
 */
@Controller
@RequestMapping("/base/shop")
public class ShopController extends BaseController {
    private String prefix = "base/shop";

    @Autowired
    IShopService shopService;

    @RequiresPermissions("base:shop:view")
    @GetMapping()
    public String shop() {
        return prefix + "/shop";
    }

    /**
     * 查询店铺档案列表
     */
    @RequiresPermissions("base:shop:list")
    @ResponseBody
    @PostMapping("list")
    public TableDataInfo list(Shop shop) {
        QueryWrapper<Shop> queryWrapper = getShopQueryWrapper(shop);
        startPage();
        return getDataTable(shopService.list(queryWrapper));
    }

    /**
     * 获取店铺档案列表下拉框
     */
    @GetMapping("/selectShop")
    @ResponseBody
    public AjaxResult selectShop() {
        AjaxResult ajax = new AjaxResult();
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Shop::getStatus, Constants.VALID)
                .select(Shop::getGco, Shop::getGna);
        List<Shop> shopList = shopService.list(queryWrapper);
        ajax.put("code", 200);
        ajax.put("value", shopList);
        return ajax;
    }

    /**
     * 导出店铺档案列表
     */
    @RequiresPermissions("base:shop:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Shop shop) {
        QueryWrapper<Shop> queryWrapper = getShopQueryWrapper(shop);
        List<Shop> list = shopService.list(queryWrapper);
        ExcelUtil<Shop> util = new ExcelUtil<>(Shop.class);
        return util.exportExcel(list, "shop");
    }

    /**
     * 要根据页面查询条件进行组装
     *
     * @param shop 店铺查询参数
     * @return
     */
    private QueryWrapper<Shop> getShopQueryWrapper(Shop shop) {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(shop.getGco())) {
            queryWrapper.lambda().eq(Shop::getGco, shop.getGco());
        }
        if (StringUtils.isNotEmpty(shop.getGna())) {
            queryWrapper.lambda().eq(Shop::getGna, shop.getGna());
        }
        if (StringUtils.isNotEmpty(shop.getPlatform())) {
            queryWrapper.lambda().eq(Shop::getPlatform, shop.getPlatform());
        }
        if (StringUtils.isNotEmpty(shop.getOwner())) {
            queryWrapper.lambda().eq(Shop::getOwner, shop.getOwner());
        }
        if (StringUtils.isNotEmpty(shop.getStatus())) {
            queryWrapper.lambda().eq(Shop::getStatus, shop.getStatus());
        }
        return queryWrapper;
    }

    /**
     * 新增店铺档案
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存店铺档案
     */
    @RequiresPermissions("base:shop:add")
    @Log(title = "店铺档案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Shop shop) {
        Shop add = shopService.add(shop);
        if (add != null) {
            return toAjax(true);
        }
        return toAjax(false);
    }

    /**
     * 修改店铺档案
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Shop shop = shopService.getById(id);
        mmap.put("shop", shop);
        return prefix + "/edit";
    }

    /**
     * 修改保存店铺档案
     */
    @RequiresPermissions("base:shop:edit")
    @Log(title = "店铺档案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Shop shop) {
        Shop update = shopService.update(shop);
        if (update != null) {
            return toAjax(true);
        }
        return toAjax(false);
    }

    /**
     * 删除店铺档案
     */
    @RequiresPermissions("base:shop:remove")
    @Log(title = "店铺档案", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(shopService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 根据编码删除店铺档案
     */
    @RequiresPermissions("base:shop:remove")
    @Log(title = "店铺档案", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    @ResponseBody
    public AjaxResult delete(String gcos) {

        String[] gcoList = Convert.toStrArray(gcos);

        boolean flag = true;

        for (String gco : gcoList) {
            flag = shopService.delete(gco);
        }

        return toAjax(flag);
    }

}
