import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.dom.append
import kotlinx.html.js.div
import kotlinx.html.js.pre
import kotlinx.html.js.span
import kotlinx.html.style
import org.w3c.dom.Element
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.Node

/**
 * Change state of the page briefly to erroneous for [ERROR_VISIBLE_FOR_MS] milliseconds.
 */
fun showUrlError() {
    (document.querySelector(".search-bar__input") as HTMLInputElement?)?.value = ""
    document.querySelector(".search-bar__error-popup")?.turnVisible()
}

/**
 * Show error popup briefly [ERROR_VISIBLE_FOR_MS] milliseconds.
 */
fun Element.turnVisible() {
    val visible = "visible"
    classList.add(visible)
    window.setTimeout({ this.classList.remove(visible)}, ERROR_VISIBLE_FOR_MS)
}

/**
 * Insert content into response window.
 */
fun Node.appendPreTag(value: String) {
    append {
        pre {
            +value
        }
    }
}

/**
 * Generates a small black bootstrap spinner while
 * waiting for the query.
 */
fun Node.appendSpinner() {
    append {
        div("spinner-border") {
            style = "margin: auto"
            span("visually-hidden") {
                +"Loading..."
            }
        }
    }
}

fun Element.clearContents() {
    innerHTML = ""
}
