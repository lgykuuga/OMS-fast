package com.lgy.web.controller.oms;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.StrategyAudit;
import com.lgy.oms.domain.StrategyAuditShop;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.enums.strategy.AuditAmountEnum;
import com.lgy.oms.enums.strategy.AuditNumberEnum;
import com.lgy.oms.enums.strategy.AuditTimeEnum;
import com.lgy.oms.enums.strategy.ProcessEnum;
import com.lgy.oms.service.IStrategyAuditService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 审单策略Controller
 *
 * @author lgy
 * @date 2019-12-17
 */
@Controller
@RequestMapping("/oms/audit")
public class StrategyAuditController extends BaseController {
    private String prefix = "oms/audit";

    @Autowired
    private IStrategyAuditService strategyAuditService;

    @RequiresPermissions("oms:audit:view")
    @GetMapping()
    public String audit(Model model) {
        //金额拦截类型
        model.addAttribute("auditAmountList", AuditAmountEnum.getList());
        //数值拦截类型
        model.addAttribute("auditNumberList", AuditNumberEnum.getList());
        //时间拦截类型
        model.addAttribute("auditTimeList", AuditTimeEnum.getList());
        //表单对象
        model.addAttribute("strategyAudit", new StrategyAudit());
        return prefix + "/audit";
    }

    /**
     * 查询审单策略列表
     */
    @RequiresPermissions("oms:audit:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyAudit strategyAudit) {
        QueryWrapper<StrategyAudit> queryWrapper = getStrategyAuditQueryWrapper(strategyAudit);
        startPage();
        return getDataTable(strategyAuditService.list(queryWrapper));
    }

    private QueryWrapper<StrategyAudit> getStrategyAuditQueryWrapper(StrategyAudit strategyAudit) {
        QueryWrapper<StrategyAudit> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(strategyAudit.getGco())) {
            queryWrapper.eq("gco", strategyAudit.getGco());
        }
        if (StringUtils.isNotEmpty(strategyAudit.getGna())) {
            queryWrapper.like("gna", strategyAudit.getGna());
        }
        return queryWrapper;
    }

    /**
     * 新增审单策略
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存审单策略
     */
    @RequiresPermissions("oms:audit:add")
    @Log(title = "审单策略", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyAudit strategyAudit) {
        return toAjax(strategyAuditService.save(strategyAudit));
    }

    /**
     * 修改审单策略
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyAudit strategyAudit = strategyAuditService.getById(id);
        mmap.put("strategyAudit", strategyAudit);
        return prefix + "/edit";
    }

    /**
     * 加载策略到form表单
     */
    @GetMapping("/loadStrategy/{id}")
    @ResponseBody
    public AjaxResult loadStrategy(@PathVariable("id") Long id, Model model) {
        StrategyAudit strategyAudit = strategyAuditService.getById(id);
        //表单对象
        model.addAttribute("strategyAudit", strategyAudit);
        return AjaxResult.success(strategyAudit);
    }

    /**
     * 修改保存审单策略
     */
    @RequiresPermissions("oms:audit:edit")
    @Log(title = "审单策略", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StrategyAudit strategyAudit) {
        return toAjax(strategyAuditService.updateById(strategyAudit));
    }



    /**
     * 删除转单策略
     */
    @RequiresPermissions("oms:convert:remove")
    @Log(title = "转单策略", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        String[] idList = Convert.toStrArray(ids);
        for (String id : idList) {
            StrategyAudit strategyAudit = strategyAuditService.getById(id);
            //删除策略店铺及明细
            strategyAuditService.deleteByGco(strategyAudit.getGco());
        }
        return toAjax(strategyAuditService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 查询策略店铺列表
     */
    @PostMapping("/auditShop")
    @ResponseBody
    public TableDataInfo auditShop(String gco) {
        List<StrategyAuditShop> shopList = new ArrayList<>();
        if (StringUtils.isNotEmpty(gco)) {
            shopList = strategyAuditService.getStrategyShop(gco);
            startPage();
            return getDataTable(shopList);
        }
        return getDataTable(shopList);
    }

    /**
     * 更改策略店铺是否开启自动
     */
    @PostMapping("/changeAuto")
    @ResponseBody
    @Log(title = "转单策略", businessType = BusinessType.UPDATE)
    public AjaxResult changeAuto(Long id, String auto) {
        return toAjax(strategyAuditService.changeAuto(id, auto));
    }

    /**
     * 选择店铺窗口
     */
    @GetMapping("/selectShop/{gco}")
    public String selectShop(@PathVariable("gco") String gco, ModelMap mmap) {
        mmap.put("gco", gco);
        return prefix + "/shop";
    }

    /**
     * 删除转单策略店铺
     */
    @RequiresPermissions("oms:audit:remove")
    @PostMapping("/removeShop")
    @ResponseBody
    public AjaxResult removeShop(String ids) {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        //删除策略店铺
        Integer integer = strategyAuditService.deleteShopById(idList);
        return toAjax(SqlHelper.retBool(integer));
    }

    /**
     * 新增策略店铺弹窗
     * gco:      策略编码
     * enforce: 是否强制新增店铺;
     * true:取不在该策略的店铺
     * false:获取未加入策略的店铺
     */
    @RequiresPermissions("oms:audit:add")
    @GetMapping("/selectShop")
    public String selectShop(String gco, boolean enforce, ModelMap mmap) {
        mmap.put("gco", gco);
        mmap.put("enforce", enforce);
        return prefix + "/selectShop";
    }

    /**
     * 新增读取店铺
     * gco:      策略编码
     * enforce: 是否强制新增店铺
     * true:获取不在该策略的店铺
     * false:获取未加入策略的店铺
     */
    @PostMapping("/addLoadShop")
    @ResponseBody
    public TableDataInfo addLoadShop(String shopCode, String shopName, String gco, boolean enforce) {
        List<StrategyAuditShop> shopList = strategyAuditService.addLoadShop(shopCode, shopName, gco, enforce);
        startPage();
        return getDataTable(shopList);
    }

    /**
     * 新增保存店铺:保存不在该策略的店铺
     */
    @PostMapping("/addNotInShop")
    @ResponseBody
    public AjaxResult addNotInShop(String data) {
        List<StrategyAuditShop> strategyShops = JSON.parseArray(data, StrategyAuditShop.class);
        Integer count = strategyAuditService.saveStrategyShop(strategyShops, true);
        return toAjax(SqlHelper.retBool(count));
    }

    /**
     * 新增保存店铺:保存未加入策略的店铺
     */
    @PostMapping("/addNotJoinShop")
    @ResponseBody
    public AjaxResult addNotJoinShop(String data) {
        List<StrategyAuditShop> strategyShops = JSON.parseArray(data, StrategyAuditShop.class);
        Integer count = strategyAuditService.saveStrategyShop(strategyShops, false);
        return toAjax(SqlHelper.retBool(count));
    }

}
