package ir.sinanakhaei.retrofit.services.post

import com.skydoves.sandwich.ApiResponse
import ir.sinanakhaei.retrofit.services.post.model.PostResponse
import retrofit2.http.GET

interface PostService {
    @GET("posts/1")
    suspend fun getPost(): ApiResponse<PostResponse>
}

