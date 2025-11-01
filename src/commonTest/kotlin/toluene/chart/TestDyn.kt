@file:Suppress("SpellCheckingInspection", "ktlint:standard:max-line-length")
@file:OptIn(ExperimentalChartApi::class)

package toluene.chart

import kotlinx.serialization.json.JsonObject
import toluene.chart.dyn.*
import java.nio.file.Files
import kotlin.test.Test

internal class TestDyn {
	@Test
	fun `Test DynFormat decodeFromString`() {
		ChartLib.DynFormat.decodeFromString(sampleJson)
	}

	@Test
	fun `Test DynFormat encodeToString`() {
		ChartLib.DynFormat.encodeToString(sampleDynProject)
	}

	@Test
	fun `Test DynFormat compressed write and read`() {
		val f = Files.createTempFile("chart-lib-test", "dyn").toFile().also { it.deleteOnExit() }
		ChartLib.DynFormat.encodeToFile(sampleDynProject, f)
		ChartLib.DynFormat.decodeFromFile(f)
	}

	private val sampleJson =
		"{\"charts\":[{\"metadata\":{\"artist\":\"\",\"charter\":\"\",\"difficulty\":3,\"sideType\":[\"PAD\",\"PAD\"],\"title\":\"Intel Sound Logo\"},\"notes\":[{\"length\":0.0,\"position\":2.1,\"side\":0,\"time\":340.00000000000006,\"type\":1,\"width\":1.6},{\"length\":0.0,\"position\":2.1,\"side\":0,\"time\":366.78571428571433,\"type\":1,\"width\":2.95},{\"length\":0.0,\"position\":2.1,\"side\":0,\"time\":393.5714285714286,\"type\":1,\"width\":3.3},{\"length\":0.0,\"position\":3.5,\"side\":0,\"time\":420.3571428571429,\"type\":1,\"width\":1.15},{\"length\":0.0,\"position\":0.6,\"side\":0,\"time\":420.3571428571429,\"type\":1,\"width\":0.7}],\"path\":{\"image\":\"Intel Sound Logo.jpg\",\"music\":\"Intel Sound Logo.mp3\",\"video\":\"\"},\"timingPoints\":[{\"bpm\":140.0,\"meter\":4,\"offset\":340.00000000000006}]}],\"formatVersion\":1,\"metadata\":{\"settings\":{\"beatlineAlpha\":[0.7,0.0,0.0],\"bgdim\":0.65,\"defaultWidth\":[1.0,1.0,[1.0,1.0],[1.0,1.0,1.0]],\"defaultWidthMode\":0.0,\"editmode\":4.0,\"editorSelectMultiSidesBinding\":true,\"editside\":1.0,\"fade\":false,\"hitvol\":0.0,\"mainvol\":1.0,\"ntime\":-40.16758858972224,\"pbspd\":1.6,\"pitchshift\":false},\"stats\":{\"projectTime\":273720.1049999985}},\"version\":\"v0.2.2\"}"

	private val sampleDynProject =
		DynProject(
			version = "v0.2.2",
			formatVersion = 1,
			charts =
				listOf(
					DynChart(
						metadata =
							DynChartMetadata(
								title = "Intel Sound Logo",
								difficulty = 3,
								sideType = listOf(SideType.PAD, SideType.PAD),
								artist = "",
								charter = "",
							),
						path =
							DynChartPath(
								image = "Intel Sound Logo.jpg",
								music = "Intel Sound Logo.mp3",
								video = "",
							),
						timingPoints =
							listOf(
								DynChartTimingPoint(
									offset = 340.00000000000006,
									bpm = 140.0,
									meter = 4,
								),
							),
						notes =
							listOf(
								DynChartNote(
									time = 340.00000000000006,
									position = 2.1,
									width = 1.6,
									side = 0,
									type = 1,
									length = 0.0,
								),
								DynChartNote(
									length = 0.0,
									position = 2.1,
									side = 0,
									time = 366.78571428571433,
									type = 1,
									width = 2.95,
								),
							),
					),
				),
			metadata = JsonObject(emptyMap()),
		)
}
