package ir.sinanakhaei.baseproject

import android.util.Log
import com.example.database.dao.PostDao
import com.example.database.model.PostEntity
import com.example.datastore.data.token.TokenKeyValueStore
import ir.sinanakhaei.retrofit.services.post.PostService
import javax.inject.Inject

class MyRepo @Inject constructor(
    private val postService: PostService,
    private val postDao: PostDao,
    private val tokenKvs: TokenKeyValueStore
) {
    suspend fun getPosts() = postService.getPost()

    suspend fun insert(post: PostEntity) {
        val token = tokenKvs.getToken()
        Log.d("sinatest", "insert: $token")
        postDao.insert(post)
    }
}