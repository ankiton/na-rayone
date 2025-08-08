pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "na-rayone"
include(":app")
include(":core:common")
include(":core:network")
include(":feature-auth")
include(":feature-main")
include(":feature-map")
include(":feature-house-detail")
include(":feature-post-create")
include(":feature-feed")
include(":data")
include(":domain")
include(":shared-ui")
 