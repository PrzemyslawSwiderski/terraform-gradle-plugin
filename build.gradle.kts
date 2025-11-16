import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.pluginPublish)
}

version = System.getenv("PLUGIN_VERSION") ?: "unspecified"

repositories {
    mavenLocal()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleKotlinDsl())
    testImplementation(libs.bundles.test)
}

java {
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    test {
        useJUnitPlatform()
    }
    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
}

gradlePlugin {
    website = "https://github.com/PrzemyslawSwiderski/terraform-gradle-plugin"
    vcsUrl = "https://github.com/PrzemyslawSwiderski/terraform-gradle-plugin"
    plugins {
        create("terraform-gradle-plugin") {
            id = "com.pswidersk.terraform-plugin"
            implementationClass = "com.pswidersk.gradle.terraform.TerraformPlugin"
            displayName = "Simple Plugin to wrap Terraform executable as task."
            description = "Simple Plugin to wrap Terraform executable as task."
            tags = listOf("terraform", "devops", "infra")
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

