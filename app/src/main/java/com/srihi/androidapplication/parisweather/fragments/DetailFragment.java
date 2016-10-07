package com.srihi.androidapplication.parisweather.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.srihi.androidapplication.parisweather.R;
import com.srihi.androidapplication.parisweather.activities.MainActivity;
import com.srihi.androidapplication.parisweather.models.JsonResponse;
import com.srihi.androidapplication.parisweather.network.ParamsWebService;
import com.srihi.androidapplication.parisweather.network.interfaces.MySpiceManager;
import com.srihi.androidapplication.parisweather.network.requests.DetailRequest;
import com.srihi.androidapplication.parisweather.utils.NetworkUtils;
import com.srihi.androidapplication.parisweather.view.adapters.ListDetailsItemAdapter;

import java.util.Calendar;
import java.util.Date;

import static com.srihi.androidapplication.parisweather.utils.DateConverssion.getDateString;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    View rootView;
    long cityId;

    LinearLayoutManager mLayoutManager;
    RecyclerView mDetailRecyclerView;
    LinearLayoutManager mLayoutManagerDetail;

    ListDetailsItemAdapter mDetailAdapter;
    int position;
    TextView cityName;
    TextView date;
    ImageView todayImage;
    ImageView btnBack;
    RequestListener mDetailRequestListener = new RequestListener<JsonResponse>() {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            //Hide refresh loader
            Log.e("request", "failure");
            if (!isFragmentRunning())
                return;
            // DialogUtils.getAlertErrorDialog(baseActivity, getString(R.string.error_ws_default)).show();

        }

        @Override
        public void onRequestSuccess(JsonResponse mDetailResult) {
            //Hide refresh loader

            Log.e("request", "success");
            if (!isFragmentRunning())
                return;
            if (mDetailResult != null) {
                Log.e("request", "home result not null");

                cityName.setText(mDetailResult.getCity().getName());
                // get today date
                Calendar c = Calendar.getInstance();
                Date d = c.getTime();
                // convert date to string
                date.setText(getDateString(d));

                if (mDetailResult.getList() != null && mDetailResult.getList().size() > 0) {
                    if (mDetailResult.getList().get(0).getWeather().get(0).getIcon() != null) {
                        String url = ParamsWebService.ICON_URL + mDetailResult.getList().get(0).getWeather().get(0).getIcon() + ".png";
                        Log.e("url", url);
                        Glide.with(getContext())
                                .load(url)
                                .into(todayImage);
                    }
                    // Details Adapter
                    mDetailAdapter = new ListDetailsItemAdapter(getContext(), mDetailResult.getList(), new ListDetailsItemAdapter.ItemDayObjectClickListener() {
                        @Override
                        public void onItemClickDay(int position) {

                        }
                    });
                    // second linear layout manager for detail recyclerView
                    mLayoutManagerDetail = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    mDetailRecyclerView.setLayoutManager(mLayoutManagerDetail);

                    mDetailRecyclerView.setAdapter(mDetailAdapter);
                    mDetailRecyclerView.scrollToPosition(position);

                }
            } else {

                showDialog();
            }
        }
    };

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(long cityId, int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putLong("cityId", cityId);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;

    }

    public void initArgList() {
        Bundle args = getArguments();
        if (args != null) {
            cityId = args.getLong("cityId");
            position = args.getInt("position");
        }
        getArguments().clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        initArgList();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        performRequest();
    }


    // Ws request
    private void performRequest() {
        Log.e("request", "performing");
        if (NetworkUtils.isInternetExist(getContext())) {
            DetailRequest mHomeRequest = new DetailRequest(getContext(), cityId);
            MySpiceManager.getmSpiceManager().execute(mHomeRequest, mHomeRequest.generateCacheKey(), DurationInMillis.ALWAYS_EXPIRED, mDetailRequestListener);
        } else {
            DetailRequest mHomeRequest = new DetailRequest(getContext(), cityId);
            MySpiceManager.getmSpiceManager().execute(mHomeRequest, mHomeRequest.generateCacheKey(), DurationInMillis.ALWAYS_RETURNED, mDetailRequestListener);
        }
    }

    // initiate View
    private void initView() {
        mDetailRecyclerView = (RecyclerView) rootView.findViewById(R.id.day_list_details);
        mDetailRecyclerView.setHasFixedSize(false);
        mDetailRecyclerView.setHasFixedSize(false);
        cityName = (TextView) rootView.findViewById(R.id.city);
        date = (TextView) rootView.findViewById(R.id.today_date);
        todayImage = (ImageView) rootView.findViewById(R.id.today_img);
        btnBack = (ImageView) rootView.findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back
                ((MainActivity) getActivity()).onBackClick();
            }
        });

    }


    // test if the fragment is running
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
