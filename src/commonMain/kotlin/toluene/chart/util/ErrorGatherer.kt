package toluene.chart.util

/**
 * An error gatherer for validation.
 * The invalid conditions can be checked in one go, and all errors will be collected.
 *
 * ```kotlin
 * val eg = ErrorGatherer()
 * with(eg) {
 *     check(someCondition) { "Some condition failed!" }
 * }
 * eg.build().throwIfErrors()
 * ```
 */
public class ErrorGatherer {
	public enum class Level {
		WEAK_WARN,
		WARN,
		ERROR,
	}

	public data class Error(
		val message: String,
		val level: Level,
	)

	@PublishedApi
	internal var errors: MutableList<Error> = mutableListOf<Error>()

	public inline fun check(
		condition: Boolean,
		level: Level = Level.ERROR,
		lazyMessage: () -> String,
	) {
		if (!condition) {
			val message = lazyMessage()
			errors.add(Error(message, level))
		}
	}

	public fun build(): List<Error> = errors.toList()
}

public fun List<ErrorGatherer.Error>.throwIfErrors() {
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
