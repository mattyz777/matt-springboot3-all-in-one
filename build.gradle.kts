plugins {
    id("java")
    id("org.springframework.boot") version "3.5.13"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.matt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// define version number
val owaspHtmlSanitizerVersion = "20260313.1"
val mybatisPlusVersion = "3.5.9"
val mybatisPlusJsqlparserVersion = "3.5.16"
val mysqlConnectorVersion = "8.3.0"
val lombokVersion = "1.18.44"
val h2Version = "2.4.240"

/**
 * compileOnly          -> needed only at compile time (not included at runtime)
 * annotationProcessor  -> code generation during compilation (e.g., Lombok)
 * implementation       -> required for both compile and runtime
 */
dependencies {
    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // transaction
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // MyBatis Plus
    implementation("com.baomidou:mybatis-plus-spring-boot3-starter:$mybatisPlusVersion")
    implementation("com.baomidou:mybatis-plus-jsqlparser:$mybatisPlusJsqlparserVersion")

    // MySQL Driver
    implementation("com.mysql:mysql-connector-j:$mysqlConnectorVersion")

    // H2 Database (for development and testing)
    runtimeOnly("com.h2database:h2:${h2Version}")

    // lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // html-sanitizer
    implementation("com.googlecode.owasp-java-html-sanitizer:owasp-java-html-sanitizer:$owaspHtmlSanitizerVersion")

    //
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// specify java version
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    // Enable JUnit 5 (Gradle uses JUnit 4 by default)
    useJUnitPlatform()
    // Print each test case
    testLogging {
        events("passed", "failed", "skipped")
    }
}


