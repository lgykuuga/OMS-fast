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
import com.lgy.oms.domain.Stock;
import com.lgy.oms.service.IStockService;
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
 * 库存信息Controller
 *
 * @author lgy
 * @date 2019-10-18
 */
@Controller
@RequestMapping("/oms/stock")
public class StockController extends BaseController {
    private String prefix = "oms/stock";

    @Autowired
    private IStockService stockService;

    @RequiresPermissions("oms:stock:view")
    @GetMapping()
    public String stock() {
        return prefix + "/stock";
    }

    /**
     * 查询库存信息列表
     */
    @RequiresPermissions("oms:stock:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Stock stock) {
        QueryWrapper<Stock> queryWrapper = getStockQueryWrapper(stock);
        startPage();
        return getDataTable(stockService.list(queryWrapper));
    }

    /**
     * 导出库存信息列表
     */
    @RequiresPermissions("oms:stock:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Stock stock) {
        QueryWrapper<Stock> queryWrapper = getStockQueryWrapper(stock);
        List<Stock> list = stockService.list(queryWrapper);
        ExcelUtil<Stock> util = new ExcelUtil<>(Stock.class);
        return util.exportExcel(list, "stock");
    }

    /**
     * 查询条件
     *
     * @param stock
     * @return
     */
    private QueryWrapper<Stock> getStockQueryWrapper(Stock stock) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(stock.getWarehouse())) {
            queryWrapper.eq("warehouse", stock.getWarehouse());
        }
        if (StringUtils.isNotEmpty(stock.getCommodity())) {
            queryWrapper.eq("commodity", stock.getCommodity());
        }
        if (StringUtils.isNotEmpty(stock.getOwner())) {
            queryWrapper.eq("owner", stock.getOwner());
        }
        return queryWrapper;
    }

    /**
     * 删除库存信息
     */
    @RequiresPermissions("oms:stock:remove")
    @Log(title = "库存信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(stockService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
