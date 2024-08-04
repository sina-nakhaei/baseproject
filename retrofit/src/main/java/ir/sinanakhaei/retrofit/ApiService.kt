package ir.sinanakhaei.retrofit

import com.skydoves.sandwich.ApiResponse
import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface ApiService {
    @GET("posts/1")
    suspend fun getPost(): ApiResponse<Post>
}

@Serializable
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)