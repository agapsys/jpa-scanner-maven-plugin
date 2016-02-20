# Agapsys JPA Scanner Maven Plugin

Welcome to the JPA Scanner Maven Plugin project.

**Attention:** If you obtained the source from a development branch be aware that  such branch can contain unstable and/or uncompilable code.

## Compiling

The first build may take a long time as Maven downloads all the dependencies.

## Dependencies

JPA Scanner Maven Plugin project requires Java 6 compatible runtime and depends on the following external libraries:

* Agapsys Scanner Maven Plugin Library (https://github.com/agapsys/scanner-maven-plugin-lib)
* Maven plugin annotations

For detailed information on external dependencies please see *pom.xml*.

## Licensing

JPA Scanner Maven Plugin project is licensed under the **Apache License 2.0**. See the files called *LICENSE* and *NOTICE* for more information.

## About

JPA Scanner plugin makes easy to reuse JPA classes (entities and converters) across projects avoiding the developer to declare classes in `persistence.xml` file

JPA specification requires application to have all JPA classes (entities and converters) declared in a persistence unit.

Hibernate JPA implementation has a wonderful feature called entity auto-detection which exempts the developer from having to declare all application's JPA classes in a persistence unit, but if a JPA class is external to your application (e.g. library), this feature won't work. To solve this, Java EE specification introduced the `<jar-file>` tag in `persistence.xml` which allows an application to use external libraries in a JPA-implementation-independent fashion.

According to http://stackoverflow.com/a/6522308, if your application will not run in a full-fledged java application server (e.g. your app will be deployed in a tomcat server), you are required to declare any external JPA class in `persistence.xml` file. Consider that your application is composed of many entity/converter classes distributed along a multiple libraries (JAR files), each one maintained by a distinct group of developers and you will notice that you're in trouble.

## Plugin usage:

* [Create a library of entities and converters](#create-a-library-of-entities-and-converters)
* [Create a simple JPA application](#create-a-simple-jpa-application)
* [Create an application using a library of entities and converters](#create-an-application-using-a-library-of-entities-and-converters)

### Create a library of entities and converters

* The plugin will scan project's source directory in order to get all JPA classes and list them in a generated file called `jpa.info` (file will be stored under `META-INF`)
* goal `create` will be responsible by generating `META-INF/jpa.info`
* Sample project structure:
```
project_root
    |
    +---- pom.xml
    |
    +---- src/main/java/your-package
            |
            + ---- Entity1.java (contains class Entity1 annotated with @Entity)
            |
            + ---- Converter1.java (contains class Converter1 implementing AttributeConverter and annotated with @Converter )
```

* Sample `pom.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>sample-lib</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.7</maven.compiler.source>
        <maven.compiler.target>1.7</maven.compiler.target>
    </properties>
    <name>sample-lib</name>
    <dependencies>
        <dependency>
            <groupId>javax</groupId>
            <artifactId>javaee-web-api</artifactId>
            <version>7.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>com.agapsys.plugins</groupId>
				<artifactId>jpa-scanner-maven-plugin</artifactId>
				<version>0.1.0-SNAPSHOT</version>
				<executions>
					<execution>
						<goals>
							<goal>create</goal>  
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
</project>
```

* Generated `META-INF/jpa.info`:
```
com.example.entities.Entity1
com.example.entities.Converter1
```

### Create a simple JPA application

* `persistence.xml` should be prepared to be filtered by maven resource plugin.
* A resource filter variable called `jpa-classes` will be exposed by `list` goal.
* Exposed variable name can be changed via `filterProperty` mojo parameter
* Sample project structure:
```
project_root
    |
    +---- pom.xml
    |
    +---- src/main/java/your-package
    |       |
    |       + ---- Entity1.java (contains class Entity1 annotated with @Entity)
    |       |
    |       + ---- Converter1.java (contains class Converter1 implementing AttributeConverter and annotated with @Converter )
    |       
    +---- src/main/java/resources
            |
            + ---- persistence.xml (<class> section must be prepared to be filtered)
```

* Sample `pom.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.example</groupId>
	<artifactId>sample-project</artifactId>
	<version>1.0-SNAPSHOT</version>
	<packaging>jar</packaging>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<maven.compiler.source>1.7</maven.compiler.source>
		<maven.compiler.target>1.7</maven.compiler.target>
	</properties>
	<name>sample-project</name>
	<dependencies>
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-entitymanager</artifactId>
			<version>5.0.2.Final</version>
		</dependency>
		<dependency>
			<groupId>javax</groupId>
			<artifactId>javaee-web-api</artifactId>
			<version>7.0</version>
			<scope>provided</scope>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>com.agapsys.plugins</groupId>
				<artifactId>jpa-scanner-maven-plugin</artifactId>
				<version>0.1.0-SNAPSHOT</version>
				<executions>
					<execution>
						<configuration>
							<!-- if this property is not changed, default value is `jpa-classes` -->
							<filterProperty>resource.filter.var</filterProperty>
						</configuration>
						<goals>
							<goal>list</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
		<resources>
			<resource>
				<directory>src/main/resources</directory>
				<filtering>true</filtering>
			</resource>
		</resources>
	</build>
</project>
```
* Sample `persistence.xml` contents:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
	<persistence-unit name="pu-name" transaction-type="RESOURCE_LOCAL">
		<!-- classes will be included by resource filter plugin. This variable was set in `filterProperty` configuration of `list` goal -->
		${resource.filter.var}
	</persistence-unit>
</persistence>

```

### Create an application using a library of entities and converters
* The process is similar to that described in [Create a simple JPA application](#create-a-simple-jpa-application).
* Just add library dependency as an usual dependency include in a maven project
* sample `pom.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.example</groupId>
	<artifactId>sample-project</artifactId>
	<version>1.0-SNAPSHOT</version>
	<packaging>jar</packaging>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<maven.compiler.source>1.7</maven.compiler.source>
		<maven.compiler.target>1.7</maven.compiler.target>
	</properties>
	<name>sample-project</name>
	<dependencies>
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-entitymanager</artifactId>
			<version>5.0.2.Final</version>
		</dependency>
		<dependency>
			<groupId>javax</groupId>
			<artifactId>javaee-web-api</artifactId>
			<version>7.0</version>
			<scope>provided</scope>
		</dependency>
		<!-- This library contains a `META-INFO/jpa.info` generated by jpa-scanner-maven-plugin:create -->
		<dependency>
			<groupId>com.example</groupId>
			<artifactId>sample-lib</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>com.agapsys.plugins</groupId>
				<artifactId>jpa-scanner-maven-plugin</artifactId>
				<version>0.1.0-SNAPSHOT</version>
				<executions>
					<execution>
						<configuration>
						<!-- if this property is not changed, default value is `jpa-classes` -->
							<filterProperty>resource.filter.var</filterProperty>
						</configuration>
						<goals>
							<goal>list</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
		<resources>
			<resource>
				<directory>src/main/resources</directory>
				<filtering>true</filtering>
			</resource>
		</resources>
	</build>
</project>
```

## Contact

For general information visit the main project site at https://github.com/agapsys/jpa-scanner-maven-plugin
