package com.wangtao.system.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangtao.common.result.Result;
import com.wangtao.model.vo.SysLoginLogQueryVo;
import com.wangtao.system.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录日志控制器")
@RequestMapping("/admin/system/sysLoginLog")
public class SysLoginLogController {

    @Autowired
    SysLoginLogService sysLoginLogService;

    @ApiOperation("分页查询")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result getSysLoginLog(@PathVariable Long pageNum,
                                 @PathVariable Long pageSize,
                                 SysLoginLogQueryVo sysLoginLogQueryVo) {
        Page page = new Page(pageNum, pageSize);
        Page sysLoginLogPage = sysLoginLogService.findPage(page,sysLoginLogQueryVo);
        return Result.ok(sysLoginLogPage);
    }


}
