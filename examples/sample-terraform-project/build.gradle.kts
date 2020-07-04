import com.pswidersk.gradle.terraform.TerraformTask

plugins {
    id("com.pswidersk.terraform-plugin") version "1.0.0"
}

terraformPlugin {
    terraformVersion.set("0.13.0-beta3")
}

tasks {

    register<TerraformTask>("terraform") {
        // exec args can be passed by commandline, for example
        // ./gradlew terraform --args="--version"
    }

    register<TerraformTask>("terraformHelp") {
        args("--help")
    }
}