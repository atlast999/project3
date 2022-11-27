package com.example.webtoapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.webtoapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private lateinit var webView: WebView
    private val baseUrl = "http://192.168.1.6:3000"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.app_name)
//        webView = findViewById(R.id.webview)
//        webView.settings.javaScriptEnabled = true
//        webView.webViewClient = Client()
    }

    override fun onResume() {
        super.onResume()
//        webView.loadUrl(baseUrl)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//            webView.goBack()
//            return true
//        }
        return super.onKeyDown(keyCode, event)
    }

    class Client : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }
    }
}