package ir.sinanakhaei.baseproject

import ir.sinanakhaei.retrofit.ApiService
import javax.inject.Inject

class MyRepo @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getPosts() = apiService.getPost()
}