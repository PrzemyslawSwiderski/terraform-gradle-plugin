rootProject.name = "terraform-gradle-plugin"

include("examples:sample-terraform-project")

pluginManagement {
    repositories {
        mavenLocal()
        jcenter()
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }
}