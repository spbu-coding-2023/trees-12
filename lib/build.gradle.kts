plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.jvm)

    // Code coverage plugin
    jacoco
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(libs.commons.math3)
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(19)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        showCauses = false
        showStackTraces = false
        showExceptions = false
    }
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)
    val sep = File.separator
    val jacocoReportsDirName = "reports${sep}jacoco"
    reports {
        csv.required = true
        xml.required = false
        html.required = true
        csv.outputLocation = layout.buildDirectory.file("${jacocoReportsDirName}${sep}info.csv")
        html.outputLocation = layout.buildDirectory.dir("${jacocoReportsDirName}${sep}html")
    }
}