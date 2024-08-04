package ir.sinanakhaei.baseproject

import android.util.Log
import android.util.Log.d
import androidx.compose.ui.tooling.data.EmptyGroup.data
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.map
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.retrofit.statusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.internal.NopCollector.emit
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
                Log.d("sinatest", "getPost: $data")
            }.onError {
                statusCode
                message()
            }
            
            response.onError {
                Log.d("sinatest", "getPost: $code")
            }
            
        }
    }
}