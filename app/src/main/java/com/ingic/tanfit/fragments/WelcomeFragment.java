package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.adapters.SwipeDeckAdapter;
import com.ingic.tanfit.ui.viewbinders.abstracts.WelcomeItemBinder;
import com.ingic.tanfit.ui.views.SwipeDeck;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/20/2017.
 */
public class WelcomeFragment extends BaseFragment {

    @BindView(R.id.swipe_deck)
    SwipeDeck swipeDeck;
    @BindView(R.id.btn_next)
    Button btnNext;
    Unbinder unbinder;
    private int previousPosition = 0;
    private SwipeDeckAdapter<String> adapter;
    private ArrayList<String> userCollection;

    public static WelcomeFragment newInstance() {
        Bundle args = new Bundle();

        WelcomeFragment fragment = new WelcomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        adapter = new ArrayListAdapter<String>(getDockActivity(), new WelcomeItemBinder(getDockActivity(), prefHelper));
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swapCard();

    }

    private void swapCard() {
        final ArrayList<String> testData = new ArrayList<>();
        testData.add("0");
        testData.add("1");
        testData.add("2");
        testData.add("3");
        testData.add("4");

        final SwipeDeckAdapter<String> adapter = new SwipeDeckAdapter<String>(testData, getDockActivity(),
                new WelcomeItemBinder(getDockActivity(),prefHelper));
        swipeDeck.setAdapter(adapter);


        swipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                if(position==4){
                    return;
                }
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
                if(position==4){
                    return;
                }
            }

            @Override
            public void cardsDepleted() {

                Log.i("MainActivity", "no more cards");
            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showSkipText();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.swipe_deck, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.swipe_deck:
                break;
            case R.id.btn_next:
                swipeDeck.swipeTopCardLeft(1);
                break;
        }
    }
}
