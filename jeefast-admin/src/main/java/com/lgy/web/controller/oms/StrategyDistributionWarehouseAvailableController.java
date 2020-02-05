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
import com.lgy.oms.domain.StrategyDistributionPre;
import com.lgy.oms.domain.StrategyDistributionWarehouseAvailable;
import com.lgy.oms.enums.strategy.DistributionLockModelEnum;
import com.lgy.oms.service.IStrategyDistributionWarehouseAvailableService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 配货策略分仓规则列Controller
 *
 * @author lgy
 * @date 2020-02-04
 */
@Controller
@RequestMapping("/oms/strategy/distribution/warehouse/available")
public class StrategyDistributionWarehouseAvailableController extends BaseController {

    private String prefix = "oms/strategy/distribution/warehouse/available";

    @Autowired
    IStrategyDistributionWarehouseAvailableService distributionWarehouseAvailableService;

    @RequiresPermissions("oms:rule:view")
    @GetMapping("/{gco}")
    public String available(@PathVariable("gco") String gco, ModelMap mmap) {
        //策略编码
        mmap.put("gco", gco);
        return prefix + "/available";
    }

    /**
     * 查询配货策略分仓规则列列表
     */
    @RequiresPermissions("oms:rule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyDistributionWarehouseAvailable strategyDistributionWarehouseAvailable) {
        QueryWrapper<StrategyDistributionWarehouseAvailable> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(strategyDistributionWarehouseAvailable.getGco())) {
            queryWrapper.eq("gco", strategyDistributionWarehouseAvailable.getGco());
        }
        if (StringUtils.isNotEmpty(strategyDistributionWarehouseAvailable.getWarehouse())) {
            queryWrapper.eq("warehouse", strategyDistributionWarehouseAvailable.getWarehouse());
        }
        startPage();
        return getDataTable(distributionWarehouseAvailableService.list(queryWrapper));
    }

    /**
     * 规则排序
     */
    @RequiresPermissions("oms:distribution:edit")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.UPDATE)
    @PostMapping("/sort")
    @ResponseBody
    public AjaxResult sort(String data) {
        if (StringUtils.isEmpty(data)) {
            return AjaxResult.error("传入对象为空");
        }
        List<StrategyDistributionWarehouseAvailable> list = JSON.parseArray(data, StrategyDistributionWarehouseAvailable.class);
        for (int i = 0; i < list.size(); i++) {
            //调整优先级顺序
            distributionWarehouseAvailableService.updatePrePriority(list.get(i).getId(), i);
        }
        return toAjax(true);
    }

    /**
     * 开启关闭
     */
    @PostMapping("/changeField")
    @ResponseBody
    @Log(title = "配货策略分仓规则", businessType = BusinessType.UPDATE)
    public AjaxResult changeField(Long id, String field, int value) {
        return toAjax(distributionWarehouseAvailableService.changeField(id, field, value));
    }

    /**
     * 打开新增页面
     */
    @GetMapping("/add/{gco}")
    public String add(@PathVariable("gco") String gco, ModelMap mmap) {
        mmap.put("gco", gco);
        return prefix + "/add";
    }

    /**
     * 新增配货策略分仓可用仓库规则
     */
    @RequiresPermissions("oms:distribution:add")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyDistributionWarehouseAvailable entity) {
        return toAjax(distributionWarehouseAvailableService.save(entity));
    }

    /**
     * 删除
     */
    @RequiresPermissions("oms:distribution:remove")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        List<String> idz = Arrays.asList(Convert.toStrArray(ids));
        return toAjax(distributionWarehouseAvailableService.removeByIds(idz));
    }



}
