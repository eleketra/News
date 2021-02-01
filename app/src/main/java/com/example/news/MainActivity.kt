package com.example.news

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Adapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.Adapter.NewsAdapter
import com.example.news.Model.Article
import com.example.news.Model.News
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    private var displayArticles= mutableListOf<Article>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NewsAdapter(this@MainActivity, displayArticles)
        newsList.adapter = adapter
        newsList.layoutManager=LinearLayoutManager(this@MainActivity)
       // val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
       // layoutManager.setPagerMode(true)
      //  layoutManager.setPagerFlingVelocity(3000)
        launch(Dispatchers.Main){
            val response=NewsService.newInstance.getHeadlines("in",1)
            if(response.isSuccessful)
            {
                articles.addAll(response.body()!!.articles)
                displayArticles.addAll(articles)
                adapter.notifyDataSetChanged()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val searchItem=menu!!.findItem(R.id.search_list)
        if(searchItem!=null)
        {
            val searchView=searchItem.actionView as SearchView
            searchView.queryHint="Search Here....."

           // val editText=searchView.findViewById<EditText>()
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty())
                    {
                        displayArticles.clear()
                        val search=newText.toLowerCase()
                        articles.forEach {
                            if(it.title.toLowerCase().contains(search))
                            {
                                displayArticles.add(it)
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                    else
                    {
                        displayArticles.clear()
                        displayArticles.addAll(articles)
                        adapter.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }
    /*fun getNews() {
        val news = NewsService.newInstance.getHeadlines("in", 1)
        news.enqueue(object : Callback<News> {
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("error", "Error in getting data  ${t.printStackTrace()}")
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                articles.addAll(response.body()!!.articles)
                adapter.notifyDataSetChanged()
            }

        })
    }*/
}
