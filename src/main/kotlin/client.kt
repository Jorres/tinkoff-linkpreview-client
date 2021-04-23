import kotlinx.html.dom.append
import org.w3c.dom.Node
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.js.pre
import org.w3c.dom.Element
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
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

        window.fetch("http://localhost:8091/query/$encodedQueryURL", RequestInit(
            method = "POST",
            body = JSON.stringify(requestParams)
        )).then {
            it.text().then { data ->
                console.log(data)
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