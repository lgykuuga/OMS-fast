package com.lgy.web.controller.oms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.annotation.Log;
import com.lgy.common.core.controller.BaseController;
import com.lgy.common.core.domain.AjaxResult;
import com.lgy.common.core.page.TableDataInfo;
import com.lgy.common.core.text.Convert;
import com.lgy.common.enums.BusinessType;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.poi.ExcelUtil;
import com.lgy.oms.domain.Stocklock;
import com.lgy.oms.service.IStocklockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 库存锁定Controller
 *
 * @author lgy
 * @date 2019-10-18
 */
@Controller
@RequestMapping("/oms/stockLock")
public class StocklockController extends BaseController {
    private String prefix = "oms/stockLock";

    @Autowired
    private IStocklockService stocklockService;

    @RequiresPermissions("oms:stockLock:view")
    @GetMapping()
    public String stockLock() {
        return prefix + "/stockLock";
    }

    /**
     * 查询库存锁定列表
     */
    @RequiresPermissions("oms:stockLock:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Stocklock stocklock) {
        QueryWrapper<Stocklock> queryWrapper = getStocklockQueryWrapper(stocklock);
        startPage();
        return getDataTable(stocklockService.list(queryWrapper));
    }

    /**
     * 导出库存锁定列表
     */
    @RequiresPermissions("oms:stockLock:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Stocklock stocklock) {
        QueryWrapper<Stocklock> queryWrapper = getStocklockQueryWrapper(stocklock);
        List<Stocklock> list = stocklockService.list(queryWrapper);
        ExcelUtil<Stocklock> util = new ExcelUtil<>(Stocklock.class);
        return util.exportExcel(list, "stockLock");
    }

    /**
     * 查询条件
     *
     * @param stocklock
     * @return
     */
    private QueryWrapper<Stocklock> getStocklockQueryWrapper(Stocklock stocklock) {
        QueryWrapper<Stocklock> queryWrapper = new QueryWrapper<>();
        // 需要根据页面查询条件进行组装
        if (StringUtils.isNotEmpty(stocklock.getBiid())) {
            queryWrapper.eq("biid", stocklock.getBiid());
        }
        if (StringUtils.isNotEmpty(stocklock.getWarehouse())) {
            queryWrapper.eq("warehouse", stocklock.getWarehouse());
        }
        if (StringUtils.isNotEmpty(stocklock.getCommodity())) {
            queryWrapper.eq("commodity", stocklock.getCommodity());
        }
        if (StringUtils.isNotEmpty(stocklock.getOwner())) {
            queryWrapper.eq("owner", stocklock.getOwner());
        }
        return queryWrapper;
    }

    /**
     * 新增库存锁定
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存库存锁定
     */
    @RequiresPermissions("oms:stockLock:add")
    @Log(title = "库存锁定", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Stocklock stocklock) {
        return toAjax(stocklockService.save(stocklock));
    }

    /**
     * 修改库存锁定
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Stocklock stocklock = stocklockService.getById(id);
        mmap.put("stocklock", stocklock);
        return prefix + "/edit";
    }

    /**
     * 修改保存库存锁定
     */
    @RequiresPermissions("oms:stockLock:edit")
    @Log(title = "库存锁定", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Stocklock stocklock) {
        return toAjax(stocklockService.updateById(stocklock));
    }

    /**
     * 删除库存锁定
     */
    @RequiresPermissions("oms:stockLock:remove")
    @Log(title = "库存锁定", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(stocklockService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
