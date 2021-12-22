package com.example.lab4

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

const val REQUEST_VIDEO_CODE = 12
const val REQUEST_AUDIO_CODE = 13


class MainActivity : AppCompatActivity() {

    lateinit var video: VideoView
    lateinit var mediaPLayer: MediaPlayer
    lateinit var currentMedia: TextView
    lateinit var webView: WebView
    var contentType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        video = findViewById(R.id.videoPlayer)
        mediaPLayer = MediaPlayer()
        currentMedia = findViewById(R.id.curentMedia)
        webView = findViewById(R.id.webView)


        val videoButton: Button = findViewById(R.id.buttonVideo)
        videoButton.setOnClickListener {
            contentType = REQUEST_VIDEO_CODE
            closeAudio()
            closeWeb()
            getVideo()
            video.visibility = View.VISIBLE

        }
        val openWebButton: Button = findViewById(R.id.buttonOpen)
        openWebButton.setOnClickListener {
            contentType = 0
            val editTextUrl: TextView = findViewById<TextView>(R.id.editTextUrl)
            val url = editTextUrl.text.toString()
            editTextUrl.text = ""
            val v1 = MimeTypeMap.getFileExtensionFromUrl(url)
            val mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(v1)
            if (mime != null) {
                if (mime.contains("video/") or mime.contains("audio/")) {
                    closeVideo()
                    closeAudio()
                    webView.visibility = View.VISIBLE
                    webView.loadUrl(url)
                } else {
                    Log.d("Mime", v1)
                    Toast.makeText(this, "No valid input", Toast.LENGTH_SHORT).show()
                }
            } else Toast.makeText(this, "No valid input", Toast.LENGTH_SHORT).show()
        }

        val audioButton: Button = findViewById(R.id.buttonAudio)
        audioButton.setOnClickListener {
            contentType = REQUEST_AUDIO_CODE
            closeVideo()
            closeWeb()
            getAudio()
        }

        val startButton: ImageButton = findViewById(R.id.buttonPlay)
        startButton.setOnClickListener {
            start(contentType)
        }

        val stopButton: ImageButton = findViewById(R.id.buttonStop)
        stopButton.setOnClickListener {
            pause(contentType)
        }

        val reloadButton: ImageButton = findViewById(R.id.buttonReload)
        reloadButton.setOnClickListener {
            reload(contentType)
        }


    }

    fun closeVideo() {
        video.visibility = View.INVISIBLE
    }

    fun closeAudio() {
        mediaPLayer.reset()
    }

    fun closeWeb() {
        webView.loadUrl("")
        webView.visibility = View.INVISIBLE
    }

    fun start(requestCode: Int = 0) {
        when (requestCode) {
            REQUEST_VIDEO_CODE -> {
                video.start()
            }

            REQUEST_AUDIO_CODE -> {
                mediaPLayer.start()
            }
        }
    }

    fun pause(requestCode: Int = 0) {
        when (requestCode) {
            REQUEST_VIDEO_CODE -> {
                video.pause()
            }

            REQUEST_AUDIO_CODE -> {
                mediaPLayer.pause()
            }
        }
    }

    fun reload(requestCode: Int = 0) {
        when (requestCode) {
            REQUEST_VIDEO_CODE -> {
                video.stopPlayback()
                video.resume()
            }

            REQUEST_AUDIO_CODE -> {

            }
        }
    }

    fun getVideo() {
        val intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.type = "video/*"
        startActivityForResult(intent, REQUEST_VIDEO_CODE)
    }

    fun getAudio() {
        val intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.type = "audio/*"
        video.stopPlayback()
        startActivityForResult(intent, REQUEST_AUDIO_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            val mediaUri: Uri = data?.data!!
            currentMedia.text = mediaUri.path?.substring(mediaUri.path!!.indexOf(':') + 1)
            when (requestCode) {

                REQUEST_VIDEO_CODE -> {
                    video.setVideoURI(mediaUri)
                }
                REQUEST_AUDIO_CODE -> {

                    mediaPLayer.reset()

                    mediaPLayer.setDataSource(this, mediaUri)

                    mediaPLayer.prepare()

                }
            }
        }
    }


}

