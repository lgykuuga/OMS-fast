package com.lgy.web.controller.oms;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.StrategyDistributionPre;
import com.lgy.oms.domain.StrategyDistributionPreDetail;
import com.lgy.oms.enums.order.OrderTableEnum;
import com.lgy.oms.enums.strategy.ConditionEnum;
import com.lgy.oms.enums.strategy.DistributionLockModelEnum;
import com.lgy.oms.service.IStrategyDistributionPreDetailService;
import com.lgy.oms.service.IStrategyDistributionPreService;
import com.lgy.system.domain.vo.Config;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 配货预分配规则Controller
 *
 * @author lgy
 * @date 2020-01-20
 */
@Controller
@RequestMapping("/oms/strategy/distribution/pre")
public class StrategyDistributionPreController extends BaseController {

    private String prefix = "oms/strategy/distribution/pre";

    @Autowired
    IStrategyDistributionPreService preService;
    @Autowired
    IStrategyDistributionPreDetailService preDetailService;

    @RequiresPermissions("oms:distribution:view")
    @GetMapping("/{gco}")
    public String pre(@PathVariable("gco") String gco, ModelMap mmap) {
        //订单表类型字段
        mmap.put("orderTableEnum", OrderTableEnum.getList());
        //判断条件
        mmap.put("conditionEnum", ConditionEnum.getList());
        //锁库方式
        mmap.put("lockModelEnum", DistributionLockModelEnum.getList());
        //策略编码
        mmap.put("gco", gco);
        return prefix + "/pre";
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("oms:distribution:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyDistributionPre strategyDistributionPre) {
        QueryWrapper<StrategyDistributionPre> queryWrapper = getQueryWrapper(strategyDistributionPre);
        startPage();
        return getDataTable(preService.list(queryWrapper));
    }

    private QueryWrapper<StrategyDistributionPre> getQueryWrapper(StrategyDistributionPre strategyDistributionPre) {
        QueryWrapper<StrategyDistributionPre> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(strategyDistributionPre.getGco())) {
            queryWrapper.eq("gco", strategyDistributionPre.getGco());
        }
        if (StringUtils.isNotEmpty(strategyDistributionPre.getGna())) {
            queryWrapper.like("gna", strategyDistributionPre.getGna());
        }
        return queryWrapper;
    }

    /**
     * 新增审单策略
     */
    @GetMapping("/add/{gco}")
    public String add(@PathVariable("gco") String gco, ModelMap mmap) {
        mmap.put("gco", gco);
        mmap.put("lockModelEnum", DistributionLockModelEnum.getList());
        return prefix + "/add";
    }

    /**
     * 新增配货策略新增预分配规则
     */
    @RequiresPermissions("oms:distribution:add")
    @Log(title = "配货策略预分配规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyDistributionPre strategyDistributionPre) {
        return toAjax(preService.save(strategyDistributionPre));
    }

    /**
     * 修改配货策略预分配规则
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyDistributionPre strategyDistributionPre = preService.getById(id);
        mmap.put("strategyDistributionPre", strategyDistributionPre);
        mmap.put("lockModelEnum", DistributionLockModelEnum.getList());
        return prefix + "/edit";
    }

    /**
     * 修改保存配货策略预分配规则
     */
    @RequiresPermissions("oms:distribution:edit")
    @Log(title = "配货策略预分配规则", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StrategyDistributionPre strategyDistributionPre) {
        return toAjax(preService.updateById(strategyDistributionPre));
    }

    /**
     * 删除配货策略预分配规则
     */
    @RequiresPermissions("oms:distribution:remove")
    @Log(title = "配货策略预分配规则", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        List<String> idz = Arrays.asList(Convert.toStrArray(ids));
        for (String id : idz) {
            //删除明细
            preDetailService.deleteByParentId(id);
        }
        return toAjax(preService.removeByIds(idz));
    }

    /**
     * 配货策略预分配规则排序
     */
    @RequiresPermissions("oms:distribution:edit")
    @Log(title = "配货策略预分配规则", businessType = BusinessType.UPDATE)
    @PostMapping("/sort")
    @ResponseBody
    public AjaxResult sort(String data) {
        if (StringUtils.isEmpty(data)) {
            return AjaxResult.error("");
        }
        List<StrategyDistributionPre> list = JSON.parseArray(data, StrategyDistributionPre.class);
        for (int i = 0; i < list.size(); i++) {
            //调整组合信息拦截优先级顺序
            preService.updatePrePriority(list.get(i).getId(), i);
        }
        return toAjax(true);
    }

    /**
     * 查询预分配规则明细
     */
    @PostMapping("/detailList")
    @ResponseBody
    public TableDataInfo detailList(Long parentId) {
        List<StrategyDistributionPreDetail> detailList = new ArrayList<>();
        if (parentId != null) {
            detailList = preDetailService.getDetailByParentId(parentId);
            startPage();
            return getDataTable(detailList);
        }
        return getDataTable(detailList);
    }

    /**
     * 新增预分配规则
     */
    @GetMapping("/addDetail/{gco}/{parentId}")
    public String addDetail(@PathVariable("gco") String gco,
                            @PathVariable("parentId") String parentId, ModelMap mmap) {
        mmap.put("gco", gco);
        mmap.put("parentId", parentId);

        mmap.put("orderTableEnum", OrderTableEnum.getList());
        mmap.put("conditionEnum", ConditionEnum.getList());

        return prefix + "/addDetail";
    }

    /**
     * 新增配货策略预分配规则
     */
    @RequiresPermissions("oms:distribution:add")
    @Log(title = "配货策略预分配规则明细", businessType = BusinessType.INSERT)
    @PostMapping("/addDetail")
    @ResponseBody
    public AjaxResult addSave(StrategyDistributionPreDetail detail) {
        return toAjax(preDetailService.save(detail));
    }

    /**
     * 修改配货策略预分配规则
     */
    @GetMapping("/editDetail/{id}")
    public String editDetail(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyDistributionPreDetail detail = preDetailService.getById(id);
        mmap.put("strategyDistributionPreDetail", detail);

        mmap.put("orderTableEnum", OrderTableEnum.getList());
        mmap.put("conditionEnum", ConditionEnum.getList());
        return prefix + "/editDetail";
    }

    /**
     * 修改配货策略预分配规则明细
     */
    @RequiresPermissions("oms:distribution:edit")
    @Log(title = "配货策略预分配规则明细", businessType = BusinessType.UPDATE)
    @PostMapping("/editDetail")
    @ResponseBody
    public AjaxResult editSave(StrategyDistributionPreDetail detail) {
        return toAjax(preDetailService.updateById(detail));
    }

    /**
     * 删除配货策略预分配规则明细
     */
    @RequiresPermissions("oms:distribution:remove")
    @Log(title = "配货策略预分配规则明细", businessType = BusinessType.DELETE)
    @PostMapping("/removeDetail")
    @ResponseBody
    public AjaxResult removeDetail(String ids) {
        return toAjax(preDetailService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 选择表字段,联动事件
     */
    @PostMapping("/selectField")
    @ResponseBody
    public AjaxResult selectField(Integer code) {

        List<Config> selected = OrderTableEnum.getSelected(code);

        return AjaxResult.success(selected);
    }


}
