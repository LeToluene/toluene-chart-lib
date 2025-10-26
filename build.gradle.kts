import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	kotlin("multiplatform") version "2.2.20"
	kotlin("plugin.serialization") version "2.2.20"

	`maven-publish`

	id("com.gradleup.shadow") version "9.2.2"
}

group = "cn.taskeren.toluene"
version = "1.0.1"

kotlin {
	explicitApi()

	jvm()
	js(IR) {
		browser()
		binaries.library()
		outputModuleName = "toluene-chart"
		generateTypeScriptDefinitions()

		compilations["main"].packageJson {
			customField("homepage", "https://github.com/LeToluene/toluene-chart-lib")
			customField("license", "LGPL-3.0-or-later")
		}
	}

	sourceSets {
		val commonMain by getting {
			dependencies {
				implementation("org.jetbrains:annotations:26.0.2-1")
				implementation("io.github.pdvrieze.xmlutil:serialization:0.91.2")
			}
		}

		val commonTest by getting {
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
	}
}
