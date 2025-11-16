rootProject.name = "terraform-gradle-plugin"

include("examples:sample-terraform-project")

pluginManagement {
    val pluginVersionForExamples: String by settings

    plugins {
        id("com.pswidersk.terraform-plugin") version pluginVersionForExamples
    }

    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}
