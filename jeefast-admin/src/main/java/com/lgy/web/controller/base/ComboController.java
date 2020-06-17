package com.lgy.web.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.base.domain.Combo;
import com.lgy.base.domain.Commodity;
import com.lgy.base.service.IComboService;
import com.lgy.base.service.ICommodityService;
import com.lgy.common.annotation.Log;
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
 * 组合商品Controller
 *
 * @author lgy
 * @date 2019-10-25
 */
@Controller
@RequestMapping("/base/combo")
public class ComboController extends BaseController {
    private String prefix = "base/combo";

    @Autowired
    private IComboService comboService;

    @Autowired
    private ICommodityService commodityService;

    @RequiresPermissions("base:combo:view")
    @GetMapping()
    public String combo() {
        return prefix + "/combo";
    }

    /**
     * 查询组合商品列表
     */
    @RequiresPermissions("base:combo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Combo combo) {
        QueryWrapper<Combo> queryWrapper = getComboQueryWrapper(combo);
        startPage();
        return getDataTable(comboService.list(queryWrapper));
    }

    /**
     * 导出组合商品列表
     */
    @RequiresPermissions("base:combo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Combo combo) {
        QueryWrapper<Combo> queryWrapper = getComboQueryWrapper(combo);
        List<Combo> list = comboService.list(queryWrapper);
        ExcelUtil<Combo> util = new ExcelUtil<>(Combo.class);
        return util.exportExcel(list, "combo");
    }

    private QueryWrapper<Combo> getComboQueryWrapper(Combo combo) {
        QueryWrapper<Combo> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(combo.getParent())) {
            queryWrapper.lambda().eq(Combo::getParent, combo.getParent());
        }
        if (StringUtils.isNotEmpty(combo.getChildren())) {
            queryWrapper.lambda().eq(Combo::getChildren, combo.getChildren());
        }
        if (StringUtils.isNotEmpty(combo.getStatus())) {
            queryWrapper.lambda().eq(Combo::getStatus, combo.getStatus());
        }
        return queryWrapper;
    }

    /**
     * 新增组合商品
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存组合商品
     */
    @RequiresPermissions("base:combo:add")
    @Log(title = "组合商品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Combo combo) {
        return toAjax(comboService.save(combo));
    }

    /**
     * 修改组合商品
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Combo combo = comboService.getById(id);
        mmap.put("combo", combo);
        return prefix + "/edit";
    }

    /**
     * 修改保存组合商品
     */
    @RequiresPermissions("base:combo:edit")
    @Log(title = "组合商品", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Combo combo) {
        return toAjax(comboService.updateById(combo));
    }

    /**
     * 删除组合商品
     */
    @RequiresPermissions("base:combo:remove")
    @Log(title = "组合商品", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(comboService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }


    /**
     * 获取商品档案信息
     */
    @GetMapping("/selectCommodityParent/{combo}")
    @ResponseBody
    public AjaxResult selectCommodityParent(@PathVariable("combo") String combo) {
        AjaxResult ajax = new AjaxResult();
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(combo)) {
            queryWrapper.lambda().eq(Commodity::getCombo, combo);
        }
        queryWrapper.lambda().select(Commodity::getId, Commodity::getGco, Commodity::getGna);
        List<Commodity> commodityList = commodityService.list(queryWrapper);
        ajax.put("code", 200);
        ajax.put("value", commodityList);
        return ajax;
    }

}
