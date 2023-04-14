整个项目为一个基础架构的示例学习项目，包含如下几个部分
- basic-arch-entity: 基础的pojo类，公共的实体类
- basic-arch-log: 公共的日志配置文件,日志系统、分布式日志tracing相关的实现，基于日志采集的性能监控系统、系统容量监控与分析的相关实现
- basic-arch-common:各个模块公共的工具类、常量类等
- basic-arch-serial:序列号生成器,包好一个基于leaf算法的序列号生成器以及snowflake流水号生成器服务
- basic-arch-payment:基于shardingsphere做的分库分表,包含部分支付相关的表与设计，主要用来演示分库分表在具体系统中的使用与一些最佳实践方式
- basic-arch-admin:后台管理界面,基于ruoyi-vue项目fork而来，包含一些基于代码生成的工具类以及一些后台管理功能的标准实现
- build-tools:整个项目build相关的pwd、checkstyle插件规则目录，用来规范整体项目的代码风格、编码规范
