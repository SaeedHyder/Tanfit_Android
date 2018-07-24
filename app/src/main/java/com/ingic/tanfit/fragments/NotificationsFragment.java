package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.NotificationEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.BinderNotification;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 9/15/2017.
 */

public class NotificationsFragment extends BaseFragment {

    @BindView(R.id.lv_notification)
    ListView lvNotification;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    private List<NotificationEnt> notificationCollection;
    private ArrayListAdapter<NotificationEnt> adapter;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderNotification(getDockActivity(),prefHelper));
    }

   /* @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getNotification:
                bindData((ArrayList<NotificationEnt>) result);
                break;
            case WebServiceConstants.NotificaitonCount:
                prefHelper.setNotificationCount(0);
                break;
        }
    }*/

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.notification));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefHelper.setNotificationCount(0);
        //serviceHelper.enqueueCall(webService.getNotificationCount(prefHelper.getMerchantId()), WebServiceConstants.NotificaitonCount);
       //bindData();

        lvNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }



    public void bindData(ArrayList<NotificationEnt> result) {

        notificationCollection = new ArrayList<>();

        if (result.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvNotification.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvNotification.setVisibility(View.VISIBLE);

        }
        notificationCollection.addAll(result);
        adapter.clearList();
        lvNotification.setAdapter(adapter);
        adapter.addAll(notificationCollection);
        adapter.notifyDataSetChanged();

    }

}
