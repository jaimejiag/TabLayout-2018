package com.examle.jaime.tablayout;

import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * OPCIÓN 1: En este ejercicio se ha visto como crear un tabLayout de forma independiente
 * al elemento ViewPager.
 *
 * OPCION 2: Es vincular el TabLayout al ViewPager con el método setupWithViewPager().
 * Se debe cumplir únicamente el siguiente requisito: el título de las pestañas del TAB se
 * inicializan mediante el método
 */
public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        for (String title : getResources().getStringArray(R.array.tabs)) {
            //mTabLayout.addTab(mTabLayout.newTab().setText(title));
            mTabLayout.addTab(mTabLayout.newTab());
        }

        //Configurar ViewPager
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.tabs)))));
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        //OPCION 2:
        mTabLayout.setupWithViewPager(mViewPager);

        //Si se quiere personalizar las pestañas del TabLayout siempre debe de hacerse
        //despues del método setupWithViewPager.
        setupTabIcons();

        /*
        OPCION 1:
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position, 0f, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fragment, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }


    private void setupTabIcons() {
        TypedArray typedArray = getResources().obtainTypedArray(R.array.icons);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(typedArray.getDrawable(i));
        }

        typedArray.recycle();
    }
}
