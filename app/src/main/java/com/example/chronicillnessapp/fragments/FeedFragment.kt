package com.example.chronicillnessapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.chronicillnessapp.R
import com.example.chronicillnessapp.adapters.Article
import com.example.chronicillnessapp.adapters.ArticleAdapter

class FeedFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        val numberOfColumns = 2 // You can adjust the number of columns here
        recyclerView.layoutManager = StaggeredGridLayoutManager(numberOfColumns, StaggeredGridLayoutManager.VERTICAL)

        // Initialize your article list and adapter here
//        val articles = listOf<Article>() // Replace with your list of articles

        val articles = listOf(
            Article(
                title = "Revolutionizing Heart Health: Breakthroughs in Cardiology",
                imageUrl = "https://ts2.space/wp-content/uploads/2023/12/compressed_img-5uPVZECXlY0ThhswtH6dW5XX-1024x585.png"
            ),
            Article(
                title = "The Power of Nutrition: Eating Your Way to Better Health",
                imageUrl = "https://miro.medium.com/v2/resize:fit:1400/1*7gis3sbtMsI0ZzdYrpCgNw.png"
            ),
            Article(
                title = "Mental Wellness in the Digital Age: Strategies for Balance",
                imageUrl = "https://miro.medium.com/v2/resize:fit:2000/0*L2Uk8qNnHmKzbc4i.jpg"
            ),
            Article(
                title = "Exercise as Medicine: The Benefits of Physical Activity",
                imageUrl = "https://blogs.bmj.com/bjsm/files/2021/11/FDp7c4fXsAE9aNd.jpeg"
            ),
            Article(
                title = "Sleep Science: Unlocking the Secrets of a Good Night's Rest",
                imageUrl = "https://entail-assets.com/cannabotech/Deep_sleep_secrets-1674658103979.jpg"
            )
        )



        adapter = ArticleAdapter(articles)
        recyclerView.adapter = adapter
    }
}
