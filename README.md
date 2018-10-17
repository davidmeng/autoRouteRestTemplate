# 背景
公司开始向微服务迁移。并不是所有的微服务都迁移到注册发现服务器上面。而且在开发环境和docker环境不是连同的。就是本地开发环境不能通过docker中的IP访问到docker内的微服务。这样就造成不同的环境访问的方式不同。

*如果是通过url访问的，那么应该使用普通的restTemplate，如果是部署到docker环境，应该通过profile使用zuul的restTemplate*
后来又有的服务只有提供域名的访问方式，并没有祖册到服务发现服务器上面。这样就造成的开发的时候很麻烦。需要知道每一种是通过哪种方式访问的，很不方便。

# 解决方案
研究了一下restTemplate的源码，想通过工厂方式，根据访问不同的url，来选择不同的restTemplate，就写了这个jar。



	@LoadBalanced
    @Bean(name = "dockerRestTemplate")
    @ConditionalOnMissingBean(name = "dockerRestTemplate")
    RestTemplate dockerRestTemplate() {
        return new RestTemplate();
    }


    @Bean(name = "domainRestTemplate")
    @ConditionalOnMissingBean(name = "domainRestTemplate")
    RestTemplate domainRestTemplate() {
        return new RestTemplate();
    }
···

这个是配置模块，需要配置两个restTemplate，jar包提供默认配置。

提供一个接口，RouteDockerRule，需要写一个方法，哪些URI是访问docker环境，哪些不是。
默认的方法首先判断如果是localhost就不是，
否则看url里面是否包含"."，如果含有那么就是域名，否则就是serviceId。

所有的bean在项目中都可以重写。

