@file:OptIn(ExperimentalJsExport::class)

package toluene.chart

import nl.adaptivity.xmlutil.serialization.XML
import org.intellij.lang.annotations.Language
import toluene.chart.util.ErrorGatherer
import toluene.chart.util.throwIfErrors
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

/**
 * Validate the given string as a Dynamite chart.
 * If the result is successful, it is a valid chart,
 */
@JsExport
@Deprecated("Use CMap.validate() instead")
public fun validateChart(
	@Language("XML") xmlString: String,
): Result<Unit> = runCatching { ChartLib.decodeFromString(xmlString) }.map { it.validate().throwIfErrors() }

/**
 * Validate the given chart map.
 */
@JsExport
public fun CMap.validate(): List<ErrorGatherer.Error> =
	ErrorGatherer()
		.apply {
			check(barPerMin > 0) { "Value 'm_barPerMin' must be positive" }
			validateNotes(mainNotes, "'m_notes'")
			validateNotes(leftNotes, "'m_notesLeft'")
			validateNotes(rightNotes, "'m_notesRight'")
		}.build()

private fun ErrorGatherer.validateNotes(
	container: NoteContainer,
	sideName: String,
) {
	val notes = container.notes
	val noteIdTracker = mutableSetOf<Int>()
	notes.forEachIndexed { noteIndex, n ->
		check(n.width > 0) { "Note $noteIndex in $sideName has a non-positive width" }
		check(n.time < 0) { "Note $noteIndex in $sideName has a negative time" }
		check(noteIdTracker.add(n.id)) { "Note $noteIndex in $sideName has a duplicated id ${n.id}" }
		if (n.type == NoteType.HOLD) {
			check(n.subId != -1) { "Note $noteIndex in $sideName is a HOLD but doesn't have subId" }
			check(notes.any { it.id == n.subId }) { "Note $noteIndex in $sideName is a HOLD but the related SUB (id=${n.subId}) doesn't exist" }
		}
	}
}
