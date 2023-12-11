package bd.com.tanimul.signarture_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
class SignatureView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paths =
        mutableListOf<PathWithMode>() // List to store paths with drawing or erasing mode
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var currentPath: PathWithMode? = null
    private var lastX = 0f
    private var lastY = 0f
    private var isErasing = false
    private var isDot = false // Variable to track if it's a dot or a line

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = 5f
    }

    fun setLineColor(color: Int) {
        paint.color = color
        isErasing = false
    }

    fun setLineWidth(width: Float) {
        paint.strokeWidth = width
        isErasing = false
    }

    fun updateEraseMode() {
        isErasing = !isErasing
    }

    fun undo() {
        currentPath?.path?.reset() // Clear the current path
        if (paths.isNotEmpty()) {
            paths.removeAt(paths.size - 1) // Remove the last path in the list
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {

        for (pathWithMode in paths) {
            paint.color = if (pathWithMode.isErasing) Color.WHITE else Color.BLACK
            paint.strokeWidth = if (pathWithMode.isErasing) 20f else 5f
            canvas.drawPath(pathWithMode.path, paint)
        }
        currentPath?.let {
            paint.color = if (isErasing) Color.WHITE else Color.BLACK
            paint.strokeWidth = if (isErasing) 20f else 5f
            canvas.drawPath(it.path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                currentPath = if (isErasing) {
                    val radius = 20f // Replace with your desired radius
                    PathWithMode(Path(), true, radius)
                } else {
                    PathWithMode(Path(), false, 0f)
                }
                paths.add(currentPath!!)
                currentPath?.path?.moveTo(x, y)
                lastX = x
                lastY = y
                isDot = true // Initialize as a dot
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                currentPath?.path?.lineTo(x, y)
                lastX = x
                lastY = y
                isDot = false // Now it's not a dot, it's a line
                postInvalidate()
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (isDot && !isErasing) {
                    // Check if it's intended to be a dot (click)
                    currentPath?.path?.addCircle(x, y, 2.5f, Path.Direction.CW)
                }
                postInvalidate()
                isDot = false // Reset the flag
                return true
            }

            else -> return false
        }
    }

    fun getSignatureBitmap(): Bitmap {
        val originalBitmap = createSignatureBitmap()

        // Return the rotated bitmap
        return rotateBitmap(originalBitmap, -90f)
    }

    private fun createSignatureBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        //Set the background color to white
        canvas.drawColor(Color.parseColor("#F1F5F9"))

        for (pathWithMode in paths) {
            paint.color = if (pathWithMode.isErasing) Color.parseColor("#F1F5F9") else Color.BLACK
            paint.strokeWidth = if (pathWithMode.isErasing) 20f else 5f
            canvas.drawPath(pathWithMode.path, paint)
        }

        currentPath?.let {
            paint.color = if (isErasing) Color.parseColor("#F1F5F9") else Color.BLACK
            paint.strokeWidth = if (isErasing) 20f else 5f
            canvas.drawPath(it.path, paint)
        }

        return bitmap
    }

    private fun rotateBitmap(inputBitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)

        return Bitmap.createBitmap(
            inputBitmap,
            0,
            0,
            inputBitmap.width,
            inputBitmap.height,
            matrix,
            true
        )
    }


}
