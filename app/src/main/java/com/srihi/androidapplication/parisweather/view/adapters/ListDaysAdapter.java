package com.srihi.androidapplication.parisweather.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.srihi.androidapplication.parisweather.R;
import com.srihi.androidapplication.parisweather.activities.MainActivity;
import com.srihi.androidapplication.parisweather.models.Day;
import com.srihi.androidapplication.parisweather.network.ParamsWebService;

import java.util.ArrayList;

import static com.srihi.androidapplication.parisweather.utils.DateConverssion.getDateFormat;

/**
 * Created by Hamza Srihi on 06/10/2016.
 */
public class ListDaysAdapter extends RecyclerView.Adapter<ListDaysAdapter.ViewHolder> {


    private ItemDayObjectClickListener mObjectClickListener;
    private ArrayList<Day> dayList = new ArrayList<>();
    private Context context;


    // Provide a suitable constructor (depends on the kind of dataset)
    public ListDaysAdapter(Context context, ArrayList<Day> myDataset, ItemDayObjectClickListener mObjectClickListener) {
        dayList = myDataset;
        this.mObjectClickListener = mObjectClickListener;
        this.context = context;

    }


    // Create new views (invoked by the layout manager)
    @Override
    public ListDaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_day_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemDay.setText(getDateFormat(dayList.get(position).getDt()));
        if (dayList.get(position).getWeather().get(0).getIcon() != null) {
            String url = ParamsWebService.ICON_URL + dayList.get(position).getWeather().get(0).getIcon() + ".png";
            Log.e("url", url);
            Glide.with(context)
                    .load(url)
                    .into(holder.itemImage);
        }

        holder.itemMinTemp.setText(String.format(context.getResources().getString(R.string.min_temperature), dayList.get(position).getTemp().getMin()));
        holder.itemMaxTemp.setText(String.format(context.getResources().getString(R.string.max_temperature), dayList.get(position).getTemp().getMax()));
        holder.speed.setText(String.format(context.getResources().getString(R.string.speed), dayList.get(position).getSpeed()));
        holder.humidity.setText(String.format(context.getResources().getString(R.string.humidity), dayList.get(position).getHumidity()));

    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public static interface ItemDayObjectClickListener {

        public void onItemClickDay(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView itemImage;
        public TextView itemDay;
        public TextView itemMinTemp;
        public TextView itemMaxTemp;
        public TextView speed;
        public TextView humidity;
        public LinearLayout itemLayout;


        public ViewHolder(View v) {
            super(v);

            itemImage = (ImageView) v.findViewById(R.id.img_item);

            itemDay = (TextView) v.findViewById(R.id.day_name);
            itemMinTemp = (TextView) v.findViewById(R.id.min_temp);
            itemLayout = (LinearLayout) v.findViewById(R.id.itemLayout);
            itemLayout.setOnClickListener(this);
            itemMaxTemp = (TextView) v.findViewById(R.id.max_temp);
            speed = (TextView) v.findViewById(R.id.speed);
            humidity = (TextView) v.findViewById(R.id.humidity);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId()) {

                case R.id.itemLayout:
                    if (mObjectClickListener != null)
                        mObjectClickListener.onItemClickDay(position);
                    break;

            }

        }
    }


}
