package com.lgy.web.controller.oms;

import java.util.Arrays;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.lgy.common.annotation.Log;
import com.lgy.common.enums.BusinessType;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.service.IStrategyDistributionWarehouseRuleService;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.common.core.text.Convert;
import com.lgy.common.core.page.TableDataInfo;

/**
 * 配货策略分仓规则列Controller
 * 
 * @author lgy
 * @date 2020-02-04
 */
@Controller
@RequestMapping("/oms/strategy/distribution/warehouse/rule")
public class StrategyDistributionWarehouseRuleController extends BaseController {

    private String prefix = "oms/strategy/distribution/warehouse/rule";

    @Autowired
    private IStrategyDistributionWarehouseRuleService strategyDistributionWarehouseRuleService;

    @RequiresPermissions("oms:rule:view")
    @GetMapping()
    public String rule() {
        return prefix + "/rule";
    }

    /**
     * 查询配货策略分仓规则列列表
     */
    @RequiresPermissions("oms:rule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyDistributionWarehouseRule strategyDistributionWarehouseRule) {
        QueryWrapper<StrategyDistributionWarehouseRule> queryWrapper = new QueryWrapper<>();
    	// 需要根据页面查询条件进行组装
    	if(StringUtils.isNotEmpty(strategyDistributionWarehouseRule.getGco())) {
    		queryWrapper.like("gco", strategyDistributionWarehouseRule.getGco());
    	} 
        startPage();
        return getDataTable(strategyDistributionWarehouseRuleService.list(queryWrapper));
    }

    /** 初始化指定配货策略分仓规则 */
    @ResponseBody
    @PostMapping("/initRule")
    @RequiresPermissions("oms:rule:add")
    public AjaxResult initRule(String gco){
        if (StringUtils.isNotEmpty(gco)) {

            strategyDistributionWarehouseRuleService.initRule(gco);

            WhereBuilder builder = new WhereBuilder();
            builder.add("stco", Operator.eq, stco);
            List<WaruBean> list = waruService.findByWhere(builder);
            if (list != null && list.size() > 0) {
                waruService.deleteByWhere(builder);
            }
            if (waruService.saveWaru(stco)) {
                return new Message("初始化成功",true);
            }
            return new Message("初始化失败",false);
        } else {
            return new Message("传入编码为空,不能初始化指定配货策略分仓规则",false);
        }
    }


}
