# Java第四轮单人作业



## API文档

https://apifox.com/apidoc/shared-e3790ee1-bd63-44ac-89c7-7c94c49b7411

- 测试有些示例数据在旁边描述
- 测试接口注册登录完成后要手动设置accessToken环境变量











## 1.项目目录结构介绍、

```tree
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─zhang
│  │  │          ├─config   //相关配置
│  │  │          ├─controller
│  │  │          ├─exception    /异常处理
│  │  │          ├─handler
│  │  │          ├─mapper
│  │  │          ├─pojo
│  │  │          ├─service
│  │  │          │  └─Impl
│  │  │          ├─utils  //工具类
│  │  │          └─vo  //数据封装传输
│  │  └─resources
```

**数据库结构**

<img src="Java第四轮单人作业/image-20240219170935216.png" alt="image-20240219170935216" style="zoom: 50%;" />

## 2.技术栈、

###   1. Spring Boot框架 + **MybatisPlus**数据持久层框架

```pom.xml
		<dependency>
     		<groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>3.0.3</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter-test</artifactId>
            <version>3.0.3</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
            <version>3.5.5</version>
        </dependency>
```

###   2. *Redis*工具类（封装了Redis工具类）

- 存储登录用户的信息；

- 存储并实现热门视频排行榜：在工具类里封装了相关方法，用opsForZSet()；

- 只是简单实现**Redis**工具类，配置Redis

  ```Redis
  [
    "com.zhang.pojo.User",
    {
      "id": "1758284901141635074",
      "username": "郝秀兰",
      "avatarUrl": null,
      "createdAt": [
        "java.util.Date",
        "2024-02-16 08:19:22"
      ],
      "updatedAt": null,
      "deletedAt": null,
      "enabled": true,       //因为Security用到了UserDetails，所以用户Redis储存中加入了以下属性
      "authorities": null,
      "accountNonExpired": true,
      "credentialsNonExpired": true,
      "accountNonLocked": true
    }
  ]
  ```



### 3. Spring Security

#### 1.接口放行问题

**Security直接放行Login登录接口，自定义的拦截不能用注解@Component，这样会把拦截器注入到Spring的Bean容器里，无法放行，应该在Config中用构造器注入**

#### 2.Security实现拦截器链及自定义拦截器实现

- 自定义Token拦截器应该把登录请求忽略

  ```java
  //获取accessToken
  String accessToken = request.getHeader("accessToken");
  //排除登录(login)请求，排除accessToken没有内容的
  if (!StringUtils.hasText(accessToken)|| Objects.equals(request.getServletPath(), "/user/login")) {
   	//放行
   	filterChain.doFilter(request, response);
   	return;
   }
  ```



- 实现filterChain拦截链，添加 WebSecurityConfig配置类实现认证

- Security6.2相较于以前有一些变动

- HttpSecurity

#### 3.登陆注册密码解密+当前登录用户储存

- 密码加密方面：采用了Security的passwordEncoder，不可解密
- 当前登录用户存储：SecurityContextHolder.getContext().setAuthentication






## 3.项目亮点、



## 4.项目待改进点、

- MFA认证

## 5.

### 1. Aop

本来用Aop实现对视频访问切入让后增加VisitCount，但是一直不成功，就用了拦截器实现

看来面向切面AOP和Spring Boot的整合还待提升

### 2.文件视频头像存储

本机的存储在了work4-file文件中，配置了 WebConfig配置类保证静态资源访问

阿里云服务器的就比较恶心了，不知道诸位大神有什么好的策略

### 3.服务器启动项目

这是在docker部署之前的测试项目是否运行

>转到jar包所在目录

```
cd /home/work4     #我的jar包放在服务器的/home/work4包里
```

>命令启动项目

```
java -jar work4.jar  
```



### 4.docker部署服务器项目

有关docker的文档笔记