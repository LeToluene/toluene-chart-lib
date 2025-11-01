package toluene.chart.dyn

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import toluene.chart.SideType

@Serializable
public data class DynProject(
	val version: String,
	val formatVersion: Int,
	val charts: List<DynChart>,
	// internal object for DyNode,
	// according to the document, we should keep it as-is.
	val metadata: JsonElement,
)

@Serializable
public data class DynChart(
	val metadata: DynChartMetadata,
	val path: DynChartPath,
	val timingPoints: List<DynChartTimingPoint>,
	val notes: List<DynChartNote>,
)

@Serializable
public data class DynChartMetadata(
	val title: String,
	val difficulty: Int, // 0-5; casual-tera
	val sideType: List<SideType>,
	val artist: String,
	val charter: String,
)

@Serializable
public data class DynChartNote(
	val time: Double,
	val position: Double,
	val width: Double,
	val side: Int,
	val type: Int,
	val length: Double,
)

@Serializable
public data class DynChartPath(
	val image: String,
	val music: String,
	val video: String,
)

@Serializable
public data class DynChartTimingPoint(
	val offset: Double,
	val bpm: Double,
	val meter: Int,
)
