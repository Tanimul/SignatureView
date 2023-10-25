package bd.com.media365.signatureview

import android.graphics.Path

data class PathWithMode(val path: Path, val isErasing: Boolean, val eraserRadius: Float)