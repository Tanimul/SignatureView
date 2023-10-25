package bd.com.media365.signatureview

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bd.com.media365.signatureview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var signatureView: SignatureView

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibUndo.setOnClickListener {
            signatureView.undo()
        }

        binding.btnDone.setOnClickListener {
            saveSignatureImage(signatureView.getSignatureBitmap())

        }

        binding.ibErase.setOnClickListener {
            signatureView.updateEraseMode()
        }

        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        signatureView = SignatureView(this, null)
        frameLayout.addView(signatureView)


    }

    private fun saveSignatureImage(bitmap: Bitmap) {

        // Set the Bitmap to the ImageView
//        binding.imageView.setImageBitmap(bitmap)

        // Show a toast message
        Toast.makeText(this, "Signature Done", Toast.LENGTH_SHORT).show()
    }
}
