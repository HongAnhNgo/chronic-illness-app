package com.example.chronicillnessapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chronicillnessapp.R
import com.bumptech.glide.Glide
import com.example.chronicillnessapp.database.ArticleEntity

class ArticleAdapter(private var articles: List<ArticleEntity>, private val onFavoriteClicked: (ArticleEntity) -> Unit) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.article_image)
        val title: TextView = view.findViewById(R.id.article_title)
        val favoriteImageView: ImageView = view.findViewById(R.id.favoriteImageView)
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

        // Handle favoriteImageView click
        holder.favoriteImageView.setOnClickListener {
            onFavoriteClicked(article)
        }
    }
    fun updateArticles(newArticles: List<ArticleEntity>) {
        this.articles = newArticles
        notifyDataSetChanged()
    }

    override fun getItemCount() = articles.size
}

