# 概述

搜狗pingback记录框架。
## 特点：
* 子线程记录事件
* 底层记录采用mmkv
* 记录数据结构支持常用类型，并且支持自定义扩展
* 模块依赖解耦

## 事件类型<br>
* Int类型 increaseNumber
* Map类型 increaseOneMap
* 多层Map类型 increaseMultiMap
* Gson类型 

# 实现<br>
* EventRecord
  外部接口
* EventEngine
  处理引擎
 * EventProto
   事件proto数据结构，支持异步和缓存
 * EventParamProto
   参数proto数据结构，支持异步和缓存
 * MemoryCache
   事件缓存
* BaseNumberOperation
 * IntOperation
 * LongOperation
* BaseMapOperation
* BaseGsonOperation
 * CustomGsonOperation
* MmkvStorage

# 扩展<br>
## SafeReentrantLock
内置有向图数据结构，存储锁的获取和释放行为。能够检查潜在的死锁风险。
从而达到安全锁功能

## ImeThread
轻量级线程池实现。按照使用场景，提供UI，FILE和IO不同类型线程任务，简化使用调用。
* UI Android主线程
* FILE 面向文件系统
* IO io操作，ipc调用，或者网络请求

