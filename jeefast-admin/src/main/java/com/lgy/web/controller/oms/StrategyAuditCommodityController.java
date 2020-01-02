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
import com.lgy.oms.domain.StrategyAuditCommodity;
import com.lgy.oms.enums.strategy.AuditCommodityEnum;
import com.lgy.oms.service.IStrategyAuditCommodityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 审单策略指定商品拦截Controller
 *
 * @author lgy
 * @date 2019-12-31
 */
@Controller
@RequestMapping("/oms/audit/commodity")
public class StrategyAuditCommodityController extends BaseController {
    private String prefix = "oms/audit/commodity";

    @Autowired
    private IStrategyAuditCommodityService strategyAuditCommodityService;

    @RequiresPermissions("oms:commodity:view")
    @GetMapping("/{gco}")
    public String commodity(@PathVariable("gco") String gco, ModelMap mmap) {
        mmap.put("strategyAuditCommodityList", AuditCommodityEnum.getList());
        return prefix + "/auditCommodity";
    }

    /**
     * 查询审单策略指定商品拦截列表
     */
    @RequiresPermissions("oms:commodity:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyAuditCommodity strategyAuditCommodity) {
        QueryWrapper<StrategyAuditCommodity> queryWrapper = getStrategyAuditCommodityQueryWrapper(strategyAuditCommodity);
        startPage();
        return getDataTable(strategyAuditCommodityService.list(queryWrapper));
    }

    /**
     * 导出审单策略指定商品拦截列表
     */
    @RequiresPermissions("oms:commodity:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StrategyAuditCommodity strategyAuditCommodity) {
        QueryWrapper<StrategyAuditCommodity> queryWrapper = getStrategyAuditCommodityQueryWrapper(strategyAuditCommodity);
        List<StrategyAuditCommodity> list = strategyAuditCommodityService.list(queryWrapper);
        ExcelUtil<StrategyAuditCommodity> util = new ExcelUtil<StrategyAuditCommodity>(StrategyAuditCommodity.class);
        return util.exportExcel(list, "commodity");
    }

    /**
     * 查询条件
     *
     * @param strategyAuditCommodity
     * @return
     */
    private QueryWrapper<StrategyAuditCommodity> getStrategyAuditCommodityQueryWrapper(StrategyAuditCommodity strategyAuditCommodity) {
        QueryWrapper<StrategyAuditCommodity> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(strategyAuditCommodity.getGco())) {
            queryWrapper.eq("gco", strategyAuditCommodity.getGco());
        }
        if (strategyAuditCommodity.getType() != null) {
            queryWrapper.eq("type", strategyAuditCommodity.getType());
        }
        if (StringUtils.isNotEmpty(strategyAuditCommodity.getValue())) {
            queryWrapper.like("value", strategyAuditCommodity.getValue());
        }
        return queryWrapper;
    }

    /**
     * 新增审单策略指定商品拦截
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("strategyAuditCommodityList", AuditCommodityEnum.getList());
        return prefix + "/add";
    }

    /**
     * 新增保存审单策略指定商品拦截
     */
    @RequiresPermissions("oms:commodity:add")
    @Log(title = "审单策略指定商品拦截", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyAuditCommodity strategyAuditCommodity) {
        return toAjax(strategyAuditCommodityService.save(strategyAuditCommodity));
    }

    /**
     * 修改审单策略指定商品拦截
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyAuditCommodity strategyAuditCommodity = strategyAuditCommodityService.getById(id);
        mmap.put("strategyAuditCommodityList", AuditCommodityEnum.getList());
        mmap.put("strategyAuditCommodity", strategyAuditCommodity);
        return prefix + "/edit";
    }

    /**
     * 修改保存审单策略指定商品拦截
     */
    @RequiresPermissions("oms:commodity:edit")
    @Log(title = "审单策略指定商品拦截", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StrategyAuditCommodity strategyAuditCommodity) {
        return toAjax(strategyAuditCommodityService.updateById(strategyAuditCommodity));
    }

    /**
     * 删除审单策略指定商品拦截
     */
    @RequiresPermissions("oms:commodity:remove")
    @Log(title = "审单策略指定商品拦截", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(strategyAuditCommodityService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
