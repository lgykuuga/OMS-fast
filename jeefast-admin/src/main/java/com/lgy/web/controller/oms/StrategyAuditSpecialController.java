package com.lgy.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.oms.domain.StrategyAuditSpecial;
import com.lgy.oms.enums.strategy.AuditSpecialEnum;
import com.lgy.oms.service.IStrategyAuditSpecialService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 审单策略特定信息拦截Controller
 *
 * @author lgy
 * @date 2019-12-31
 */
@Controller
@RequestMapping("/oms/audit/special")
public class StrategyAuditSpecialController extends BaseController {

    private String prefix = "oms/audit/special";

    @Autowired
    private IStrategyAuditSpecialService strategyAuditSpecialService;

    @RequiresPermissions("oms:special:view")
    @GetMapping("/{gco}")
    public String special(@PathVariable("gco") String gco, ModelMap mmap) {
        mmap.put("strategyAuditSpecialList", AuditSpecialEnum.getList());
        return prefix + "/special";
    }

    /**
     * 查询审单策略特定信息拦截列表
     */
    @RequiresPermissions("oms:special:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyAuditSpecial strategyAuditSpecial) {
        QueryWrapper<StrategyAuditSpecial> queryWrapper = getStrategyAuditSpecialQueryWrapper(strategyAuditSpecial);
        startPage();
        return getDataTable(strategyAuditSpecialService.list(queryWrapper));
    }

    /**
     * 导出审单策略特定信息拦截列表
     */
    @RequiresPermissions("oms:special:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StrategyAuditSpecial strategyAuditSpecial) {
        QueryWrapper<StrategyAuditSpecial> queryWrapper = getStrategyAuditSpecialQueryWrapper(strategyAuditSpecial);
        List<StrategyAuditSpecial> list = strategyAuditSpecialService.list(queryWrapper);
        ExcelUtil<StrategyAuditSpecial> util = new ExcelUtil<StrategyAuditSpecial>(StrategyAuditSpecial.class);
        return util.exportExcel(list, "special");
    }

    /**
     * 查询条件
     *
     * @param strategyAuditSpecial
     * @return
     */
    private QueryWrapper<StrategyAuditSpecial> getStrategyAuditSpecialQueryWrapper(StrategyAuditSpecial strategyAuditSpecial) {
        QueryWrapper<StrategyAuditSpecial> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(strategyAuditSpecial.getGco())) {
            queryWrapper.eq("gco", strategyAuditSpecial.getGco());
        }
        if (strategyAuditSpecial.getType() != null) {
            queryWrapper.eq("type", strategyAuditSpecial.getType());
        }
        if (StringUtils.isNotEmpty(strategyAuditSpecial.getValue())) {
            queryWrapper.like("value", strategyAuditSpecial.getValue());
        }
        return queryWrapper;
    }

    /**
     * 新增审单策略特定信息拦截
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存审单策略特定信息拦截
     */
    @RequiresPermissions("oms:special:add")
    @Log(title = "审单策略特定信息拦截", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyAuditSpecial strategyAuditSpecial) {
        return toAjax(strategyAuditSpecialService.save(strategyAuditSpecial));
    }

    /**
     * 修改审单策略特定信息拦截
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyAuditSpecial strategyAuditSpecial = strategyAuditSpecialService.getById(id);
        mmap.put("strategyAuditSpecial", strategyAuditSpecial);
        return prefix + "/edit";
    }

    /**
     * 修改保存审单策略特定信息拦截
     */
    @RequiresPermissions("oms:special:edit")
    @Log(title = "审单策略特定信息拦截", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StrategyAuditSpecial strategyAuditSpecial) {
        return toAjax(strategyAuditSpecialService.updateById(strategyAuditSpecial));
    }

    /**
     * 删除审单策略特定信息拦截
     */
    @RequiresPermissions("oms:special:remove")
    @Log(title = "审单策略特定信息拦截", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(strategyAuditSpecialService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
