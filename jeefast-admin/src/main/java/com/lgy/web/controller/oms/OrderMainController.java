package com.lgy.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.annotation.Log;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.oms.disruptor.audit.AuditApi;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.domain.vo.OrderVO;
import com.lgy.oms.service.IOrderMainService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    IOrderMainService orderMainService;
    @Autowired
    AuditApi auditApi;

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
    public TableDataInfo list(OrderVO orderVO) {
        startPage();
        List<OrderVO> list = orderMainService.queryOrderList(orderVO);
        return getDataTable(list);
    }


    /**
     * 生成订单
     *
     * @param orderIds 订单号
     * @return
     */
    @RequiresPermissions("oms:main:add")
    @Log(title = "订单审核信息", businessType = BusinessType.INSERT)
    @PostMapping("/audit")
    @ResponseBody
    public AjaxResult audit(String orderIds) {

        //全部成功标识
        boolean flag = true;
        //失败原因
        StringBuffer failureMessage = new StringBuffer();

        String[] orderIdz = orderIds.split(Constants.COMMA);
        for (String orderId : orderIdz) {
            final CommonResponse<String> response = auditApi.addAuditAction(orderId);
            if (!Constants.SUCCESS.equals(response.getCode())) {
                failureMessage.append(response.getMsg());
                flag = false;
            }
        }

        if (flag) {
            return AjaxResult.success("生成订单成功");
        }
        return AjaxResult.error(failureMessage.toString());
    }


    /**
     * 导出订单审核信息列表
     */
    @RequiresPermissions("oms:main:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderVO orderVO) {
        QueryWrapper<OrderMain> queryWrapper = getOrderMainQueryWrapper(orderVO);
        List<OrderMain> list = orderMainService.list(queryWrapper);
        ExcelUtil<OrderMain> util = new ExcelUtil<>(OrderMain.class);
        return util.exportExcel(list, "main");
    }

    private QueryWrapper<OrderMain> getOrderMainQueryWrapper(OrderVO orderVO) {
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(orderVO.getOrderId())) {
            queryWrapper.like("orderId", orderVO.getOrderId());
        }
        if (StringUtils.isNotEmpty(orderVO.getSourceId())) {
            queryWrapper.like("sourceId", orderVO.getSourceId());
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


}
