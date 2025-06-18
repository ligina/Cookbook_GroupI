# Digital Recipe Cookbook 项目说明

## 项目概述
Digital Recipe Cookbook 是一个基于 Java 的应用程序，它允许用户以数字方式管理食谱。该应用提供了创建、编辑、显示和删除食谱的功能，同时还能管理每个食谱的配料和制备步骤。应用使用 JavaFX 构建用户界面，MyBatis 进行数据库操作。

## 功能特性
1. **用户认证**：用户可以注册和登录应用。
2. **食谱管理**：创建、编辑、显示和删除食谱。
3. **配料和步骤管理**：添加、更新和管理每个食谱的配料和制备步骤。
4. **图片处理**：上传和显示食谱的图片。
5. **份量调整**：调整份量并相应地更新配料数量。

## 项目结构
项目的目录结构如下：
```
Cookbook_GroupI/
├── .idea/                 # IntelliJ IDEA 配置文件
│   ├── vcs.xml            # 版本控制系统映射
│   ├── misc.xml           # 杂项项目设置
│   ├── encodings.xml      # 文件编码设置
│   └── .gitignore         # .idea 目录的忽略文件
├── src/
│   └── main/
│       ├── java/          # Java 源代码
│       │   ├── control/   # 控制器类
│       │   │   ├── RecipeCreateController.java
│       │   │   └── RecipeDisplayController.java
│       │   ├── dao/
│       │   │   └── mappers/ # 数据库映射器类
│       │   │       ├── Recipe.java
│       │   │       ├── RecipeIngredient.java
│       │   │       └── PreparationStep.java
│       │   ├── model/     # 模型类和接口
│       │   │   ├── Model.java
│       │   │   └── ModelMethod.java
│       │   └── view/      # 视图类
│       │       ├── RecipeCreateView.java
│       │       ├── RecipeDisplayView.java
│       │       ├── MainPageView.java
│       │       └── RecipeSelectView.java
│       └── resources/     # 资源，如配置文件
│           └── mybatis-config.xml
├── target/                # 编译输出目录（在.gitignore 中忽略）
├── .gitignore             # Git 忽略文件
└── pom.xml                # Maven 项目配置文件
```

## 技术选型
- **Java**：应用的核心编程语言。
- **JavaFX**：用于创建图形用户界面。
- **MyBatis**：一个持久化框架，用于数据库操作。
- **Maven**：构建自动化和依赖管理工具。
- **MySQL**：用于存储食谱、配料和用户信息的数据库。

## 依赖项
项目使用以下 Maven 依赖项：
- **MyBatis**：版本 3.5.13，用于数据库操作。
- **Mockito**：版本 2.23.4，用于单元测试。
- **TestFX**：版本 4.0.16-alpha，用于测试 JavaFX 应用程序。
- **MySQL Connector/J**：版本 8.0.33，用于连接 MySQL 数据库。
- **JUnit**：版本 4.13.1 和 5.8.1，用于单元测试。
- **JavaFX Controls、FXML 和 Media**：版本 20，用于 JavaFX 组件。

## 运行步骤
1. **克隆仓库**：
    ```sh
    git clone <repository-url>
    cd Cookbook_GroupI
    ```
    -建议使用github destop 进行操作
2. **配置数据库**：
    - 创建一个 MySQL 数据库，并在 `mybatis-config.xml` 文件中更新数据库连接详细信息。
    - 建议使用datagrid进行数据库管理（信息办上有相关激活手册）
3. **构建项目**：
    ```sh
    mvn clean install
    ```
4. **运行应用程序**：
    - 运行 `Enter/App.java` 类中的 `main` 方法启动应用程序。

## 未来改进方向
- **搜索功能**：实现搜索功能，按名称、配料或类别查找食谱。
- **用户个人资料**：添加用户个人资料，保存喜爱的食谱和个人设置。
- **营养分析**：提供更详细的食谱营养信息。

## 贡献者
- Ziang Liu
