package com.lgy.web.controller.oms;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.base.domain.Warehouse;
import com.lgy.base.service.IWarehouseService;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseAvailable;
import com.lgy.oms.domain.StrategyDistributionWarehouseLogistics;
import com.lgy.oms.service.IStrategyDistributionWarehouseAvailableService;
import com.lgy.oms.service.IStrategyDistributionWarehouseLogisticsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 配货策略分仓物流规则Controller
 *
 * @author lgy
 * @date 2020-02-04
 */
@Controller
@RequestMapping("/oms/strategy/distribution/warehouse/logistics")
public class StrategyDistributionWarehouseLogisticsController extends BaseController {

    private String prefix = "oms/strategy/distribution/warehouse/logistics";

    @Autowired
    private IStrategyDistributionWarehouseLogisticsService distributionWarehouseLogisticsService;

    @Autowired
    IStrategyDistributionWarehouseAvailableService availableService;

    @Autowired
    IWarehouseService warehouseService;

    @RequiresPermissions("oms:rule:view")
    @GetMapping("/{gco}")
    public String logistics(@PathVariable("gco") String gco, ModelMap mmap) {
        //策略编码
        mmap.put("gco", gco);
        return prefix + "/logistics";
    }

    /**
     * 查询配货策略分仓物流规则列表
     */
    @RequiresPermissions("oms:rule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyDistributionWarehouseLogistics entity) {
        QueryWrapper<StrategyDistributionWarehouseLogistics> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(entity.getGco())) {
            queryWrapper.eq("gco", entity.getGco());
        }
        if (StringUtils.isNotEmpty(entity.getWarehouse())) {
            queryWrapper.eq("warehouse", entity.getWarehouse());
        }
        if (StringUtils.isNotEmpty(entity.getLogistics())) {
            queryWrapper.eq("logistics", entity.getLogistics());
        }
        if (entity.getArrive() != null) {
            queryWrapper.eq("arrive", entity.getArrive());
        }
        startPage();
        return getDataTable(distributionWarehouseLogisticsService.list(queryWrapper));
    }

    /**
     * 规则排序
     */
    @RequiresPermissions("oms:rule:edit")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.UPDATE)
    @PostMapping("/sort")
    @ResponseBody
    public AjaxResult sort(String data) {
        if (StringUtils.isEmpty(data)) {
            return AjaxResult.error("传入对象为空");
        }
        List<StrategyDistributionWarehouseLogistics> list = JSON.parseArray(data, StrategyDistributionWarehouseLogistics.class);
        for (int i = 0; i < list.size(); i++) {
            //调整优先级顺序
            distributionWarehouseLogisticsService.updatePrePriority(list.get(i).getId(), i);
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
        return toAjax(distributionWarehouseLogisticsService.changeField(id, field, value));
    }

    /**
     * 打开新增页面
     */
    @GetMapping("/add/{gco}")
    public String add(@PathVariable("gco") String gco, ModelMap mmap) {
        mmap.put("gco", gco);
        //配货策略可用仓库列表
        List<StrategyDistributionWarehouseAvailable> availableList = availableService.getStrategyByGco(gco);

        if (StringUtils.isNotEmpty(availableList)) {
            List<String> warehouseList = availableList.stream().map(StrategyDistributionWarehouseAvailable::getWarehouse).collect(Collectors.toList());
            List<Warehouse> warehouses = warehouseService.selectWarehouse(warehouseList);
            mmap.put("warehouses", warehouses);
        }
        return prefix + "/add";
    }

    /**
     * 新增保存配货策略分仓物流规则
     */
    @RequiresPermissions("oms:rule:add")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyDistributionWarehouseLogistics strategyDistributionWarehouseLogistics) {
        return toAjax(distributionWarehouseLogisticsService.save(strategyDistributionWarehouseLogistics));
    }

    /**
     * 删除配货策略分仓物流规则
     */
    @RequiresPermissions("oms:rule:remove")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(distributionWarehouseLogisticsService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
