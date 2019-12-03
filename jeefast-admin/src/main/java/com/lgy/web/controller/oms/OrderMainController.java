package com.lgy.web.controller.oms;

import java.util.Arrays;
import java.util.List;

import com.lgy.oms.domain.order.OrderMain;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.lgy.common.annotation.Log;
import com.lgy.common.enums.BusinessType;
import com.lgy.oms.service.IOrderMainService;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.common.core.text.Convert;
import com.lgy.common.core.page.TableDataInfo;

/**
 * 订单主信息Controller
 * 
 * @author lgy
 * @date 2019-11-25
 */
@Controller
@RequestMapping("/oms/order")
public class OrderMainController extends BaseController {
    private String prefix = "oms/order";

    @Autowired
    private IOrderMainService orderMainService;

    @RequiresPermissions("oms:main:view")
    @GetMapping()
    public String main() {
        return prefix + "/main";
    }

    /**
     * 查询订单审核信息列表
     */
    @RequiresPermissions("oms:main:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OrderMain orderMain) {
        QueryWrapper<OrderMain> queryWrapper = getOrderMainQueryWrapper(orderMain);
        startPage();
        return getDataTable(orderMainService.list(queryWrapper));
    }

    /**
     * 导出订单审核信息列表
     */
    @RequiresPermissions("oms:main:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderMain orderMain) {
        QueryWrapper<OrderMain> queryWrapper = getOrderMainQueryWrapper(orderMain);
        List<OrderMain> list = orderMainService.list(queryWrapper);
        ExcelUtil<OrderMain> util = new ExcelUtil<>(OrderMain.class);
        return util.exportExcel(list, "main");
    }

    private QueryWrapper<OrderMain> getOrderMainQueryWrapper(OrderMain orderMain) {
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(orderMain.getOrderId())) {
            queryWrapper.like("orderId", orderMain.getOrderId());
        }
        if (StringUtils.isNotEmpty(orderMain.getSourceId())) {
            queryWrapper.like("sourceId", orderMain.getSourceId());
        }
        return queryWrapper;
    }

    /**
     * 新增订单审核信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存订单审核信息
     */
    @RequiresPermissions("oms:main:add")
    @Log(title = "订单审核信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OrderMain orderMain) {
        return toAjax(orderMainService.save(orderMain));
    }

    /**
     * 修改订单审核信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        OrderMain orderMain = orderMainService.getById(id);
        mmap.put("orderMain", orderMain);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单审核信息
     */
    @RequiresPermissions("oms:main:edit")
    @Log(title = "订单审核信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OrderMain orderMain) {
        return toAjax(orderMainService.updateById(orderMain));
    }

    /**
     * 删除订单审核信息
     */
    @RequiresPermissions("oms:main:remove")
    @Log(title = "订单审核信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(orderMainService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
