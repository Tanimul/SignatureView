package bd.com.tanimul.signatureview

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bd.com.tanimul.signarture_view.SignatureView
import bd.com.tanimul.signatureview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var signatureView: SignatureView


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

        signatureView = SignatureView(this, null)
        binding.frameLayout.addView(signatureView)


    }

    private fun saveSignatureImage(bitmap: Bitmap) {

        // Set the Bitmap to the ImageView
//        binding.imageView.setImageBitmap(bitmap)

        // Show a toast message
        Toast.makeText(this, "Signature Done", Toast.LENGTH_SHORT).show()
    }
}
