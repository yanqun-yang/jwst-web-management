package com.yanqun.service;

import com.yanqun.pojo.Dept;

import java.util.List;

//部门业务规则
public interface DeptService {
    /**
     * 查询全部部门数据
     * @return
     */
    List<Dept> list();

    /**
     * 删除部门
     * @param id
     */
    void delete(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    void add(Dept dept);

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    Dept selectById(Integer id);

    /**
     * 更新部门名称
     * @param dept
     */
    void update(Dept dept);
}