package com.wangtao.system.test;


import com.atguigu.model.system.SysRole;
import com.wangtao.system.mapper.SysRoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SysRoleMapperTest {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Test
    public void t1() {
//        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
//        System.out.println(sysRoles);
//        SysRole sysRole = sysRoleMapper.selectById(1);
//        System.out.println(sysRole);
//        List<SysRole> sysRoles1 = sysRoleMapper.selectBatchIds(Arrays.asList(1, 2, 3));
//        System.out.println(sysRoles1);
    }
}
