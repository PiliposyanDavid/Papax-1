package com.papax.ag.papax.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.papax.ag.papax.R;

public class LocationSelectView extends ConstraintLayout {

	private TextView locationTextView;
	private TextView editTextView;
	private ProgressBar loadingIndicator;

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
		loadingIndicator = findViewById(R.id.loading_indicator);
	}

	public void setEditable(boolean editable) {
		editTextView.setVisibility(editable ? View.VISIBLE : View.GONE);
	}

	public void setProgressMode(boolean progress) {
		findViewById(R.id.marker_icon).setVisibility(progress ? View.GONE : View.VISIBLE);
		loadingIndicator.setVisibility(progress ? View.VISIBLE : View.GONE);
	}

	public void setOnEditClickListener(OnClickListener clickListener) {
		editTextView.setOnClickListener(clickListener);
	}

	public void setLocationText(String text) {
		locationTextView.setText(text);
	}

	public String getLocationText() {
		return locationTextView.getText().toString();
	}
}
