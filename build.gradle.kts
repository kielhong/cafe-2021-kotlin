import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.6.10"
    val springVersion = "2.6.3"
    val dependencyVersion = "1.0.11.RELEASE"
    val ktlintVersion = "10.0.0"

    id("org.springframework.boot") version springVersion
    id("io.spring.dependency-management") version dependencyVersion
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    jacoco
    `java-test-fixtures`
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
}

object Versions {
    const val kotestVersion = "4.6.1"
    const val mockkVersion = "1.12.0"
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

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:${Versions.kotestVersion}")
    testImplementation("io.kotest:kotest-assertions-core:${Versions.kotestVersion}")
    testImplementation("io.kotest:kotest-property:${Versions.kotestVersion}")
    testImplementation("io.mockk:mockk:${Versions.mockkVersion}")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.test {
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
