import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit

/**
 * Responsible for acting upon the only query button on the client.
 * Queries the server, waits for result, formats output.
 */
class FetchListener: EventListener {
    override fun handleEvent(event: Event) {
        val queryURL = (
                document.querySelector(".search-bar__input") as HTMLInputElement
                ).value

        val responseWindow = document.querySelector(".response-field-wrapper")

        if (!URLValidator().validateUrl(queryURL)) {
            responseWindow!!.clearContents()
            showUrlError()
            return
        }

        val encodedQueryURL = encodeURI(stripProtocol(queryURL))

        // TODO server and client are on the same machine, but
        // integrate this into configuration
        window.fetch("http://localhost:8090/query/$encodedQueryURL", configureRequest())
         .then {
             it.text()
        }.then { data ->
            responseWindow!!.clearContents()
            responseWindow.appendPreTag(formatResponse(data))
        }

        responseWindow!!.clearContents()
        responseWindow.appendSpinner()
    }

    /**
     * TODO temporary crutch, for some reason protocols encode badly
     * and ktor does not recognize this as a route.
     */
    private fun stripProtocol(url: String): String {
        return url.removePrefix("http://").removePrefix("https://")
    }

    private fun formatResponse(data: String): String {
        return JSON.stringify(JSON.parse(data), null, space = 4)
    }

    private fun getFromCheckbox(cls: String): Boolean {
        return (document.querySelector("#$cls") as HTMLInputElement).checked
    }

    /**
     * Extracts a subset of required info from checkboxes and
     * defines required headers and a request's body.
     */
    private fun configureRequest(): RequestInit {
        val requestParams = RequestParams(
            img = getFromCheckbox("img"),
            video = getFromCheckbox("video"),
            text = getFromCheckbox("text")
        )

        val headers = Headers()
        headers.append("Content-Type", "application/json")
        val body = JSON.stringify(requestParams)
        console.log(body)
        headers.append("Content-Length", "${body.length}")
        return RequestInit(
            method = "POST",
            headers = headers,
            body = body
        )
    }
}

class URLValidator {
    fun validateUrl(maybeUrl: String): Boolean {
        val regex = Regex(
    "^(https?://)?"+ // protocol
            "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|"+ // domain name
            "((\\d{1,3}\\.){3}\\d{1,3}))"+ // or ip (v4) address
            "(:\\d+)?(/[-a-z\\d%_.~+]*)*"+ // port and path
            "(\\?[;&a-z\\d%_.~+=-]*)?"+ // query string
            "(#[-a-z\\d_]*)?$"
        )

        return regex.matches(maybeUrl)
    }
}