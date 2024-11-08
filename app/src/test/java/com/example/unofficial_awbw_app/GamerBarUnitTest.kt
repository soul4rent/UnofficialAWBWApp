package com.example.unofficial_awbw_app
import android.widget.Button
import android.widget.LinearLayout
import org.junit.Test
import org.mockito.*
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.kotlin.mock
import android.view.View
import org.mockito.kotlin.doReturn

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class GamerBarUnitTest {

    private var gamerButton: Button = mock<Button>{}
    private var endTurnButton: Button = mock<Button>{}
    private var confirmEndTurnButton: Button = mock<Button>{}
    private var moveCalcButton: Button = mock<Button>{}
    private var nextUnitButton: Button = mock<Button>{}
    private var replayButton: Button = mock<Button>{}
    private var superButton: Button = mock<Button>{}
    private var copButton: Button = mock<Button>{}
    private var scopButton: Button = mock<Button>{}
    private var gamerButtonList: LinearLayout = mock<LinearLayout>{}
    private var gamerContentButtonList: LinearLayout = mock<LinearLayout>{}

    @Mock
    private var webView: ScrollDisabledWebView = mock<ScrollDisabledWebView>{}

    private var gamerBar = GamerBar(
        gamerButton,
        endTurnButton,
        confirmEndTurnButton,
        moveCalcButton,
        nextUnitButton,
        replayButton,
        superButton,
        copButton,
        scopButton,
        gamerButtonList,
        gamerContentButtonList,
        webView
    )
    @Test
    fun ResetButtonFrontendsSetsTextCorrectly() {
        gamerBar.resetButtonFrontends()
        Mockito.verify(endTurnButton, Mockito.times(1)).setText("End Turn")
        Mockito.verify(copButton, Mockito.times(1)).setVisibility(View.GONE)
        Mockito.verify(scopButton, Mockito.times(1)).setVisibility(View.GONE)
        Mockito.verify(confirmEndTurnButton, Mockito.times(1)).setVisibility(View.GONE)
    }
    @Test
    fun SetGamerButtonVisibilityWebviewCorrectPage() {
        webView = mock{ on{ url } doReturn "https://awbw.amarriner.com/game.php?games_id=123"}
        gamerBar.setGamerButtonListVisiblity(webView)
        Mockito.verify(gamerButtonList, Mockito.times(1)).setVisibility(View.VISIBLE)
    }

    @Test
    fun SetGamerButtonVisibilityWebviewIncorrectPage() {
        webView = mock{ on{ url } doReturn "https://awbw.amarriner.com/incorrect_url"}
        gamerBar.setGamerButtonListVisiblity(webView)
        Mockito.verify(gamerButtonList, Mockito.times(1)).setVisibility(View.GONE)
    }
}
