package toluene.chart

import kotlin.io.path.Path
import kotlin.io.path.notExists
import kotlin.io.path.readText

fun main(argsArray: Array<String>) {
	val args = argsArray.toMutableList()

	when (args.removeFirstOrNull()) {
		null -> println("Supported modes: validate")
		"validate" -> {
			val pathString = args.removeFirstOrNull() ?: return println("Path of chart is required")
			val path = Path(pathString)
			if (path.notExists()) return println("Path $path does not exist")
			try {
				val text = path.readText()
				validateChart(text).onFailure {
					println("Validation failed!")
					it.printStackTrace()
				}
			} catch (e: Exception) {
				println("Failed to validate!")
				e.printStackTrace()
			}
		}
	}
}
