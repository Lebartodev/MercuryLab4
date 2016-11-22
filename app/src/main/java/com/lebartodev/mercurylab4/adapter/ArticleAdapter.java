package com.lebartodev.mercurylab4.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lebartodev.mercurylab4.R;
import com.lebartodev.mercurylab4.model.Article;
import com.lebartodev.mercurylab4.ui.fragment.ContentFragment;
import com.lebartodev.mercurylab4.ui.fragment.ContentFragment_;

import java.util.List;

/**
 * Created by Александр on 22.11.2016.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleVH> {
    private List<Article> articles;
    private Context context;
    private int selectedPos = -1;

    public ArticleAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
        ((Activity) this.context).getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (((Activity) ArticleAdapter.this.context).getFragmentManager().getBackStackEntryCount() == 0) {
                    selectedPos = -1;
                    notifyDataSetChanged();
                }
            }
        });


    }

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
        ((Activity) this.context).getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (((Activity) ArticleAdapter.this.context).getFragmentManager().getBackStackEntryCount() == 0) {
                    selectedPos = -1;
                    notifyDataSetChanged();
                }
            }
        });
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @Override
    public ArticleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.i_article, parent, false);
        return new ArticleVH(v);
    }

    @Override
    public void onBindViewHolder(final ArticleVH holder, int position) {
        holder.title.setText(articles.get(position).getTitle());
        if (selectedPos == position) {
            holder.mainView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

        } else
            holder.mainView.setBackgroundColor(Color.WHITE);
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedPos = holder.getAdapterPosition();
                notifyDataSetChanged();
                ContentFragment contentFragment = (ContentFragment) ((Activity) context).getFragmentManager().findFragmentByTag(ContentFragment.TAG);
                if (contentFragment == null) {
                    contentFragment = ContentFragment_.builder().article(articles.get(selectedPos)).build();
                    ((Activity) context).getFragmentManager().beginTransaction()
                            .replace(R.id.layout_content, contentFragment, ContentFragment.TAG).addToBackStack("tag").commit();

                    //contentFragment.setLoadArticle(articles.get(holder.getAdapterPosition()));
                } else {
                    contentFragment.setLoadArticle(articles.get(selectedPos));
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticleVH extends RecyclerView.ViewHolder {
        private TextView title;
        private View mainView;


        public ArticleVH(View itemView) {


            super(itemView);
            this.mainView = itemView;


            title = (TextView) itemView.findViewById(R.id.article_title);


        }
    }
}
