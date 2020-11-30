# 概述

搜狗pingback记录框架。
特点：
* 子线程记录事件
* 底层记录采用mmkv
* 记录数据结构支持常用类型，并且支持自定义扩展
* 模块依赖解耦

# 架构
# 细节
# 扩展
## SafeReentrantLock
内置有向图数据结构，存储锁的获取和释放行为。能够检查潜在的死锁风险。
从而达到安全锁功能
## ImeThread
轻量级线程池实现。按照使用场景，提供UI，FILE和IO不同类型线程任务，简化使用调用。
* UI Android主线程
* FILE 面向文件系统
* IO io操作，ipc调用，或者网络请求

