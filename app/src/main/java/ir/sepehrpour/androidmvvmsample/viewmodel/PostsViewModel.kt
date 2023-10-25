package ir.sepehrpour.androidmvvmsample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.sepehrpour.androidmvvmsample.data.model.Post
import ir.sepehrpour.androidmvvmsample.data.remote.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsViewModel : ViewModel() {

    val postList = MutableLiveData<List<Post>>()
    val postlistError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun getAllPostsRequest() {
        loading.value = true

        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.api.getAllPosts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let { allPosts ->
                        postList.value = allPosts
                        postlistError.value = null
                        loading.value = false
                    }
                } else {
                    postlistError.value = response.message()
                    loading.value = false
                }
            }
        }

    }
}