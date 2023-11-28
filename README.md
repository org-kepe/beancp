# beancp
The basic function is to copy beans (copy from bean to bean), and other common types of conversions can also be implemented.
## Maven
```xml
<dependency>
    <groupId>io.github.org-kepe</groupId>
    <artifactId>beancp</artifactId>
    <version>2.0.3</version>
</dependency>
```
## Basic examples

~~~Java
//from bean to bean
DemoUser user = ...;
DemoConsumer consumer = BeancpUtil.copy(user, DemoConsumer.class);
//copy bean to exist bean
DemoUser user = ...;
DemoConsumer consumer = ...;
BeancpUtil.copy(user, consumer);
//basic type conversion
BigDecimal num = BeancpUtil.copy("123.4", BigDecimal.class);
//copy list to list
List<DemoUser> userList = ...;
List<DemoConsumer> consumerList = BeancpUtil.copy(userList, BeancpUtil.type(List.class,DemoConsumer.class));
//copy bean to map
DemoUser user = ...;
Map<String,Object> map = BeancpUtil.copy(user, Map.class);
...
~~~
## Features:
* Support mutual conversion between Javabeans and Maps
* Support mutual conversion of List, array, and Set
* Support conversion of basic types (number, date, string, enum, byte [], etc.)
* Powerful performance, generated using bytecode
* By default, it supports the conversion of public attributes, but it can also support the conversion of protected and private attributes through feature parameters
* Provide APIs and annotations to support custom type conversions
## Examples
### Features
~~~Java
//If the original value is null,will not set value
DemoUser user = ...;
DemoConsumer consumer = BeancpUtil.copy(user, DemoConsumer.class,BeancpFeature.SETVALUE_WHENNOTNULL);
~~~
|  Feature   | description  |
|  :----  | :----  |
| SETVALUE_WHENNOTNULL  | If the original value is null,will not set value |
| ALLWAYS_NEW  | Copy a new object instead of the source object when assigning values |
| SETVALUE_TYPEEQUALS  | Only when the type is consistent will value be set without type conversion |
| BEAN2MAP_UNDERLINE  | When converting a Javabean to a map, the key of the map is in lowercase underlined form(userName->user_name) |
| BEAN2MAP_UNDERLINE_UPPER  | When converting a Javabean to a map, the key of the map is in upper underlined form(userName->USER_NAME) |
| ACCESS_PROTECTED  | Including protected attributes and default permission attributes |
| ACCESS_PRIVATE  | Including protected default and private permission attributes |
| THROW_EXCEPTION  | Throw an exception when encountering an exception |
### context
~~~Java

~~~
### ...