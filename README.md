# 概述

基于Key-Value结构，事件记录框架。
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


