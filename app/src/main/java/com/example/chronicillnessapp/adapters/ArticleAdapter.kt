package com.example.chronicillnessapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chronicillnessapp.R
import com.bumptech.glide.Glide

class ArticleAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.article_image)
        val title: TextView = view.findViewById(R.id.article_title)
        // Add other views if necessary
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_articles, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(article.imageUrl)
            .into(holder.image)
    }

    override fun getItemCount() = articles.size
}

// Assuming you have an Article data class
data class Article(
    val title: String,
    val imageUrl: String
)
