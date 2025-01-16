package com.wangtao.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangtao.common.result.Result;
import com.wangtao.common.util.MD5;
import com.wangtao.model.system.SysUser;
import com.wangtao.model.vo.SysUserQueryVo;
import com.wangtao.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "后台用户管理控制器")
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    SysUserService sysUserService;

    @GetMapping("/{pageNum}/{pageSize}")
    public Result index(@ApiParam(name = "pageNum",value = "当前页码",readOnly = true) @PathVariable Long pageNum,
                        @ApiParam(name = "pageSize",value = "每页现实的行数",readOnly = true) @PathVariable Long pageSize,
                        SysUserQueryVo sysUserQueryVo) {
        Page page = new Page(pageNum,pageSize);
        Page<SysUser> sysUserPage = sysUserService.findPage(page,sysUserQueryVo);
        return Result.ok(sysUserPage);
    }

    @PostMapping("/save")
    @ApiOperation("用户信息添加")
    public Result save(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return Result.ok();
    }

    @PutMapping("/update")
    @ApiOperation("用户信息修改")
    public Result update(@RequestBody SysUser sysUser) {
        sysUser.setPassword(MD5.encrypt(sysUser.getPassword()));
        sysUserService.updateById(sysUser);
        return Result.ok();
    }

    @GetMapping("/getById/{id}")
    @ApiOperation("回显")
    public Result getById(@PathVariable Long id) {
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除用户信息")
    public Result delete(@PathVariable Long id) {
        sysUserService.removeById(id);
        return Result.ok();
    }

    @GetMapping("/updateStatus/{userId}/{status}")
    @ApiOperation("修改用户的状态")
    public Result updateStatus(@PathVariable Long userId,@PathVariable Integer status) {
        sysUserService.updateStatus(userId,status);
        return Result.ok();
    }
}
