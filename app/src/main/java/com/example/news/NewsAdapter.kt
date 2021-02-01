package com.example.news.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.DetailNews
import com.example.news.Model.Article
import com.example.news.Model.News
import com.example.news.R


class NewsAdapter(val context: Context, val article: List<Article>) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.news_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return article.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.title.text=article[position].title
        holder.description.text=article[position].description
        Glide.with(context).load(article[position].urlToImage).into(holder.image)
        holder.itemView.setOnClickListener{
             val intent= Intent(context,DetailNews::class.java)
            intent.putExtra("url",article[position].url)
            context.startActivity(intent)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.article_image)
        var title = itemView.findViewById<TextView>(R.id.article_title)
        var description = itemView.findViewById<TextView>(R.id.article_description)


    }

}
