package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.BookingHistoryEnt;
import com.ingic.tanfit.entities.CurrentBookingEnt;
import com.ingic.tanfit.entities.CurrentBookingEntWithHeader;
import com.ingic.tanfit.entities.FitnessClassess;
import com.ingic.tanfit.entities.UserFitnessClasses;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.helpers.DateHelper;
import com.ingic.tanfit.ui.adapters.ArrayListExpandableAdapter;
import com.ingic.tanfit.ui.binders.CurrentBookingItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/25/2017.
 */
public class BookingHistoryFragment extends BaseFragment {
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.elv_booking_history)
    ExpandableListView elvBookingHistory;
    Unbinder unbinder;
    private ArrayList<BookingHistoryEnt> bookingHistory=new ArrayList<>();
    private ArrayList<FitnessClassess> CurrentFitnessClasses = new ArrayList<>();
    private ArrayList<CurrentBookingEntWithHeader> headerList=new ArrayList<>();

    private ArrayListExpandableAdapter<String, CurrentBookingEnt> adapter;
    private ArrayList<String> collectionGroup;
    private ArrayList<CurrentBookingEnt> collectionChild ;
    private HashMap<String, ArrayList<CurrentBookingEnt>> listDataChild;

    public static BookingHistoryFragment newInstance() {
        Bundle args = new Bundle();

        BookingHistoryFragment fragment = new BookingHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setContent(ArrayList<BookingHistoryEnt> bookingHistory){
            this.bookingHistory=bookingHistory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookinghistory, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isLanguagePersian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
/*
        for (int i = 0; i < prefHelper.getNearestStuidos().getFitnessClassess().size(); i++) {
            for (int j = 0; j < bookingHistory.size(); j++) {
                if (prefHelper.getNearestStuidos().getFitnessClassess().get(i).getId().equals(bookingHistory.get(j).getFitnessClassId())) {
                    CurrentFitnessClasses.add(prefHelper.getNearestStuidos().getFitnessClassess().get(i));
                }
            }
        }*/

        for(BookingHistoryEnt item:bookingHistory){

            if(prefHelper.isLanguagePersian()){
                headerList.add(new CurrentBookingEntWithHeader(item.getClassNamePer(),
                        new CurrentBookingEnt(DateHelper.getFormatedDate("yyyy-MM-dd'T'HH:mm:ss", "HH:mm MMMM dd,yyyy", item.getModifiedOn()),
                                item.getStudioNamePer(),item.getAddressPer())));
            }else{
                headerList.add(new CurrentBookingEntWithHeader(item.getClassNameEng(),
                        new CurrentBookingEnt(DateHelper.getFormatedDate("yyyy-MM-dd'T'HH:mm:ss", "HH:mm MMMM dd,yyyy", item.getModifiedOn()),
                                item.getStudioNameEng(),item.getAddressEng())));
            }


        }

        setBookingHistoryData(headerList);
    }

    private void setBookingHistoryData(ArrayList<CurrentBookingEntWithHeader> headerList) {
        collectionGroup = new ArrayList<>();
        collectionChild = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i = 0; i < headerList.size(); i++) {

            collectionGroup.add(headerList.get(i).getFitnessHeader());
            collectionChild.add(headerList.get(i).getDetail());

            listDataChild.put(collectionGroup.get(i), collectionChild);
            collectionChild = new ArrayList<>();

        }

     /*   collectionGroup.add("Spinning Class");
        collectionGroup.add("Yoga Class");
        collectionGroup.add("Spinning Class");

        collectionChild.add(new CurrentBookingEnt("December 13,2018","Fitness Center 55",
                "IR IRAN Tehran Vali Asr St Something House 2554 Apartment Theus"));

        listDataChild.put(collectionGroup.get(0),collectionChild);
        listDataChild.put(collectionGroup.get(1),collectionChild);
        listDataChild.put(collectionGroup.get(2),collectionChild);*/

     if(collectionGroup.size()<=0){
         txtNoresult.setVisibility(View.VISIBLE);
         elvBookingHistory.setVisibility(View.GONE);
     }
     else
     {
         txtNoresult.setVisibility(View.GONE);
         elvBookingHistory.setVisibility(View.VISIBLE);
     }

        adapter = new ArrayListExpandableAdapter<>(getDockActivity(), collectionGroup, listDataChild, new CurrentBookingItemBinder(getDockActivity(),prefHelper), elvBookingHistory);
        elvBookingHistory.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


}
