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
import com.lgy.oms.domain.StrategyDistributionWarehouseSpecial;
import com.lgy.oms.enums.order.OrderTableEnum;
import com.lgy.oms.enums.strategy.ConditionEnum;
import com.lgy.oms.service.IStrategyDistributionWarehouseAvailableService;
import com.lgy.oms.service.IStrategyDistributionWarehouseSpecialService;
import com.lgy.oms.util.OrderFieldUtils;
import com.lgy.system.domain.vo.Config;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 配货策略特定信息分仓规则Controller
 *
 * @author lgy
 * @date 2020-02-10
 */
@Controller
@RequestMapping("/oms/strategy/distribution/warehouse/special")
public class StrategyDistributionWarehouseSpecialController extends BaseController {

    private String prefix = "oms/strategy/distribution/warehouse/special";

    @Autowired
    private IStrategyDistributionWarehouseSpecialService distributionWarehouseSpecialService;

    @Autowired
    IStrategyDistributionWarehouseAvailableService availableService;

    @Autowired
    IWarehouseService warehouseService;

    @RequiresPermissions("oms:rule:view")
    @GetMapping("/{gco}")
    public String special(@PathVariable("gco") String gco, ModelMap mmap) {
        //策略编码
        mmap.put("gco", gco);
        //订单表类型字段
        mmap.put("orderTableEnum", OrderTableEnum.getList());
        //订单全字段
        mmap.put("orderFullFieldEnum", OrderFieldUtils.getFullFieldList());
        //判断条件
        mmap.put("conditionEnum", ConditionEnum.getList());
        return prefix + "/special";
    }

    /**
     * 查询配规则列表
     */
    @RequiresPermissions("oms:rule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyDistributionWarehouseSpecial entity) {
        QueryWrapper<StrategyDistributionWarehouseSpecial> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(entity.getGco())) {
            queryWrapper.eq("gco", entity.getGco());
        }
        if (StringUtils.isNotEmpty(entity.getWarehouse())) {
            queryWrapper.eq("warehouse", entity.getWarehouse());
        }

        startPage();
        return getDataTable(distributionWarehouseSpecialService.list(queryWrapper));
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
        List<StrategyDistributionWarehouseSpecial> list = JSON.parseArray(data, StrategyDistributionWarehouseSpecial.class);
        for (int i = 0; i < list.size(); i++) {
            //调整优先级顺序
            distributionWarehouseSpecialService.updatePrePriority(list.get(i).getId(), i);
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
        return toAjax(distributionWarehouseSpecialService.changeField(id, field, value));
    }

    /**
     * 打开新增页面
     */
    @GetMapping("/add/{gco}")
    public String add(@PathVariable("gco") String gco, ModelMap mmap) {
        mmap.put("gco", gco);
        mmap.put("orderTableEnum", OrderTableEnum.getList());
        mmap.put("conditionEnum", ConditionEnum.getList());
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
     * 新增规则
     */
    @RequiresPermissions("oms:rule:add")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyDistributionWarehouseSpecial strategyDistributionWarehouseSpecial) {
        return toAjax(distributionWarehouseSpecialService.save(strategyDistributionWarehouseSpecial));
    }


    /**
     * 删除规则
     */
    @RequiresPermissions("oms:rule:remove")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(distributionWarehouseSpecialService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 选择表字段,联动事件
     */
    @PostMapping("/selectField")
    @ResponseBody
    public AjaxResult selectField(Integer code) {

        List<Config> selected = OrderTableEnum.getSelected(code);

        return AjaxResult.success(selected);
    }

}
