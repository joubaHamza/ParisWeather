package com.srihi.androidapplication.parisweather.view.adapters;

import android.content.Context;
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
import com.srihi.androidapplication.parisweather.models.Day;
import com.srihi.androidapplication.parisweather.network.ParamsWebService;

import java.util.ArrayList;

import static com.srihi.androidapplication.parisweather.utils.DateConverssion.getDateFormat;

/**
 * Created by Hamza Srihi on 06/10/2016.
 */
public class ListDetailsItemAdapter extends RecyclerView.Adapter<ListDetailsItemAdapter.ViewHolder> {


    private ItemDayObjectClickListener mObjectClickListener;
    private ArrayList<Day> dayList = new ArrayList<>();
    private Context context;
    int selectItem = 0;


    // Provide a suitable constructor (depends on the kind of dataset)
    public ListDetailsItemAdapter(Context context, ArrayList<Day> myDataset, ItemDayObjectClickListener mObjectClickListener) {
        dayList = myDataset;
        this.mObjectClickListener = mObjectClickListener;
        this.context = context;

    }


    // Create new views (invoked by the layout manager)
    @Override
    public ListDetailsItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list__detail_day_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void selectItem(int position) {
        selectItem = position;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position == 0)
            holder.previous.setVisibility(View.INVISIBLE);
       else if(position == dayList.size()-1)
            holder.next.setVisibility(View.INVISIBLE);
        else {
            holder.previous.setVisibility(View.VISIBLE);
            holder.next.setVisibility(View.VISIBLE);
        }

        if (dayList.get(position).getWeather().get(0).getIcon() != null) {
            String url = ParamsWebService.ICON_URL + dayList.get(position).getWeather().get(0).getIcon() + ".png";
            Log.e("url", url);
            Glide.with(context)
                    .load(url)
                    .into(holder.itemImage);
        }
        holder.dayTitle.setText(getDateFormat(dayList.get(position).getDt()));
        if (dayList.get(position).getWeather().get(0) != null && dayList.get(position).getWeather().get(0).getDescription() != null)
            holder.description.setText(dayList.get(position).getWeather().get(0).getDescription());
        holder.itemMinTemp.setText(String.format(context.getResources().getString(R.string.morn_temperature_detail), dayList.get(position).getTemp().getMin()));
        holder.itemMaxTemp.setText(String.format(context.getResources().getString(R.string.max_temperature_detail), dayList.get(position).getTemp().getMax()));
        holder.itemMornTemp.setText(String.format(context.getResources().getString(R.string.morn_temperature_detail), dayList.get(position).getTemp().getMorn()));
        holder.itemEvenTemp.setText(String.format(context.getResources().getString(R.string.even_temperature_detail), dayList.get(position).getTemp().getEve()));
        holder.itemDayTemp.setText(String.format(context.getResources().getString(R.string.day_temperature_detail), dayList.get(position).getTemp().getDay()));
        holder.itemNightTemp.setText(String.format(context.getResources().getString(R.string.night_temperature_detail), dayList.get(position).getTemp().getNight()));
        holder.speed.setText(String.format(context.getResources().getString(R.string.speed), dayList.get(position).getSpeed()));
        holder.humidity.setText(String.format(context.getResources().getString(R.string.humidity), dayList.get(position).getHumidity()));
        holder.pression.setText(String.format(context.getResources().getString(R.string.pressure), dayList.get(position).getPressure()));
        holder.clouds.setText(String.format(context.getResources().getString(R.string.clouds), dayList.get(position).getClouds()));

    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public static interface ItemDayObjectClickListener {

        public void onItemClickDay(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView dayTitle;
        public ImageView itemImage;
        public TextView itemMinTemp;
        public TextView itemMaxTemp;
        public TextView itemMornTemp;
        public TextView itemEvenTemp;
        public TextView itemDayTemp;
        public TextView itemNightTemp;
        public TextView speed;
        public TextView humidity;
        public TextView pression;
        public TextView clouds;
        public TextView description;
        public LinearLayout itemLayout;
        TextView previous;
        TextView next;

        public ViewHolder(View v) {
            super(v);

            itemImage = (ImageView) v.findViewById(R.id.img_item);
            dayTitle = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
            itemMinTemp = (TextView) v.findViewById(R.id.min_temp);
            itemDayTemp = (TextView) v.findViewById(R.id.day_temp);
            itemNightTemp = (TextView) v.findViewById(R.id.night_temp);
            itemMornTemp = (TextView) v.findViewById(R.id.morn_temp);
            itemEvenTemp = (TextView) v.findViewById(R.id.even_temp);
            itemMaxTemp = (TextView) v.findViewById(R.id.max_temp);
            speed = (TextView) v.findViewById(R.id.speed);
            humidity = (TextView) v.findViewById(R.id.humidity);
            pression = (TextView) v.findViewById(R.id.pressure);
            clouds = (TextView) v.findViewById(R.id.clouds);
            previous = (TextView) v.findViewById(R.id.previous);
            next = (TextView) v.findViewById(R.id.next);

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
