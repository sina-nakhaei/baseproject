package ir.sinanakhaei.baseproject

import com.example.database.dao.PostDao
import com.example.database.model.PostEntity
import ir.sinanakhaei.retrofit.services.post.PostService
import javax.inject.Inject

class MyRepo @Inject constructor(
    private val postService: PostService,
    private val postDao: PostDao
) {
    suspend fun getPosts() = postService.getPost()

    suspend fun insert(post: PostEntity) {
        postDao.insert(post)
    }
}