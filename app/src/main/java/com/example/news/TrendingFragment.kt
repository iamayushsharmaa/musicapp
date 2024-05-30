package com.example.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.callback.Callback


@Suppress("NAME_SHADOWING")
class TrendingFragment : Fragment() {

    companion object {
        const val API_KEY = "d688ac952aff4c4187ea180d0bfa960c"
        lateinit var modelClassList: ArrayList<ModelClass>
        @SuppressLint("StaticFieldLeak")
        lateinit var adapter: NewsAdapterClass
        const val COUNTRY = "in"
        var CATEGORY = "science"
        private lateinit var trendingRV: RecyclerView
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_trending, container, false)

        trendingRV = view.findViewById(R.id.trendingRV)
        modelClassList = ArrayList()
        trendingRV.layoutManager = LinearLayoutManager(context)
        adapter = context?.let { NewsAdapterClass(it, modelClassList) }!!
        adapter = context?.let { NewsAdapterClass(it, modelClassList) } ?: NewsAdapterClass(requireContext(), modelClassList)
        trendingRV.adapter = adapter


        findNews()
        return view
    }

    private fun findNews() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://newsapi.org/v2/")
            .build().create(ApiInterface::class.java)
        val response = retrofit.gettrending(COUNTRY,100,   API_KEY)
        response.enqueue(object : retrofit2.Callback<MainNewsClass> {

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<MainNewsClass>, response: Response<MainNewsClass>) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null) {

                    responseBody.let { responseBody -> modelClassList.addAll(responseBody.articles)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<MainNewsClass>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}


