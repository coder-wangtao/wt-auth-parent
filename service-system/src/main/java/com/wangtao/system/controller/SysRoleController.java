package com.wangtao.system.controller;

import com.wangtao.common.result.Result;
import com.wangtao.model.system.SysRole;
import com.wangtao.model.vo.AssginRoleVo;
import com.wangtao.model.vo.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangtao.system.annotation.Log;
import com.wangtao.system.enums.BusinessType;
import com.wangtao.system.service.SysRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//权限以SysRoleController为例子，其他Controller没加
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")  //权限以SysRoleController为例子，其他Controller没加
    @GetMapping("/findAll")
    public Result findAll(){
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("角色分页查询")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result index(@ApiParam(name = "pageNum",value = "当前页码",readOnly = true) @PathVariable long pageNum,
                        @ApiParam(name = "pageSize",value = "每页现实的行数",readOnly = true) @PathVariable long pageSize,
                        @ApiParam(name = "sysRoleQueryVo",value = "角色查询条件",readOnly = false)  SysRoleQueryVo sysRoleQueryVo
    ){
        Page<SysRole> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        queryWrapper.like(roleName == null ? false : true,"role_name",roleName);
        Page<SysRole> pageRoleList = sysRoleService.page(page, queryWrapper);
        return Result.ok(pageRoleList);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("角色分页查询(自己封装)")
    @GetMapping("/byself/{pageNum}/{pageSize}")
    public Result index1(@ApiParam(name = "pageNum",value = "当前页码",readOnly = true) @PathVariable long pageNum,
                        @ApiParam(name = "pageSize",value = "每页现实的行数",readOnly = true) @PathVariable long pageSize,
                        @ApiParam(name = "sysRoleQueryVo",value = "角色查询条件",readOnly = false)  SysRoleQueryVo sysRoleQueryVo
    ){
        Page<SysRole> page = new Page<>(pageNum,pageSize);
        Page<SysRole> pageRole = sysRoleService.findPage(page,sysRoleQueryVo);
        return Result.ok(pageRole);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("根据id查询角色信息")
    @GetMapping("/get/{id}")
    public Result get(@ApiParam(name = "id",value = "角色主键",readOnly = true) @PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    @Log(title = "角色管理",businessType = BusinessType.INSERT,isSaveRequestData = true,isSaveResponseData = true)
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色信息")
    @PostMapping("/save")
    public Result save(@ApiParam(name = "id",value = "角色对象",readOnly = true) @RequestBody SysRole sysRole){
        sysRoleService.save(sysRole);
        return Result.ok().message("保存成功");
    }

    @Log(title = "角色管理",businessType = BusinessType.UPDATE,isSaveRequestData = true,isSaveResponseData = true)
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色信息")
    @PutMapping("/update")
    public Result update(@ApiParam(name = "id",value = "修改的角色对象",readOnly = true) @RequestBody SysRole sysRole){
        sysRoleService.updateById(sysRole);
        return Result.ok().message("修改成功");
    }

    @Log(title = "角色管理",businessType = BusinessType.DELETE,isSaveRequestData = true,isSaveResponseData = true)
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("根据id删除单条角色信息")
    @DeleteMapping("/remove/{id}")
    public Result remove(@ApiParam(name = "id",value = "角色主键",readOnly = true) @PathVariable Long id){
        sysRoleService.removeById(id);
        return Result.ok().message("删除成功");
    }

    @Log(title = "角色管理",businessType = BusinessType.DELETE,isSaveRequestData = true,isSaveResponseData = true)
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("根据id批量删除角色信息")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@ApiParam(name = "ids",value = "角色主键",readOnly = true) @RequestBody List<Long> ids){
        sysRoleService.removeByIds(ids);
        return Result.ok().message("删除成功");
    }


    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @GetMapping("/getRolesByUserId/{userId}")
    @ApiOperation("获取用户的角色信息")
    public Result getRolesByUserId(@PathVariable Long userId){
       Map map = sysRoleService.getRolesByUserId(userId);
       return Result.ok(map);
    }

    @Log(title = "角色管理",businessType = BusinessType.ASSIGN,isSaveRequestData = true,isSaveResponseData = true)
    @PreAuthorize("hasAuthority('bnt.sysUser.assignRole')")
    @PostMapping("/doAssignRole")
    @ApiOperation("为用户分配角色")
    public Result doAssignRole(@RequestBody AssginRoleVo assginRoleVo){
        sysRoleService.doAssignRole(assginRoleVo);
        return Result.ok();
    }

}
