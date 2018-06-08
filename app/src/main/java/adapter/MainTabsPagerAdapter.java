package adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import fragments.HomeTabFragment;
import fragments.LunchTabFragment;
import fragments.WorkTabFragment;

public class MainTabsPagerAdapter extends FragmentStatePagerAdapter {
	private HomeTabFragment homeTabFragment;
	private LunchTabFragment lunchTabFragment;
	private WorkTabFragment workTabFragment;

	public MainTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0: {

				if (workTabFragment == null) {
					workTabFragment = new WorkTabFragment();
					return workTabFragment;
				}
				return workTabFragment;
			}
			case 1: {
				if (lunchTabFragment == null) {
					lunchTabFragment = new LunchTabFragment();
					return lunchTabFragment;
				}
				return lunchTabFragment;

			}

			case 2: {
				if (homeTabFragment == null) {
					homeTabFragment = new HomeTabFragment();
					return homeTabFragment;
				}
				return homeTabFragment;

			}
		}
		return null;
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return false;
	}
}
