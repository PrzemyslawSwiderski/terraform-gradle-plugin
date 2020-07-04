import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm") version "1.3.72"
    id("com.gradle.plugin-publish") version "0.11.0"
    id("net.researchgate.release") version "2.8.1"
}

repositories {
    mavenLocal()
    jcenter()
    maven {
        setUrl("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}

tasks {
    test {
        useJUnitPlatform()
    }
    "afterReleaseBuild"{
        dependsOn("publish", "publishPlugins")
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
gradlePlugin {
    plugins {
        create("terraform-gradle-plugin") {
            id = "com.pswidersk.terraform-plugin"
            implementationClass = "com.pswidersk.gradle.terraform.TerraformPlugin"
            displayName = "Simple Gradle plugin to wrap Terraform executable as task. https://github.com/PrzemyslawSwiderski/terraform-gradle-plugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/PrzemyslawSwiderski/terraform-gradle-plugin"
    vcsUrl = "https://github.com/PrzemyslawSwiderski/terraform-gradle-plugin"
    description = "Simple Gradle plugin to wrap Terraform executable as task."
    tags = listOf("terraform")
}

publishing {
    repositories {
        mavenLocal()
    }
}