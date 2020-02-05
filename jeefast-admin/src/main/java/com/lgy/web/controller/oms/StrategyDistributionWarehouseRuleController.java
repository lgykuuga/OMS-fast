package com.lgy.web.controller.oms;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.annotation.Log;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.enums.strategy.DistributionWarehouseRuleEnum;
import com.lgy.oms.service.IStrategyDistributionWarehouseRuleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{gco}")
    public String rule(@PathVariable("gco") String gco, ModelMap mmap) {
        //策略编码
        mmap.put("gco", gco);
        //分仓规则list
        mmap.put("rules", DistributionWarehouseRuleEnum.getList());

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
        if (StringUtils.isNotEmpty(strategyDistributionWarehouseRule.getGco())) {
            queryWrapper.eq("gco", strategyDistributionWarehouseRule.getGco());
        }
        startPage();
        return getDataTable(strategyDistributionWarehouseRuleService.list(queryWrapper));
    }

    /**
     * 初始化指定配货策略分仓规则
     */
    @ResponseBody
    @PostMapping("/initRule")
    @RequiresPermissions("oms:rule:add")
    public AjaxResult initRule(String gco) {

        if (StringUtils.isNotEmpty(gco)) {

            CommonResponse<String> response = strategyDistributionWarehouseRuleService.initRule(gco);

            if (Constants.SUCCESS.equals(response.getCode())) {
                return AjaxResult.success("初始化仓库规则成功");
            }

            return AjaxResult.error(response.getMsg());

        }
        return AjaxResult.error("传入策略编码为空");
    }

    /**
     * 配货策略预分配规则排序
     */
    @RequiresPermissions("oms:distribution:edit")
    @Log(title = "配货策略分仓规则", businessType = BusinessType.UPDATE)
    @PostMapping("/sort")
    @ResponseBody
    public AjaxResult sort(String data) {
        if (StringUtils.isEmpty(data)) {
            return AjaxResult.error("");
        }
        List<StrategyDistributionWarehouseRule> list = JSON.parseArray(data, StrategyDistributionWarehouseRule.class);

        if (!DistributionWarehouseRuleEnum.RULE_AVAILABLE.getCode().equals(list.get(0).getRuleId())) {
            return AjaxResult.error("不能调整【配货策略可用仓库】首位顺序");
        }

        for (int i = 0; i < list.size(); i++) {
            //调整优先级顺序
            strategyDistributionWarehouseRuleService.updatePrePriority(list.get(i).getId(), i);
        }
        return toAjax(true);
    }

    /**
     * 开启关闭
     */
    @PostMapping("/changeField")
    @ResponseBody
    @Log(title = "配货策略分仓规则", businessType = BusinessType.UPDATE)
    public AjaxResult changeField(Long id, String field, int value) throws Exception {
        return toAjax(strategyDistributionWarehouseRuleService.changeField(id, field, value));
    }


}
