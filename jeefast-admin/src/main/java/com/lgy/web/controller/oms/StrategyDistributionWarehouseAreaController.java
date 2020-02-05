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
import com.lgy.oms.domain.StrategyDistributionWarehouseArea;
import com.lgy.oms.service.IStrategyDistributionWarehouseAreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
    private IStrategyDistributionWarehouseAreaService strategyDistributionWarehouseAreaService;

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
            queryWrapper.like("gco", strategyDistributionWarehouseArea.getGco());
        }
        startPage();
        return getDataTable(strategyDistributionWarehouseAreaService.list(queryWrapper));
    }

    /**
     * 导出配货策略分仓覆盖区域规则列表
     */
    @RequiresPermissions("oms:rule:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StrategyDistributionWarehouseArea strategyDistributionWarehouseArea) {
        List<StrategyDistributionWarehouseArea> list = strategyDistributionWarehouseAreaService.list(new QueryWrapper<>());
        ExcelUtil<StrategyDistributionWarehouseArea> util = new ExcelUtil<StrategyDistributionWarehouseArea>(StrategyDistributionWarehouseArea.class);
        return util.exportExcel(list, "area");
    }

    /**
     * 新增配货策略分仓覆盖区域规则
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存配货策略分仓覆盖区域规则
     */
    @RequiresPermissions("oms:rule:add")
    @Log(title = "配货策略分仓覆盖区域规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyDistributionWarehouseArea strategyDistributionWarehouseArea) {
        return toAjax(strategyDistributionWarehouseAreaService.save(strategyDistributionWarehouseArea));
    }

    /**
     * 修改配货策略分仓覆盖区域规则
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyDistributionWarehouseArea strategyDistributionWarehouseArea = strategyDistributionWarehouseAreaService.getById(id);
        mmap.put("strategyDistributionWarehouseArea", strategyDistributionWarehouseArea);
        return prefix + "/edit";
    }

    /**
     * 修改保存配货策略分仓覆盖区域规则
     */
    @RequiresPermissions("oms:rule:edit")
    @Log(title = "配货策略分仓覆盖区域规则", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StrategyDistributionWarehouseArea strategyDistributionWarehouseArea) {
        return toAjax(strategyDistributionWarehouseAreaService.updateById(strategyDistributionWarehouseArea));
    }

    /**
     * 删除配货策略分仓覆盖区域规则
     */
    @RequiresPermissions("oms:rule:remove")
    @Log(title = "配货策略分仓覆盖区域规则", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(strategyDistributionWarehouseAreaService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
