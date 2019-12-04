package com.lgy.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.enums.PlatformOrderStatusEnum;
import com.lgy.oms.enums.TradeTranformStatusEnum;
import com.lgy.oms.service.ITradeService;
import com.lgy.oms.service.business.ITradeConvertService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    ITradeService tradeService;

    /**
     * 交易订单转换策略
     */
    @Autowired
    ITradeConvertService tradeConvertService;

    @RequiresPermissions("oms:trade:view")
    @GetMapping()
    public String trade(Model model) {
        //下单订单接口
        model.addAttribute("orderStatusList", PlatformOrderStatusEnum.getList());
        model.addAttribute("tradeTranformStatusList", TradeTranformStatusEnum.getList());
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
        if (trade.getStatus() != null) {
            queryWrapper.eq("status", trade.getStatus());
        }
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }

    /**
     * 预览订单报文
     */
    @RequiresPermissions("tool:trade:view")
    @GetMapping("/preview/{tid}/{type}")
    @ResponseBody
    public AjaxResult preview(@PathVariable("tid") String tableId, @PathVariable("type") String type) throws IOException {
        return AjaxResult.success(tradeService.previewOrder(tableId, type));
    }

    /**
     * 生成订单
     *
     * @param tids 平台单号
     * @return
     */
    @RequiresPermissions("tool:trade:add")
    @PostMapping("/convert")
    @ResponseBody
    public AjaxResult convert(String tids) {
        String[] tidz = tids.split(",");
        for (String tid : tidz) {
            tradeConvertService.execute(tid, null);
        }
        return null;
    }

    /**
     * 生成订单快照
     *
     * @param tids 平台单号
     * @return
     */
    @RequiresPermissions("tool:trade:add")
    @PostMapping("/createSnapshot")
    @ResponseBody
    public AjaxResult createSnapshot(String tids) {
        String[] tidCollection = tids.split(",");
        //全部成功标识
        boolean flag = true;
        //失败原因
        StringBuffer failureMessage = new StringBuffer();

        for (String tid : tidCollection) {
            CommonResponse<String> createSnapshot = tradeService.createSnapshot(tid);
            if (!Constants.SUCCESS.equals(createSnapshot.getCode())) {
                flag = false;
                failureMessage.append(createSnapshot.getMsg());
            }
        }

        if (flag) {
            return AjaxResult.success("生成订单快照成功");
        }

        return AjaxResult.error(failureMessage.toString());
    }

}
