package com.lebartodev.mercurylab4;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.lebartodev.mercurylab4.ui.fragment.ListFragment;
import com.lebartodev.mercurylab4.ui.fragment.ListFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

@EActivity(R.layout.a_news_list)
public class NewsListActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    @ViewById
    DrawerLayout drawer_layout;

    private ActionBarDrawerToggle toggle;

    private void checkBackStack() {
        boolean canback = fragmentManager.getBackStackEntryCount() > 0 && getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT;
        toggle.setDrawerIndicatorEnabled(!canback);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @AfterViews
    void initToolbar() {

        toggle = new ActionBarDrawerToggle(
                this,
                drawer_layout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(true);

        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        fragmentManager = getFragmentManager();
        checkBackStack();



        /*if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT&&contentFragment!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }*/
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        fragmentManager = getFragmentManager();


        Fragment listFragment = fragmentManager.findFragmentByTag(ListFragment.TAG);
        if (listFragment == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.layout_list, ListFragment_.builder().build(), ListFragment.TAG)
                    .commit();

        }
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                checkBackStack();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fragmentManager = getFragmentManager();

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                boolean canback = fragmentManager.getBackStackEntryCount() > 0 && getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT;
                if (canback)
                    fragmentManager.popBackStack();
                else if (drawer_layout.isDrawerOpen(GravityCompat.START))
                    drawer_layout.closeDrawer(GravityCompat.START);
                else
                    drawer_layout.openDrawer(GravityCompat.START);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

}
