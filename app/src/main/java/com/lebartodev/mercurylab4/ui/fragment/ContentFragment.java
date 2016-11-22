package com.lebartodev.mercurylab4.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lebartodev.mercurylab4.R;
import com.lebartodev.mercurylab4.model.Article;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.f_content)
public class ContentFragment extends Fragment {
    public static final String TAG = "ContentFragment";
    @FragmentArg
    Article article;
    @ViewById
    WebView content_view;


    public ContentFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        content_view.getSettings().setJavaScriptEnabled(true);
        content_view.setWebViewClient(new WebViewClient());
        content_view.loadUrl(article.getUrl());


    }

    public void setLoadArticle(Article article) {
        this.article=article;
        content_view.getSettings().setJavaScriptEnabled(true);
        content_view.setWebViewClient(new WebViewClient());
        content_view.loadUrl(article.getUrl());


    }
}
