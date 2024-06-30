package com.example.unofficial_awbw_app

import android.view.View
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
    val superButton: Button,
    val copButton: Button,
    val scopButton: Button,
    val gamerButtonList: LinearLayout,
    val gamerContentButtonList: LinearLayout,
    val webView: ScrollDisabledWebView,

) {
    fun resetButtonFrontends() {
        endTurnButton.text = "End Turn"
        scopButton.visibility = View.GONE
        copButton.visibility = View.GONE
        confirmEndTurnButton.visibility = View.GONE
    }

    fun setGamerButtonListVisiblity(webView: WebView, isRefresh: Boolean = true) {
        if (webView.url != null
            && webView.url!!.contains("https://awbw.amarriner.com/game.php?")
            && !webView.url!!.contains("&ndx")
        ) {
            gamerButtonList.visibility = View.VISIBLE
            if(isRefresh){resetButtonFrontends()}
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
                copButton.visibility = View.GONE
                scopButton.visibility = View.GONE
                endTurnButton.text = "Undo"
            } else {
                webView.loadUrl(JSScriptConstants.clickUndoEndTurn)
                confirmEndTurnButton.visibility = View.GONE
                endTurnButton.text = "End Turn"
            }
        }

        confirmEndTurnButton.setOnClickListener {
            webView.loadUrl(JSScriptConstants.clickConfirm)
            resetButtonFrontends()
        }

        superButton.setOnClickListener {
            if (copButton.visibility != View.VISIBLE ) {
                webView.evaluateJavascript(JSScriptConstants.getCOPButtonVisibility) {
                    if (it.equals("true")) {
                        copButton.visibility = View.VISIBLE //set button visible
                        confirmEndTurnButton.visibility = View.GONE
                    }
                }
            } else {
                copButton.visibility = View.GONE
            }

            if (scopButton.visibility != View.VISIBLE) {
                webView.evaluateJavascript(JSScriptConstants.getSCOPButtonVisibility) {
                    if (it.equals("true")) {
                        scopButton.visibility = View.VISIBLE //set button visible
                        confirmEndTurnButton.visibility = View.GONE
                    }
                }
            } else {
                scopButton.visibility = View.GONE
            }
        }

        copButton.setOnClickListener {
            webView.loadUrl(JSScriptConstants.clickCOP)
            copButton.visibility = View.GONE
            scopButton.visibility = View.GONE
        }

        scopButton.setOnClickListener {
            webView.loadUrl(JSScriptConstants.clickSCOP)
            copButton.visibility = View.GONE
            scopButton.visibility = View.GONE
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
                resetButtonFrontends()
                gamerContentButtonList.visibility = View.GONE
                gamerButton.text = "\uD83C\uDFAE"
            }
        }
    }

}