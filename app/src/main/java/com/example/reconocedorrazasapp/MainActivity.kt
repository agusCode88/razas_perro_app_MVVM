package com.example.reconocedorrazasapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.reconocedorrazasapp.ViewPhotoActivity.Companion.PHOTO_URL_KEY
import com.example.reconocedorrazasapp.data.api.connection.ApiServiceInterceptor
import com.example.reconocedorrazasapp.databinding.ActivityMainBinding
import com.example.reconocedorrazasapp.domain.model.User
import com.example.reconocedorrazasapp.presentation.auth.LoginActivity
import com.example.reconocedorrazasapp.presentation.doglist.view.DogListActivity
import com.example.reconocedorrazasapp.presentation.settings.SettingsActivity
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                //OPEN CAMERA
                setupCamera()

            } else {
                Toast.makeText(
                    this,
                    "You need accept camera permission to use camera ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    private var isCameraReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        val user = User.getLoggedInUser(this)
        if (user == null) {
            openLoginActivity()
            return
        } else {
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }

        binding.settingsAppFab.setOnClickListener {
            openSettingsActivity()
        }

        binding.listDogsFab.setOnClickListener {
            openListActivity()
        }

        binding.takePhotoFab.setOnClickListener {
            if (isCameraReady)
                takePhoto()
        }

        requestCameraPermission()

    }

    private fun openListActivity() {
        startActivity(Intent(this, DogListActivity::class.java))
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun requestCameraPermission() {

        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                setupCamera()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, android.Manifest.permission.CAMERA
            ) -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.camera_persmission_title_dialog))
                    .setMessage(getString(R.string.camera_messagge_dialog))
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        requestPermissionLauncher.launch(
                            android.Manifest.permission.CAMERA
                        )
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ ->
                    }.show()
            }

            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.CAMERA
                )
            }
        }

    }

    private fun startCamera() {
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider): Camera {
        val preview: Preview = Preview.Builder()
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            imageProxy.close()
        }

        preview.surfaceProvider = binding.cameraPreview.getSurfaceProvider()

        return cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            preview ,
            imageCapture,
            imageAnalysis
        )
    }

    private fun setupCamera(){
        binding.cameraPreview.post{
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()

            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        }

    }

    private fun takePhoto(){
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException)
                {
                    Toast.makeText(this@MainActivity,
                        "Error taking photo ${error.message}",
                        Toast.LENGTH_SHORT).show()
                }
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val photoUri = outputFileResults.savedUri
                    openPhotoImageActivity(photoUri.toString())
                }
            })
    }

    private fun getOutputPhotoFile(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it , resources.getString(R.string.app_name) + ".jpg").apply { mkdirs() }
        }
        return if (mediaDir!= null && mediaDir.exists()){
            mediaDir
        }else{
            filesDir
        }
    }

    private fun openPhotoImageActivity(phoroUri: String){
        val intent = Intent(this , ViewPhotoActivity::class.java)
        intent.putExtra(PHOTO_URL_KEY, phoroUri)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(::cameraExecutor.isInitialized)
            cameraExecutor.shutdown()
    }


}