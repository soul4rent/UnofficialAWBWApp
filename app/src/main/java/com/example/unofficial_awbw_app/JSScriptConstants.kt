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

    private fun clickElementByCSS(css: String): String{
        return "document.querySelector('$css').click();"
    }

    private fun wrapJSforLoad(js: String): String {
        return "javascript:(function f(){"+ js + "})()"
    }

    private fun hideCSSElement(css: String) :String {
        return "document.querySelector('$css').style.display='none';"
    }

    private fun showCSSElement(css: String) :String {
        return "document.querySelector('$css').style.display='block';"
    }
}