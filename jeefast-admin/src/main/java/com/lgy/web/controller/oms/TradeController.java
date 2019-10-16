package com.lgy.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.service.ITradeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 交易订单Controller
 *
 * @author lgy
 * @date 2019-10-15
 */
@Controller
@RequestMapping("/oms/trade")
public class TradeController extends BaseController {

    private String prefix = "oms/trade";

    @Autowired
    private ITradeService tradeService;

    @RequiresPermissions("oms:trade:view")
    @GetMapping()
    public String trade() {
        return prefix + "/trade";
    }

    /**
     * 查询交易订单列表
     */
    @RequiresPermissions("oms:trade:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Trade trade) {
        QueryWrapper<Trade> queryWrapper = getTradeQueryWrapper(trade);
        startPage();
        return getDataTable(tradeService.list(queryWrapper));
    }

    /**
     * 导出交易订单列表
     */
    @RequiresPermissions("oms:trade:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Trade trade) {
        QueryWrapper<Trade> queryWrapper = getTradeQueryWrapper(trade);
        List<Trade> list = tradeService.list(queryWrapper);
        ExcelUtil<Trade> util = new ExcelUtil<>(Trade.class);
        return util.exportExcel(list, "trade");
    }

    /**
     * 查询条件
     *
     * @param trade 查询条件参数
     * @return
     */
    private QueryWrapper<Trade> getTradeQueryWrapper(Trade trade) {
        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(trade.getTid())) {
            queryWrapper.eq("tid", trade.getTid());
        }
        if (StringUtils.isNotEmpty(trade.getShop())) {
            queryWrapper.eq("shop", trade.getShop());
        }
        return queryWrapper;
    }

    /**
     * 预览订单报文
     */
    @RequiresPermissions("tool:trade:view")
    @GetMapping("/view/{id}")
    @ResponseBody
    public AjaxResult preview(@PathVariable("id") Long id) throws IOException {
        return AjaxResult.success(tradeService.previewOrder(id));
    }

}
