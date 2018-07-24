package com.ingic.tanfit.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.SlotsEnt;
import com.ingic.tanfit.entities.StudioImage;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


/**
 * Created on 5/24/2017.
 */

public class DialogHelper {
    private Dialog dialog;
    private Context context;
    private ImageLoader imageLoader;
    private RadioGroup rg;


    public DialogHelper(Context context) {
        this.context = context;
        this.dialog = new Dialog(context);
    }


    public Dialog initlogout(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog initbooknow(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog initActivateAccount(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog iniDeleteAccount(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog initSlotDialoge(int layoutID, RadioGroup.OnCheckedChangeListener listener, CompoundButton.OnCheckedChangeListener radioBtnListner, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener,
                                  DockActivity dockActivity, ArrayList<SlotsEnt> slots) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        final RelativeLayout rl = (RelativeLayout) dialog.findViewById(R.id.rl_radioGroup);

        setRadioGroup(rl, slots,listener,radioBtnListner);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);

        return this.dialog;
    }

    private void setRadioGroup(RelativeLayout radioGroup, final ArrayList<SlotsEnt> slots, RadioGroup.OnCheckedChangeListener listener, CompoundButton.OnCheckedChangeListener listner2) {

        rg = new RadioGroup(context);
        //RadioGroup rg=(RadioGroup)dialog.findViewById(R.id.radioGroupView);
        rg.setOrientation(RadioGroup.VERTICAL);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        rg.setLayoutParams(lp);

        for (SlotsEnt item : slots) {

            RadioButton radioBtn = new RadioButton(context);
            radioBtn.setPadding((int) context.getResources().getDimension(R.dimen.x5), 0, 0, 0);
           // radioBtn.setGravity(Gravity.START);
            radioBtn.setTextColor(context.getResources().getColor(R.color.dark_grey));
            radioBtn.setText(DateHelper.getFormatedDate("HH:mm:ss", "hh:mm aa", item.getTimeIn()) + " - " + DateHelper.getFormatedDate("HH:mm:ss", "hh:mm aa", item.getTimeOut()));
            radioBtn.setOnCheckedChangeListener(listner2);
            rg.addView(radioBtn);

            //rg.check(radioBtn.getId());
          //  ((RadioButton)rg.getChildAt(0)).setChecked(true);
            //radioBtn.rg.getChildAt(0).setChecked(true);
        }
        radioGroup.addView(rg);

        rg.setOnCheckedChangeListener(listener);


    }


    public Dialog initCancel(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog initConformBooking(int layoutID, View.OnClickListener onokclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_ok);
        okbutton.setOnClickListener(onokclicklistener);
        return this.dialog;
    }


    public Dialog openImageinBig(int layoutID, int imageRes) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        ImageView okbutton = (ImageView) dialog.findViewById(R.id.img_bigview);
        okbutton.setImageResource(imageRes);

        return this.dialog;
    }

    public Dialog openSlider(int layoutID, ArrayList<StudioImage> images, int position, Context context) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        SliderLayout imageSlider = (SliderLayout) dialog.findViewById(R.id.slider);

        for (StudioImage item : images) {
            DefaultSliderView textSliderView = new DefaultSliderView(context);
            // initialize a SliderLayout
            textSliderView
                    .image(item.getImage())
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", item.getImage() + "");

            imageSlider.addSlider(textSliderView);
        }
        imageSlider.setCurrentPosition(position);
        imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        imageSlider.stopAutoCycle();

        return this.dialog;
    }

    public Dialog subcriptionDialoge(int layoutID, String Title, String Description, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        AnyTextView title = (AnyTextView) dialog.findViewById(R.id.txt_title);
        AnyTextView description = (AnyTextView) dialog.findViewById(R.id.txt_description);
        title.setText(Title);
        description.setText(Description);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }


    public void showDialog() {

        dialog.show();
    }

    public void setCancelable(boolean isCancelable) {
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);
    }

    public void hideDialog() {
        dialog.dismiss();
    }
}
