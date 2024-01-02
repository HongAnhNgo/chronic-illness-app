package com.example.chronicillnessapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.chronicillnessapp.R
import com.example.chronicillnessapp.adapters.ArticleAdapter
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.chronicillnessapp.database.ArticleDatabase
import com.example.chronicillnessapp.viewmodels.ArticleViewModel
import com.example.chronicillnessapp.viewmodels.ArticleViewModelFactory

class FeedFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleAdapter
    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val database = ArticleDatabase.getDatabase(requireContext())
        val articleDao = database.articleDao()
        viewModel = ViewModelProvider(this, ArticleViewModelFactory(articleDao)).get(ArticleViewModel::class.java)

        setupRecyclerView(view)
        setupSearchView(view)
        setupFavoriteButton(view)
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        adapter = ArticleAdapter(listOf()) { articleEntity ->
            val updatedArticle = articleEntity.copy(isFavorite = !articleEntity.isFavorite)
            viewModel.updateArticle(updatedArticle)
        }
        recyclerView.adapter = adapter

        viewModel.allArticles.observe(viewLifecycleOwner, { articles ->
            adapter.updateArticles(articles)
        })
    }
    private fun setupSearchView(view: View) {
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Implement the logic to handle the search query
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Implement the logic for real-time search filtering
                return true
            }
        })
    }

    private fun setupFavoriteButton(view: View) {
        val btnFavorite = view.findViewById<Button>(R.id.btnFavorite)
        btnFavorite.setOnClickListener {
            viewModel.favoriteArticles.observe(viewLifecycleOwner, { favoriteArticles ->
                adapter.updateArticles(favoriteArticles)
            })
        }

        // Implement other filter buttons (like Newest, Oldest, etc.) similarly
        // For example:
        // val btnNewest = view.findViewById<Button>(R.id.btnNewest)
        // btnNewest.setOnClickListener { /* Your logic here */ }
    }

    // Add any additional methods or logic needed for your fragment here


}


