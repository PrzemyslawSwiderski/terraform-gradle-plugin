import com.pswidersk.gradle.terraform.TerraformTask

plugins {
    id("com.pswidersk.terraform-plugin")
}

val userHome = providers.systemProperty("user.home").get()

terraformPlugin {
    terraformVersion = "1.12.2"
    terraformSetupDir = File(userHome).resolve(".tf")
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
