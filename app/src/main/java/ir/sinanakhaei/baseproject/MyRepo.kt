package ir.sinanakhaei.baseproject

import ir.sinanakhaei.retrofit.services.post.PostService
import javax.inject.Inject

class MyRepo @Inject constructor(
    private val postService: PostService
) {
    suspend fun getPosts() = postService.getPost()
}