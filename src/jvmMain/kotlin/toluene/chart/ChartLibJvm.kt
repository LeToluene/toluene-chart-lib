package toluene.chart

import okio.sink
import okio.source
import toluene.chart.dyn.DynProject
import java.io.File

@ExperimentalChartApi
public fun ChartLib.DynFormat.encodeToFile(
	dyn: DynProject,
	file: File,
): Unit = encodeToSink(dyn, file.sink())

@ExperimentalChartApi
public fun ChartLib.DynFormat.decodeFromFile(file: File): DynProject = decodeFromSource(file.source())
