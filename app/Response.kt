data class Response(
	val response: List<ResponseItem?>? = null
)

data class ResponseItem(
	val author: String? = null,
	val text: String? = null
)

