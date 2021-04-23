import kotlinx.serialization.Serializable

@Serializable
data class RequestParams(
    val accessibility: Boolean,
    val video: Boolean,
    val img: Boolean,
    val text: Boolean
)