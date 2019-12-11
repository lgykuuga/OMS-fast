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
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.StrategyConvertShop;
import com.lgy.oms.enums.strategy.ConvertMatchCommodityEnum;
import com.lgy.oms.enums.strategy.ConvertTriggerNodeEnum;
import com.lgy.oms.enums.strategy.ProcessEnum;
import com.lgy.oms.service.IStrategyConvertService;
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
    public String convert(Model model) {
        //转单触发节点
        model.addAttribute("convertTriggerNodeList", ConvertTriggerNodeEnum.getList());
        //匹配商品方式
        model.addAttribute("matchCommodityList", ConvertMatchCommodityEnum.getList());
        //流程方式
        model.addAttribute("processList", ProcessEnum.getList());
        //表单对象
        model.addAttribute("strategyConvert", new StrategyConvert());
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
     * 修改转单策略
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        StrategyConvert strategyConvert = strategyConvertService.getById(id);
        mmap.put("strategyConvert", strategyConvert);
        return prefix + "/edit";
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
     * 加载策略到form表单
     */
    @GetMapping("/loadStrategy/{id}")
    @ResponseBody
    public AjaxResult loadStrategy(@PathVariable("id") Long id, Model model) {
        StrategyConvert strategyConvert = strategyConvertService.getById(id);
        //表单对象
        model.addAttribute("strategyConvert", strategyConvertService.getById(id));
        return AjaxResult.success(strategyConvert);
    }

    /**
     * 删除转单策略
     */
    @RequiresPermissions("oms:convert:remove")
    @Log(title = "转单策略", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        for (String id : idList) {
            StrategyConvert strategyConvert = strategyConvertService.getById(id);
            //删除策略店铺
            strategyConvertService.deleteConvertShopByGco(strategyConvert.getGco());
        }
        return toAjax(strategyConvertService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 查询策略店铺列表
     */
    @PostMapping("/convertShop")
    @ResponseBody
    public TableDataInfo convertShop(String gco) {
        List<StrategyConvertShop> convertShop = new ArrayList<>();
        if (StringUtils.isNotEmpty(gco)) {
            convertShop = strategyConvertService.getConvertShop(gco);
            startPage();
            return getDataTable(convertShop);
        }
        return getDataTable(convertShop);
    }

    /**
     * 更改策略店铺是否开启自动
     */
    @PostMapping("/changeAuto")
    @ResponseBody
    @Log(title = "转单策略", businessType = BusinessType.UPDATE)
    public AjaxResult changeAuto(Long id, String auto) {
        return toAjax(strategyConvertService.changeAuto(id, auto));
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
    @RequiresPermissions("oms:convert:remove")
    @PostMapping("/removeShop")
    @ResponseBody
    public AjaxResult removeShop(String ids) {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        //删除策略店铺
        Integer integer = strategyConvertService.deleteConvertShopById(idList);
        return toAjax(SqlHelper.retBool(integer));
    }

    /**
     * 新增策略店铺弹窗
     * gco:      策略编码
     * enforce: 是否强制新增店铺;
     *          true:取不在该策略的店铺
     *          false:获取未加入策略的店铺
     */
    @RequiresPermissions("oms:convert:add")
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
     *          true:获取不在该策略的店铺
     *          false:获取未加入策略的店铺
     */
    @PostMapping("/addLoadShop")
    @ResponseBody
    public TableDataInfo addLoadShop(String shopCode, String shopName, String gco, boolean enforce) {
        List<StrategyConvertShop> convertShop = strategyConvertService.addLoadShop(shopCode, shopName, gco, enforce);
        startPage();
        return getDataTable(convertShop);
    }

    /**
     * 新增保存店铺:保存不在该策略的店铺
     */
    @PostMapping("/addNotInShop")
    @ResponseBody
    public AjaxResult addNotInShop(String data) {
        List<StrategyConvertShop> strategyConvertShops = JSON.parseArray(data, StrategyConvertShop.class);
        Integer count = strategyConvertService.saveStrategyConvertShop(strategyConvertShops, true);
       return toAjax(SqlHelper.retBool(count));
    }

    /**
     * 新增保存店铺:保存未加入策略的店铺
     */
    @PostMapping("/addNotJoinShop")
    @ResponseBody
    public AjaxResult addNotJoinShop(String data) {
        List<StrategyConvertShop> strategyConvertShops = JSON.parseArray(data, StrategyConvertShop.class);
        Integer count = strategyConvertService.saveStrategyConvertShop(strategyConvertShops, false);
        return toAjax(SqlHelper.retBool(count));
    }


}
