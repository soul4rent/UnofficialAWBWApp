package com.example.unofficial_awbw_app

import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout

/**
 * Class description
 *
 * @constructor
 * @param gamerButton
 * @param endTurnButton
 * @param confirmEndTurnButton
 * @param moveCalcButton
 * @param nextUnitButton
 * @param gamerButtonList
 * @param gamerContentButtonList
 * @param webView
 **/
class GamerBar(
    val gamerButton: Button,
    val endTurnButton: Button,
    val confirmEndTurnButton: Button,
    val moveCalcButton: Button,
    val nextUnitButton: Button,
    val replayButton: Button,
    val gamerButtonList: LinearLayout,
    val gamerContentButtonList: LinearLayout,
    val webView: ScrollDisabledWebView
) {
    fun resetEndTurnButtonFrontends() {
        endTurnButton.text = "End Turn"
        confirmEndTurnButton.visibility = View.GONE
    }

    fun setGamerButtonListVisiblity(webView: WebView, isRefresh: Boolean = true) {
        if (webView.url != null
            && webView.url!!.contains("https://awbw.amarriner.com/game.php?")
            && !webView.url!!.contains("&ndx")
        ) {
            gamerButtonList.visibility = View.VISIBLE
            if(isRefresh){resetEndTurnButtonFrontends()}
        } else {
            gamerButtonList.visibility = View.GONE
        }
    }

    fun updateFrame() {
        if (gamerButtonList.visibility == View.VISIBLE) {
            webView.evaluateJavascript(JSScriptConstants.getNextUnitButtonVisibility) {
                if (!it.equals("true")) {
                    nextUnitButton.text = "[ - ]"
                } else {
                    nextUnitButton.text = "[ ! ]"
                }
            }
        }
    }

    init {
        endTurnButton.setOnClickListener {
            if (confirmEndTurnButton.visibility != View.VISIBLE) {
                webView.loadUrl(JSScriptConstants.clickEndTurn)
                confirmEndTurnButton.visibility = View.VISIBLE
                endTurnButton.text = "Undo"
            } else {
                webView.loadUrl(JSScriptConstants.clickUndoEndTurn)
                confirmEndTurnButton.visibility = View.GONE
                endTurnButton.text = "End Turn"
            }
        }

        confirmEndTurnButton.setOnClickListener {
            webView.loadUrl(JSScriptConstants.clickConfirm)
            confirmEndTurnButton.visibility = View.GONE
            endTurnButton.text = "End Turn"
        }

        replayButton.setOnClickListener {
            webView.loadUrl(JSScriptConstants.clickReplayOpenButton)
        }

        moveCalcButton.setOnClickListener {
            webView.loadUrl(JSScriptConstants.openDamageCalculator(1.0)) //use default calculator size for now
        }

        nextUnitButton.setOnClickListener {
            updateFrame()
            if (nextUnitButton.text == "[ ! ]") {
                webView.loadUrl(JSScriptConstants.clickNextUnitButton)
            }
        }

        gamerButton.setOnClickListener {
            if (gamerContentButtonList.visibility != View.VISIBLE) {
                gamerContentButtonList.visibility = View.VISIBLE
                gamerButton.text = "◀"
            } else {
                gamerContentButtonList.visibility = View.GONE
                gamerButton.text = "\uD83C\uDFAE"
            }
        }
    }

}