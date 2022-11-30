package com.example.webtoapp.ui.browser

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.webtoapp.BR
import com.example.webtoapp.base.fragment.BaseFragment
import com.example.webtoapp.databinding.FragmentBrowserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowserFragment : BaseFragment() {

    override val viewModel by viewModels<BrowserViewModel>()

    private val navArgs by navArgs<BrowserFragmentArgs>()

    @SuppressLint("SetJavaScriptEnabled")
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding =
        FragmentBrowserBinding.inflate(inflater, container, false).apply {

            requireActivity().title = navArgs.webApp.name

            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebAppClient
            webView.loadUrl(navArgs.webApp.url)
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    findNavController().navigateUp()
                }
            }
        }

    override fun getViewModelVariable(): Int = BR.vm


    object WebAppClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?, request: WebResourceRequest?
        ): Boolean {
            return false
        }
    }
}