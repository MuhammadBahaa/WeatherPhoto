package muhammad.bahaa.robustatask.data.models

import com.google.gson.annotations.SerializedName

class NetworkError(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("code")
    val code: Int = 0
)