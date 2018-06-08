package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import model.Card;

public class TabCardViewAdapter extends RecyclerView.Adapter<TabCardViewAdapter.TabViewHolder> {
	public static int LocationViewType = 1;
	public static int SUGGESTION_ROUTE_VIEWTYPE = 2;
	public static int UNKNOWN_VIEW_TYPE = 3;
	private List<Card> itemList;



	public TabCardViewAdapter() {
		itemList = new ArrayList<>();
	}

	@Override
	public int getItemViewType(int position) {
//		itemList.get(position)
		return 0;
	}

	@NonNull
	@Override
	public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return null;
	}

	@Override
	public void onBindViewHolder(@NonNull TabViewHolder holder, int position) {

	}


	@Override
	public int getItemCount() {
		return itemList.size();
	}

	public static class TabViewHolder extends RecyclerView.ViewHolder {

		public TabViewHolder(View itemView) {
			super(itemView);
		}
	}
}
