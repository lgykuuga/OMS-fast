package com.lgy.web.controller.system;

import java.util.Arrays;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.lgy.common.annotation.Log;
import com.lgy.common.enums.BusinessType;
import com.lgy.system.domain.SysDemo;
import com.lgy.system.service.ISysDemoService;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.common.core.text.Convert;
import com.lgy.common.core.page.TableDataInfo;

/**
 * 用户演示Controller
 * 
 * @author lgy
 * @date 2019-09-24
 */
@Controller
@RequestMapping("/system/demo")
public class SysDemoController extends BaseController {
    private String prefix = "system/demo";

    @Autowired
    private ISysDemoService sysDemoService;

    @RequiresPermissions("system:demo:view")
    @GetMapping()
    public String demo() {
        return prefix + "/demo";
    }

    /**
     * 查询用户演示列表
     */
    @RequiresPermissions("system:demo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDemo sysDemo) {
    	QueryWrapper<SysDemo> queryWrapper = new QueryWrapper<>();
    	// 需要根据页面查询条件进行组装
    	if(StringUtils.isNotEmpty(sysDemo.getLoginName())) {
    		queryWrapper.like("login_name", sysDemo.getLoginName());
    	} 
    	if(StringUtils.isNotEmpty(sysDemo.getUserName())) {
    		queryWrapper.like("user_name", sysDemo.getUserName());
    	}
		// 特殊查询时条件需要进行单独组装
		/*Map<String, Object> params = sysDemo.getParams();
		if (StringUtils.isNotEmpty(params)) {
			queryWrapper.ge(StringUtils.isNotEmpty((String)params.get("beginTime")), "create_time", params.get("beginTime"));
			queryWrapper.le(StringUtils.isNotEmpty((String)params.get("endTime")), "create_time", params.get("endTime"));
		}*/
        startPage();
        return getDataTable(sysDemoService.list(queryWrapper));
    }

    /**
     * 导出用户演示列表
     */
    @RequiresPermissions("system:demo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDemo sysDemo) {
        List<SysDemo> list = sysDemoService.list(new QueryWrapper<>());
        ExcelUtil<SysDemo> util = new ExcelUtil<SysDemo>(SysDemo.class);
        return util.exportExcel(list, "demo");
    }

    /**
     * 新增用户演示
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户演示
     */
    @RequiresPermissions("system:demo:add")
    @Log(title = "用户演示", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysDemo sysDemo) {
        return toAjax(sysDemoService.save(sysDemo));
    }

    /**
     * 修改用户演示
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        SysDemo sysDemo = sysDemoService.getById(userId);
        mmap.put("sysDemo", sysDemo);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户演示
     */
    @RequiresPermissions("system:demo:edit")
    @Log(title = "用户演示", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDemo sysDemo) {
        return toAjax(sysDemoService.updateById(sysDemo));
    }

    /**
     * 删除用户演示
     */
    @RequiresPermissions("system:demo:remove")
    @Log(title = "用户演示", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysDemoService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
