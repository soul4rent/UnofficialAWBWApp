package com.example.unofficial_awbw_app

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var webView: ScrollDisabledWebView
    lateinit var drawerButton: Button
    lateinit var zoomButton: Button
    lateinit var moveButton: Button
    lateinit var refreshButton: Button

    //gameplay buttons
    lateinit var gamerButton: Button
    lateinit var endTurnButton: Button
    lateinit var confirmEndTurnButton: Button
    lateinit var moveCalcButton: Button
    lateinit var gamerButtonList: LinearLayout
    lateinit var gamerContentButtonList: LinearLayout
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
        setGamerButtonListVisiblity(webView)
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


        //default buttons
        drawerButton = findViewById(R.id.drawer_btn)
        zoomButton = findViewById(R.id.zoom_btn)
        moveButton = findViewById(R.id.move_btn)
        refreshButton = findViewById(R.id.refresh_btn)

        //gameplay buttons
        gamerButton = findViewById(R.id.gamer_btn)
        endTurnButton = findViewById(R.id.end_btn)
        confirmEndTurnButton = findViewById(R.id.confirm_btn)
        moveCalcButton = findViewById(R.id.move_calc_btn)
        gamerButtonList = findViewById(R.id.gamer_btn_list)
        gamerContentButtonList = findViewById(R.id.gamer_content_btn_list)
        var calcSize = 1.0 //default calculator size


        //webview init
        webView = findViewById(R.id.webview)
        // chromium, enable hardware acceleration
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView.setWebViewClient(object : WebViewClient() {

            //make sure deep links are forced to be rendered inside the webview
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (view != null) {
                    setGamerButtonListVisiblity(view)
                }
                super.onPageFinished(view, url)
            }

        })
        webView.getSettings().domStorageEnabled = true //for safe password storage
        webView.getSettings().javaScriptEnabled = true
        webView.getSettings().builtInZoomControls = true //for toggling zoom
        webView.getSettings().displayZoomControls = false //hide +/- buttons
        if (savedInstanceState == null) {
            webView.loadUrl("https://awbw.amarriner.com/")
        }

        drawerButton.setOnClickListener {
            if (drawerButton.text == "▶"){
                drawerButton.text = "☰"
            } else {
                drawerButton.text = "▶"
            }
            zoomButton.visibility = toggleVisibility(zoomButton)
            moveButton.visibility = toggleVisibility(moveButton)
            refreshButton.visibility = toggleVisibility(refreshButton)
        }

        zoomButton.setBackgroundColor(Color.DKGRAY)
        zoomButton.setOnClickListener {
            if (webView.settings.builtInZoomControls){
                zoomButton.setBackgroundColor(Color.BLUE)
            } else {
                zoomButton.setBackgroundColor(Color.DKGRAY)
            }
            webView.getSettings().builtInZoomControls = !webView.getSettings().builtInZoomControls
        }

        moveButton.setBackgroundColor(Color.DKGRAY)
        moveButton.setOnClickListener {
            if (webView.scrollEnabled){
                moveButton.setBackgroundColor(Color.BLUE)
            } else {
                moveButton.setBackgroundColor(Color.DKGRAY)
            }
            webView.scrollEnabled = !webView.scrollEnabled
        }

        refreshButton.setOnClickListener {
            webView.reload()
        }

        endTurnButton.setOnClickListener{
            if (confirmEndTurnButton.visibility != View.VISIBLE) {
                webView.loadUrl(JSScriptConstants.clickEndTurn)
                confirmEndTurnButton.visibility = View.VISIBLE
                endTurnButton.text = "Undo"
            }
            else {
                webView.loadUrl(JSScriptConstants.clickUndoEndTurn)
                confirmEndTurnButton.visibility = View.INVISIBLE
                endTurnButton.text = "End Turn"
            }
        }

        confirmEndTurnButton.setOnClickListener{
            webView.loadUrl(JSScriptConstants.clickConfirm)
            confirmEndTurnButton.visibility = View.INVISIBLE
            endTurnButton.text = "End Turn"
        }

        moveCalcButton.setOnClickListener {
            webView.loadUrl(JSScriptConstants.openDamageCalculator(calcSize))
        }

        gamerButton.setOnClickListener {
            if (gamerContentButtonList.visibility != View.VISIBLE) {
                gamerContentButtonList.visibility = toggleVisibility(gamerContentButtonList)
                gamerButton.text = "◀"
            }
            else {
                gamerContentButtonList.visibility = toggleVisibility(gamerContentButtonList)
                gamerButton.text = "\uD83C\uDFAE"
            }
        }
    }
    private fun toggleVisibility(view: View): Int {
        if (view.visibility == View.VISIBLE) {
            return View.INVISIBLE
        } else
            return View.VISIBLE
    }

    private fun resetEndTurnButtonFrontends() {
        endTurnButton.text = "End Turn"
        confirmEndTurnButton.visibility = View.INVISIBLE
    }

    private fun setGamerButtonListVisiblity(webView: WebView, isBackButton: Boolean = false){
        if(webView.url != null && webView.url!!.contains("https://awbw.amarriner.com/game.php?")){
            gamerButtonList.visibility = View.VISIBLE
            resetEndTurnButtonFrontends()
        } else {
            gamerButtonList.visibility = View.INVISIBLE
        }
    }
}