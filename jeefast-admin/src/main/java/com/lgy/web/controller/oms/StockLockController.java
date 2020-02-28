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
import com.lgy.oms.domain.StockLock;
import com.lgy.oms.service.IStockLockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * 库存锁定Controller
 *
 * @author lgy
 * @date 2019-10-21
 */
@Controller
@RequestMapping("/oms/lock")
public class StockLockController extends BaseController {
    private String prefix = "oms/lock";

    @Autowired
    private IStockLockService stockLockService;

    @RequiresPermissions("oms:lock:view")
    @GetMapping()
    public String lock() {
        return prefix + "/lock";
    }

    /**
     * 查询库存锁定列表
     */
    @RequiresPermissions("oms:lock:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StockLock stockLock) {
        QueryWrapper<StockLock> queryWrapper = getStockLockQueryWrapper(stockLock);
        startPage();
        return getDataTable(stockLockService.list(queryWrapper));
    }

    /**
     * 导出库存锁定列表
     */
    @RequiresPermissions("oms:lock:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StockLock stockLock) {
        QueryWrapper<StockLock> queryWrapper = getStockLockQueryWrapper(stockLock);
        List<StockLock> list = stockLockService.list(queryWrapper);
        ExcelUtil<StockLock> util = new ExcelUtil<>(StockLock.class);
        return util.exportExcel(list, "stockLock");
    }

    /**
     * 查询条件
     *
     * @param stockLock
     * @return
     */
    private QueryWrapper<StockLock> getStockLockQueryWrapper(StockLock stockLock) {
        QueryWrapper<StockLock> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(stockLock.getOrderId())) {
            queryWrapper.eq("order_id", stockLock.getOrderId());
        }
        if (StringUtils.isNotEmpty(stockLock.getWarehouse())) {
            queryWrapper.eq("warehouse", stockLock.getWarehouse());
        }
        if (StringUtils.isNotEmpty(stockLock.getCommodity())) {
            queryWrapper.eq("commodity", stockLock.getCommodity());
        }
        if (StringUtils.isNotEmpty(stockLock.getOwner())) {
            queryWrapper.eq("owner", stockLock.getOwner());
        }
        return queryWrapper;
    }

    /**
     * 删除库存锁定
     */
    @RequiresPermissions("oms:lock:remove")
    @Log(title = "库存锁定", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(stockLockService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

}
