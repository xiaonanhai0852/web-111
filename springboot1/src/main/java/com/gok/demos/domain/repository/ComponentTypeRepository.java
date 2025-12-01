package com.gok.demos.domain.repository;

import com.gok.demos.domain.entity.ComponentType;
import java.util.List;

/**
 * 元器件类型仓储接口
 * 定义领域层的数据访问契约，由基础设施层实现
 */
public interface ComponentTypeRepository {
    
    /**
     * 根据ID查询元器件类型
     */
    ComponentType findById(Long id);
    
    /**
     * 根据型号查询元器件类型
     */
    ComponentType findByModel(String model);
    
    /**
     * 查询所有元器件类型
     */
    List<ComponentType> findAll();
    
    /**
     * 分页查询元器件类型
     */
    List<ComponentType> findByPage(Integer offset, Integer size);
    
    /**
     * 统计元器件类型总数
     */
    int count();
    
    /**
     * 保存元器件类型
     */
    int save(ComponentType componentType);
    
    /**
     * 更新元器件类型
     */
    int update(ComponentType componentType);
    
    /**
     * 删除元器件类型
     */
    int delete(Long id);
    
    /**
     * 根据供应商ID查询元器件类型
     */
    List<ComponentType> findBySupplierId(Long supplierId);
}

