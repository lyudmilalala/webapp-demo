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
│       └── com/example/task/
│           ├── TaskDTO.java
│           ├── TaskVO.java
│           └── TaskController.java   
├── user/                                  # user module
│   ├── pom.xml
│   └── src/main/java/
│       └── com/example/user/
│           ├── UserDTO.java
│           ├── UserService.java
│           └── UserController.java   
└── common/                                # common functions
    ├── pom.xml
    └── src/main/java/
        └── com/example/common/
            └── response
                ├── CommonResponse.java
                ├── CommonEntityResponse.java
                └── CommonListResponse.java
```

It should only have one main function. 

When `mvn clean package` the parent module, it will not pack its dependent submodules into the jar by default. If you want to include everything about the project in one large jar, you should include the `spring-boot-maven-plugin` in `pom.xml` of the parent module.

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

Q: What is difference between running `mvn clean install` in the root directory and running `mvn clean install` in the `app` directory?
A: When running `mvn clean install` based on the `pom.xml` in the root directory, maven will compile all the submodules, and add the latest version of all submodules to the local maven repo. When running `mvn clean install` based on the `app/pom.xml`, maven will only compile the `app` module. If the package of any of its dependent module is already existed in the local repo, it will use that existing package instead of the latest source code.

If you do not want to compile some completely relevant submodules in your development environment, you can 
1. Prepare a local parent `pom.xml` without the dependencies of those submodules, and use it to compile the project in your development environment.
2. Remove those submodules from the `Modules` section of your project settings in your IntelliJ IDEA.