import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Libs.Plugins.spring) version Libs.Versions.spring
    id(Libs.Plugins.dependencyManagement) version Libs.Versions.dependency

    id(Libs.Plugins.kotlinJvm) version Libs.Versions.kotlin
    kotlin("plugin.spring") version Libs.Versions.kotlin
    kotlin("plugin.jpa") version Libs.Versions.kotlin

    jacoco
    `java-test-fixtures`
    id(Libs.Plugins.ktlint) version Libs.Versions.ktlint
}

group = "com.widehouse"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    // TODO : only local profiles
    implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

    testImplementation(Libs.Test.springTest)
    testImplementation(Libs.Test.reactorTest)
    testImplementation(Libs.Test.kotest)
    testImplementation(Libs.Test.kotestAssertionsCore)
    testImplementation(Libs.Test.kotestProperty)
    testImplementation(Libs.Test.mockk)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) { exclude("**/CafeApplicationKt.**") }
            }
        )
    )
}

tasks.bootRun {
    systemProperty("spring.profiles.active", "local")
}
