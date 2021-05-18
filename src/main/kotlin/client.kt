import kotlinx.browser.document
import kotlinx.browser.window

external fun encodeURI(uri: String): String

const val ERROR_VISIBLE_FOR_MS = 2000

fun main() {
    window.onload = {
        val fetchButton = document.querySelector(".fetch-button")
        val fetchListener = FetchListener()
        fetchButton?.addEventListener("click", fetchListener)
    }
}
