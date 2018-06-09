package adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.DriverWorkTabFragment;
import fragments.WorkTabFragment;
import model.MainTabResponse;

public class MainTabsPagerAdapter extends FragmentPagerAdapter {
	private WorkTabFragment homeTabFragment;
	private WorkTabFragment lunchTabFragment;
	private WorkTabFragment workTabFragment;
	private DriverWorkTabFragment driverWorkTabFragment;
	private DriverWorkTabFragment driverLunchTabFragment;
	private DriverWorkTabFragment driverHomeTabFragment;
	private boolean isPassanger;

	public MainTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}


	public void updateTabsWithData(MainTabResponse response) {
		if (workTabFragment != null) {
			workTabFragment.onDataUpdated(response);
		}
		if (homeTabFragment != null) {
			homeTabFragment.onDataUpdated(response);
		}
		if (lunchTabFragment != null) {
			lunchTabFragment.onDataUpdated(response);
		}
	}

	public void setUserTypeIsPassanger(boolean isPassanger) {
		this.isPassanger = isPassanger;
		if (workTabFragment != null) {
			workTabFragment.setUserTypeIsPassanger(isPassanger);
		}
		if (homeTabFragment != null) {
			homeTabFragment.setUserTypeIsPassanger(isPassanger);
		}
		if (lunchTabFragment != null) {
			lunchTabFragment.setUserTypeIsPassanger(isPassanger);
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Fragment getItem(int position) {

		if (isPassanger) {
			switch (position) {
				case 0: {

					if (workTabFragment == null) {
						workTabFragment = new WorkTabFragment();
						workTabFragment.setTabPosition(position);
						workTabFragment.setUserTypeIsPassanger(isPassanger);
						return workTabFragment;
					}
					return workTabFragment;
				}
				case 1: {
					if (lunchTabFragment == null) {
						lunchTabFragment = new WorkTabFragment();
						lunchTabFragment.setTabPosition(position);
						lunchTabFragment.setUserTypeIsPassanger(isPassanger);
						return lunchTabFragment;
					}
					return lunchTabFragment;

				}

				case 2: {
					if (homeTabFragment == null) {
						homeTabFragment = new WorkTabFragment();
						homeTabFragment.setTabPosition(position);
						homeTabFragment.setUserTypeIsPassanger(isPassanger);
						return homeTabFragment;
					}
					return homeTabFragment;

				}
			}
		} else {
			switch (position) {
				case 0: {

					if (driverWorkTabFragment == null) {
						driverWorkTabFragment = new DriverWorkTabFragment();
						driverWorkTabFragment.setUserTypeIsPassanger(isPassanger);
						driverWorkTabFragment.setTabPosition(position);
						return driverWorkTabFragment;
					}
					return driverWorkTabFragment;
				}
				case 1: {
					if (driverLunchTabFragment == null) {
						driverLunchTabFragment = new DriverWorkTabFragment();
						driverLunchTabFragment.setUserTypeIsPassanger(isPassanger);
						driverLunchTabFragment.setTabPosition(position);
						return driverLunchTabFragment;
					}
					return driverLunchTabFragment;

				}

				case 2: {
					if (driverHomeTabFragment == null) {
						driverHomeTabFragment = new DriverWorkTabFragment();
						driverHomeTabFragment.setUserTypeIsPassanger(isPassanger);
						driverHomeTabFragment.setTabPosition(position);
						return driverHomeTabFragment;
					}
					return driverHomeTabFragment;

				}
			}
		}
		return null;
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
			case 0: {
				return "TO WORK";
			}
			case 1: {
				return "LUNCH";
			}

			case 2: {
				return "FROM WORK";
			}
		}
		return super.getPageTitle(position);
	}

}
