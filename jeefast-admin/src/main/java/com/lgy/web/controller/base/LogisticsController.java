package com.lgy.web.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.base.domain.Logistics;
import com.lgy.base.service.ILogisticsService;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 物流商信息Controller
 *
 * @author lgy
 * @date 2019-10-09
 */
@Controller
@RequestMapping("/base/logistics")
public class LogisticsController extends BaseController {
    private String prefix = "base/logistics";

    @Autowired
    private ILogisticsService logisticsService;

    @RequiresPermissions("base:logistics:view")
    @GetMapping()
    public String logistics() {
        return prefix + "/logistics";
    }

    /**
     * 查询物流商信息列表
     */
    @RequiresPermissions("base:logistics:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Logistics logistics) {
        QueryWrapper<Logistics> queryWrapper = getLogisticsQueryWrapper(logistics);
        startPage();
        return getDataTable(logisticsService.list(queryWrapper));
    }

    /**
     * 导出物流商信息列表
     */
    @RequiresPermissions("base:logistics:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Logistics logistics) {
        QueryWrapper<Logistics> queryWrapper = getLogisticsQueryWrapper(logistics);
        List<Logistics> list = logisticsService.list(queryWrapper);
        ExcelUtil<Logistics> util = new ExcelUtil<>(Logistics.class);
        return util.exportExcel(list, "logistics");
    }

    private QueryWrapper<Logistics> getLogisticsQueryWrapper(Logistics logistics) {
        QueryWrapper<Logistics> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(logistics.getGco())) {
            queryWrapper.lambda().eq(Logistics::getGco, logistics.getGco());
        }
        if (StringUtils.isNotEmpty(logistics.getGna())) {
            queryWrapper.lambda().eq(Logistics::getGna, logistics.getGna());
        }
        if (StringUtils.isNotEmpty(logistics.getStatus())) {
            queryWrapper.lambda().eq(Logistics::getStatus, logistics.getStatus());
        }
        return queryWrapper;
    }

    /**
     * 新增物流商信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存物流商信息
     */
    @RequiresPermissions("base:logistics:add")
    @Log(title = "物流商信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Logistics logistics) {
        Logistics add = logisticsService.add(logistics);
        if (add != null) {
            return toAjax(true);
        }
        return toAjax(false);
    }

    /**
     * 修改物流商信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Logistics logistics = logisticsService.getById(id);
        mmap.put("logistics", logistics);
        return prefix + "/edit";
    }

    /**
     * 修改保存物流商信息
     */
    @RequiresPermissions("base:logistics:edit")
    @Log(title = "物流商信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Logistics logistics) {
        Logistics update = logisticsService.update(logistics);
        if (update != null) {
            return toAjax(true);
        }
        return toAjax(false);
    }

    /**
     * 删除物流商信息
     */
    @RequiresPermissions("base:logistics:remove")
    @Log(title = "物流商信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(logisticsService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 根据编码删除物流商档案
     */
    @RequiresPermissions("base:logistics:remove")
    @Log(title = "物流商档案", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    @ResponseBody
    public AjaxResult delete(String gcos) {

        String[] gcoList = Convert.toStrArray(gcos);

        boolean flag = true;

        for (String gco : gcoList) {
            flag = logisticsService.delete(gco);
        }

        return toAjax(flag);
    }

}
