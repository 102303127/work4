# work4 Java第四轮单人作业
2024寒假轮

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
- Security 认证异常处理

