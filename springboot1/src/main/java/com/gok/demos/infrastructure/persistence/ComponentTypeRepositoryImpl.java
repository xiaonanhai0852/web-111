package com.gok.demos.infrastructure.persistence;

import com.gok.demos.domain.entity.ComponentType;
import com.gok.demos.domain.repository.ComponentTypeRepository;
import com.gok.demos.infrastructure.persistence.mapper.ComponentTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 元器件类型仓储实现
 * 基础设施层实现领域层定义的仓储接口
 */
@Repository
public class ComponentTypeRepositoryImpl implements ComponentTypeRepository {

    @Autowired
    private ComponentTypeMapper componentTypeMapper;

    @Override
    public ComponentType findById(Long id) {
        return componentTypeMapper.findById(id);
    }

    @Override
    public ComponentType findByModel(String model) {
        return componentTypeMapper.findByModel(model);
    }

    @Override
    public List<ComponentType> findAll() {
        return componentTypeMapper.findAll();
    }

    @Override
    public List<ComponentType> findByPage(Integer offset, Integer size) {
        return componentTypeMapper.findByPage(offset, size);
    }

    @Override
    public int count() {
        return componentTypeMapper.count();
    }

    @Override
    public int save(ComponentType componentType) {
        return componentTypeMapper.insert(componentType);
    }

    @Override
    public int update(ComponentType componentType) {
        return componentTypeMapper.update(componentType);
    }

    @Override
    public int delete(Long id) {
        return componentTypeMapper.delete(id);
    }

    @Override
    public List<ComponentType> findBySupplierId(Long supplierId) {
        return componentTypeMapper.findBySupplierId(supplierId);
    }
}

