package com.gok.demos.interfaces.controller;

import com.gok.demos.application.ComponentApplicationService;
import com.gok.demos.domain.entity.ComponentType;
import com.gok.demos.domain.repository.ComponentTypeRepository;
import com.gok.demos.domain.valueobject.Specification;
import com.gok.demos.interfaces.dto.ComponentTypeDTO;
import com.gok.demos.interfaces.dto.StockInDTO;
import com.gok.demos.interfaces.dto.StockOutDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 元器件控制器
 * 用户接口层：处理HTTP请求，调用应用服务
 */
@RestController
@RequestMapping("/api/component")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080", "file://*", "http://localhost:63342"}, 
             allowCredentials = "true")
@Api(tags = "元器件管理接口")
public class ComponentController {

    @Autowired
    private ComponentApplicationService componentApplicationService;

    @Autowired
    private ComponentTypeRepository componentTypeRepository;

    /**
     * 获取元器件类型列表（用于下拉框）
     */
    @GetMapping("/types")
    @ApiOperation(value = "获取元器件类型列表", notes = "用于下拉框选择")
    public Map<String, Object> getComponentTypes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return componentApplicationService.getComponentTypeList(page, size);
    }

    /**
     * 添加元器件类型（管理员功能）
     */
    @PostMapping("/type")
    @ApiOperation(value = "添加元器件类型", notes = "管理员创建新的元器件类型")
    public Map<String, Object> addComponentType(@RequestBody ComponentTypeDTO dto) {
        try {
            // 创建值对象
            Specification specification = new Specification(
                dto.getVoltage(), dto.getPower(), dto.getTolerance(),
                dto.getTemperature(), dto.getPackageType(), dto.getOtherParams()
            );

            // 创建聚合根
            ComponentType componentType = new ComponentType();
            componentType.setName(dto.getName());
            componentType.setModel(dto.getModel());
            componentType.setSupplierId(dto.getSupplierId());
            componentType.setUnitPrice(dto.getUnitPrice());
            componentType.setImageUrl(dto.getImageUrl());
            componentType.setSpecification(specification);
            componentType.setCreatedAt(LocalDateTime.now());
            componentType.setUpdatedAt(LocalDateTime.now());

            int rows = componentTypeRepository.save(componentType);
            
            Map<String, Object> result = new java.util.HashMap<>();
            if (rows > 0) {
                result.put("success", true);
                result.put("message", "添加成功");
                result.put("data", componentType);
            } else {
                result.put("success", false);
                result.put("message", "添加失败");
            }
            return result;
        } catch (Exception e) {
            Map<String, Object> result = new java.util.HashMap<>();
            result.put("success", false);
            result.put("message", "添加失败：" + e.getMessage());
            return result;
        }
    }

    /**
     * 添加入库记录（普通用户功能）
     */
    @PostMapping("/stock-in")
    @ApiOperation(value = "添加入库记录", notes = "普通用户选择已有类型进行入库")
    public Map<String, Object> addStockIn(@RequestBody StockInDTO dto) {
        return componentApplicationService.addStockIn(
            dto.getComponentTypeId(), dto.getQuantity(), dto.getPurchaseDate(),
            dto.getPurchaserId(), dto.getPurchaserName(), 
            dto.getBatchNumber(), dto.getRemarks()
        );
    }

    /**
     * 取用元器件（普通用户功能）
     */
    @PostMapping("/stock-out")
    @ApiOperation(value = "取用元器件", notes = "普通用户取用元器件，系统自动扣减库存")
    public Map<String, Object> takeComponent(@RequestBody StockOutDTO dto) {
        return componentApplicationService.takeComponent(
            dto.getComponentTypeId(), dto.getQuantity(), dto.getUserId(),
            dto.getUserName(), dto.getPurpose(), dto.getProjectName(), dto.getRemarks()
        );
    }

    /**
     * 查询元器件的取用历史
     */
    @GetMapping("/stock-out/history/{componentTypeId}")
    @ApiOperation(value = "查询取用历史", notes = "查询指定元器件的取用历史记录")
    public Map<String, Object> getStockOutHistory(@PathVariable Long componentTypeId) {
        return componentApplicationService.getStockOutHistory(componentTypeId);
    }

    /**
     * 查询当前库存
     */
    @GetMapping("/stock/{componentTypeId}")
    @ApiOperation(value = "查询当前库存", notes = "查询指定元器件的当前库存数量")
    public Map<String, Object> getCurrentStock(@PathVariable Long componentTypeId) {
        return componentApplicationService.getCurrentStock(componentTypeId);
    }

    /**
     * 获取所有入库记录
     */
    @GetMapping("/stock-in/records")
    @ApiOperation(value = "获取入库记录", notes = "分页获取所有入库记录")
    public Map<String, Object> getStockInRecords(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return componentApplicationService.getAllStockInRecords(page, size);
    }

    /**
     * 获取所有出库记录
     */
    @GetMapping("/stock-out/records")
    @ApiOperation(value = "获取出库记录", notes = "分页获取所有出库记录")
    public Map<String, Object> getStockOutRecords(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return componentApplicationService.getAllStockOutRecords(page, size);
    }

    /**
     * 删除元器件类型（管理员功能）
     */
    @DeleteMapping("/type/{id}")
    @ApiOperation(value = "删除元器件类型", notes = "管理员删除元器件类型")
    public Map<String, Object> deleteComponentType(@PathVariable Long id) {
        return componentApplicationService.deleteComponentType(id);
    }
}

