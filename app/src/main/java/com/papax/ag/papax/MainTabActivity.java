package com.papax.ag.papax;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

import adapter.MainTabsPagerAdapter;
import model.MainTabResponse;
import model.User;
import utils.UserUtil;

public class MainTabActivity extends AppCompatActivity {
	private TabLayout slidingTabLayout;
	private ViewPager pager;
	private MainTabsPagerAdapter adapter;
	private SimpleDraweeView avatarImage;
	private SwipeRefreshLayout swipeRefreshLayout;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_layout);

		slidingTabLayout = findViewById(R.id.sliding_tabs);
		pager = findViewById(R.id.pager);
		avatarImage = findViewById(R.id.user_avatar_image);
		swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setNestedScrollingEnabled(true);

		initPagerWithAdapter();
		initTabsAndAdd();
		setupPagerWithTabs();
		avatarImage.setImageURI("https://i.ytimg.com/vi/aaAty6HhN5c/hqdefault.jpg");


		User user = UserUtil.getInstance().getUser(this);
		adapter.setUserTypeIsPassanger(!user.isDriver());
		if (adapter != null) {
			adapter.updateTabsWithData(new MainTabResponse()); //// TODO: 6/8/18  change to response
		}

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO: 6/8/18 do /rides request

				swipeRefreshLayout.setRefreshing(true);
				swipeRefreshLayout.postDelayed(new Runnable() {
					@Override
					public void run() {
						swipeRefreshLayout.setRefreshing(false);
						if (adapter != null) {
							adapter.updateTabsWithData(new MainTabResponse()); //// TODO: 6/8/18  change to response
						}
					}
				}, 1200);
			}
		});

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
		pager.setOffscreenPageLimit(2);
		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (swipeRefreshLayout != null) {
					swipeRefreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
				}
			}
		});

	}


	@Override
	protected void onResume() {
		super.onResume();
	}


}
