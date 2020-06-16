package com.lgy.web.controller.oms;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.annotation.Log;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.DateUtils;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.oms.domain.DownloadOrder;
import com.lgy.oms.service.IDownloadOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 下载订单Controller
 *
 * @author lgy
 * @date 2019-10-14
 */
@Controller
@RequestMapping("/oms/downloadorder")
public class DownloadOrderController extends BaseController {

    private String prefix = "oms/downloadorder";

    private static int ONE = 1;


    @Autowired
    private IDownloadOrderService downloadOrderService;

    @RequiresPermissions("oms:downloadorder:view")
    @GetMapping()
    public String downloadorder() {
        return prefix + "/downloadorder";
    }

    /**
     * 打开根据时间下载订单页面
     */
    @GetMapping("/downloadByTimeForm")
    public String downloadByTimeForm() {
        return prefix + "/downloadByTimeForm";
    }

    /**
     * 打开根据单号下载订单页面
     */
    @GetMapping("/downloadByTidForm")
    public String downloadByTidForm() {
        return prefix + "/downloadByTidForm";
    }

    /**
     * 删除下载订单日志
     */
    @RequiresPermissions("base:commodity:remove")
    @Log(title = "下载订单日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(downloadOrderService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean() {
        downloadOrderService.cleanDownloadLog();
        return success();
    }

    /**
     * 查询下载订单列表
     */
    @RequiresPermissions("oms:downloadorder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DownloadOrder downloadorder) {
        QueryWrapper<DownloadOrder> queryWrapper = getDownloadOrderQueryWrapper(downloadorder);
        startPage();
        return getDataTable(downloadOrderService.list(queryWrapper));
    }

    /**
     * 导出下载订单列表
     */
    @RequiresPermissions("oms:downloadorder:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DownloadOrder downloadorder) {
        QueryWrapper<DownloadOrder> queryWrapper = getDownloadOrderQueryWrapper(downloadorder);
        List<DownloadOrder> list = downloadOrderService.list(queryWrapper);
        ExcelUtil<DownloadOrder> util = new ExcelUtil<>(DownloadOrder.class);
        return util.exportExcel(list, "downloadorder");
    }

    /**
     * 查询条件
     *
     * @param downloadorder
     * @return
     */
    private QueryWrapper<DownloadOrder> getDownloadOrderQueryWrapper(DownloadOrder downloadorder) {
        QueryWrapper<DownloadOrder> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(downloadorder.getShop())) {
            queryWrapper.eq("shop", downloadorder.getShop());
        }
        if (StringUtils.isNotEmpty(downloadorder.getStat())) {
            queryWrapper.eq("stat", downloadorder.getStat());
        }
        // 特殊查询时条件需要进行单独组装
        Map<String, Object> params = downloadorder.getParams();
        if (StringUtils.isNotEmpty(params)) {
            queryWrapper.ge(StringUtils.isNotEmpty((String) params.get("beginTime")), "create_time", params.get("beginTime"));
            queryWrapper.le(StringUtils.isNotEmpty((String) params.get("endTime")), "create_time", params.get("endTime"));
        }
        //根据创建时间排序
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }

    /**
     * 根据时间段下载订单
     */
    @RequiresPermissions("oms:downloadorder:add")
    @PostMapping("/downloadByTime")
    @ResponseBody
    public AjaxResult downloadByTime(DownloadOrder downloadorder) {
        if (StringUtils.isEmpty(downloadorder.getShop())) {
            return AjaxResult.error("店铺不能为空");
        }
        if (StringUtils.isNull(downloadorder.getEndt())
                || StringUtils.isNull(downloadorder.getBedt())) {
            return AjaxResult.error("查单时间不能为空");
        }
        //比较前后时间，跨度大于一天即不能下单
        if (DateUtil.between(downloadorder.getEndt(),downloadorder.getBedt(), DateUnit.DAY) >= ONE) {
            return AjaxResult.error("下单时间跨度大于一天不能下单");
        }
        CommonResponse<String> response = downloadOrderService.downloadByTime(downloadorder);
        if (Constants.SUCCESS.equals(response.getCode())) {
            return AjaxResult.success(response.getMsg());
        }
        return AjaxResult.error(response.getMsg());
    }

    /**
     * 根据订单号下载订单
     */
    @RequiresPermissions("oms:downloadorder:add")
    @PostMapping("/downloadByTid")
    @ResponseBody
    public AjaxResult downloadByTid(String shop, String tids) {
        if (StringUtils.isEmpty(shop)) {
            return AjaxResult.error("店铺不能为空");
        }
        if (StringUtils.isEmpty(tids)) {
            return AjaxResult.error("订单号不能为空");
        }
        CommonResponse<String> response = downloadOrderService.downloadByTid(shop, tids);
        if (Constants.SUCCESS.equals(response.getCode())) {
            return AjaxResult.success("下载成功");
        }
        return AjaxResult.error(response.getMsg());
    }

}
