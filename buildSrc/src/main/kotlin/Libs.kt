object Libs {
    object Plugins {
        const val spring = "org.springframework.boot"
        const val dependencyManagement = "io.spring.dependency-management"
        const val kotlinJvm = "org.jetbrains.kotlin.jvm"
        const val ktlint = "org.jlleitschuh.gradle.ktlint"
        const val ktlintIdea = "org.jlleitschuh.gradle.ktlint-idea"
        const val jacocoBadge = "com.github.dawnwords.jacoco.badge"
    }

    object Versions {
        const val spring = "2.6.3"
        const val dependency = "1.0.11.RELEASE"
        const val kotlin = "1.6.10"
        const val kotest = "4.6.2"
        const val mockk = "1.12.0"
        const val ktlint = "10.2.0"
        const val jacoco = "0.8.7"
        const val jacocoBadge = "0.2.4"
    }

    object Test {
        const val springTest = "org.springframework.boot:spring-boot-starter-test"
        const val reactorTest = "io.projectreactor:reactor-test"
        const val kotest = "io.kotest:kotest-runner-junit5:${Versions.kotest}"
        const val kotestAssertionsCore = "io.kotest:kotest-assertions-core:${Versions.kotest}"
        const val kotestProperty = "io.kotest:kotest-property:${Versions.kotest}"
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
    }
}
