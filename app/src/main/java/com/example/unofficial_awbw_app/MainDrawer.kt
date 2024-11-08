package com.example.unofficial_awbw_app

import android.graphics.Color
import android.view.View
import android.widget.Button

class MainDrawer(
    val drawerButton: Button,
    val moveButton: Button,
    val refreshButton: Button,
    webView: ScrollDisabledWebView
) {

    private fun toggleVisibility(view: View): Int {
        return if (view.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else
            View.VISIBLE
    }

    init {
        drawerButton.setOnClickListener {
            if (drawerButton.text == "▶") {
                drawerButton.text = "☰"
            } else {
                drawerButton.text = "▶"
            }
            moveButton.visibility = toggleVisibility(moveButton)
            refreshButton.visibility = toggleVisibility(refreshButton)
        }

        moveButton.setBackgroundColor(Color.DKGRAY)
        moveButton.setOnClickListener {
            if (webView.scrollEnabled) {
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
}