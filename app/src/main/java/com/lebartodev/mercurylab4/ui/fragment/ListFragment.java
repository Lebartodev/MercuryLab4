package com.lebartodev.mercurylab4.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.crazyhitty.chdev.ks.rssmanager.OnRssLoadListener;
import com.crazyhitty.chdev.ks.rssmanager.RssItem;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;
import com.github.mrengineer13.snackbar.SnackBar;
import com.lebartodev.mercurylab4.R;
import com.lebartodev.mercurylab4.adapter.ArticleAdapter;
import com.lebartodev.mercurylab4.model.Article;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.f_list)

public class ListFragment extends Fragment implements OnRssLoadListener, SwipeRefreshLayout.OnRefreshListener {
    private ArticleAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @ViewById
    RecyclerView list_articles;
    @ViewById
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String TAG = "ListFragment";

    public ListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLayoutManager = new LinearLayoutManager(getActivity());

        list_articles.setLayoutManager(mLayoutManager);
        list_articles.setAdapter(adapter);

        if (adapter == null) {
            adapter = new ArticleAdapter(new ArrayList<Article>(),getActivity());
            list_articles.setAdapter(adapter);
            updateFromDB();

        }
        else
        adapter.setContext(getActivity());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }
    private void updateFromDB(){
        List<Article> articles =Article.listAll(Article.class);
        adapter.setArticles(articles);
        loadFeeds();
    }
    private void loadFeeds() {
        new SnackBar.Builder(getActivity())
                .withMessage("Update started")
                .show();
        //you can also pass multiple urls
        String[] urlArr = {"http://feeds.bbci.co.uk/news/rss.xml"};
        new RssReader(getActivity())
                .showDialog(false)
                .urls(urlArr)
                .parse(this);
    }

    @Override
    public void onSuccess(List<RssItem> rssItems) {
        List<Article> articles = new ArrayList<>();
        for (RssItem item : rssItems) {
            Article article = new Article(item.getTitle(), item.getLink(), item.getSourceUrl());
            articles.add(article);
            createOrUpdate(article);

        }
        mSwipeRefreshLayout.setRefreshing(false);
        new SnackBar.Builder(getActivity())
                .withMessage("Update done!")
                .show();
        adapter.setArticles(articles);


    }

    private void createOrUpdate(Article article) {
        List<Article> list = Article.find(Article.class, "url=?", article.getUrl());
        if (list.size() == 0) {
            article.save();
            Log.d(TAG, "Article not exist");

        } else
            Log.d(TAG, "Article exist");

    }

    @Override
    public void onFailure(String message) {

    }


    @Override
    public void onRefresh() {
        // начинаем показывать прогресс
        mSwipeRefreshLayout.setRefreshing(true);
        loadFeeds();
    }
}
