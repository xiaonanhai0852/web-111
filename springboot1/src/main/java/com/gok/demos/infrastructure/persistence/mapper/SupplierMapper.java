package com.gok.demos.infrastructure.persistence.mapper;

import com.gok.demos.domain.entity.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应商Mapper接口
 */
@Mapper
public interface SupplierMapper {
    
    Supplier findById(@Param("id") Long id);
    
    List<Supplier> findAll();
    
    List<Supplier> findByPage(@Param("offset") Integer offset, @Param("size") Integer size);
    
    int count();
    
    int insert(Supplier supplier);
    
    int update(Supplier supplier);
    
    int delete(@Param("id") Long id);
}

