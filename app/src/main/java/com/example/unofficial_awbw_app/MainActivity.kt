package com.example.unofficial_awbw_app

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var webView: ScrollDisabledWebView
    lateinit var drawerButton: Button
    lateinit var moveButton: Button
    lateinit var refreshButton: Button

    //gameplay buttons
    lateinit var gamerBar: GamerBar
    lateinit var replayViewerBar: ReplayViewerBar

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean { //get back button presses
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
        gamerBar.setGamerButtonListVisiblity(webView)
        replayViewerBar.setReplayButtonListVisiblity(webView)
        return super.onKeyDown(keyCode, event)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        gamerBar.updateFrame()
        gamerBar.setGamerButtonListVisiblity(webView, isRefresh = false) //failsafe. Update on any touch.
        replayViewerBar.setReplayButtonListVisiblity(webView) //failsafe. Update on any touch.
        return super.dispatchTouchEvent(event)
    }

    @SuppressLint("ClickableViewAccessibility")
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

        //default buttons
        drawerButton = findViewById(R.id.drawer_btn)
        moveButton = findViewById(R.id.move_btn)
        refreshButton = findViewById(R.id.refresh_btn)

        //webView init
        webView = findViewById(R.id.webview)
        webView.setWebChromeClient(WebChromeClient())
        // chromium, enable hardware acceleration
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView.setWebViewClient(object : WebViewClient() {

            //make sure deep links are forced to be rendered inside the webView
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (view != null) {
                    runJSOnPageLoad(webView)
                    gamerBar.setGamerButtonListVisiblity(view)
                    replayViewerBar.setReplayButtonListVisiblity(view)
                }
                super.onPageFinished(view, url)
            }

        })
        webView.getSettings().domStorageEnabled = true //for safe password storage
        webView.getSettings().javaScriptEnabled = true
        webView.getSettings().builtInZoomControls = true //for toggling zoom
        webView.getSettings().displayZoomControls = false //hide +/- buttons

        //gameplay buttons init
        gamerBar = GamerBar(
            findViewById(R.id.gamer_btn),
            findViewById(R.id.end_btn),
            findViewById(R.id.confirm_btn),
            findViewById(R.id.move_calc_btn),
            findViewById(R.id.next_unit_btn),
            findViewById(R.id.gamer_replay_btn),
            findViewById(R.id.super_button),
            findViewById(R.id.COP_button),
            findViewById(R.id.SCOP_button),
            findViewById(R.id.gamer_btn_list),
            findViewById(R.id.gamer_content_btn_list),
            webView
        )

        replayViewerBar = ReplayViewerBar(
            findViewById(R.id.replay_exit),
            findViewById(R.id.replay_ff_forward),
            findViewById(R.id.replay_forward),
            findViewById(R.id.replay_back),
            findViewById(R.id.replay_ff_back),
            findViewById(R.id.replay_viewer_btn_list),
            webView,
        )

        if (savedInstanceState == null) { //enable clean loading
            webView.loadUrl("https://awbw.amarriner.com/")
        }

        drawerButton.setOnClickListener {
            if (drawerButton.text == "▶"){
                drawerButton.text = "☰"
            } else {
                drawerButton.text = "▶"
            }
            moveButton.visibility = toggleVisibility(moveButton)
            refreshButton.visibility = toggleVisibility(refreshButton)
        }

        moveButton.setBackgroundColor(Color.DKGRAY)
        moveButton.setOnClickListener {
            if (webView.scrollEnabled){
                moveButton.setBackgroundColor(Color.BLUE)
            } else {
                moveButton.setBackgroundColor(Color.DKGRAY)
            }
            webView.scrollEnabled = !webView.scrollEnabled
            webView.getSettings().builtInZoomControls = !webView.getSettings().builtInZoomControls
        }

        refreshButton.setOnClickListener {
            webView.reload()
        }

    }
    private fun toggleVisibility(view: View): Int {
        return if (view.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else
            View.VISIBLE
    }

    private fun runJSOnPageLoad(webView: ScrollDisabledWebView){
        if(webView.url?.contains("&ndx")!!){
            webView.loadUrl(JSScriptConstants.hideReplayClose)
        } //ensure replay close button is unavailable on all pages
    }
}