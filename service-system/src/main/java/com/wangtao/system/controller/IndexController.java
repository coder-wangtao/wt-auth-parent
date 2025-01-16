package com.wangtao.system.controller;

import com.wangtao.common.helper.JwtHelper;
import com.wangtao.common.result.Result;
import com.wangtao.common.result.ResultCodeEnum;
import com.wangtao.common.util.MD5;
import com.wangtao.model.system.SysUser;
import com.wangtao.model.vo.LoginVo;
import com.wangtao.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/system/index")
@Api(tags = "后台登录管理")
public class IndexController {

    @Autowired
    SysUserService sysUserService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result login(@RequestBody LoginVo loginVo) {
//        String username = loginVo.getUsername();
//        String password = loginVo.getPassword();
//        SysUser sysUser = sysUserService.getUserByUserName(username);
//        if(sysUser == null){
//            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
//        }
//        if(!MD5.encrypt(password).equals(sysUser.getPassword())){
//            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
//        }
//        if(sysUser.getStatus() == 0){
//            return Result.build(null, ResultCodeEnum.ACCOUNT_STOP);
//        }
//
//        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
//
//        Map map = new HashMap();
//        map.put("token", token);
        return Result.ok();
    }

    @GetMapping("/info")
    @ApiOperation("获取信息")
    public Result info(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        String username = JwtHelper.getUsername(token);
        Map map =  sysUserService.getUserByUserId(userId);
        return Result.ok(map);
    }

    @PostMapping("/logout")
    @ApiOperation("用户退出")
    public Result logout() {
        return Result.ok();
    }

}
