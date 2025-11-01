import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	kotlin("multiplatform") version "2.2.20"
	kotlin("plugin.serialization") version "2.2.20"

	`maven-publish`

	id("com.gradleup.shadow") version "9.2.2"
	id("com.palantir.git-version") version "4.1.0"
}

group = "cn.taskeren.toluene"
version = "0.0.0"

try {
	val gitVersion: groovy.lang.Closure<String> by extra
	version = gitVersion()
} catch (e: Exception) {
	println("Failed to get git version: ${e.message}")
}

kotlin {
	explicitApi()

	jvm()

	sourceSets {
		val commonMain by getting {
			dependencies {
				implementation("org.jetbrains:annotations:26.0.2-1")
				implementation("io.github.pdvrieze.xmlutil:serialization:0.91.2")
				implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
				implementation("com.squareup.okio:okio:3.15.0")
				implementation("com.squareup.zstd:zstd-kmp-okio:0.4.0")
			}
		}

		val commonTest by getting {
			dependencies {
				implementation(kotlin("test"))
			}
		}
	}
}

tasks.withType<ShadowJar> {
	archiveClassifier = "fat"
	from(
		kotlin.targets["jvm"]
			.compilations["main"]
			.output.allOutputs,
	)
	configurations = listOf(project.configurations.getByName("jvmRuntimeClasspath"))
	mainClass = "toluene.chart.MainKt"
}

publishing {
	@Suppress("SpellCheckingInspection")
	repositories {
		val lwgmrPassword = project.findProperty("lwgmr.password") as? String
		if (lwgmrPassword != null) {
			println("Adding LWGMR repository in publishing repositories")
			maven {
				name = "LWGMR"
				url = uri("https://lwgmr.elytra.cn")
				credentials {
					username = "Taskeren"
					password = lwgmrPassword
				}
			}
		}

		val ghUsername = project.findProperty("gpr.user") as? String ?: System.getenv("USERNAME")
		val ghPassword = project.findProperty("gpr.key") as? String ?: System.getenv("TOKEN")
		if (ghUsername != null && ghPassword != null) {
			println("Adding GPR repository in publishing repositories")
			maven {
				name = "GitHubPackages"
				url = uri("https://maven.pkg.github.com/LeToluene/toluene-chart-lib")
				credentials {
					username = ghUsername
					password = ghPassword
				}
			}
		}
	}
}
