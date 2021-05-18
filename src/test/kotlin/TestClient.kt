import kotlinx.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class TestClient {
    @Test
    fun testSimpleUrl() {
        val url = "google.com"
        assertTrue { URLValidator().validateUrl(url) }
    }

    @Test
    fun testUrlWithProtocol() {
        val url = "https://google.com"
        assertTrue { URLValidator().validateUrl(url) }
    }

    @Test
    fun testCompleteUrl() {
        val url = "https://a1.a2.xyz:1234/path?name=stuff&otherlist=me%20encoded"
        assertTrue { URLValidator().validateUrl(url) }
    }

    @Test
    fun testTurnVisibleForSpecifiedTime() {
        val visible = "visible"
        val container = document.createElement("div")

        container.turnVisible()
        assertTrue { container.classList.contains(visible)}
    }

    @Test
    fun testClearContents() {
        val container = document.createElement("div")
        container.innerHTML = "<p>some text!</p>"
        container.clearContents()
        assertTrue {
            container.innerHTML.isEmpty()
        }
    }
}
