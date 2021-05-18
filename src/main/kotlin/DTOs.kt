import kotlinx.serialization.Serializable

/**
 * Used to communicate between client and server.
 * Describes a subset of required information.
 */
@Serializable
data class RequestParams(
    val video: Boolean,
    val img: Boolean,
    val text: Boolean
)