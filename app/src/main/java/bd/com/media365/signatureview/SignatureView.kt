package bd.com.media365.signatureview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SignatureView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val path = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var lastX = 0f
    private var lastY = 0f

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                lastX = x
                lastY = y
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = Math.abs(x - lastX)
                val dy = Math.abs(y - lastY)
                if (dx >= 5 || dy >= 5) {
                    path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2)
                    lastX = x
                    lastY = y
                    postInvalidate()
                }
                return true
            }

            else -> return false
        }
    }

    fun clear() {
        path.reset()
        invalidate()
    }

    fun getSignatureBitmap(): Bitmap {
        val signatureBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(signatureBitmap)
        draw(canvas)
        return signatureBitmap
    }
}
