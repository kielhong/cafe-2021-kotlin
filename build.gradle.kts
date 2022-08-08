import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    jacoco
    `java-test-fixtures`

    id(Libs.Plugins.spring) version Libs.Versions.spring
    id(Libs.Plugins.dependencyManagement) version Libs.Versions.dependency
    id(Libs.Plugins.ktlint) version Libs.Versions.ktlint
    id(Libs.Plugins.ktlintIdea) version Libs.Versions.ktlint
    kotlin("jvm") version Libs.Versions.kotlin
    kotlin("plugin.spring") version Libs.Versions.kotlin
    kotlin("plugin.jpa") version Libs.Versions.kotlin
}

group = "com.widehouse"
version = "1.0.0-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Libs.springBootWebflux)
    implementation(Libs.springBootValidation)
    implementation(Libs.springBootDataMongoReactive)

    implementation(Libs.jacksonKotlin)
    implementation(Libs.reactorKotlin)
    implementation(Libs.kotlinReflect)
    implementation(Libs.kotlinJdk8)
    implementation(Libs.kotlinCoroutineReactor)
    // TODO : only local profiles
    implementation(Libs.embeddedMongo)

    testImplementation(Libs.Test.springTest)
    testImplementation(Libs.Test.reactorTest)
    testImplementation(Libs.Test.kotest)
    testImplementation(Libs.Test.kotestAssertionsCore)
    testImplementation(Libs.Test.kotestProperty)
    testImplementation(Libs.Test.kotestExtensionsSpring)
    testImplementation(Libs.Test.mockk)
    testImplementation(Libs.Test.springMockk)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) { exclude("**/CafeApplicationKt.**") }
            }
        )
    )
}

tasks.check {
    dependsOn(tasks.test)
    dependsOn(tasks.ktlintCheck)
}

tasks.bootRun {
    systemProperty("spring.profiles.active", "local")
}
