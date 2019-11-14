package com.lgy.web.controller.tool;

import com.lgy.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * diff 文本对比
 * 
 * @author lgy
 */
@Controller
@RequestMapping("/tool/diff")
public class DiffController extends BaseController
{
    private String prefix = "tool/diff";

    @RequiresPermissions("tool:build:view")
    @GetMapping()
    public String diff()
    {
        return prefix + "/diff";
    }
}
