import kotlinx.html.dom.append
import org.w3c.dom.Node
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.js.pre
import org.w3c.dom.Element
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit

external fun encodeURI(uri: String): String

fun main() {
    window.onload = {
        val fetchButton = document.querySelector(".fetch-button")
        fetchButton?.addEventListener("click", FetchListener())
    }
}

class FetchListener: EventListener {
    private fun getFromCheckbox(cls: String): Boolean {
        return (document.querySelector("#$cls") as HTMLInputElement).checked
    }

    override fun handleEvent(event: Event) {
        val queryInput = document.querySelector(".search-bar__input") as HTMLInputElement
        val queryURL = queryInput.value
        val encodedQueryURL = encodeURI(queryURL)

        val requestParams = RequestParams(
            img = getFromCheckbox("img"),
            video = getFromCheckbox("video"),
            text = getFromCheckbox("text"),
            accessibility = getFromCheckbox("a11y")
        )

        val headers = Headers()
        headers.append("Content-Type", "application/json")
        val body = JSON.stringify(requestParams)
        headers.append("Content-Length", "${body.length}")

        window.fetch("http://localhost:8090/query/$encodedQueryURL", RequestInit(
            method = "POST",
            headers = headers,
            body = JSON.stringify(requestParams)
        )).then {
            it.text().then { data ->
                val responseWindow = document.querySelector(".response-field-wrapper")
                responseWindow!!.clearContents()
                responseWindow.appendField(JSON.stringify(JSON.parse(data), null, space = 4))
            }
        }
    }
}

fun Node.appendField(value: String) {
   append {
       pre {
          +value
       }
   }
}

fun Element.clearContents() {
    innerHTML = ""
}