package com.lebartodev.mercurylab4.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lebartodev.mercurylab4.R;
import com.lebartodev.mercurylab4.model.Article;
import com.lebartodev.mercurylab4.ui.fragment.ContentFragment;
import com.lebartodev.mercurylab4.ui.fragment.ContentFragment_;
import com.lebartodev.mercurylab4.ui.fragment.ListFragment;
import com.lebartodev.mercurylab4.ui.fragment.ListFragment_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

@EActivity(R.layout.a_content)
public class ActivityContent extends AppCompatActivity {
    FragmentManager fragmentManager;
    @Extra
    Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        fragmentManager = getFragmentManager();
        if (getResources().getConfiguration().orientation != ORIENTATION_PORTRAIT){
            finish();
        }
            Fragment contentFragment = fragmentManager.findFragmentByTag(ContentFragment.TAG);
        if (contentFragment == null) {
            fragmentManager.beginTransaction().replace(R.id.layout_content, ContentFragment_.builder().article(article).build(), ContentFragment.TAG).commit();
        }

    }
}
