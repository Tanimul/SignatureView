package bd.com.tanimul.signatureview

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import bd.com.tanimul.signarture_view.SignatureView
import bd.com.tanimul.signatureview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var signatureView: SignatureView

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()

        with(binding) {
            ibUndo.setOnClickListener {
                signatureView.undo()
            }

            btnDone.setOnClickListener {
                saveSignatureImage(signatureView.getSignatureBitmap())
            }

            ibErase.setOnClickListener {
                signatureView.updateEraseMode()
            }
        }

    }

    private fun initializeViews() {
        signatureView = SignatureView(this, null)
        binding.frameLayout.addView(signatureView)
    }

    private fun saveSignatureImage(bitmap: Bitmap) {
        Log.d(TAG, "saveSignatureImage: $bitmap")
    }
}
