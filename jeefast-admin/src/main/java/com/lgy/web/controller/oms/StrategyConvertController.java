package com.lgy.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.service.IStrategyConvertService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 转单策略Controller
 *
 * @author lgy
 * @date 2019-10-31
 */
@Controller
@RequestMapping("/oms/convert")
public class StrategyConvertController extends BaseController {
    private String prefix = "oms/convert";

    @Autowired
    private IStrategyConvertService strategyConvertService;

    @RequiresPermissions("oms:convert:view")
    @GetMapping()
    public String convert() {
        return prefix + "/convert";
    }

    /**
     * 查询转单策略列表
     */
    @RequiresPermissions("oms:convert:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyConvert strategyConvert) {
        QueryWrapper<StrategyConvert> queryWrapper = getStrategyConvertQueryWrapper(strategyConvert);
        startPage();
        return getDataTable(strategyConvertService.list(queryWrapper));
    }

    private QueryWrapper<StrategyConvert> getStrategyConvertQueryWrapper(StrategyConvert strategyConvert) {
        QueryWrapper<StrategyConvert> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(strategyConvert.getGco())) {
            queryWrapper.eq("gco", strategyConvert.getGco());
        }
        if (StringUtils.isNotEmpty(strategyConvert.getGna())) {
            queryWrapper.like("gna", strategyConvert.getGna());
        }
        return queryWrapper;
    }

    /**
     * 新增转单策略
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存转单策略
     */
    @RequiresPermissions("oms:convert:add")
    @Log(title = "转单策略", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyConvert strategyConvert) {
        return toAjax(strategyConvertService.save(strategyConvert));
    }

    /**
     * 修改转单策略
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyConvert strategyConvert = strategyConvertService.getById(id);
        mmap.put("strategyConvert", strategyConvert);
        return prefix + "/edit";
    }

    /**
     * 修改保存转单策略
     */
    @RequiresPermissions("oms:convert:edit")
    @Log(title = "转单策略", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StrategyConvert strategyConvert) {
        return toAjax(strategyConvertService.updateById(strategyConvert));
    }

    /**
     * 删除转单策略
     */
    @RequiresPermissions("oms:convert:remove")
    @Log(title = "转单策略", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(strategyConvertService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
