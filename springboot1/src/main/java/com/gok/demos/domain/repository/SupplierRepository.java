package com.gok.demos.domain.repository;

import com.gok.demos.domain.entity.Supplier;
import java.util.List;

/**
 * 供应商仓储接口
 */
public interface SupplierRepository {
    
    /**
     * 根据ID查询供应商
     */
    Supplier findById(Long id);
    
    /**
     * 查询所有供应商
     */
    List<Supplier> findAll();
    
    /**
     * 分页查询供应商
     */
    List<Supplier> findByPage(Integer offset, Integer size);
    
    /**
     * 统计供应商总数
     */
    int count();
    
    /**
     * 保存供应商
     */
    int save(Supplier supplier);
    
    /**
     * 更新供应商
     */
    int update(Supplier supplier);
    
    /**
     * 删除供应商
     */
    int delete(Long id);
}

