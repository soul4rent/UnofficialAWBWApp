package com.example.unofficial_awbw_app

object JSScriptConstants {
    //'end-turn'
    val clickEndTurn: String = wrapJSforLoad(
        hideCSSElement("#end-turn")
    )
    val clickConfirm: String =  wrapJSforLoad(
        showCSSElement("#end-turn") +
                clickElementByCSS("#end-turn") +
                clickElementByCSS(".end-turn-conf-btn")
    )
    val clickUndoEndTurn: String = wrapJSforLoad(
        showCSSElement("#end-turn")
    )

    val getNextUnitButtonVisibility: String = "(function() { return document.querySelector('#select-next-unit').checkVisibility(); })();"

    val clickNextUnitButton: String = wrapJSforLoad(
        clickElementByCSS("#select-next-unit")+
        scrollAndCenterByCSS(".movement-tile")+
        scrollAndCenterByCSS(".build-options-game")
    )

    val hideReplayClose: String = wrapJSforLoad(
        hideCSSElement(".replay-close")
    )

    val clickReplayOpenButton: String = wrapJSforLoad(
        clickElementByCSS(".replay-open")
    )

    val clickReplayFFForward: String = wrapJSforLoad(
        clickElementByCSS(".replay-forward")
    )

    val clickReplayNext: String = wrapJSforLoad(
        clickElementByCSS(".replay-forward-action")
    )

    val clickReplayBack: String = wrapJSforLoad(
        clickElementByCSS(".replay-backward-action")
    )

    val clickReplayFFBack: String = wrapJSforLoad(
        clickElementByCSS(".replay-backward")
    )


    fun openDamageCalculator(zoomLevel: Double): String {
        return wrapJSforLoad(
            clickElementByCSS(".calculator-toggle") + zoomElement("#calculator", zoomLevel) + repositionCalculatorElement()
        )
    }

    private fun waitForCSSElement(css: String): String {
        return "const elementOnPage = (query: string, timeout: number = 10000): Promise<HTMLElement | null> => {" +
            "return new Promise((resolve, reject) => {" +
            "const startTime = Date.now();" +
            "const tryQuery = () => {" +
            "const elem = dom.window.document.querySelector(query);" +
            "if (elem) resolve(elem); // Found the element" +
            "else if (Date.now() - startTime > timeout) resolve(null); // Give up eventually" +
            "else setTimeout(tryQuery, 10); // check again every 10ms }" +
            "tryQuery(); // Initial check }); };" +
            "const elem = await elementOnPage('$css');"
    }

    private fun scrollAndCenterByCSS(css: String): String {
        return "try{document.querySelector('$css').scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});}catch{}"
    }

    private fun clickElementByCSS(css: String): String{
        return "document.querySelector('$css').click();"
    }

    private fun wrapJSforLoad(js: String): String {
        return "javascript:(function f(){"+ js + "})()"
    }

    private fun hideCSSElement(css: String): String {
        return "document.querySelector('$css').style.display='none';"
    }

    private fun showCSSElement(css: String): String {
        return "document.querySelector('$css').style.display='block';"
    }

    private fun zoomElement(css: String, zoomLevel: Double): String {
        return "document.querySelector('$css').style.transform ='scale($zoomLevel)';"
    }

    private fun repositionCalculatorElement(): String {
        return "var elem = document.getElementById('calculator');" +
                "elem.style.top = window.visualViewport.offsetTop + 'px';"+
                "elem.style.left = window.visualViewport.offsetLeft + 'px';"
    }
}