

**启动数据库命令：**

```shell
/opt/homebrew/Cellar/mysql/8.3.0_1/bin/mysql.server start
```







```sql
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `type` int DEFAULT NULL COMMENT '0-普通用户; 1-超级管理员; 2-版主;',
  `status` int DEFAULT NULL COMMENT '0-未激活; 1-已激活;',
  `activation_code` varchar(100) DEFAULT NULL,
  `header_url` varchar(200) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_username` (`username`(20)),
  KEY `index_email` (`email`(20))
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb3;
```



这里的索引创建加了(`username`(20))，是什么意思呢？是给字符串的前20个字符创建索引吗？这个索引是如何创建的（底层数据结构）？



`KEY index_username (username(20))` 表示为 `username` 列的**前 20 个字符**创建索引。对于较长的字符串列，MySQL 允许你为字符串的前 N 个字符创建索引，这样可以减少索引的大小，同时提高效率，特别是在列的数据内容较长时。





























### 测试类的使用

```java
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void testApplicationContext() {
		System.out.println(applicationContext);

		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());

		alphaDao = applicationContext.getBean("alphaHibernate", AlphaDao.class);
		System.out.println(alphaDao.select());
	}

	@Test
	public void testBeanManagement() {
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);

		alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
	}

	@Test
	public void testBeanConfig() {
		SimpleDateFormat simpleDateFormat =
				applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}

	@Autowired
	@Qualifier("alphaHibernate")
	private AlphaDao alphaDao;

	@Autowired
	private AlphaService alphaService;

	@Autowired
	private SimpleDateFormat simpleDateFormat;

	@Test
	public void testDI() {
		System.out.println(alphaDao);
		System.out.println(alphaService);
		System.out.println(simpleDateFormat);
	}

}

```





**热部署**

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope> <!-- 确保它只在开发环境中使用 -->
    <optional>true</optional>
</dependency>
```





在方法的前面标记上bean？这个方法返回的类会变成Bean

浏览器的检查功能：general是请求和响应的头部其中的重要信息；response headers是服务器返回的信息；

properties配置实际上是给配置类的属性赋值，配置类会有ConfigurationProperties(prefix="server")类似的注释，然后赋的值就是属性的值。

HttpServletRequest, HttpServletResponse这两个分别是请求对象和响应对象

Enumeration是迭代器对象



HikariDataSource 连接池



快捷键control+return

@Mapper是mybatis的注释



parameterType和keyProperty是啥意思啊？



插入user的时候没有设置id，但是输出的时候输出id了？？



为什么要加userid？查询有可能需要id，有可能不需要？sql是动态的，xml怎么写？

offset和limit用于mysql分页功能



@Param用于给参数取别名。如果只有一个参数，并且<if>条件标签里使用，必须添加别名？（对吗，不太懂）



在xml文件中，#{}添加的是属性（Java entity类的属性），别和mysql字段搞混了



thymeleaf 写的时候是.，实际上调用的时候是调用的get函数



utext会把转义字符转换成本身的符号。



分页



不需要把page添加到model中，自动添加的？为啥？





### Spring注解



* @Primary 
* @Scope("prototype")
* @Qualifier("alphaHibernate") 有多个同类型的Bean，想控制是哪个Bean的依赖注入@Autowired





* java面向对象（基础）：init函数，destroy函数





### Config







### HttpServletRequest, HttpServletResponse

