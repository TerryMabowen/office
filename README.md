## OFFICE

### 个人技术提升用

* 准备发布

```
    mvn release:prepare -Darguments="-Dmaven.test.skip=true -Dmaven.javadoc.skip=true"
```
* 错误回滚

```
    mvn release:rollback
```

* 正式发布

```
    mvn release:perform -Darguments="-Dmaven.test.skip=true -Dmaven.javadoc.skip=true"
```
