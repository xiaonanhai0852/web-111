package com.gok.demos.infrastructure.persistence.mapper;

import com.gok.demos.domain.entity.ComponentType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 元器件类型Mapper接口
 */
@Mapper
public interface ComponentTypeMapper {
    
    ComponentType findById(@Param("id") Long id);
    
    ComponentType findByModel(@Param("model") String model);
    
    List<ComponentType> findAll();
    
    List<ComponentType> findByPage(@Param("offset") Integer offset, @Param("size") Integer size);
    
    int count();
    
    int insert(ComponentType componentType);
    
    int update(ComponentType componentType);
    
    int delete(@Param("id") Long id);
    
    List<ComponentType> findBySupplierId(@Param("supplierId") Long supplierId);
}

