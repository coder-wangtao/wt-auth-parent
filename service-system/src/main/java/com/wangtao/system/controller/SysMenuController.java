package com.wangtao.system.controller;

import com.wangtao.common.result.Result;
import com.wangtao.model.system.SysMenu;
import com.wangtao.model.vo.AssginMenuVo;
import com.wangtao.model.vo.AssginRoleVo;
import com.wangtao.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/sysMenu")
@Api(tags = "后台菜单控制器")
public class SysMenuController {

    @Autowired
    SysMenuService sysMenuService;

    @GetMapping("/findMenuNodes")
    public Result findMenuNodes() {
        List<SysMenu> sysMenuList = sysMenuService.findMenuNodes();
        return Result.ok(sysMenuList);
    }

    @PostMapping("/save")
    @ApiOperation("/目录/菜单添加")
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("按钮删除/菜单删除")
    public Result delete(@PathVariable Long id) {
        sysMenuService.delete(id);
        return Result.ok();
    }

    @PutMapping("/update")
    @ApiOperation("修改目录，菜单，按钮")
    public Result update(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @GetMapping("/getById/{id}")
    @ApiOperation("回显")
    public Result getById(@PathVariable Long id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    @ApiOperation("查询角色的权限")
    @GetMapping("/getRoleMenuList/{roleId}")
    public Result getRoleMenuList(@PathVariable Long roleId) {
        List<SysMenu> sysMenuList = sysMenuService.getRoleMenuList(roleId);
        return Result.ok(sysMenuList);
    }

    @ApiOperation("给角色分配权限")
    @PostMapping("/assignMenu")
    public Result assignMenu(@RequestBody AssginMenuVo AssginMenuVo) {
        sysMenuService.assignMenu(AssginMenuVo);
        return Result.ok();
    }
}
