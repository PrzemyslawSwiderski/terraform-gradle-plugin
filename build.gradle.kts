plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.pluginPublish)
    alias(libs.plugins.changelog)
}

repositories {
    mavenLocal()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleKotlinDsl())
    testImplementation(libs.jupiter)
    testImplementation(libs.jupiterParams)
    testImplementation(libs.assertj)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}

gradlePlugin {
    website = "https://github.com/PrzemyslawSwiderski/terraform-gradle-plugin"
    vcsUrl = "https://github.com/PrzemyslawSwiderski/terraform-gradle-plugin"
    plugins {
        create("terraform-gradle-plugin") {
            id = "com.pswidersk.terraform-plugin"
            implementationClass = "com.pswidersk.gradle.terraform.TerraformPlugin"
            displayName = "Simple Plugin to wrap Terraform executable as task. "
                .plus("https://github.com/PrzemyslawSwiderski/terraform-gradle-plugin")
            description = "Simple Plugin to wrap Terraform executable as task."
            tags = listOf("terraform")
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

// Configuring changelog Gradle plugin https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups = listOf("Added", "Changed", "Removed")
}
