## Initialize a Java SpringBoot Web Application

This `webapp-demo` directory contains a simple Java SpringBoot Web Application where we will always start with.

### About the properties/yaml configuration

You may use `application.properties` or `application.yml` or `application.yaml` to configure your application. You do not need any extra configuration to switch between these configuration file types. If two or more of these file exist, SpringBoot will load in the order of `application.properties`, `application.yml`, `application.yaml`.

## Run

To compile, Run `mvn clean install` in the project directory.

After successful compilation, executable jar will be generated in `target` directory. The version suffix `0.0.1-SNAPSHOT` is based on the `pom.xml` file.

To start the application, run `java -jar target/webapp-demo-0.0.1-SNAPSHOT.jar` in the project directory. You will see console output like this:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.5.2)

2025-09-12 00:13:35.932  INFO 12688 --- [           main] c.j.webappdemo.WebappDemoApplication     : Starting WebappDemoApplication using Java 1.8.0_442 on mila-txair with PID 12688 (D:\projects\webapp-demo-java\chapter_1_init\webapp-demo\target\classes started by lyudm in D:\projects\webapp-demo-java\chapter_1_init\webapp-demo)
2025-09-12 00:13:35.934  INFO 12688 --- [           main] c.j.webappdemo.WebappDemoApplication     : No active profile set, falling back to default profiles: default
2025-09-12 00:13:36.941  INFO 12688 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2025-09-12 00:13:36.953  INFO 12688 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-09-12 00:13:36.953  INFO 12688 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.48]
2025-09-12 00:13:37.035  INFO 12688 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-09-12 00:13:37.036  INFO 12688 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1042 ms
2025-09-12 00:13:37.413  INFO 12688 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2025-09-12 00:13:37.424  INFO 12688 --- [           main] c.j.webappdemo.WebappDemoApplication     : Started WebappDemoApplication in 2.04 seconds (JVM running for 3.154)
```

To test, run `curl -G http://192.168.1.3:8080/healthz`, and you will see `{"status":200,"msg":""}`


https://juejin.cn/post/7205045004221644856
