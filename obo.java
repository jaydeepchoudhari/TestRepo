"C:\Program Files\Amazon Corretto\jdk17.0.16_8\bin\java.exe" -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53786,suspend=y,server=n -Dspring.config.additional-location=file:/C:/BitBucket/GitHub/dtran-fho-uvt-inspections-service/src/main/resources/application-local.yml -Dspring-profile-active=cloud,local -Dduke.security.jwt.rsaKeyUrl=https://login.microsoftonline.com/2ede383a-7e1f-4357-a846-85886b2c0c4d/discovery/v2.0/keys -XX:TieredStopAtLevel=1 -Dspring.output.ansi.enabled=always -Dcom.sun.management.jmxremote -Dspring.jmx.enabled=true -Dspring.liveBeansView.mbeanDomain -Dspring.application.admin.enabled=true "-Dmanagement.endpoints.jmx.exposure.include=*" -javaagent:C:\Users\eginnis\AppData\Local\JetBrains\IntelliJIdea2023.1\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 @C:\Users\eginnis\AppData\Local\Temp\2\idea_arg_file1389917985 duke.aigis.fho.uvt.FhoUvtInspectionsServiceApplication
Connected to the target VM, address: '127.0.0.1:53786', transport: 'socket'
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/C:/Users/eginnis/.m2/repository/ch/qos/logback/logback-classic/1.2.7/logback-classic-1.2.7.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/C:/Users/eginnis/.m2/repository/org/slf4j/slf4j-simple/1.7.29/slf4j-simple-1.7.29.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [ch.qos.logback.classic.util.ContextSelectorStaticBinder]
12:41:46.665 [main] INFO duke.aigis.fho.uvt.FhoUvtInspectionsServiceApplication - This message will be written to the application's CloudWatch log
12:41:47.191 [Thread-0] DEBUG org.springframework.boot.devtools.restart.classloader.RestartClassLoader - Created RestartClassLoader org.springframework.boot.devtools.restart.classloader.RestartClassLoader@508472a4
12:41:47.199 [restartedMain] INFO duke.aigis.fho.uvt.FhoUvtInspectionsServiceApplication - This message will be written to the application's CloudWatch log
2025-09-05 12:41:48.235  INFO 8516 --- [  restartedMain] o.s.b.devtools.restart.ChangeableUrls    : The Class-Path manifest attribute in C:\Users\eginnis\.m2\repository\com\drewnoakes\metadata-extractor\2.14.0\metadata-extractor-2.14.0.jar referenced one or more files that do not exist: file:/C:/Users/eginnis/.m2/repository/com/drewnoakes/metadata-extractor/2.14.0/xmpcore-6.0.6.jar
2025-09-05 12:41:48.236  INFO 8516 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.6.1)

2025-09-05 12:41:49.422  INFO 8516 --- [  restartedMain] c.c.c.ConfigServicePropertySourceLocator : Fetching config from server at : http://localhost:8888
2025-09-05 12:41:49.588  INFO 8516 --- [  restartedMain] c.c.c.ConfigServicePropertySourceLocator : Connect Timeout Exception on Url - http://localhost:8888. Will be trying the next url if available
2025-09-05 12:41:49.588  WARN 8516 --- [  restartedMain] c.c.c.ConfigServicePropertySourceLocator : Could not locate PropertySource: I/O error on GET request for "http://localhost:8888/application/default": Connection refused: getsockopt; nested exception is java.net.ConnectException: Connection refused: getsockopt
2025-09-05 12:41:49.590  INFO 8516 --- [  restartedMain] .f.u.FhoUvtInspectionsServiceApplication : No active profile set, falling back to default profiles: default
2025-09-05 12:41:51.444  INFO 8516 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2025-09-05 12:41:52.120  INFO 8516 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 668 ms. Found 67 JPA repository interfaces.
2025-09-05 12:41:52.991  INFO 8516 --- [  restartedMain] o.s.cloud.context.scope.GenericScope     : BeanFactory id=7f0a7349-a445-345e-9eac-018b726eff19
2025-09-05 12:41:53.285  INFO 8516 --- [  restartedMain] trationDelegate$BeanPostProcessorChecker : Bean 'methodSecurityConfig' of type [com.dukeenergy.security.jwtauth.config.MethodSecurityConfig$$EnhancerBySpringCGLIB$$3468813d] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2025-09-05 12:41:54.055  INFO 8516 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8088 (http)
2025-09-05 12:41:54.074  INFO 8516 --- [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-09-05 12:41:54.074  INFO 8516 --- [  restartedMain] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.55]
2025-09-05 12:41:55.184  INFO 8516 --- [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-09-05 12:41:55.184  INFO 8516 --- [  restartedMain] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 5554 ms
AppConfig:dataSource
dataSource: org.springframework.jdbc.datasource.DriverManagerDataSource@4a26ea53
dataSource:dbUrl: jdbc:postgresql://fho-uvt-inspections-cluster-dev-0.cnfhcqtbo5ix.us-east-1.rds.amazonaws.com:5432/movesdev
dataSource:username: sa
dataSource:password: fred1234$
AppConfig:dukeJpaProperties
dukeEntityManager: org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean@3d9d3146
2025-09-05 12:41:55.653  INFO 8516 --- [  restartedMain] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: dukeEntityManager]
2025-09-05 12:41:55.737  INFO 8516 --- [  restartedMain] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.6.1.Final
2025-09-05 12:41:56.012  INFO 8516 --- [  restartedMain] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
2025-09-05 12:41:57.235  INFO 8516 --- [  restartedMain] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.PostgreSQL94Dialect
2025-09-05 12:42:00.903  INFO 8516 --- [  restartedMain] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2025-09-05 12:42:00.917  INFO 8516 --- [  restartedMain] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'dukeEntityManager'
2025-09-05 12:42:03.870  WARN 8516 --- [  restartedMain] ConfigServletWebServerApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'solarController': Unsatisfied dependency expressed through field 'solarInspectionViewService'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'solarInspectionViewServiceImpl': Unsatisfied dependency expressed through field 'solarInspectionViewRepository'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'solarInspectionViewRepository' defined in duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository defined in @EnableJpaRepositories declared on AppConfig: Invocation of init method failed; nested exception is org.springframework.data.repository.query.QueryCreationException: Could not create query for public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)! Reason: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort); nested exception is org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)
2025-09-05 12:42:03.878  INFO 8516 --- [  restartedMain] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'dukeEntityManager'
2025-09-05 12:42:03.881  INFO 8516 --- [  restartedMain] o.apache.catalina.core.StandardService   : Stopping service [Tomcat]
2025-09-05 12:42:03.899  INFO 8516 --- [  restartedMain] ConditionEvaluationReportLoggingListener : 

Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2025-09-05 12:42:03.937 ERROR 8516 --- [  restartedMain] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'solarController': Unsatisfied dependency expressed through field 'solarInspectionViewService'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'solarInspectionViewServiceImpl': Unsatisfied dependency expressed through field 'solarInspectionViewRepository'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'solarInspectionViewRepository' defined in duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository defined in @EnableJpaRepositories declared on AppConfig: Invocation of init method failed; nested exception is org.springframework.data.repository.query.QueryCreationException: Could not create query for public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)! Reason: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort); nested exception is org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.resolveFieldValue(AutowiredAnnotationBeanPostProcessor.java:659) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:639) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.annotation.InjectionMetadata.inject(InjectionMetadata.java:119) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessProperties(AutowiredAnnotationBeanPostProcessor.java:399) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1431) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:619) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:944) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918) ~[spring-context-5.3.13.jar:5.3.13]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583) ~[spring-context-5.3.13.jar:5.3.13]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:145) ~[spring-boot-2.6.1.jar:2.6.1]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:730) ~[spring-boot-2.6.1.jar:2.6.1]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:412) ~[spring-boot-2.6.1.jar:2.6.1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:302) ~[spring-boot-2.6.1.jar:2.6.1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1301) ~[spring-boot-2.6.1.jar:2.6.1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1290) ~[spring-boot-2.6.1.jar:2.6.1]
	at duke.aigis.fho.uvt.FhoUvtInspectionsServiceApplication.main(FhoUvtInspectionsServiceApplication.java:103) ~[classes/:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:569) ~[na:na]
	at org.springframework.boot.devtools.restart.RestartLauncher.run(RestartLauncher.java:49) ~[spring-boot-devtools-2.6.1.jar:2.6.1]
Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'solarInspectionViewServiceImpl': Unsatisfied dependency expressed through field 'solarInspectionViewRepository'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'solarInspectionViewRepository' defined in duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository defined in @EnableJpaRepositories declared on AppConfig: Invocation of init method failed; nested exception is org.springframework.data.repository.query.QueryCreationException: Could not create query for public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)! Reason: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort); nested exception is org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.resolveFieldValue(AutowiredAnnotationBeanPostProcessor.java:659) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:639) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.annotation.InjectionMetadata.inject(InjectionMetadata.java:119) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessProperties(AutowiredAnnotationBeanPostProcessor.java:399) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1431) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:619) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1380) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1300) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.resolveFieldValue(AutowiredAnnotationBeanPostProcessor.java:656) ~[spring-beans-5.3.13.jar:5.3.13]
	... 25 common frames omitted
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'solarInspectionViewRepository' defined in duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository defined in @EnableJpaRepositories declared on AppConfig: Invocation of init method failed; nested exception is org.springframework.data.repository.query.QueryCreationException: Could not create query for public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)! Reason: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort); nested exception is org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1804) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1380) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1300) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.resolveFieldValue(AutowiredAnnotationBeanPostProcessor.java:656) ~[spring-beans-5.3.13.jar:5.3.13]
	... 39 common frames omitted
Caused by: org.springframework.data.repository.query.QueryCreationException: Could not create query for public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)! Reason: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort); nested exception is org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)
	at org.springframework.data.repository.query.QueryCreationException.create(QueryCreationException.java:101) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.lookupQuery(QueryExecutorMethodInterceptor.java:106) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.lambda$mapMethodsToQuery$1(QueryExecutorMethodInterceptor.java:94) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197) ~[na:na]
	at java.base/java.util.Iterator.forEachRemaining(Iterator.java:133) ~[na:na]
	at java.base/java.util.Collections$UnmodifiableCollection$1.forEachRemaining(Collections.java:1062) ~[na:na]
	at java.base/java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1845) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499) ~[na:na]
	at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234) ~[na:na]
	at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682) ~[na:na]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.mapMethodsToQuery(QueryExecutorMethodInterceptor.java:96) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.lambda$new$0(QueryExecutorMethodInterceptor.java:86) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at java.base/java.util.Optional.map(Optional.java:260) ~[na:na]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.<init>(QueryExecutorMethodInterceptor.java:86) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at org.springframework.data.repository.core.support.RepositoryFactorySupport.getRepository(RepositoryFactorySupport.java:364) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport.lambda$afterPropertiesSet$5(RepositoryFactoryBeanSupport.java:322) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at org.springframework.data.util.Lazy.getNullable(Lazy.java:230) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at org.springframework.data.util.Lazy.get(Lazy.java:114) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport.afterPropertiesSet(RepositoryFactoryBeanSupport.java:328) ~[spring-data-commons-2.6.0.jar:2.6.0]
	at org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean.afterPropertiesSet(JpaRepositoryFactoryBean.java:144) ~[spring-data-jpa-2.6.0.jar:2.6.0]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1863) ~[spring-beans-5.3.13.jar:5.3.13]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1800) ~[spring-beans-5.3.13.jar:5.3.13]
	... 49 common frames omitted
Caused by: org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException: Cannot use native queries with dynamic sorting in method public abstract java.util.List duke.aigis.fho.uvt.solar.repository.SolarInspectionViewRepository.findByPlantIdWithSort(short,org.springframework.data.domain.Sort)
	at org.springframework.data.jpa.repository.query.NativeJpaQuery.<init>(NativeJpaQuery.java:58) ~[spring-data-jpa-2.6.0.jar:2.6.0]
	at org.springframework.data.jpa.repository.query.JpaQueryFactory.fromMethodWithQueryString(JpaQueryFactory.java:51) ~[spring-data-jpa-2.6.0.jar:2.6.0]
	at org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy$DeclaredQueryLookupStrategy.resolveQuery(JpaQueryLookupStrategy.java:163) ~[spring-data-jpa-2.6.0.jar:2.6.0]
	at org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy$CreateIfNotFoundQueryLookupStrategy.resolveQuery(JpaQueryLookupStrategy.java:252) ~[spring-data-jpa-2.6.0.jar:2.6.0]
	at org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy$AbstractQueryLookupStrategy.resolveQuery(JpaQueryLookupStrategy.java:87) ~[spring-data-jpa-2.6.0.jar:2.6.0]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.lookupQuery(QueryExecutorMethodInterceptor.java:102) ~[spring-data-commons-2.6.0.jar:2.6.0]
	... 71 common frames omitted

Disconnected from the target VM, address: '127.0.0.1:53786', transport: 'socket'

Process finished with exit code 0
