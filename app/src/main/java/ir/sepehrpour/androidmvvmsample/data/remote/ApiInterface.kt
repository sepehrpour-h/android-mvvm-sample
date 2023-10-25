package ir.sepehrpour.androidmvvmsample.data.remote

import ir.sepehrpour.androidmvvmsample.data.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>
}