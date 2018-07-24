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
public class CurrentBookingFragment extends BaseFragment {
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.elv_current_booking)
    ExpandableListView elvCurrentBooking;
    Unbinder unbinder;
    private ArrayList<BookingHistoryEnt> currentBooking = new ArrayList<>();
    private ArrayList<FitnessClassess> CurrentFitnessClasses = new ArrayList<>();
    private ArrayList<CurrentBookingEntWithHeader> headerList=new ArrayList<>();


    private ArrayListExpandableAdapter<String, CurrentBookingEnt> adapter;
    private ArrayList<String> collectionGroup;
    private ArrayList<CurrentBookingEnt> collectionChild;
    private HashMap<String, ArrayList<CurrentBookingEnt>> listDataChild;


    public static CurrentBookingFragment newInstance() {
        Bundle args = new Bundle();

        CurrentBookingFragment fragment = new CurrentBookingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setContent(ArrayList<BookingHistoryEnt> currentBooking) {
        this.currentBooking = currentBooking;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_booking, container, false);
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

       /* for (int i = 0; i < prefHelper.getNearestStuidos().getFitnessClassess().size(); i++) {
            for (int j = 0; j < currentBooking.size(); j++) {
                if (prefHelper.getNearestStuidos().getFitnessClassess().get(i).getId().equals(currentBooking.get(j).getFitnessClassId())) {
                    CurrentFitnessClasses.add(prefHelper.getNearestStuidos().getFitnessClassess().get(i));
                }
            }
        }*/

        for(BookingHistoryEnt item:currentBooking){

            if(prefHelper.isLanguagePersian()){
                headerList.add(new CurrentBookingEntWithHeader(item.getClassNamePer(),
                        new CurrentBookingEnt(DateHelper.getFormatedDate("yyyy-MM-dd'T'HH:mm:ss", "HH:mm MMMM dd,yyyy", item.getBookingDateTime()),
                                item.getStudioNamePer(),item.getAddressPer())));
            }
            else{
                headerList.add(new CurrentBookingEntWithHeader(item.getClassNameEng(),
                        new CurrentBookingEnt(DateHelper.getFormatedDate("yyyy-MM-dd'T'HH:mm:ss", "HH:mm MMMM dd,yyyy", item.getBookingDateTime()),
                                item.getStudioNameEng(),item.getAddressEng())));
            }

        }

        setCurrentBookingData(headerList);
    }

    private void setCurrentBookingData(ArrayList<CurrentBookingEntWithHeader> headerList) {
        collectionGroup = new ArrayList<>();
        collectionChild = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i = 0; i < headerList.size(); i++) {

            collectionGroup.add(headerList.get(i).getFitnessHeader());
            collectionChild.add(headerList.get(i).getDetail());

            listDataChild.put(collectionGroup.get(i), collectionChild);
            collectionChild = new ArrayList<>();

        }

       /* collectionGroup.add("Yoga Class");
        collectionGroup.add("Spinning Class");
        collectionGroup.add("Yoga Class");

        collectionChild.add(new CurrentBookingEnt("November 13,2017", "Fitness Center 1", "IR IRAN Tehran Vali Asr St Something House 2554 Apartment Theus"));
*/
     /*
        listDataChild.put(collectionGroup.get(1), collectionChild);
        listDataChild.put(collectionGroup.get(2), collectionChild);*/

        if(collectionGroup.size()<=0){
            txtNoresult.setVisibility(View.VISIBLE);
            elvCurrentBooking.setVisibility(View.GONE);
        }
        else
        {
            txtNoresult.setVisibility(View.GONE);
            elvCurrentBooking.setVisibility(View.VISIBLE);
        }

        adapter = new ArrayListExpandableAdapter<>(getDockActivity(), collectionGroup, listDataChild, new CurrentBookingItemBinder(getDockActivity(), prefHelper), elvCurrentBooking);
        elvCurrentBooking.setAdapter(adapter);
        adapter.notifyDataSetChanged();
       // currentBooking=new ArrayList<>();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


}
