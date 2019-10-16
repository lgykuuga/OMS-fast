package com.lgy.web.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.base.domain.Platform;
import com.lgy.base.service.IPlatformService;
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
 * 平台档案Controller
 *
 * @author lgy
 * @date 2019-10-10
 */
@Controller
@RequestMapping("/base/platform")
public class PlatformController extends BaseController {
    private String prefix = "base/platform";

    @Autowired
    private IPlatformService platformService;

    @RequiresPermissions("base:platform:view")
    @GetMapping()
    public String platform() {
        return prefix + "/platform";
    }

    /**
     * 查询平台档案列表
     */
    @RequiresPermissions("base:platform:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Platform platform) {
        QueryWrapper<Platform> queryWrapper = getPlatformQueryWrapper(platform);
        startPage();
        return getDataTable(platformService.list(queryWrapper));
    }

    /**
     * 导出平台档案列表
     */
    @RequiresPermissions("base:platform:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Platform platform) {
        QueryWrapper<Platform> queryWrapper = getPlatformQueryWrapper(platform);
        List<Platform> list = platformService.list(queryWrapper);
        ExcelUtil<Platform> util = new ExcelUtil<Platform>(Platform.class);
        return util.exportExcel(list, "platform");
    }

    /**
     * 需要根据页面查询条件进行组装
     *
     * @param platform 查询条件
     * @return
     */
    private QueryWrapper<Platform> getPlatformQueryWrapper(Platform platform) {
        QueryWrapper<Platform> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(platform.getGco())) {
            queryWrapper.eq("gco", platform.getGco());
        }
        if (StringUtils.isNotEmpty(platform.getGna())) {
            queryWrapper.eq("gna", platform.getGna());
        }
        if (StringUtils.isNotEmpty(platform.getStatus())) {
            queryWrapper.eq("status", platform.getStatus());
        }
        return queryWrapper;
    }

    /**
     * 新增平台档案
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存平台档案
     */
    @RequiresPermissions("base:platform:add")
    @Log(title = "平台档案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Platform platform) {
        return toAjax(platformService.save(platform));
    }

    /**
     * 修改平台档案
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Platform platform = platformService.getById(id);
        mmap.put("platform", platform);
        return prefix + "/edit";
    }

    /**
     * 修改保存平台档案
     */
    @RequiresPermissions("base:platform:edit")
    @Log(title = "平台档案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Platform platform) {
        return toAjax(platformService.updateById(platform));
    }

    /**
     * 删除平台档案
     */
    @RequiresPermissions("base:platform:remove")
    @Log(title = "平台档案", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(platformService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
