package com.example.news

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.news.databinding.ActivityWebViewClassBinding

class WebViewClass : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewClassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)  // Moved this line up before any UI operations.

        binding = ActivityWebViewClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        // Apply window insets to the root view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Handle back button press in the WebView activity
        binding.backButtonWV.setOnClickListener { finish() }

        // Get the URL from the intent and load it in the WebView
        val url = intent.getStringExtra("url")
        binding.webView.webViewClient = WebViewClient()
        if (url != null) {
            binding.webView.loadUrl(url)
        }
    }
}
