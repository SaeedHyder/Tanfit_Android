package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.ui.adapters.CardArrayAdapter;
import com.ingic.tanfit.ui.binders.WelcomeItemBinder;
import com.ingic.tanfit.ui.views.TitleBar;
import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/20/2017.
 */
public class WelcomeFragment extends BaseFragment {

    final ArrayList<String> testData = new ArrayList<>();
    @BindView(R.id.swipe_deck)
    CardStack swipeDeck;
    @BindView(R.id.btn_next)
    Button btnNext;
    Unbinder unbinder;
    private int previousPosition = 0;
    private CardArrayAdapter<String> adapter;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void swapCard() {

        testData.clear();
        testData.add("0");
        testData.add("1");
        testData.add("2");
        testData.add("3");
        testData.add("4");
        adapter = new CardArrayAdapter<String>(getDockActivity(), R.layout.row_item_card,
                new WelcomeItemBinder(getDockActivity(), prefHelper), testData);
     /*   final ArrayListAdapter<String> adapter = new ArrayListAdapter<String>(testData, getDockActivity(),
                new WelcomeItemBinder(getDockActivity(),prefHelper));*/
        swipeDeck.setContentResource(R.layout.row_item_card);

        swipeDeck.setAdapter(adapter);
        final int count = 0;
        swipeDeck.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int i, float v) {
                return v > 300;
            }

            @Override
            public boolean swipeStart(int i, float v) {
                return false;
            }

            @Override
            public boolean swipeContinue(int i, float v, float v1) {
                return false;
            }

            @Override
            public void discarded(int i, int i1) {

                if (swipeDeck != null && swipeDeck.getCurrIndex() == testData.size() - 1) {
                    swipeDeck.setCanSwipe(false);
                    btnNext.setText(R.string.finish);
                    getMainActivity().titleBar.hideButtons();
                }
            }

            @Override
            public void topCardTapped() {

            }
        });


    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showSkipText(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
            }
        });
    }



    @OnClick({R.id.swipe_deck, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.swipe_deck:
                //moveToLoginFragment();
                break;
            case R.id.btn_next:
                if (swipeDeck != null) {
                    if (swipeDeck.getCurrIndex() == testData.size() - 1) {

                        getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                    } else {
                        swipeDeck.discardTop(2);
                    }
                }
                break;
        }
    }

    private void moveToLoginFragment() {
        getDockActivity().popBackStackTillEntry(0);
        getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
    }
}
