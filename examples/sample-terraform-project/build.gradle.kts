import com.pswidersk.gradle.terraform.TerraformTask

plugins {
    id("com.pswidersk.terraform-plugin") version "1.1.1"
}

terraformPlugin {
    terraformVersion = "0.13.0-beta3"
    terraformSetupDir = File("C:\\terraform")
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
