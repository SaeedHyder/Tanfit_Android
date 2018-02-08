package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.SlotsEnt;
import com.ingic.tanfit.ui.viewbinders.abstracts.ViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 1/1/2018.
 */

public class ClassSlotsItemBinder extends ViewBinder<SlotsEnt> {

    private DockActivity dockActivity;
    private ImageLoader imageLoader;
    private RadioButton lastCheckedRB = null;
    private RadioGroup group;


    public ClassSlotsItemBinder(DockActivity dockActivity) {
        super(R.layout.row_item_classesslot);
        this.dockActivity = dockActivity;
        this.imageLoader = ImageLoader.getInstance();
        group = new RadioGroup(dockActivity);
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(SlotsEnt entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

       /* viewHolder.raioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (lastCheckedRB != null) {
                    lastCheckedRB.setChecked(false);
                }
                //store the clicked radiobutton
                lastCheckedRB =  viewHolder.raioBtn;
            }
        });*/

        viewHolder.txtSlot.setText(entity.getTimeIn() + " - " + entity.getTimeOut());

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.raio_btn)
        RadioButton raioBtn;
        @BindView(R.id.txt_slot)
        AnyTextView txtSlot;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
