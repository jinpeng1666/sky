# 1-开发环境搭建

## 1-1前端开发环境搭建

用`nginx`运行前端工程

![image-20250112200143023](https://raw.githubusercontent.com/jinpeng1666/picgo/master/Typora/Medical/image-20250112200143023.png)

![image-20250112200155250](https://raw.githubusercontent.com/jinpeng1666/picgo/master/Typora/Medical/image-20250112200155250.png)

双击启动`nginx.exe`即可启动`nginx`服务，访问端口号为80

## 1-2后端开发环境搭建

后端工程基于`maven`进行项目构建，进行分模块开发

项目的整体结构如下：

![image-20250112201133885](https://raw.githubusercontent.com/jinpeng1666/picgo/master/Typora/Medical/image-20250112201133885.png)

| **序号** | **名称**     | **说明**                                                     |
| -------- | ------------ | ------------------------------------------------------------ |
| 1        | sky-take-out | maven父工程，统一管理依赖版本，聚合其他子模块                |
| 2        | sky-common   | 子模块，存放公共类，例如：工具类、常量类、异常类等           |
| 3        | sky-pojo     | 子模块，存放实体类、VO、DTO等                                |
| 4        | sky-server   | 子模块，后端服务，存放配置文件、Controller、Service、Mapper、启动类等 |

