@file:OptIn(ExperimentalJsExport::class)

package toluene.chart

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlChildrenName
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.util.CompactFragment
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@Serializable
@XmlSerialName("CMap")
@JsExport
public data class CMap(
	@XmlSerialName("m_path") @XmlElement var path: String,
	@XmlSerialName("m_barPerMin") @XmlElement var barPerMin: Double,
	@XmlSerialName("m_timeOffset") @XmlElement var timeOffset: Double,
	@XmlSerialName("m_leftRegion") @XmlElement var leftRegion: String,
	@XmlSerialName("m_rightRegion") @XmlElement var rightRegion: String,
	@XmlSerialName("m_mapID") @XmlElement var mapId: String,
	@XmlSerialName("m_notes") val mainNotes: NoteContainer,
	@XmlSerialName("m_notesLeft") val leftNotes: NoteContainer,
	@XmlSerialName("m_notesRight") val rightNotes: NoteContainer,
	@XmlSerialName("m_argument") val arguments: CompactFragment,
)

@Serializable
@JsExport
public data class NoteContainer(
	@XmlSerialName("m_notes") @XmlChildrenName("CMapNoteAsset") val notes: List<CMapNoteAsset>,
)

@Serializable
@XmlSerialName("CMapNoteAsset")
@JsExport
public data class CMapNoteAsset(
	@XmlSerialName("m_id") @XmlElement val id: Int,
	@XmlSerialName("m_type") @XmlElement val type: NoteType,
	@XmlSerialName("m_time") @XmlElement val time: Double,
	@XmlSerialName("m_position") @XmlElement val position: Double,
	@XmlSerialName("m_width") @XmlElement val width: Double,
	@XmlSerialName("m_subId") @XmlElement val subId: Int = -1,
	@Deprecated("Only used in gameplay") @XmlSerialName("status") @XmlElement val status: String? = null,
)

@Serializable
@JsExport
public enum class NoteType {
	NORMAL,
	CHAIN,
	HOLD,
	SUB,
}
