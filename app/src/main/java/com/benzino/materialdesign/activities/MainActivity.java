package com.benzino.materialdesign.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.benzino.materialdesign.fragments.FragmentBoxOffice;
import com.benzino.materialdesign.fragments.FragmentSearch;
import com.benzino.materialdesign.fragments.FragmentUpcoming;
import com.benzino.materialdesign.fragments.MyFragment;
import com.benzino.materialdesign.fragments.NavigationDrawerFragment;
import com.benzino.materialdesign.R;
import com.benzino.materialdesign.views.SlidingTabLayout;


public class MainActivity extends ActionBarActivity  implements FragmentBoxOffice.OnFragmentInteractionListener{
    private Toolbar toolbar;
    private ViewPager pager;
    private SlidingTabLayout tabs;
    private static final int MOVIES_SEARCH_RESULTS = 0;
    private static final int MOVIES_HITS = 1;
    private static final int MOVIES_UPCOMING = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Replace with this if you want the appbar
        // to stay above the navigation drawer
        //setContentView(R.layout.activity_main_appbar);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setup(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //pager.setOffscreenPageLimit();
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.accentColor));
        tabs.setBackgroundColor(getResources().getColor(R.color.primaryColor));

        tabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"Hey you just hit the "+item.getTitle(), Toast.LENGTH_LONG).show();
            return true;
        }

        if(id == R.id.navigate){
            startActivity(new Intent(getApplicationContext(), SubActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        String[] tabs;
        int icons[] = {R.mipmap.ic_home,R.mipmap.ic_articles, R.mipmap.ic_account};
        String[] tabText = getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabText = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position){
                case MOVIES_SEARCH_RESULTS:
                    fragment = FragmentSearch.newInstance("", "");
                    break;

                case MOVIES_HITS:
                    fragment = FragmentBoxOffice.newInstance("", "");
                    break;

                case MOVIES_UPCOMING:
                    fragment = FragmentUpcoming.newInstance("", "");
                    break;
            }

            return fragment;
        }


        @Override
        public int getCount() {
            return 3;
        }
    }


}
