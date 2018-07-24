package com.ingic.tanfit.ui.binders;


import android.app.Activity;
import android.view.View;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.StudioFeature;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.viewbinders.abstracts.ExpandableListViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.ExpandableGridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 6/6/2017.
 */


public class StudioFeatureExpandedBinder extends ExpandableListViewBinder<String, ArrayList<StudioFeature>> {

    private ArrayListAdapter<StudioFeature> adapter;
    private ArrayList<StudioFeature> userCollection = new ArrayList<>();
    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;

    public StudioFeatureExpandedBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper) {
        super(R.layout.row_item_feature_header, R.layout.row_item_gridview);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
    }

    @Override
    public BaseGroupViewHolder createGroupViewHolder(View view) {
        return new GroupViewHolder(view);
    }

    @Override
    public BaseGroupViewHolder createChildViewHolder(View view) {
        return new ChildViewholder(view);
    }

    @Override
    public void bindGroupView(String entity, int groupPosition, int position, int grpPosition, View view, Activity activity, boolean isExpanded) {
        GroupViewHolder holder = (GroupViewHolder) view.getTag();
        holder.header.setText(entity);
    }

    @Override
    public void bindChildView(ArrayList<StudioFeature> entity, int position, int grpPosition, View view, Activity activity) {


    }

    @Override
    public void bindGroupView(String entity, int position, int grpPosition, int childCount, View view, Activity activity) {

    }

    @Override
    public void bindChildView(ArrayList<StudioFeature> entity, int position, int grpPosition, int size, View view, Activity activity) {
        ChildViewholder childViewholder = (ChildViewholder) view.getTag();

        if ((entity.size() % 2) == 0)
            childViewholder.view.setVisibility(View.GONE);
        else
            childViewholder.view.setVisibility(View.VISIBLE);



        adapter = new ArrayListAdapter<StudioFeature>(dockActivity, new speacialFeatureItemBinder(dockActivity, prefHelper));
        userCollection = new ArrayList<>();
        userCollection.addAll(entity);
        adapter.clearList();
        adapter.addAll(userCollection);
        childViewholder.gvGym.setAdapter(adapter);

    }


    static class GroupViewHolder extends BaseGroupViewHolder {
        @BindView(R.id.header)
        AnyTextView header;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewholder extends BaseGroupViewHolder {
        @BindView(R.id.gv_gym)
        ExpandableGridView gvGym;
        @BindView(R.id.view)
        View view;


        ChildViewholder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
