# beancp
Provide some processing APIs for Java objects, such as object copying(include bean copy,beans to Maps, lists to lists, strings to dates...), object cloning, getting or setting the values of Javabeans, etc
## Maven
```xml
<dependency>
    <groupId>io.github.org-kepe</groupId>
    <artifactId>beancp</artifactId>
    <version>2.0.5</version>
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
* Support clone for any Object
* Support get or set properties for JavaBeans
## Examples
### Feature
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

It can also be configured through global features with configAdd and configRemove
~~~Java
BeancpUtil.configAdd(BeancpFeature.SETVALUE_WHENNOTNULL);
~~~
### context
~~~Java
//The 'id' property of the DemoConsumer class is not assigned a value
DemoUser user = ...;
DemoConsumer consumer = BeancpUtil.copy(user, DemoConsumer.class,BeancpUtil.newContext().disallowKey(DemoConsumer.class, "id"));
~~~
|  api   | description  |
|  :----  | :----  |
| disallowKey  | Disallow the assignment of certain attributes in a class |
| allowOnly  | Only the assignment of certain attributes in the class |
| addValueFilter  | add a value filter when set value for attribute |
| setExceptionFilter  | set exception filter |
### annotation
~~~Java
	@BeancpIgnore
	private String firstName;
	
	@BeancpProperty("familyName")
	public String getLastName() {
		return lastName;
	}
~~~
|  annotation   | description  |
|  :----  | :----  |
| @BeancpIgnore  | ignore this method or field |
| @BeancpProperty  | It can be placed on the parameters of methods, fields, and constructors, and set one or more other names. It can also be placed on a non get set method, and the beancp framework will automatically parse it as a get set method |
### clone
~~~Java
//clone object
DemoUser user = ...
DemoUser user2 = BeancpUtil.clone(user);
~~~
### get and set property
~~~Java
//Obtain Javabean property value
String age=BeancpUtil.getProperty(user,"age",String.class);
String userName = BeancpUtil.getProperty(user,"userName",String.class);
//By default, the underline format is also supported.The same goes for setProperty
String userName1 = BeancpUtil.getProperty(user,"user_name",String.class);

//Setting Property Value for Javabeans
BeancpUtil.setProperty(user, "id", "a");
BeancpUtil.setProperty(user, "live", true);
~~~
|  api   | description  |
|  :----  | :----  |
| getProperty  | Obtain Javabean property value |
| setProperty  | Setting Property Value for Javabeans |
### Custom Type Conversion
~~~Java
//register Custom Type Conversion
BeancpUtil.registerTypeConversion(DemoUser.class, DemoConsumer.class, (invocation,context,fromObj,toObj)->{
	DemoUser user=(DemoUser)fromObj;
	DemoConsumer consumer=(DemoConsumer)toObj;
	if(consumer==null) {
		consumer=new DemoConsumer();
	}
	consumer.setFamilyName(user.getLastName());
	return consumer;
}, 1);
~~~
### ...