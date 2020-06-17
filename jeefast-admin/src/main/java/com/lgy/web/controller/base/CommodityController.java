package com.lgy.web.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.base.constant.BaseConstants;
import com.lgy.base.domain.Commodity;
import com.lgy.base.service.ICommodityService;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 商品档案Controller
 *
 * @author lgy
 * @date 2019-10-09
 */
@Controller
@RequestMapping("/base/commodity")
public class CommodityController extends BaseController {
    private String prefix = "base/commodity";

    @Autowired
    private ICommodityService commodityService;

    @RequiresPermissions("base:commodity:view")
    @GetMapping()
    public String commodity() {
        return prefix + "/commodity";
    }

    /**
     * 查询商品档案列表
     */
    @RequiresPermissions("base:commodity:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Commodity commodity) {
        QueryWrapper<Commodity> queryWrapper = getCommodityQueryWrapper(commodity);
        startPage();
        return getDataTable(commodityService.list(queryWrapper));
    }


    /**
     * 导出商品档案列表
     */
    @RequiresPermissions("base:commodity:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Commodity commodity) {
        QueryWrapper<Commodity> queryWrapper = getCommodityQueryWrapper(commodity);
        List<Commodity> list = commodityService.list(queryWrapper);
        ExcelUtil<Commodity> util = new ExcelUtil<>(Commodity.class);
        return util.exportExcel(list, "commodity");
    }

    /**
     * 新增商品档案
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存商品档案
     */
    @RequiresPermissions("base:commodity:add")
    @Log(title = "商品档案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Commodity commodity) {
        Commodity add = commodityService.add(commodity);
        if (add != null) {
            return toAjax(true);
        }
        return toAjax(false);
    }

    /**
     * 修改商品档案
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Commodity commodity = commodityService.getById(id);
        mmap.put("commodity", commodity);
        return prefix + "/edit";
    }

    /**
     * 修改保存商品档案
     */
    @RequiresPermissions("base:commodity:edit")
    @Log(title = "商品档案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Commodity commodity) {

        Commodity update = commodityService.update(commodity);
        if (update != null) {
            return toAjax(true);
        }
        return toAjax(false);
    }

    /**
     * 删除商品档案
     */
    @RequiresPermissions("base:commodity:remove")
    @Log(title = "商品档案", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(commodityService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 根据编码删除商品档案
     */
    @RequiresPermissions("base:commodity:remove")
    @Log(title = "商品档案", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    @ResponseBody
    public AjaxResult delete(String gcos) {

        String[] gcoList = Convert.toStrArray(gcos);

        boolean flag = true;

        for (String gco : gcoList) {
            flag = commodityService.delete(gco);
        }

        return toAjax(flag);
    }

    /**
     * 校验商品编码
     */
    @PostMapping("/checkCodeUnique")
    @ResponseBody
    public String checkCodeUnique(Commodity commodity) {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Commodity::getGco, commodity.getGco());
        Commodity one = commodityService.getOne(queryWrapper);
        if (one != null) {
            return BaseConstants.NOT_UNIQUE;
        }
        return BaseConstants.UNIQUE;
    }

    @Log(title = "商品档案", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:commodity:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Commodity> util = new ExcelUtil<>(Commodity.class);
        List<Commodity> commoditys = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = commodityService.importData(commoditys, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:commodity:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Commodity> util = new ExcelUtil<>(Commodity.class);
        return util.importTemplateExcel("商品档案数据");
    }

    /**
     * 查询条件
     *
     * @param commodity 商品查询条件
     * @return QueryWrapper
     */
    private QueryWrapper<Commodity> getCommodityQueryWrapper(Commodity commodity) {

        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(commodity.getGco())) {
            queryWrapper.lambda().eq(Commodity::getGco, commodity.getGco());
        }
        if (StringUtils.isNotEmpty(commodity.getGna())) {
            queryWrapper.lambda().like(Commodity::getGna, commodity.getGna());
        }
        if (StringUtils.isNotEmpty(commodity.getOwner())) {
            queryWrapper.lambda().eq(Commodity::getOwner, commodity.getOwner());
        }
        if (StringUtils.isNotEmpty(commodity.getCombo())) {
            queryWrapper.lambda().eq(Commodity::getCombo, commodity.getCombo());
        }
        if (StringUtils.isNotEmpty(commodity.getStatus())) {
            queryWrapper.lambda().eq(Commodity::getStatus, commodity.getStatus());
        }
        return queryWrapper;
    }

    /**
     * 获取商品档案信息
     */
    @GetMapping("/selectCommodity")
    @ResponseBody
    public AjaxResult selectCommodityParent() {
        AjaxResult ajax = new AjaxResult();
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(Commodity::getId, Commodity::getGco, Commodity::getGna);
        List<Commodity> commodityList = commodityService.list(queryWrapper);
        ajax.put("code", 200);
        ajax.put("value", commodityList);
        return ajax;
    }


}
