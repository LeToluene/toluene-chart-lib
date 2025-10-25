package toluene.chart.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.ReturnsNotNull
import kotlin.contracts.contract

class ErrorGatherer {
	enum class Level {
		WEAK_WARN,
		WARN,
		ERROR,
	}

	data class Error(
		val message: String,
		val level: ErrorGatherer.Level,
	)

	@PublishedApi
	internal var errors = mutableListOf<Error>()

	inline fun check(
		condition: Boolean,
		level: Level = Level.ERROR,
		lazyMessage: () -> String,
	) {
		if (!condition) {
			val message = lazyMessage()
			errors.add(Error(message, level))
		}
	}

	fun build(): List<Error> = errors.toList()
}

fun List<ErrorGatherer.Error>.throwIfErrors() {
	if (isNotEmpty()) {
		val text =
			buildString {
				appendLine("Errors found during validation:")
				this@throwIfErrors.forEach { error ->
					appendLine("\t[${error.level}] ${error.message}")
				}
			}
		throw Exception(text)
	}
}
