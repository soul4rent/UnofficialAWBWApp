package com.example.unofficial_awbw_app

import RepeatListener
import android.R.attr.button
import android.annotation.SuppressLint
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
@SuppressLint("ClickableViewAccessibility")
class ReplayViewerBar(
    val replayViewerExitButton: Button,
    val replayViewerFFForwardButton: Button,
    val replayViewerForwardButton: Button,
    val replayViewerBackButton: Button,
    val replayViewerFFBackButton: Button,
    val replayViewerButtonList: LinearLayout,
    val webView: ScrollDisabledWebView
    ){

    private fun toggleVisibility(view: View): Int {
        if (view.visibility == View.VISIBLE) {
            return View.GONE
        } else
            return View.VISIBLE
    }

    fun setReplayButtonListVisiblity(webView: WebView){
        if(webView.url != null
            && webView.url!!.contains("https://awbw.amarriner.com/game.php?")
            && webView.url!!.contains("&ndx")){

            webView.loadUrl(JSScriptConstants.hideReplayClose)
            replayViewerButtonList.visibility = View.VISIBLE
        } else {
            replayViewerButtonList.visibility = View.GONE
        }
    }

    init {
        replayViewerExitButton.setOnClickListener {
            webView.loadUrl(webView.url?.split("&ndx")!![0])
        }

        replayViewerFFForwardButton.setOnClickListener {
            webView.loadUrl(JSScriptConstants.clickReplayFFForward)
        }

        replayViewerForwardButton.setOnTouchListener(
            RepeatListener(
                1000,
                400,
                object : View.OnClickListener {
                    override fun onClick(view: View?) {
                        webView.loadUrl(JSScriptConstants.clickReplayNext)
                    }
                })
        )

        replayViewerBackButton.setOnClickListener {
            webView.loadUrl(JSScriptConstants.clickReplayBack)
        }

        replayViewerFFBackButton.setOnClickListener {
            webView.loadUrl(JSScriptConstants.clickReplayFFBack)
        }
    }

}