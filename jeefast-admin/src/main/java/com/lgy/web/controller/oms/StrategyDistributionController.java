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
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.StrategyDistributionShop;
import com.lgy.oms.enums.strategy.DistributionLockModelEnum;
import com.lgy.oms.service.IStrategyDistributionService;
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
 * 配货策略Controller
 *
 * @author lgy
 * @date 2019-12-17
 */
@Controller
@RequestMapping("/oms/strategy/distribution")
public class StrategyDistributionController extends BaseController {

    private String prefix = "oms/strategy/distribution";

    @Autowired
    private IStrategyDistributionService strategyDistributionService;

    @RequiresPermissions("oms:distribution:view")
    @GetMapping()
    public String distribution(Model model) {
        //表单对象
        model.addAttribute("strategyDistribution", new StrategyDistribution());
        //锁库方式
        model.addAttribute("lockModelList", DistributionLockModelEnum.getList());
        return prefix + "/distribution";
    }

    /**
     * 查询审单策略列表
     */
    @RequiresPermissions("oms:distribution:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StrategyDistribution strategyDistribution) {
        QueryWrapper<StrategyDistribution> queryWrapper = getStrategyQueryWrapper(strategyDistribution);
        startPage();
        return getDataTable(strategyDistributionService.list(queryWrapper));
    }

    private QueryWrapper<StrategyDistribution> getStrategyQueryWrapper(StrategyDistribution strategyDistribution) {
        QueryWrapper<StrategyDistribution> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(strategyDistribution.getGco())) {
            queryWrapper.eq("gco", strategyDistribution.getGco());
        }
        if (StringUtils.isNotEmpty(strategyDistribution.getGna())) {
            queryWrapper.like("gna", strategyDistribution.getGna());
        }
        return queryWrapper;
    }

    /**
     * 新增策略
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存审单策略
     */
    @RequiresPermissions("oms:distribution:add")
    @Log(title = "审单策略", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StrategyDistribution strategyDistribution) {
        return toAjax(strategyDistributionService.save(strategyDistribution));
    }

    /**
     * 修改审单策略
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyDistribution strategyDistribution = strategyDistributionService.getById(id);
        mmap.put("strategyDistribution", strategyDistribution);
        return prefix + "/edit";
    }

    /**
     * 加载策略到form表单
     */
    @GetMapping("/loadStrategy/{id}")
    @ResponseBody
    public AjaxResult loadStrategy(@PathVariable("id") Long id, Model model) {
        StrategyDistribution strategyDistribution = strategyDistributionService.getById(id);
        //表单对象
        model.addAttribute("strategyDistribution", strategyDistribution);
        return AjaxResult.success(strategyDistribution);
    }

    /**
     * 修改保存审单策略
     */
    @RequiresPermissions("oms:distribution:edit")
    @Log(title = "配货策略", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StrategyDistribution strategyDistribution) {
        return toAjax(strategyDistributionService.updateById(strategyDistribution));
    }


    /**
     * 删除配货策略
     */
    @RequiresPermissions("oms:distribution:remove")
    @Log(title = "配货策略", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        String[] idList = Convert.toStrArray(ids);
        for (String id : idList) {
            StrategyDistribution strategyDistribution = strategyDistributionService.getById(id);
            //删除策略店铺及明细
            strategyDistributionService.deleteByGco(strategyDistribution.getGco());
        }
        return toAjax(strategyDistributionService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 查询策略店铺列表
     */
    @PostMapping("/distributionShop")
    @ResponseBody
    public TableDataInfo distributionShop(String gco) {
        List<StrategyDistributionShop> shopList = new ArrayList<>();
        if (StringUtils.isNotEmpty(gco)) {
            shopList = strategyDistributionService.getStrategyShop(gco);
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
    @Log(title = "配货策略", businessType = BusinessType.UPDATE)
    public AjaxResult changeAuto(Long id, String auto) {
        return toAjax(strategyDistributionService.changeAuto(id, auto));
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
    @RequiresPermissions("oms:distribution:remove")
    @PostMapping("/removeShop")
    @ResponseBody
    public AjaxResult removeShop(String ids) {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        //删除策略店铺
        Integer integer = strategyDistributionService.deleteShopById(idList);
        return toAjax(SqlHelper.retBool(integer));
    }

    /**
     * 新增策略店铺弹窗
     * gco:      策略编码
     * enforce: 是否强制新增店铺;
     * true:取不在该策略的店铺
     * false:获取未加入策略的店铺
     */
    @RequiresPermissions("oms:distribution:add")
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
        List<StrategyDistributionShop> shopList = strategyDistributionService.addLoadShop(shopCode, shopName, gco, enforce);
        startPage();
        return getDataTable(shopList);
    }

    /**
     * 新增保存店铺:保存不在该策略的店铺
     */
    @PostMapping("/addNotInShop")
    @ResponseBody
    public AjaxResult addNotInShop(String data) {
        List<StrategyDistributionShop> strategyShops = JSON.parseArray(data, StrategyDistributionShop.class);
        Integer count = strategyDistributionService.saveStrategyShop(strategyShops, true);
        return toAjax(SqlHelper.retBool(count));
    }

    /**
     * 新增保存店铺:保存未加入策略的店铺
     */
    @PostMapping("/addNotJoinShop")
    @ResponseBody
    public AjaxResult addNotJoinShop(String data) {
        List<StrategyDistributionShop> strategyShops = JSON.parseArray(data, StrategyDistributionShop.class);
        Integer count = strategyDistributionService.saveStrategyShop(strategyShops, false);
        return toAjax(SqlHelper.retBool(count));
    }

}
