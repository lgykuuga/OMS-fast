package com.lgy.web.controller.base;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.lgy.common.annotation.Log;
import com.lgy.common.enums.BusinessType;
import com.lgy.base.domain.Warehouse;
import com.lgy.base.service.IWarehouseService;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.common.core.text.Convert;
import com.lgy.common.core.page.TableDataInfo;

/**
 * 仓库信息Controller
 *
 * @author lgy
 * @date 2019-10-09
 */
@Controller
@RequestMapping("/base/warehouse")
public class WarehouseController extends BaseController {
    private String prefix = "base/warehouse";

    @Autowired
    private IWarehouseService warehouseService;

    @RequiresPermissions("base:warehouse:view")
    @GetMapping()
    public String warehouse() {
        return prefix + "/warehouse";
    }

    /**
     * 查询仓库信息列表
     */
    @RequiresPermissions("base:warehouse:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Warehouse warehouse) {
        QueryWrapper<Warehouse> queryWrapper = getWarehouseQueryWrapper(warehouse);
        startPage();
        return getDataTable(warehouseService.list(queryWrapper));
    }

    /**
     * 导出仓库信息列表
     */
    @RequiresPermissions("base:warehouse:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Warehouse warehouse) {
        QueryWrapper<Warehouse> queryWrapper = getWarehouseQueryWrapper(warehouse);
        List<Warehouse> list = warehouseService.list(queryWrapper);
        ExcelUtil<Warehouse> util = new ExcelUtil<>(Warehouse.class);
        return util.exportExcel(list, "warehouse");
    }

    private QueryWrapper<Warehouse> getWarehouseQueryWrapper(Warehouse warehouse) {
        QueryWrapper<Warehouse> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(warehouse.getGco())) {
            queryWrapper.eq("gco", warehouse.getGco());
        }
        if (StringUtils.isNotEmpty(warehouse.getGna())) {
            queryWrapper.eq("gna", warehouse.getGna());
        }
        if (StringUtils.isNotEmpty(warehouse.getStatus())) {
            queryWrapper.eq("status", warehouse.getStatus());
        }
        return queryWrapper;
    }

    /**
     * 新增仓库信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存仓库信息
     */
    @RequiresPermissions("base:warehouse:add")
    @Log(title = "仓库信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Warehouse warehouse) {
        return toAjax(warehouseService.save(warehouse));
    }

    /**
     * 修改仓库信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Warehouse warehouse = warehouseService.getById(id);
        mmap.put("warehouse", warehouse);
        return prefix + "/edit";
    }

    /**
     * 修改保存仓库信息
     */
    @RequiresPermissions("base:warehouse:edit")
    @Log(title = "仓库信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Warehouse warehouse) {
        return toAjax(warehouseService.updateById(warehouse));
    }

    /**
     * 删除仓库信息
     */
    @RequiresPermissions("base:warehouse:remove")
    @Log(title = "仓库信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(warehouseService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
