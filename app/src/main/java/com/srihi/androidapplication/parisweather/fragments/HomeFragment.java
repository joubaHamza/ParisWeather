package com.srihi.androidapplication.parisweather.fragments;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.srihi.androidapplication.parisweather.R;
import com.srihi.androidapplication.parisweather.activities.MainActivity;
import com.srihi.androidapplication.parisweather.models.JsonResponse;
import com.srihi.androidapplication.parisweather.network.interfaces.MySpiceManager;
import com.srihi.androidapplication.parisweather.network.requests.HomeRequest;
import com.srihi.androidapplication.parisweather.utils.NetworkUtils;
import com.srihi.androidapplication.parisweather.utils.SharedPreference;
import com.srihi.androidapplication.parisweather.view.adapters.ListDaysAdapter;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    View rootView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ListDaysAdapter mAdapter;
    long cityId;
    TextView advice;

    ListDaysAdapter.ItemDayObjectClickListener mItemDayObjectClickListener = new ListDaysAdapter.ItemDayObjectClickListener() {
        @Override
        public void onItemClickDay(int position) {

            if ((MainActivity) getActivity() != null)
                ((MainActivity) getActivity()).replaceFragment(DetailFragment.newInstance(cityId, position));

        }
    };

    // Retrofi Request
    RequestListener mHomeRequestListener = new RequestListener<JsonResponse>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            //Hide refresh loader
            mSwipeRefreshLayout.setRefreshing(false);
            if (!isFragmentRunning())
                return;
            showDialog();
            advice.setVisibility(View.VISIBLE);
        }

        @Override
        public void onRequestSuccess(JsonResponse mHomeResult) {
            //Hide advice text
            if (advice.getVisibility() == View.VISIBLE)
                advice.setVisibility(View.GONE);
            //Hide refresh loader
            mSwipeRefreshLayout.setRefreshing(false);
            if (!isFragmentRunning())
                return;
            if (mHomeResult != null) {
                cityId = mHomeResult.getCity().getId();
                SharedPreference.getInstance(getContext()).setCITYID(Long.toString(cityId));
                if (mHomeResult.getList() != null && mHomeResult.getList().size() > 0) {
                    mAdapter = new ListDaysAdapter(getContext(), mHomeResult.getList(), mItemDayObjectClickListener);
                    // use a linear layout manager
                    mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }
            } else {
                showDialog();

            }
        }
    };

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        performRequest();
        return rootView;

    }

    // initiate view
    private void initView() {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_day);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.weather_swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(false);
        advice = (TextView) rootView.findViewById(R.id.advice);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    // get days weather
    private void performRequest() {
        mSwipeRefreshLayout.setRefreshing(true);
        if (NetworkUtils.isInternetExist(getContext())) {
            HomeRequest mHomeRequest = new HomeRequest(getContext(), "Paris", "metric", 5);
            MySpiceManager.getmSpiceManager().execute(mHomeRequest, mHomeRequest.generateCacheKey(), DurationInMillis.ALWAYS_EXPIRED, mHomeRequestListener);
        } else {
            HomeRequest mHomeRequest = new HomeRequest(getContext(), "Paris", "metric", 5);
            MySpiceManager.getmSpiceManager().execute(mHomeRequest, mHomeRequest.generateCacheKey(), DurationInMillis.ALWAYS_RETURNED, mHomeRequestListener);
        }

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        performRequest();
    }

    public boolean isFragmentRunning() {
        return isAdded() && !isDetached() && !isHidden() && !isRemoving() && !getActivity().isFinishing();
    }

    public void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                getContext());
        builder.setMessage(getResources().getString(R.string.erreur_Ws))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}
