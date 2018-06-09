package fragments;

import model.MainTabResponse;

public interface TabFragmentDataInterface {
	void onDataUpdated(MainTabResponse mainTabResponse);
	void setUserTypeIsPassanger(boolean isPassanger);
}
