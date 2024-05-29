package com.example.unofficial_awbw_app

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebBackForwardList
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var webView: ScrollDisabledWebView
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        //app defaults
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        webView = findViewById(R.id.webview)
        // chromium, enable hardware acceleration
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setWebViewClient(object : WebViewClient() {

            //make sure deep links are forced to be rendered inside the webview
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

        })

        webView.getSettings().domStorageEnabled = true; //for safe password storage
        webView.getSettings().javaScriptEnabled = true;
        webView.getSettings().builtInZoomControls = true; //for toggling zoom
        webView.getSettings().displayZoomControls = false; //hide +/- buttons
        if (savedInstanceState == null) {
            webView.loadUrl("https://awbw.amarriner.com/");
        }

        val zoomButton: Button = findViewById(R.id.zoom_btn)
        zoomButton.setBackgroundColor(Color.DKGRAY)
        zoomButton.setOnClickListener {
            if (webView.settings.builtInZoomControls){
                zoomButton.setBackgroundColor(Color.BLUE)
            } else {
                zoomButton.setBackgroundColor(Color.DKGRAY)
            }
            webView.getSettings().builtInZoomControls = !webView.getSettings().builtInZoomControls
        }

        val moveButton: Button = findViewById(R.id.move_btn)
        moveButton.setBackgroundColor(Color.DKGRAY)
        moveButton.setOnClickListener {
            if (webView.scrollEnabled){
                moveButton.setBackgroundColor(Color.BLUE)
            } else {
                moveButton.setBackgroundColor(Color.DKGRAY)
            }
            webView.scrollEnabled = !webView.scrollEnabled
        }

        val refreshButton: Button = findViewById(R.id.refresh_btn)
        refreshButton.setOnClickListener {
            webView.reload()
        }
    }
}