package bd.com.tanimul.signarture_view

import android.graphics.Path

data class PathWithMode(val path: Path, val isErasing: Boolean, val eraserRadius: Float)