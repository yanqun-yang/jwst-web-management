# jwst-web-management

---

[TOC]

## 接口



## 知识点

### 事务

#### 事务属性-回滚rollbackFor

默认情况下，只有出现RuntimeException才回归异常。rollbackFor属性用于控制出现何种异常类型，回滚事务。

#### 事务属性-传播行为propagation

- 事务传播行为：指的就是当一个事务方法被另一个事务方法调用时，这个事务方法应该如何进行事务控制
- REQUIRED：大部分情况下都是用该传播行为即可。
- REQUIRES_NEW：当我们不希望事务之间相互影响时，可以使用该传播行为。比如：下订单前需要记录日志，不论订单保存成功与
  否，都需要保证日志记录能够记录成功
