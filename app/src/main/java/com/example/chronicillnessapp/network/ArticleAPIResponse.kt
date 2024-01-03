package com.example.chronicillnessapp.network


data class ApiResponse(
    val Result: Result?
)


data class Result(
    val Resources: Resources?
)

data class Resources(
    val Resource: List<ArticleResource>?
)

data class ArticleResource(
    val Id: Int,
    val Title: String,
    val ImageUrl: String
    // Add other relevant fields you need from the response
)
