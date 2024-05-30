package com.example.news

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.databinding.NewsLayoutBinding

class NewsAdapterClass(
    private val context: Context,
    private val modelClassList: List<ModelClass>
) : RecyclerView.Adapter<NewsAdapterClass.ViewHolder>() {

    class ViewHolder(binding: NewsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val cardView = binding.newsCardView
        val image = binding.newsImage
        val headline = binding.newsHeadline
        val description = binding.newsSummary
        val publishedAt = binding.publishedAt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return modelClassList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = modelClassList[position]

        holder.cardView.setOnClickListener {
            val intent = Intent(context, WebViewClass::class.java).apply {
                putExtra("url", model.url)
            }
            context.startActivity(intent)
        }

        Glide.with(context)
            .load(model.urlToImage) // Load the correct image URL from the model
            .into(holder.image)

        holder.publishedAt.text = model.publishedAt
        holder.headline.text = model.title
        holder.description.text = model.describtion
    }
}
