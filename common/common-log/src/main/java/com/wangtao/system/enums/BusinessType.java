package com.wangtao.system.enums;


/*
数据库操作对象的方式（请求方式）
 */
public enum BusinessType {
    /*添加功能*/
    INSERT,
    /*修改功能*/
    UPDATE,
    /*删除功能*/
    DELETE,
    /*修改状态*/
    STATUS,
    /*分配角色或者分配权限*/
    ASSIGN,
    /*其他*/
    OTHER
}
