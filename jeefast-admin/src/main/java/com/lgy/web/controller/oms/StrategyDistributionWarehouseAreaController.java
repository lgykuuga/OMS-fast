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
import com.lgy.oms.domain.StrategyDistributionWarehouseArea;
import com.lgy.oms.domain.StrategyDistributionWarehouseAvailable;
import com.lgy.oms.service.IStrategyDistributionWarehouseAreaService;
import com.lgy.oms.service.IStrategyDistributionWarehouseAvailableService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 配货策略分仓覆盖区域规则Controller
 *
 * @author lgy
 * @date 2020-02-04
 */
@Controller
@RequestMapping("/oms/strategy/distribution/warehouse/area")
public class StrategyDistributionWarehouseAreaController extends BaseController {

    private String prefix = "oms/strategy/distribution/warehouse/area";

    @Autowired
    IStrategyDistributionWarehouseAreaService strategyDistributionWarehouseAreaService;

    @Autowired
    IStrategyDistributionWarehouseAvailableService availableService;

    @Autowired
    IWarehouseService warehouseService;

    @RequiresPermissions("oms:rule:view")
    @GetMapping("/{gco}")
    public String area(@PathVariable("gco") String gco, ModelMap mmap) {
        //策略编码
        mmap.put("gco", gco);
        return prefix + "/area";
    }

    /**
     * 查询配货策略分仓覆盖区域规则列表
     */
    @RequiresPermissions("oms:rule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyDistributionWarehouseArea strategyDistributionWarehouseArea) {
        QueryWrapper<StrategyDistributionWarehouseArea> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(strategyDistributionWarehouseArea.getGco())) {
            queryWrapper.eq("gco", strategyDistributionWarehouseArea.getGco());
        }
        if (StringUtils.isNotEmpty(strategyDistributionWarehouseArea.getWarehouse())) {
            queryWrapper.eq("warehouse", strategyDistributionWarehouseArea.getWarehouse());
        }
        if (strategyDistributionWarehouseArea.getArrive() != null) {
            queryWrapper.eq("arrive", strategyDistributionWarehouseArea.getArrive());
        }
        startPage();
        return getDataTable(strategyDistributionWarehouseAreaService.list(queryWrapper));
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
        List<StrategyDistributionWarehouseArea> list = JSON.parseArray(data, StrategyDistributionWarehouseArea.class);
        for (int i = 0; i < list.size(); i++) {
            //调整优先级顺序
            strategyDistributionWarehouseAreaService.updatePrePriority(list.get(i).getId(), i);
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
        return toAjax(strategyDistributionWarehouseAreaService.changeField(id, field, value));
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
     * 新增保存配货策略分仓覆盖区域规则
     */
    @RequiresPermissions("oms:rule:add")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyDistributionWarehouseArea strategyDistributionWarehouseArea) {
        return toAjax(strategyDistributionWarehouseAreaService.save(strategyDistributionWarehouseArea));
    }


    /**
     * 删除配货策略分仓覆盖区域规则
     */
    @RequiresPermissions("oms:rule:remove")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(strategyDistributionWarehouseAreaService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
