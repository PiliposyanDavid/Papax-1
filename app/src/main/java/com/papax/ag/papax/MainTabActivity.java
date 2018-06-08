package com.papax.ag.papax;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

import adapter.MainTabsPagerAdapter;

public class MainTabActivity extends AppCompatActivity {
	private TabLayout slidingTabLayout;
	private ViewPager pager;
	private MainTabsPagerAdapter adapter;
	private SimpleDraweeView avatarImage;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_layout);

		slidingTabLayout = findViewById(R.id.sliding_tabs);
		pager = findViewById(R.id.pager);
		avatarImage = findViewById(R.id.user_avatar_image);

		initPagerWithAdapter();
		initTabsAndAdd();
		setupPagerWithTabs();
		avatarImage.setImageURI("https://i.ytimg.com/vi/aaAty6HhN5c/hqdefault.jpg");

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
