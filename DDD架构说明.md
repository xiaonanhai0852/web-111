# 元器件管理系统 - DDD架构详细说明

## 一、DDD分层架构概述

本系统严格遵循领域驱动设计（Domain-Driven Design, DDD）的分层架构原则，将系统划分为四个层次：

```
┌─────────────────────────────────────────────────────────┐
│                  用户接口层 (Interfaces)                 │
│  职责：处理HTTP请求，数据格式转换，调用应用服务          │
│  包含：Controller, DTO, RESTful API                     │
└─────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────┐
│                   应用层 (Application)                   │
│  职责：协调领域对象完成用例，管理事务边界                │
│  包含：ApplicationService                               │
└─────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────┐
│                    领域层 (Domain)                       │
│  职责：核心业务逻辑，领域模型，业务规则                  │
│  包含：Entity, ValueObject, DomainService, Repository   │
└─────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────┐
│                基础设施层 (Infrastructure)               │
│  职责：技术实现，数据持久化，外部服务集成                │
│  包含：RepositoryImpl, MyBatis Mapper, 配置类           │
└─────────────────────────────────────────────────────────┘
```

## 二、各层详细说明

### 1. 领域层 (Domain Layer)

领域层是系统的核心，包含业务逻辑和领域模型。

#### 1.1 聚合根 (Aggregate Root)

**ComponentType（元器件类型）**
- 作用：元器件类型是核心聚合根，代表一种具体的元器件
- 职责：
  - 维护元器件的基本信息（名称、型号、单价等）
  - 包含业务方法：`checkInventorySufficiency()` 检查库存是否充足
  - 确保型号的唯一性
- 文件位置：`domain/entity/ComponentType.java`

```java
public class ComponentType {
    private Long id;
    private String model;  // 型号（唯一标识）
    private Specification specification;  // 值对象
    
    // 领域方法：检查库存是否充足
    public boolean checkInventorySufficiency(Integer requiredQuantity, Integer currentStock) {
        // 业务逻辑
    }
}
```

#### 1.2 实体 (Entity)

**Supplier（供应商）**
- 作用：代表元器件的供应商
- 特点：有唯一标识（ID），生命周期独立
- 文件位置：`domain/entity/Supplier.java`

**StockInRecord（入库记录）**
- 作用：记录元器件的入库流水
- 特点：不可修改，只能新增
- 文件位置：`domain/entity/StockInRecord.java`

**StockOutRecord（取用记录）**
- 作用：记录元器件的取用流水
- 特点：不可修改，只能新增
- 文件位置：`domain/entity/StockOutRecord.java`

#### 1.3 值对象 (Value Object)

**Specification（元器件参数）**
- 作用：描述元器件的技术参数
- 特点：不可变（Immutable），无唯一标识，通过属性值判断相等性
- 属性：voltage, power, tolerance, temperature, packageType
- 文件位置：`domain/valueobject/Specification.java`

```java
public class Specification {
    private final String voltage;  // final 确保不可变
    private final String power;
    
    // 只有构造函数和getter，没有setter
    public Specification(String voltage, String power, ...) {
        this.voltage = voltage;
        this.power = power;
    }
}
```

**Address（地址）**
- 作用：描述供应商的地址信息
- 特点：不可变，值对象
- 文件位置：`domain/valueobject/Address.java`

#### 1.4 领域服务 (Domain Service)

**ComponentTakingService（元器件取用服务）**
- 作用：处理元器件取用的核心业务逻辑
- 为什么需要领域服务：
  - 取用操作涉及多个聚合（ComponentType、StockRecord）
  - 业务逻辑不适合放在单个实体中
- 核心方法：
  ```java
  @Transactional
  public StockOutRecord takeComponent(Long componentTypeId, Integer quantity, ...) {
      // 1. 检查元器件类型是否存在
      // 2. 检查库存是否充足（调用聚合根的业务方法）
      // 3. 创建取用记录
      // 4. 保存记录
  }
  ```
- 文件位置：`domain/service/ComponentTakingService.java`

#### 1.5 仓储接口 (Repository Interface)

仓储接口定义在领域层，但实现在基础设施层。这是DDD的重要原则：**依赖倒置**。

- `ComponentTypeRepository`：元器件类型仓储接口
- `StockRecordRepository`：库存记录仓储接口
- `SupplierRepository`：供应商仓储接口

### 2. 应用层 (Application Layer)

应用层协调领域对象完成用例，管理事务边界。

**ComponentApplicationService**
- 职责：
  - 协调领域对象完成入库、取用等用例
  - 管理事务（@Transactional）
  - 处理异常，返回统一格式的响应
- 特点：
  - 应用服务本身不包含业务逻辑
  - 只是调用领域服务和仓储
- 文件位置：`application/ComponentApplicationService.java`

```java
@Service
public class ComponentApplicationService {
    @Autowired
    private ComponentTakingService componentTakingService;  // 领域服务
    
    @Transactional
    public Map<String, Object> takeComponent(...) {
        try {
            // 调用领域服务
            StockOutRecord record = componentTakingService.takeComponent(...);
            return success(record);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
}
```

**SupplierApplicationService**
- 职责：供应商管理的应用服务
- 文件位置：`application/SupplierApplicationService.java`

**StatisticsApplicationService**
- 职责：统计查询的应用服务
- 文件位置：`application/StatisticsApplicationService.java`

### 3. 用户接口层 (Interfaces Layer)

用户接口层处理HTTP请求，进行数据格式转换。

#### 3.1 Controller

**ComponentController**
- 职责：
  - 接收HTTP请求
  - 验证请求参数
  - 调用应用服务
  - 返回HTTP响应
- 特点：
  - 使用DTO进行数据传输
  - 添加Swagger注解生成API文档
- 文件位置：`interfaces/controller/ComponentController.java`

```java
@RestController
@RequestMapping("/api/component")
@Api(tags = "元器件管理接口")
public class ComponentController {
    @Autowired
    private ComponentApplicationService componentApplicationService;
    
    @PostMapping("/stock-out")
    @ApiOperation(value = "取用元器件")
    public Map<String, Object> takeComponent(@RequestBody StockOutDTO dto) {
        return componentApplicationService.takeComponent(...);
    }
}
```

#### 3.2 DTO (Data Transfer Object)

DTO用于在不同层之间传输数据，避免直接暴露领域对象。

- `ComponentTypeDTO`：元器件类型数据传输对象
- `StockInDTO`：入库数据传输对象
- `StockOutDTO`：取用数据传输对象
- `SupplierDTO`：供应商数据传输对象

### 4. 基础设施层 (Infrastructure Layer)

基础设施层提供技术实现，如数据持久化。

#### 4.1 仓储实现 (Repository Implementation)

**ComponentTypeRepositoryImpl**
- 职责：实现领域层定义的仓储接口
- 技术：使用MyBatis进行数据访问
- 文件位置：`infrastructure/persistence/ComponentTypeRepositoryImpl.java`

```java
@Repository
public class ComponentTypeRepositoryImpl implements ComponentTypeRepository {
    @Autowired
    private ComponentTypeMapper componentTypeMapper;
    
    @Override
    public ComponentType findById(Long id) {
        return componentTypeMapper.findById(id);
    }
}
```

#### 4.2 MyBatis Mapper

- `ComponentTypeMapper.java` + `ComponentTypeMapper.xml`
- `StockRecordMapper.java` + `StockRecordMapper.xml`
- `SupplierMapper.java` + `SupplierMapper.xml`

## 三、DDD核心概念在本系统中的应用

### 1. 聚合 (Aggregate)

**元器件类型聚合**
- 聚合根：ComponentType
- 聚合边界：包含元器件的基本信息和参数（Specification值对象）
- 一致性边界：对元器件类型的修改必须通过聚合根

### 2. 值对象 (Value Object)

**为什么使用值对象？**
- Specification（参数）：多个属性组成一个完整的概念，不可变
- Address（地址）：多个属性组成一个完整的地址，不可变

**值对象的特点：**
- 不可变（Immutable）
- 无唯一标识
- 通过属性值判断相等性
- 可以被共享

### 3. 领域服务 (Domain Service)

**什么时候使用领域服务？**
- 业务逻辑涉及多个聚合
- 业务逻辑不适合放在单个实体中
- 业务逻辑是无状态的

**本系统的领域服务：**
- ComponentTakingService：取用操作涉及ComponentType和StockRecord

### 4. 仓储 (Repository)

**仓储模式的优势：**
- 隔离领域层和数据访问层
- 面向接口编程，易于测试
- 可以轻松切换数据访问技术

### 5. 依赖倒置原则

```
领域层定义接口 ← 基础设施层实现接口
     ↑
     │ 依赖方向
     │
应用层依赖领域层接口
```

## 四、DDD设计的优势

1. **业务逻辑集中在领域层**：易于理解和维护
2. **分层清晰**：每层职责明确，降低耦合
3. **易于测试**：可以独立测试领域逻辑
4. **易于扩展**：新增功能只需在对应层添加代码
5. **技术无关**：领域层不依赖具体技术实现

## 五、与传统三层架构的对比

| 特性 | 传统三层架构 | DDD分层架构 |
|------|-------------|------------|
| 业务逻辑位置 | 分散在Service层 | 集中在领域层 |
| 实体 | 贫血模型（只有getter/setter） | 充血模型（包含业务方法） |
| 依赖方向 | 单向依赖 | 依赖倒置 |
| 复杂度 | 适合简单业务 | 适合复杂业务 |
| 可维护性 | 业务逻辑分散 | 业务逻辑集中 |

## 六、总结

本系统通过DDD架构实现了：
1. **清晰的分层**：每层职责明确
2. **丰富的领域模型**：聚合根、实体、值对象、领域服务
3. **业务逻辑集中**：核心逻辑在领域层
4. **易于维护和扩展**：符合SOLID原则

这种架构特别适合业务逻辑复杂、需要长期维护的企业级应用。

