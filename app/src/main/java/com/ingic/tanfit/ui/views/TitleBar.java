package com.ingic.tanfit.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.rohitarya.picasso.facedetection.transformation.FaceCenterCrop;
import com.rohitarya.picasso.facedetection.transformation.core.PicassoFaceDetector;
import com.squareup.picasso.Picasso;

public class TitleBar extends RelativeLayout {

	private TextView txtTitle;
	private ImageView btnLeft;
	private ImageView btnLeft2;
	private ImageView btnRight2;
	private ImageView btnRight;
	private AnyTextView txtBadge;
	private AnyTextView txtEnd;



	private View.OnClickListener menuButtonListener;
	private OnClickListener backButtonListener;
	private OnClickListener notificationButtonListener;

	private Context context;


	public TitleBar(Context context) {
		super(context);
		this.context = context;
		initLayout(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout(context);
		if (attrs != null)
			initAttrs(context, attrs);
	}

	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initLayout(context);
		if (attrs != null)
			initAttrs(context, attrs);
	}

	private void initAttrs(Context context, AttributeSet attrs) {
	}

	private void bindViews() {

		txtTitle = (TextView) this.findViewById(R.id.txt_subHead);
		btnRight = (ImageView) this.findViewById(R.id.btnRight);
		btnRight2 = (ImageView) this.findViewById(R.id.btnRight2);
		btnLeft = (ImageView) this.findViewById(R.id.btnLeft);
		btnLeft2 = (ImageView) this.findViewById(R.id.btn_left2);
		txtBadge = (AnyTextView) findViewById(R.id.txtBadge);
		txtEnd = (AnyTextView) findViewById(R.id.txt_skip);

	}

	private void initLayout(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.header_main, this);
		bindViews();
	}

	public void hideButtons() {
		txtTitle.setVisibility(View.INVISIBLE);
		btnLeft.setVisibility(View.INVISIBLE);
		btnLeft2.setVisibility(View.INVISIBLE);
		btnRight.setVisibility(View.INVISIBLE);
		btnRight2.setVisibility(View.INVISIBLE);
		txtBadge.setVisibility(View.GONE);
		txtEnd.setVisibility(GONE);

	}

	public void showBackButton() {
		btnLeft.setVisibility(View.INVISIBLE);
		btnLeft2.setVisibility(View.VISIBLE);
		btnLeft2.setImageResource(R.drawable.back_btn);
		btnLeft2.setOnClickListener(backButtonListener);

	}

	public void showHeartButton(OnClickListener heart) {
		btnRight2.setVisibility(View.VISIBLE);
		btnRight2.setImageResource(R.drawable.heart);
		btnRight2.setOnClickListener(heart);

	}
	public void showFilterButton(OnClickListener filter) {
		btnRight2.setVisibility(View.VISIBLE);
		btnRight2.setImageResource(R.drawable.filter);
		btnRight2.setOnClickListener(filter);

	}

	public void showMenuButton(DockActivity dockActivity) {
		btnLeft2.setVisibility(View.GONE);
		btnLeft.setVisibility(View.VISIBLE);
		btnLeft.setOnClickListener(menuButtonListener);
	//	btnLeft.setImageResource(R.drawable.profileimage12);

		PicassoFaceDetector.initialize(dockActivity);
		Picasso
				.with(dockActivity)
				.load(R.drawable.profileimage12)

				.transform(new FaceCenterCrop((int)getResources().getDimension(R.dimen.x40), (int)getResources().getDimension(R.dimen.x40))) //in pixels. You can also use FaceCenterCrop(width, height, unit) to provide width, height in DP.
				.into(btnLeft);

	}

	public void setSubHeading(String heading) {
		txtTitle.setVisibility(View.VISIBLE);
		txtTitle.setText(heading);

	}

	public void showSkipText(OnClickListener skip) {
		txtEnd.setVisibility(View.VISIBLE);
		txtEnd.setText(R.string.skip);
		txtEnd.setOnClickListener(skip);

	}

	public void showNotificationButton(int Count) {
		btnRight.setVisibility(View.INVISIBLE);
		btnRight2.setVisibility(View.VISIBLE);
		btnRight2.setOnClickListener(notificationButtonListener);
		btnRight2.setImageResource(R.drawable.ic_launcher);
		if (Count > 0) {
			txtBadge.setVisibility(View.VISIBLE);
			txtBadge.setText(Count + "");
		} else {
			txtBadge.setVisibility(View.GONE);
		}

	}

	public void showTitleBar() {
		this.setVisibility(View.VISIBLE);
	}

	public void hideTitleBar() {
		this.setVisibility(View.GONE);
	}

	public void setMenuButtonListener(View.OnClickListener listener) {
		menuButtonListener = listener;
	}

	public void setBackButtonListener(View.OnClickListener listener) {
		backButtonListener = listener;
	}

	public void setNotificationButtonListener(View.OnClickListener listener) {
		notificationButtonListener = listener;
	}



}
