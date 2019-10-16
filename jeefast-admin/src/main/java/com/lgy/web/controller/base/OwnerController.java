package com.lgy.web.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.base.domain.Owner;
import com.lgy.base.service.IOwnerService;
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
 * 货主档案Controller
 *
 * @author lgy
 * @date 2019-10-11
 */
@Controller
@RequestMapping("/base/owner")
public class OwnerController extends BaseController {
    private String prefix = "base/owner";

    @Autowired
    private IOwnerService ownerService;

    @RequiresPermissions("base:owner:view")
    @GetMapping()
    public String owner() {
        return prefix + "/owner";
    }

    /**
     * 查询货主档案列表
     */
    @RequiresPermissions("base:owner:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Owner owner) {
        QueryWrapper<Owner> queryWrapper = getOwnerQueryWrapper(owner);
        startPage();
        return getDataTable(ownerService.list(queryWrapper));
    }

    /**
     * 导出货主档案列表
     */
    @RequiresPermissions("base:owner:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Owner owner) {
        QueryWrapper<Owner> queryWrapper = getOwnerQueryWrapper(owner);
        List<Owner> list = ownerService.list(queryWrapper);
        ExcelUtil<Owner> util = new ExcelUtil<>(Owner.class);
        return util.exportExcel(list, "owner");
    }

    /**
     * 需要根据页面查询条件进行组装
     * @param owner 货主查询条件
     * @return
     */
    private QueryWrapper<Owner> getOwnerQueryWrapper(Owner owner) {
        QueryWrapper<Owner> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(owner.getGco())) {
            queryWrapper.eq("gco", owner.getGco());
        }
        if (StringUtils.isNotEmpty(owner.getGna())) {
            queryWrapper.eq("gna", owner.getGna());
        }
        if (StringUtils.isNotEmpty(owner.getStatus())) {
            queryWrapper.eq("status", owner.getStatus());
        }
        return queryWrapper;
    }

    /**
     * 新增货主档案
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存货主档案
     */
    @RequiresPermissions("base:owner:add")
    @Log(title = "货主档案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Owner owner) {
        return toAjax(ownerService.save(owner));
    }

    /**
     * 修改货主档案
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Owner owner = ownerService.getById(id);
        mmap.put("owner", owner);
        return prefix + "/edit";
    }

    /**
     * 修改保存货主档案
     */
    @RequiresPermissions("base:owner:edit")
    @Log(title = "货主档案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Owner owner) {
        return toAjax(ownerService.updateById(owner));
    }

    /**
     * 删除货主档案
     */
    @RequiresPermissions("base:owner:remove")
    @Log(title = "货主档案", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(ownerService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
