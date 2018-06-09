package fragments;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.papax.ag.papax.R;

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		outRect.top = view.getContext().getResources().getDimensionPixelSize(R.dimen.space_10dp);
		outRect.bottom = view.getContext().getResources().getDimensionPixelSize(R.dimen.space_10dp);
	}
}
