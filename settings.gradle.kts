rootProject.name = "terraform-gradle-plugin"

include("examples:sample-terraform-project")

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}
