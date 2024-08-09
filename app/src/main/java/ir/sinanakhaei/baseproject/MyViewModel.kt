package ir.sinanakhaei.baseproject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.model.PostEntity
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.retrofit.statusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myRepo: MyRepo
) : ViewModel() {
    fun getPost() {
        viewModelScope.launch {
            val response = myRepo.getPosts()
            response.onSuccess {
                viewModelScope.launch {
                    myRepo.insert(
                        PostEntity(
                            id = data.id,
                            userId = data.userId,
                            title = data.title,
                            body = data.body
                        )
                    )
                }

            }
                .onError {
                    statusCode
                    message()
                }
                .onException {
                }


        }
    }
}