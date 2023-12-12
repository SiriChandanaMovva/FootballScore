package uk.ac.tees.w9623063.myapplication.ui.screens

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebScreen(
    url: String
) {
    AndroidView(factory = {

        WebView(it).apply {

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            webViewClient = WebViewClient()
            loadUrl("https://allsportsapi.com/basketball-api")
        }

    },
        update = {
            it.loadUrl("https://allsportsapi.com/basketball-api")
        }
    )
}