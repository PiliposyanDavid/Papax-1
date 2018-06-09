package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.papax.ag.papax.R;

import java.util.ArrayList;
import java.util.List;

import model.Card;

public class TabCardViewAdapter extends RecyclerView.Adapter<TabCardViewAdapter.TabViewHolder> {
	public static final int SUGGESTION_ROUTE_VIEWTYPE = 2;
	public static final int DRIVER_ROUTE_CARD_TYPE = 1;
	public static final int UNKNOWN_VIEW_TYPE = 3;
	private List<Card> itemList;



	public TabCardViewAdapter() {
		itemList = new ArrayList<>();
		Card card = new Card();
		card.type = Card.ROUTE_SUGGETION_TYPE;
		itemList.add(card);
	}

	@Override
	public int getItemViewType(int position) {
		if (Card.DRIVER_INFO_CARD.equals(itemList.get(position).type)) {
			return DRIVER_ROUTE_CARD_TYPE;
		} else if (Card.ROUTE_SUGGETION_TYPE.equals(itemList.get(position).type)) {
			return SUGGESTION_ROUTE_VIEWTYPE;
		}
		return 0;
	}

	@NonNull
	@Override
	public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v;
		if (viewType == SUGGESTION_ROUTE_VIEWTYPE) {
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_suggestion_layout, parent, false);
			return new TabViewHolder(v, viewType);
		}
		return null;
	}

	@Override
	public void onBindViewHolder(@NonNull TabViewHolder holder, int position) {
		switch (holder.getItemViewType()) {
			case SUGGESTION_ROUTE_VIEWTYPE: {
				holder.imageAvatar.setImageURI("https://i.ytimg.com/vi/aaAty6HhN5c/hqdefault.jpg");
			}
		}
	}


	@Override
	public int getItemCount() {
		return itemList.size();
	}

	public static class TabViewHolder extends RecyclerView.ViewHolder {
		public SimpleDraweeView imageAvatar;
		public TextView carModel;
		public TextView distance;
		public TextView licencePlate;
		public TextView driverName;
		public TextView pickupTime;
		public Button bookButton;


		public TabViewHolder(View itemView, int viewType) {
			super(itemView);

			if (viewType == SUGGESTION_ROUTE_VIEWTYPE) {
				imageAvatar = itemView.findViewById(R.id.user_image_id);
				carModel = itemView.findViewById(R.id.model);
				distance = itemView.findViewById(R.id.distance);
				licencePlate = itemView.findViewById(R.id.licence);
				driverName = itemView.findViewById(R.id.driver_name);
				pickupTime = itemView.findViewById(R.id.picup_time);
				bookButton = itemView.findViewById(R.id.book_btn);
			}
		}
	}
}
