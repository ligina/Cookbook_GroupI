# CookBook Recipe Management System

一个基于JavaFX和MyBatis的食谱管理系统，支持用户注册登录、食谱创建、查看和营养信息计算。

## 项目概述

CookBook是一个桌面应用程序，允许用户管理个人食谱集合。用户可以创建、查看、编辑和删除食谱，每个食谱包含详细的配料信息、制作步骤和营养成分分析。

## 技术栈

- **前端**: JavaFX 20 + FXML
- **后端**: Java 18
- **数据库**: MySQL 8.0
- **ORM框架**: MyBatis 3.5.13
- **构建工具**: Maven
- **测试框架**: JUnit 5, TestFX, Mockito

## 项目架构

### 整体架构
项目采用经典的MVC（Model-View-Controller）分层架构：

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │   JavaFX Views  │  │ FXML Controllers │  │  FXML Files  │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                    Business Layer                           │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │     Model       │  │    Services     │  │ SessionMgr   │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                    Data Access Layer                        │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │   DAO Mappers   │  │   Entity POJOs  │  │   MyBatis    │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                    Database Layer                           │
│                      MySQL Database                         │
└─────────────────────────────────────────────────────────────┘
```

### 目录结构详解

```
src/
├── main/
│   ├── java/
│   │   ├── Enter/                    # 应用程序入口
│   │   │   ├── App.java             # 主启动类
│   │   │   └── ApplicationEntrance.java  # JavaFX应用入口
│   │   ├── view/                    # 视图层
│   │   │   ├── LoginView.java       # 登录视图
│   │   │   ├── RecipeCreateView.java    # 食谱创建视图
│   │   │   ├── RecipeDisplayView.java   # 食谱显示视图
│   │   │   └── RecipeSelectView.java    # 食谱选择视图
│   │   ├── control/                 # 控制器层
│   │   │   ├── RecipeCreateFXMLController.java  # 食谱创建控制器
│   │   │   ├── RecipeDisplayFXMLController.java # 食谱显示控制器
│   │   │   ├── RecipeSelectFXMLController.java  # 食谱选择控制器
│   │   │   ├── TrailViewSignuppage.java         # 注册页面控制器
│   │   │   └── TrialViewLoginpage.java          # 登录页面控制器
│   │   ├── model/                   # 业务逻辑层
│   │   │   ├── Model.java           # 主要业务逻辑实现
│   │   │   └── ModelMethod.java     # 业务逻辑接口
│   │   ├── dao/mappers/             # 数据访问层
│   │   │   ├── User.java            # 用户实体类
│   │   │   ├── UserMapper.java      # 用户数据访问接口
│   │   │   ├── UserMapper.xml       # 用户SQL映射文件
│   │   │   ├── Recipe.java          # 食谱实体类
│   │   │   ├── RecipeMapper.java    # 食谱数据访问接口
│   │   │   ├── RecipeMapper.xml     # 食谱SQL映射文件
│   │   │   ├── RecipeIngredient.java    # 食谱配料实体类
│   │   │   ├── RecipeIngredientMapper.java  # 配料数据访问接口
│   │   │   ├── RecipeIngredientMapper.xml   # 配料SQL映射文件
│   │   │   ├── PreparationStep.java     # 制作步骤实体类
│   │   │   ├── PreparationStepMapper.java   # 步骤数据访问接口
│   │   │   └── PreparationStepMapper.xml    # 步骤SQL映射文件
│   │   ├── service/                 # 服务层
│   │   │   └── NutritionService.java    # 营养信息服务
│   │   └── config/                  # 配置层
│   │       └── SessionManager.java  # 会话管理器
│   └── resources/                   # 资源文件
│       ├── mybatis-config.xml       # MyBatis配置文件
│       ├── jdbc.properties          # 数据库连接配置
│       ├── recipe_create.fxml       # 食谱创建界面
│       ├── recipe_display.fxml      # 食谱显示界面
│       ├── recipe_select.fxml       # 食谱选择界面
│       ├── trail_view_signuppage.fxml   # 注册页面界面
│       └── trial_view_loginpage.fxml    # 登录页面界面
├── sql/                             # 数据库脚本
│   ├── cookbook_complete.sql        # 完整数据库初始化脚本
│   ├── cookbook_new.sql            # 新数据库创建脚本
│   └── recipe.json                 # 示例数据
└── test/                           # 测试代码
    └── java/test/
        ├── DaoMappersTest.java     # 数据访问层测试
        └── SignTest.java           # 登录注册测试
```

## 层次关系分析

### 1. 表示层 (Presentation Layer)
- **View类**: 负责创建和管理JavaFX窗口
- **FXML文件**: 定义用户界面布局
- **Controller类**: 处理用户交互事件，连接视图和业务逻辑

### 2. 业务逻辑层 (Business Layer)
- **Model类**: 实现核心业务逻辑，如用户认证、食谱管理等
- **Service类**: 提供专门的服务，如营养信息计算
- **SessionManager**: 管理用户会话状态

### 3. 数据访问层 (Data Access Layer)
- **Entity类**: 定义数据实体（User, Recipe, RecipeIngredient, PreparationStep）
- **Mapper接口**: 定义数据访问方法
- **Mapper XML**: 实现SQL映射

### 4. 数据库层 (Database Layer)
- **MySQL数据库**: 存储用户、食谱、配料和制作步骤数据

## 核心功能模块

### 1. 用户管理模块
- 用户注册：支持用户名和密码注册，自动生成用户ID
- 用户登录：验证用户凭据，建立会话
- 会话管理：维护当前登录用户状态

### 2. 食谱管理模块
- 食谱创建：创建新食谱，包含基本信息、配料和制作步骤
- 食谱查看：显示食谱详细信息，支持按份数调整配料用量
- 食谱编辑：修改现有食谱信息
- 食谱删除：删除不需要的食谱

### 3. 营养信息模块
- 自动计算：基于配料自动计算营养成分
- 营养数据库：内置常见食材的营养信息
- 单位转换：支持多种计量单位的自动转换

## 数据库设计

### 主要数据表

1. **users表** - 用户信息
   - user_id (主键)
   - user_name (用户名)
   - password (密码)

2. **recipe表** - 食谱基本信息
   - recipe_id (主键)
   - name (食谱名称)
   - serveamount (份数)
   - preparationTime (准备时间)
   - cookingTime (烹饪时间)
   - image_url (图片路径)
   - calories, protein, carbohydrates, fat, fiber (营养信息)

3. **ingredient表** - 食谱配料
   - recipe_id (外键)
   - name (配料名称)
   - quantity (用量)
   - unit (单位)
   - description (描述)
   - 营养信息字段

4. **preparationstep表** - 制作步骤
   - recipe_id (外键)
   - step (步骤序号)
   - description (步骤描述)

## 环境要求

- Java 18+
- MySQL 8.0+
- Maven 3.6+
- JavaFX 20

## 安装和配置

### 1. 克隆项目
```bash
git clone <repository-url>
cd cookbook-recipe-management
```

### 2. 数据库配置
1. 安装MySQL 8.0
2. 创建数据库：
   ```sql
   CREATE DATABASE cookbook CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
   ```
3. 执行初始化脚本：
   ```bash
   mysql -u root -p cookbook < src/sql/cookbook_complete.sql
   ```

### 3. 配置数据库连接
编辑 `src/main/resources/jdbc.properties`：
```properties
database.driver = com.mysql.cj.jdbc.Driver
database.url = jdbc:mysql://localhost:3306/cookbook
database.username = your_username
database.password = your_password
```

### 4. 编译和运行
```bash
# 编译项目
mvn clean compile

# 运行应用程序
mvn exec:java -Dexec.mainClass="Enter.App"
```

## 使用说明

### 1. 启动应用
运行应用后，首先显示登录界面。

### 2. 用户注册/登录
- **注册**: 点击注册按钮，输入用户名和密码
- **登录**: 输入已注册的用户名和密码

### 3. 食谱管理
- **查看食谱**: 登录后进入食谱选择界面，可以搜索和选择食谱
- **创建食谱**: 点击创建按钮，填写食谱信息、添加配料和制作步骤
- **查看详情**: 选择食谱后可查看详细信息，支持调整份数

### 4. 营养信息
系统会自动计算食谱的营养成分，包括：
- 卡路里
- 蛋白质
- 碳水化合物
- 脂肪
- 纤维

## 开发指南

### 添加新功能
1. 在相应的层次中添加新类
2. 更新数据库结构（如需要）
3. 创建对应的Mapper和XML文件
4. 实现业务逻辑
5. 创建或更新UI界面

### 测试
```bash
# 运行所有测试
mvn test

# 运行特定测试
mvn test -Dtest=DaoMappersTest
```

## 项目特色

1. **分层架构**: 清晰的MVC分层，便于维护和扩展
2. **营养计算**: 自动营养成分计算，支持多种食材
3. **用户友好**: 直观的JavaFX界面，操作简单
4. **数据持久化**: 使用MyBatis进行高效的数据库操作
5. **单位转换**: 智能的计量单位转换系统

## 贡献指南

1. Fork项目
2. 创建功能分支
3. 提交更改
4. 推送到分支
5. 创建Pull Request
