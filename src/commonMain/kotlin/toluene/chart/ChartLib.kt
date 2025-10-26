package toluene.chart

import nl.adaptivity.xmlutil.serialization.XML
import org.intellij.lang.annotations.Language
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
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
}
