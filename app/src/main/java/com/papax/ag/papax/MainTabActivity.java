package com.papax.ag.papax;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import adapter.MainTabsPagerAdapter;

public class MainTabActivity extends AppCompatActivity {
	private TabLayout slidingTabLayout;
	private ViewPager pager;
	private MainTabsPagerAdapter adapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_layout);

		slidingTabLayout = findViewById(R.id.sliding_tabs);
		pager = findViewById(R.id.pager);

		initPagerWithAdapter();
		initTabsAndAdd();
		setupPagerWithTabs();

	}


	private void initTabsAndAdd() {
		slidingTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.colorWhite70), ContextCompat.getColor(this, R.color.colorWhite));
	}

	private void setupPagerWithTabs() {
		slidingTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				pager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});
		slidingTabLayout.setupWithViewPager(pager);
	}


	private void initPagerWithAdapter() {
		adapter = new MainTabsPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);

	}


	@Override
	protected void onResume() {
		super.onResume();
	}


}
