package bd.com.media365.signatureview

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var frameLayout: FrameLayout
    private lateinit var clearButton: Button
    private lateinit var saveButton: Button
    private lateinit var erageButton: Button
    private lateinit var imageView: ImageView
    private lateinit var signatureView: SignatureView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameLayout = findViewById(R.id.frameLayout)
        clearButton = findViewById(R.id.clear)
        saveButton = findViewById(R.id.add)
        erageButton = findViewById(R.id.eragePIC)
        imageView = findViewById(R.id.imageView)

        clearButton.setOnClickListener {
            // Clear the signature
            signatureView.clear()
        }

        saveButton.setOnClickListener {
            // Save the signature as an image
            saveSignatureImage(signatureView.getSignatureBitmap())

        }

        erageButton.setOnClickListener {
        }

        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        signatureView = SignatureView(this, null)
        frameLayout.addView(signatureView)


    }

    private fun saveSignatureImage(bitmap: Bitmap) {

        // Set the Bitmap to the ImageView
        imageView.setImageBitmap(bitmap)

        // Show a toast message
        Toast.makeText(this, "Signature saved", Toast.LENGTH_SHORT).show()
    }
}
