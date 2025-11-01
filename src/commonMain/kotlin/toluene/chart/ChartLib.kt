package toluene.chart

import com.squareup.zstd.okio.zstdCompress
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.serialization.XML
import okio.Sink
import okio.Source
import okio.buffer
import okio.use
import org.intellij.lang.annotations.Language
import toluene.chart.dyn.DynProject
import toluene.chart.util.zstdDecompressOrSelf
import kotlin.js.ExperimentalJsExport

@OptIn(ExperimentalJsExport::class)
public object ChartLib {
	/**
	 * Encode the given chart to XML string.
	 */
	public fun encodeToString(chart: CMap): String = XML.encodeToString(chart)

	/**
	 * Decode the given XML string to chart.
	 */
	public fun decodeFromString(
		@Language("XML") xmlString: String,
	): CMap = XML.decodeFromString<CMap>(xmlString)

	/**
	 * # **DYN** File Format
	 *
	 * `.dyn` file format is a JSON-based format with optional Z-standard compression, used by [DyNode](https://dyn.iorinn.moe/)
	 * with its [Specification](https://dyn.iorinn.moe/guide/file-formats.html).
	 *
	 * ChartLib provides limited support for this format, where you can read it via [decodeFromString] and [decodeFromSource],
	 * or write it via [encodeToString] and [encodeToSink].
	 *
	 * A conversion between [CMap] and [DynProject] is not provided but planned.
	 */
	@ExperimentalChartApi
	public object DynFormat {
		public fun encodeToString(dyn: DynProject): String = Json.encodeToString(dyn)

		public fun decodeFromString(
			@Language("JSON") jsonString: String,
		): DynProject = Json.decodeFromString<DynProject>(jsonString)

		public fun encodeToSink(
			dyn: DynProject,
			sink: Sink,
		): Unit =
			sink
				.zstdCompress()
				.buffer()
				.use { it.writeUtf8(encodeToString(dyn)) }

		public fun decodeFromSource(source: Source): DynProject =
			source
				.buffer()
				.zstdDecompressOrSelf()
				.buffer()
				.use { it.readUtf8() }
				.let { decodeFromString(it) }
	}
}
