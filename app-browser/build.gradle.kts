plugins {
    id("org.jetbrains.kotlin.js")
}

kotlin {
    js {
        useCommonJs()
        browser() {
            webpackTask {
                sourceMaps = false
            }
        }
    }
}

dependencies {
    implementation(npm("core-js", "2.6.5"))
    implementation(npm("react", "$REACT_VERSION"))
    implementation(npm("react-dom", "$REACT_VERSION"))
    implementation(npm("react-is", "$REACT_VERSION"))
    implementation(npm("inline-style-prefixer", "5.1.0"))
    implementation(npm("styled-components", "4.3.2"))
    implementation(project(":lib-basic"))
    implementation(project(":app-di"))
    implementation(project(":solution-tabs-impl"))
}

tasks {
    register("myBuildProduction") {
        dependsOn("browserProductionWebpack")
    }
    register("myRun") {
        doLast {
            SimpleHttpFileServerFactory().start(file("build/distributions/"), 8888)
            println("server started:")
            println("http://localhost:8888/index.html")
            Thread.sleep(Long.MAX_VALUE)
        }
    }
}
