package com.papax.ag.papax.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.papax.ag.papax.R;

public class LocationSelectView extends ConstraintLayout {

	private TextView locationTextView;
	private TextView editTextView;

	public LocationSelectView(Context context) {
		super(context);
		init();
	}

	public LocationSelectView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LocationSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		setClipChildren(false);
		inflate(getContext(), R.layout.location_select_view, this);
		locationTextView = findViewById(R.id.location);
		editTextView = findViewById(R.id.edit_btn);
	}

	public void setOnEditClickListener(OnClickListener clickListener) {
		editTextView.setOnClickListener(clickListener);
	}

	public void setLocationText(String text) {
		locationTextView.setText(text);
	}
}
