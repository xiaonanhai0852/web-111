# 元器件管理系统

## 一、项目概述

本项目是为解决小型电子企业研发实验室在元器件管理上的痛点，采用 **SpringBoot + Vue3.x** 前后端分离架构，并严格遵循 **领域驱动设计（DDD）** 思想开发的智能管理系统。系统实现了多角色权限下的元器件全生命周期管理（增、删、改、查、取用、统计）。


### 项目特色

✨ **严格遵循DDD架构**：领域层、应用层、接口层、基础设施层清晰分离
🎯 **核心业务逻辑集中**：聚合根、实体、值对象、领域服务完整实现
📊 **智能库存管理**：基于流水记录的动态库存计算，确保数据一致性
🔧 **完整的API文档**：Swagger自动生成交互式API文档
🐳 **Docker一键部署**：提供完整的Docker Compose配置
🧹 **代码简洁清晰**：已清理所有无用代码，只保留元器件管理系统核心功能

## 二、项目信息

### 1. 技术栈

**后端：**
- SpringBoot 2.6.13
- MyBatis 2.3.1
- MySQL 8.0
- Swagger 3.0.0
- JWT 3.14.0

**前端：**
- HTML5 + CSS3 + JavaScript
- 原生 Fetch API

### 2. 仓库地址

- 前端仓库地址：（待填写）
- 后端仓库地址：（待填写）

## 三、系统需求分析

### 3.1 业务需求分析

小型电子企业研发实验室需要管理"电阻器、电容器、传感器等"多品种、多供应商元器件。系统需要支持：
- 元器件的分类管理
- 供应商信息管理
- 入库和取用流程管理
- 库存实时统计
- 历史记录追溯

### 3.2 角色与功能性需求

#### 管理员用例：
1. **用户信息维护**：对普通用户账号的增删改查
2. **元器件类型管理**：创建、编辑、删除元器件类型（如"电阻-10KΩ"）
3. **供应商管理**：对供应商信息的增删改查

#### 普通用户用例：
1. **添加元器件入库**：通过下拉菜单选择已有类型，填写数量、采购日期、采购人（强调：普通用户不能创建新类型）
2. **取用元器件**：选择类型和型号，填写取用数量，系统自动扣减库存
3. **统计查询**：
   - 按元器件类型统计库存总量
   - 按具体型号查询当前库存数量
   - 查询元器件的取用历史记录

### 3.3 核心领域对象与属性

- **用户（User）**：用户名、密码、手机、邮箱、角色
- **元器件类型（ComponentType）**：名称、型号（唯一）、供应商、单价、图片、参数
- **供应商（Supplier）**：名称、地址、电话
- **库存（Stock）**：元器件类型、当前数量（衍生概念，由入库和取用操作决定）
- **入库记录（StockInRecord）**：元器件类型、数量、采购日期、采购人
- **取用记录（StockOutRecord）**：元器件类型、数量、取用人、取用日期

## 四、系统设计

### 4.1 系统架构设计

本系统采用 **DDD 分层架构**：

```
┌─────────────────────────────────────────┐
│         用户接口层 (Interfaces)          │
│    Controller + DTO + RESTful API       │
├─────────────────────────────────────────┤
│          应用层 (Application)            │
│   ApplicationService (协调领域对象)      │
├─────────────────────────────────────────┤
│            领域层 (Domain)               │
│  Entity + ValueObject + DomainService   │
│         + Repository Interface          │
├─────────────────────────────────────────┤
│       基础设施层 (Infrastructure)        │
│   RepositoryImpl + MyBatis Mapper       │
└─────────────────────────────────────────┘
```

### 4.2 技术选型说明

- **SpringBoot 2.6.13**：提供快速开发和自动配置
- **MyBatis**：灵活的 SQL 映射框架
- **MySQL 8.0**：关系型数据库
- **Swagger 3.0**：API 文档自动生成
- **JWT**：用户认证和授权

### 4.3 基于DDD的领域模型设计

#### 领域模型图

```
┌──────────────────────────────────────────────┐
│         ComponentType (聚合根)                │
│  - id, name, model, supplierId, unitPrice    │
│  - specification (值对象)                     │
│  + checkInventorySufficiency()               │
└──────────────────────────────────────────────┘
                    │
                    │ 关联
                    ▼
┌──────────────────────────────────────────────┐
│         Specification (值对象)                │
│  - voltage, power, tolerance, temperature    │
│  - packageType, otherParams                  │
└──────────────────────────────────────────────┘

┌──────────────────────────────────────────────┐
│            Supplier (实体)                    │
│  - id, name, phone, email, contactPerson     │
│  - address (值对象)                           │
└──────────────────────────────────────────────┘

┌──────────────────────────────────────────────┐
│         StockInRecord (实体)                  │
│  - componentTypeId, quantity, purchaseDate   │
│  - purchaserId, purchaserName                │
└──────────────────────────────────────────────┘

┌──────────────────────────────────────────────┐
│         StockOutRecord (实体)                 │
│  - componentTypeId, quantity, userId         │
│  - userName, purpose, projectName            │
└──────────────────────────────────────────────┘
```

#### 聚合根
- **ComponentType（元器件类型）**：核心聚合根，包含型号、参数等核心信息

#### 实体
- **User（用户）**
- **Supplier（供应商）**
- **StockInRecord（入库记录）**
- **StockOutRecord（取用记录）**

#### 值对象
- **Address（地址）**：province, city, district, street, postalCode
- **Specification（元器件参数）**：voltage, power, tolerance, temperature, packageType

#### 领域服务
**ComponentTakingService（元器件取用服务）**：
- 协调元器件类型（检查型号存在性）
- 检查并扣减库存
- 生成取用记录
- 核心方法：`takeComponent(...)`

### 4.4 数据库设计

#### E-R图说明

```
users ──┐
        │
        ├──< stock_in_records >──┐
        │                         │
        └──< stock_out_records    │
                                  │
suppliers ──< component_types <───┘
```

#### 表结构说明

1. **users**：用户表，存储用户信息和角色
2. **suppliers**：供应商表，存储供应商信息和地址
3. **component_types**：元器件类型表（聚合根），model字段唯一索引
4. **stock_in_records**：入库流水表
5. **stock_out_records**：取用流水表

**重要设计决策**：
- 库存数量不直接存储，而是通过 `SUM(入库数量) - SUM(取用数量)` 动态计算
- 这种设计确保数据一致性，所有流水记录都保留便于审计

### 4.5 API 设计与测试

#### RESTful API 列表

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/component/types | 获取元器件类型列表（用于下拉框） |
| POST | /api/component/type | 添加元器件类型（管理员） |
| POST | /api/component/stock-in | 添加入库记录 |
| POST | /api/component/stock-out | 执行取用操作 |
| GET | /api/component/stock/{id} | 查询当前库存 |
| GET | /api/component/stock-out/history/{id} | 查询取用历史 |
| GET | /api/statistics/inventory | 统计库存 |
| GET | /api/statistics/stock-by-model | 按型号查询库存 |
| GET | /api/supplier/list | 获取供应商列表 |
| POST | /api/supplier | 添加供应商 |
| PUT | /api/supplier/{id} | 更新供应商 |
| DELETE | /api/supplier/{id} | 删除供应商 |

#### Swagger/OpenAPI 集成

访问地址：`http://localhost:8080/swagger-ui/index.html`

系统已集成 Swagger 3.0，可以：
- 查看所有 API 接口文档
- 在线测试接口
- 查看请求/响应示例

## 五、系统实现与核心代码

### 5.1 后端实现

#### 项目包结构（DDD分层）

```
com.gok.demos
├── domain/                          # 领域层
│   ├── entity/                      # 实体
│   │   ├── ComponentType.java       # 元器件类型（聚合根）
│   │   ├── Supplier.java            # 供应商
│   │   ├── StockInRecord.java       # 入库记录
│   │   └── StockOutRecord.java      # 取用记录
│   ├── valueobject/                 # 值对象
│   │   ├── Specification.java       # 元器件参数
│   │   └── Address.java             # 地址
│   ├── repository/                  # 仓储接口
│   │   ├── ComponentTypeRepository.java
│   │   ├── StockRecordRepository.java
│   │   └── SupplierRepository.java
│   └── service/                     # 领域服务
│       └── ComponentTakingService.java
├── application/                     # 应用层
│   ├── ComponentApplicationService.java
│   ├── SupplierApplicationService.java
│   └── StatisticsApplicationService.java
├── interfaces/                      # 用户接口层
│   ├── controller/                  # 控制器
│   │   ├── ComponentController.java
│   │   ├── StatisticsController.java
│   │   └── SupplierController.java
│   └── dto/                         # 数据传输对象
│       ├── ComponentTypeDTO.java
│       ├── StockInDTO.java
│       ├── StockOutDTO.java
│       └── SupplierDTO.java
└── infrastructure/                  # 基础设施层
    └── persistence/                 # 持久化
        ├── ComponentTypeRepositoryImpl.java
        ├── StockRecordRepositoryImpl.java
        ├── SupplierRepositoryImpl.java
        └── mapper/                  # MyBatis Mapper
            ├── ComponentTypeMapper.java
            ├── StockRecordMapper.java
            └── SupplierMapper.java
```

## 六、快速开始

### 6.1 环境要求

- JDK 1.8+
- MySQL 8.0+
- Maven 3.6+

### 6.2 数据库初始化

```bash
# 1. 创建数据库并导入表结构
mysql -u root -p < springboot1/src/main/resources/sql/component_management_schema.sql

# 2. 导入示例数据（可选）
mysql -u root -p < springboot1/src/main/resources/sql/sample_data.sql
```

### 6.3 配置数据库连接

编辑 `springboot1/src/main/resources/application.properties`：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/component_management?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=your_password
```

### 6.4 启动后端服务

```bash
cd springboot1
mvn clean install
mvn spring-boot:run
```

访问 Swagger 文档：`http://localhost:8080/swagger-ui/index.html`

### 6.5 启动前端

直接在浏览器中打开 `前端/元器件管理.html`

## 七、项目总结与展望

### 7.1 收获总结

**技术层面：**
- 掌握了前后端分离开发
- 熟悉了 SpringBoot + MyBatis 技术栈

**架构层面：**
- 实践了 DDD 思想，学会了如何通过领域模型分析复杂业务
- 掌握了如何通过分层架构解耦代码
- 理解了聚合根、实体、值对象的区别和应用场景

**工程层面：**
- 熟悉了 Swagger 文档化
- 掌握了 RESTful API 设计规范

### 7.2 难点与解决方案

**难点1：DDD领域模型的抽象**
- 解决方案：反复与业务需求对照，确认"取用"操作的最小完整单元是"元器件类型"

**难点2：库存数量的实时计算与一致性保证**
- 解决方案：采用流水记录法，通过事务确保入库和取用记录的原子性，从而保证统计结果的最终一致性

### 7.3 不足与展望

**不足：**
- 库存预警功能未实现
- 权限控制较简单（基于角色，未做到按钮级别）
- 前端使用原生 JavaScript，未使用 Vue3

**展望：**
- 增加库存阈值预警通知（邮件）
- 引入条形码/二维码进行快速入库和取用
- 实现更精细的 RBAC 权限模型
- 使用 Vue3 重构前端，提升用户体验

## 八、许可证

MIT License

