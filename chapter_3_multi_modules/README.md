A Project with multi modules has a basic struct as below.

```
chapter_3_multi_modules\user-app/
├── pom.xml                                # parent pom
├── app/                                   # main starting module
│   ├── pom.xml
│   └── src/main/java/
│       └── com/jerry/
│           └── WebDemoApplication.java    # main function
├── task/                                  # task module
│   ├── pom.xml
│   └── src/main/java/
│       └── com/jerry/task/
│           ├── TaskDTO.java
│           ├── TaskVO.java
│           └── TaskController.java   
├── user/                                  # user module
│   ├── pom.xml
│   └── src/main/java/
│       └── com/jerry/user/
│           ├── UserDTO.java
│           ├── UserService.java
│           └── UserController.java   
└── common/                                # common functions
    ├── pom.xml
    └── src/main/java/
        └── com/jerry/common/
            └── response
                ├── CommonResponse.java
                ├── CommonEntityResponse.java
                └── CommonListResponse.java
```

It should only have one main function. In this project it is `app/src/main/java/com/jerry/WebDemoApplication.java`.

The `pom.xml` of the root directory shows `<packaging>pom</packaging>`, which means this is an aggregator project that will not create a jar, but just manage the submodules.

When `mvn clean package` the root directory, it will not pack its dependent submodules into the jar by default. If you want to include everything about the project in one large jar, you should include the `spring-boot-maven-plugin` in `pom.xml` of the parent module.

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>repackage</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

**Question 1**

What is difference between running `mvn clean install` in the root directory and running `mvn clean install` in the `app` directory?

**Answer 1**

When running `mvn clean package` based on the `pom.xml` in the root directory, maven will compile all the submodules, and generate the `jar` with the latest versions of all submodules under the `target` directory in the root directory. When running `mvn clean package` based on the `app/pom.xml` in the `app` directory, maven will only compile the `app` module. Maven will try to find its dependent modules in the local repo, if any of the submodules is not existed. The compilation will fail.
Similarly, when running `mvn clean install` based on the `pom.xml` in the root directory, maven will compile all the submodules, and add the latest versions of all submodules to the local maven repo. When running `mvn clean install` based on the `app/pom.xml` in the `app` directory, maven will only compile the `app` module. If the package of any of its dependent module is already existed in the local repo, it will use that existing package instead of the latest source code.
However, to be notice, when you run `WebDemoApplication.java` in your IDE, it will compile all the submodules, and use the `jar` in the `target` directory in each submodule.

Build the whole project by running `mvn clean package` under the root directory. A `app-1.0-SNAPSHOT.jar` with all submodules packed in it is generated in directory `app/target`.

Check the content of `app-1.0-SNAPSHOT.jar` by running `jar -tf app-1.0-SNAPSHOT.jar`, and you will see something as below. It proves that this package contains all submodules.

```
BOOT-INF/lib/user-1.0-SNAPSHOT.jar
BOOT-INF/lib/task-1.0-SNAPSHOT.jar
BOOT-INF/lib/common-1.0-SNAPSHOT.jar
```

Move this `app/target/app-1.0-SNAPSHOT.jar` to another directory or server, then use `java -jar app-1.0-SNAPSHOT.jar` to start the server. You should be able to request it as before in the development environment.

**Question 2**

When to use a multi-module project, and when to use a single `pom.xml` project with multiple directories as below?

```
project/
├── pom.xml
└── src/main/java/
    └── com/jerry/
        ├── user/        
        ├── task/     
        ├── common/ 
        └── WebDemoApplication.java
```

**Answer 2**

- For small projects or small teams, using a single `pom.xml` project can speed up the development and compilation.
- A single `pom.xml` project is easier to pack up and deploy, as you won't use the old version of a module by mistake.
- If two of your modules are highly coupled, and change one will always affect the other, it's better to put them in a single `pom.xml` project.
- If you want to develop different modules in the project synchronously, a multi-module project is a better choice.
- If you want to reuse some modules in other projects, a multi-module project is a better choice.
- If you gonna of move your application into microservices architecture, a multi-module project is a better choice.


If you do not want to compile some completely relevant submodules in your development environment, you can 
1. Prepare a local parent `pom.xml` without the dependencies of those submodules, and use it to compile the project in your development environment.
2. Remove those submodules from the `Modules` section of your project settings in your IntelliJ IDEA.

