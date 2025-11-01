package toluene.chart.util

import com.squareup.zstd.okio.zstdDecompress
import okio.BufferedSource
import okio.Source
import okio.use

private const val Z_STD_MAGIC_NUMBER: Long = 0xFD2FB528
private val Z_STD_SKIPPABLE_MAGIC_NUMBER = (0x184D2A50..0x184D2A5F)

internal fun BufferedSource.isZStd(): Boolean {
	// too small file to read the magic number
	if (buffer.size < 4 && !request(4)) {
		return false
	}

	val magicNumber = peek().use { peekSource -> peekSource.readIntLe().toLong() and 0xFFFFFFFFL }
	return magicNumber == Z_STD_MAGIC_NUMBER || magicNumber in Z_STD_SKIPPABLE_MAGIC_NUMBER
}

/**
 * Decompress the source if it's a z-std compressed source, otherwise return itself.
 */
internal fun BufferedSource.zstdDecompressOrSelf(): Source = if (isZStd()) zstdDecompress() else this
