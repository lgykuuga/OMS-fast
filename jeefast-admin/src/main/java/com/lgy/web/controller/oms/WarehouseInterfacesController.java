package com.lgy.web.controller.oms;

import java.util.Arrays;
import java.util.List;

import com.lgy.oms.enums.DownloadOrderInterfaceEnum;
import com.lgy.oms.enums.InterfaceTypeEnum;
import com.lgy.oms.enums.PushFormatEnum;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.lgy.common.annotation.Log;
import com.lgy.common.enums.BusinessType;
import com.lgy.oms.domain.WarehouseInterfaces;
import com.lgy.oms.service.IWarehouseInterfacesService;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.common.core.text.Convert;
import com.lgy.common.core.page.TableDataInfo;

/**
 * 仓库接口设置Controller
 * 
 * @author lgy
 * @date 2019-11-25
 */
@Controller
@RequestMapping("/oms/warehouseInterfaces")
public class WarehouseInterfacesController extends BaseController {
    private String prefix = "oms/warehouseInterfaces";

    @Autowired
    private IWarehouseInterfacesService warehouseInterfacesService;

    @RequiresPermissions("oms:warehouseInterfaces:view")
    @GetMapping()
    public String interfaces() {
        return prefix + "/warehouseInterfaces";
    }

    /**
     * 查询仓库接口设置列表
     */
    @RequiresPermissions("oms:warehouseInterfaces:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WarehouseInterfaces warehouseInterfaces) {
        QueryWrapper<WarehouseInterfaces> queryWrapper = getWarehouseInterfacesQueryWrapper(warehouseInterfaces);
        startPage();
        return getDataTable(warehouseInterfacesService.list(queryWrapper));
    }

    private QueryWrapper<WarehouseInterfaces> getWarehouseInterfacesQueryWrapper(WarehouseInterfaces warehouseInterfaces) {
        QueryWrapper<WarehouseInterfaces> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if(StringUtils.isNotEmpty(warehouseInterfaces.getWarehouse())) {
            queryWrapper.like("warehouse", warehouseInterfaces.getWarehouse());
        }
        return queryWrapper;
    }

    /**
     * 导出仓库接口设置列表
     */
    @RequiresPermissions("oms:warehouseInterfaces:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WarehouseInterfaces warehouseInterfaces) {
        QueryWrapper<WarehouseInterfaces> queryWrapper = getWarehouseInterfacesQueryWrapper(warehouseInterfaces);
        List<WarehouseInterfaces> list = warehouseInterfacesService.list(queryWrapper);
        ExcelUtil<WarehouseInterfaces> util = new ExcelUtil<WarehouseInterfaces>(WarehouseInterfaces.class);
        return util.exportExcel(list, "interfaces");
    }

    /**
     * 新增仓库接口设置
     */
    @GetMapping("/add")
    public String add(Model model) {
        //接口类型
        model.addAttribute("interfaceTypeList", InterfaceTypeEnum.getList());
        //接口推送报文格式类型
        model.addAttribute("pushFormatList", PushFormatEnum.getList());
        return prefix + "/add";
    }

    /**
     * 新增保存仓库接口设置
     */
    @RequiresPermissions("oms:warehouseInterfaces:add")
    @Log(title = "仓库接口设置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WarehouseInterfaces warehouseInterfaces) {
        QueryWrapper<WarehouseInterfaces> wrapper = new QueryWrapper<>();
        wrapper.eq("warehouse", warehouseInterfaces.getWarehouse());
        WarehouseInterfaces one = warehouseInterfacesService.getOne(wrapper);
        if (one != null) {
            return AjaxResult.error("改仓库已存在配置接口");
        }
        return toAjax(warehouseInterfacesService.save(warehouseInterfaces));
    }

    /**
     * 修改仓库接口设置
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        WarehouseInterfaces warehouseInterfaces = warehouseInterfacesService.getById(id);
        mmap.put("warehouseInterfaces", warehouseInterfaces);
        //接口类型
        mmap.put("interfaceTypeList", InterfaceTypeEnum.getList());
        //接口推送报文格式类型
        mmap.put("pushFormatList", PushFormatEnum.getList());
        return prefix + "/edit";
    }

    /**
     * 修改保存仓库接口设置
     */
    @RequiresPermissions("oms:warehouseInterfaces:edit")
    @Log(title = "仓库接口设置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WarehouseInterfaces warehouseInterfaces) {
        return toAjax(warehouseInterfacesService.updateById(warehouseInterfaces));
    }

    /**
     * 删除仓库接口设置
     */
    @RequiresPermissions("oms:warehouseInterfaces:remove")
    @Log(title = "仓库接口设置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(warehouseInterfacesService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
