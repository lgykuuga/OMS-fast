package com.lgy.web.controller.oms;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.StrategyAuditCombo;
import com.lgy.oms.domain.StrategyAuditComboDetail;
import com.lgy.oms.enums.order.OrderTableEnum;
import com.lgy.oms.enums.strategy.AuditAmountEnum;
import com.lgy.oms.enums.strategy.ConditionEnum;
import com.lgy.oms.service.IStrategyAuditComboDetailService;
import com.lgy.oms.service.IStrategyAuditComboService;
import com.lgy.oms.util.OrderFieldUtils;
import com.lgy.system.domain.vo.Config;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 审单策略组合信息拦截Controller
 *
 * @author lgy
 * @date 2020-01-20
 */
@Controller
@RequestMapping("/oms/audit/combo")
public class StrategyAuditComboController extends BaseController {

    private String prefix = "oms/audit/combo";

    @Autowired
    IStrategyAuditComboService strategyAuditComboService;
    @Autowired
    IStrategyAuditComboDetailService strategyAuditComboDetailService;

    @RequiresPermissions("oms:audit:view")
    @GetMapping("/{gco}")
    public String combo(@PathVariable("gco") String gco,  ModelMap mmap) {
        //表
        mmap.put("orderTableEnum", OrderTableEnum.getList());
        //判断条件
        mmap.put("conditionEnum", ConditionEnum.getList());
        return prefix + "/auditCombo";
    }

    /**
     * 查询审单策略组合信息拦截列表
     */
    @RequiresPermissions("oms:audit:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyAuditCombo strategyAuditCombo) {
        QueryWrapper<StrategyAuditCombo> queryWrapper = getQueryWrapper(strategyAuditCombo);
        startPage();
        return getDataTable(strategyAuditComboService.list(queryWrapper));
    }

    private QueryWrapper<StrategyAuditCombo> getQueryWrapper(StrategyAuditCombo strategyAuditCombo) {
        QueryWrapper<StrategyAuditCombo> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(strategyAuditCombo.getGco())) {
            queryWrapper.eq("gco", strategyAuditCombo.getGco());
        }
        if (StringUtils.isNotEmpty(strategyAuditCombo.getGna())) {
            queryWrapper.like("gna", strategyAuditCombo.getGna());
        }
        return queryWrapper;
    }

    /**
     * 新增审单策略
     */
    @GetMapping("/add/{gco}")
    public String add(@PathVariable("gco") String gco, ModelMap mmap) {
        mmap.put("gco", gco);
        return prefix + "/add";
    }

    /**
     * 新增保存审单策略组合信息拦截
     */
    @RequiresPermissions("oms:audit:add")
    @Log(title = "审单策略组合信息拦截", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyAuditCombo strategyAuditCombo) {
        return toAjax(strategyAuditComboService.save(strategyAuditCombo));
    }

    /**
     * 修改审单策略组合信息拦截
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyAuditCombo strategyAuditCombo = strategyAuditComboService.getById(id);
        mmap.put("strategyAuditCombo", strategyAuditCombo);
        return prefix + "/edit";
    }

    /**
     * 修改保存审单策略组合信息拦截
     */
    @RequiresPermissions("oms:audit:edit")
    @Log(title = "审单策略组合信息拦截", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StrategyAuditCombo strategyAuditCombo) {
        return toAjax(strategyAuditComboService.updateById(strategyAuditCombo));
    }

    /**
     * 删除审单策略组合信息拦截
     */
    @RequiresPermissions("oms:audit:remove")
    @Log(title = "审单策略组合信息拦截", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        List<String> idz = Arrays.asList(Convert.toStrArray(ids));
        for (String id : idz) {
            //删除明细
            strategyAuditComboDetailService.deleteByComboId(id);
        }
        return toAjax(strategyAuditComboService.removeByIds(idz));
    }

    /**
     * 审单策略组合信息排序
     */
    @RequiresPermissions("oms:audit:edit")
    @Log(title = "审单策略组合信息排序", businessType = BusinessType.UPDATE)
    @PostMapping("/sortCombo")
    @ResponseBody
    public AjaxResult sortCombo(String data) {
        if (StringUtils.isEmpty(data)) {
            return AjaxResult.error("");
        }
        List<StrategyAuditCombo> list = JSON.parseArray(data, StrategyAuditCombo.class);
        for (int i = 0; i < list.size(); i++) {
            //调整组合信息拦截优先级顺序
            strategyAuditComboService.updateComboPriority(list.get(i).getId(), i);
        }
        return toAjax(true);
    }

    /**
     * 查询审单策略组合信息明细
     */
    @PostMapping("/detailList")
    @ResponseBody
    public TableDataInfo detailList(Long comboId) {
        List<StrategyAuditComboDetail> detailList = new ArrayList<>();
        if (comboId != null) {
            detailList = strategyAuditComboDetailService.getDetailByComboId(comboId);
            startPage();
            return getDataTable(detailList);
        }
        return getDataTable(detailList);
    }

    /**
     * 新增审单明细策略
     */
    @GetMapping("/addDetail/{gco}/{comboId}")
    public String addDetail(@PathVariable("gco") String gco,
                            @PathVariable("comboId") String comboId, ModelMap mmap) {
        mmap.put("gco", gco);
        mmap.put("comboId", comboId);

        mmap.put("orderTableEnum", OrderTableEnum.getList());
        mmap.put("conditionEnum", ConditionEnum.getList());

        mmap.put("orderMainField", OrderFieldUtils.getOrderMainList());
        mmap.put("buyerInfoField", OrderFieldUtils.getBuyerInfoList());
        mmap.put("payInfoField", OrderFieldUtils.getPayInfoList());
        mmap.put("typeInfoField", OrderFieldUtils.getTypeInfoList());
        mmap.put("statusInfoField", OrderFieldUtils.getStatusInfoList());
        mmap.put("interceptField", OrderFieldUtils.getInterceptList());
        mmap.put("detailField", OrderFieldUtils.getDetailList());
        return prefix + "/addDetail";
    }

    /**
     * 新增保存审单策略组合明细信息拦截
     */
    @RequiresPermissions("oms:audit:add")
    @Log(title = "审单策略组合明细信息拦截", businessType = BusinessType.INSERT)
    @PostMapping("/addDetail")
    @ResponseBody
    public AjaxResult addSave(StrategyAuditComboDetail detail) {
        return toAjax(strategyAuditComboDetailService.save(detail));
    }

    /**
     * 修改审单策略组合信息拦截
     */
    @GetMapping("/editDetail/{id}")
    public String editDetail(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyAuditComboDetail detail = strategyAuditComboDetailService.getById(id);
        mmap.put("strategyAuditComboDetail", detail);

        mmap.put("orderTableEnum", OrderTableEnum.getList());
        mmap.put("conditionEnum", ConditionEnum.getList());

        mmap.put("orderMainField", OrderFieldUtils.getOrderMainList());
        mmap.put("buyerInfoField", OrderFieldUtils.getBuyerInfoList());
        mmap.put("payInfoField", OrderFieldUtils.getPayInfoList());
        mmap.put("typeInfoField", OrderFieldUtils.getTypeInfoList());
        mmap.put("statusInfoField", OrderFieldUtils.getStatusInfoList());
        mmap.put("interceptField", OrderFieldUtils.getInterceptList());
        mmap.put("detailField", OrderFieldUtils.getDetailList());
        return prefix + "/editDetail";
    }

    /**
     * 修改保存审单策略组合信息拦截
     */
    @RequiresPermissions("oms:audit:edit")
    @Log(title = "审单策略组合明细信息拦截", businessType = BusinessType.UPDATE)
    @PostMapping("/editDetail")
    @ResponseBody
    public AjaxResult editSave(StrategyAuditComboDetail detail) {
        return toAjax(strategyAuditComboDetailService.updateById(detail));
    }

    /**
     * 删除审单策略组合信息拦截
     */
    @RequiresPermissions("oms:audit:remove")
    @Log(title = "审单策略组合明细信息拦截", businessType = BusinessType.DELETE)
    @PostMapping("/removeDetail")
    @ResponseBody
    public AjaxResult removeDetail(String ids) {
        return toAjax(strategyAuditComboDetailService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 删除审单策略组合信息拦截
     */
    @PostMapping("/selectField")
    @ResponseBody
    public AjaxResult selectField(Integer code) {

        List<Config> selected = OrderTableEnum.getSelected(code);

        return AjaxResult.success(selected);
    }



}
