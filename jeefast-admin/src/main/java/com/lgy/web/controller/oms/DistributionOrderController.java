package com.lgy.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.service.IDistributionOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 配货单信息Controller
 *
 * @author lgy
 * @date 2020-01-30
 */
@Controller
@RequestMapping("/oms/distribution")
public class DistributionOrderController extends BaseController {
    private String prefix = "oms/distribution";

    @Autowired
    private IDistributionOrderService distributionOrderService;

    @RequiresPermissions("oms:distribution:view")
    @GetMapping()
    public String order() {
        return prefix + "/distribution";
    }

    /**
     * 查询配货单信息列表
     */
    @RequiresPermissions("oms:distribution:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DistributionOrder distributionOrder) {
        QueryWrapper<DistributionOrder> queryWrapper = new QueryWrapper<>();
        startPage();
        return getDataTable(distributionOrderService.list(queryWrapper));
    }

    /**
     * 导出配货单信息列表
     */
    @RequiresPermissions("oms:distribution:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DistributionOrder distributionOrder) {
        List<DistributionOrder> list = distributionOrderService.list(new QueryWrapper<>());
        ExcelUtil<DistributionOrder> util = new ExcelUtil<>(DistributionOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 新增配货单信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存配货单信息
     */
    @RequiresPermissions("oms:distribution:add")
    @Log(title = "配货单信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DistributionOrder distributionOrder) {
        return toAjax(distributionOrderService.save(distributionOrder));
    }

    /**
     * 修改配货单信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        DistributionOrder distributionOrder = distributionOrderService.getById(id);
        mmap.put("distributionOrder", distributionOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存配货单信息
     */
    @RequiresPermissions("oms:distribution:edit")
    @Log(title = "配货单信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DistributionOrder distributionOrder) {
        return toAjax(distributionOrderService.updateById(distributionOrder));
    }

    /**
     * 删除配货单信息
     */
    @RequiresPermissions("oms:distribution:remove")
    @Log(title = "配货单信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(distributionOrderService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
