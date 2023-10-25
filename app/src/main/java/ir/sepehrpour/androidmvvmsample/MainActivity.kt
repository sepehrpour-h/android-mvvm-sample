package ir.sepehrpour.androidmvvmsample


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import ir.sepehrpour.androidmvvmsample.data.model.Post
import ir.sepehrpour.androidmvvmsample.ui.theme.AndroidMvvmSampleTheme
import ir.sepehrpour.androidmvvmsample.viewmodel.PostsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidMvvmSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ObservePostViewModel()
                }
            }
        }
    }

    @Composable
    private fun ObservePostViewModel() {


        var postList by remember { mutableStateOf(emptyList<Post>()) }

        Column {
            PostView(postList = postList)
        }

        LaunchedEffect(key1 = Unit){
            val viewModel = ViewModelProvider(this@MainActivity)[PostsViewModel::class.java]
            viewModel.getAllPostsRequest()
            viewModel.postList.observe(this@MainActivity) { posts ->
                postList = posts
            }

            viewModel.postlistError.observe(this@MainActivity){isError->
                isError?.let {
                    Log.e("3636", isError)
                }
            }
            viewModel.loading.observe(this@MainActivity){isLoading->
                Log.e("3636", isLoading.toString())
            }
        }


    }

    @Composable
    fun PostView(postList: List<Post>) {
        LazyColumn() {
            items(postList) { post ->
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color.Blue)
                ) {
                    Text(text = post.title, color = Color.White)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = post.body, color = Color.DarkGray)

                }
            }
        }
    }
}

