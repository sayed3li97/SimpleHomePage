package com.example.simplehomepage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.simplehomepage.MainActivity;
import com.example.simplehomepage.Model.Items;
import com.example.simplehomepage.R;
import com.example.simplehomepage.Utils.DownloadImageFromInternet;

import java.util.ArrayList;
import java.util.Locale;

public class ListViewAdapter  extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private ArrayList<Items> arraylist;

    //Adapter constructor
    public ListViewAdapter(Context context ) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.arraylist = new ArrayList<Items>();
        this.arraylist.addAll(MainActivity.itemsArrayList);
    }

    public void updateArryaList (ArrayList array) {
        this.arraylist.clear();
        this.arraylist.addAll(array);
    }
    //View holder to be able to use row items
    public class ViewHolder {
        TextView name;
        ImageView image;
    }



    @Override
    public int getCount() {
        return MainActivity.itemsArrayList.size();
    }

    @Override
    public Items getItem(int position) {
        return MainActivity.itemsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;


                holder = new ViewHolder();
                view = inflater.inflate(R.layout.listview_item, null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.name);
                holder.image = (ImageView) view.findViewById(R.id.listImageView);

                view.setTag(holder);

                // Set the results into TextViews and Image view
                holder.name.setText(MainActivity.itemsArrayList.get(position).getLabel());

                if(!MainActivity.itemsArrayList.get(position).getImg().contains("http"))
                    holder.image.setImageResource(Integer.valueOf(MainActivity.itemsArrayList.get(position).getImg()));
                else {
                    // if the image is loaded from the internet use the
                    new DownloadImageFromInternet(holder.image)
                    .execute(MainActivity.itemsArrayList.get(position).getImg());
                }

//
//                if (MainActivity.itemsArrayList.get(position).getImg() != null)
//                    holder.image.setImageResource(Integer.valueOf(MainActivity.itemsArrayList.get(position).getImg()));

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        MainActivity.itemsArrayList.clear();
        if (charText.length() == 0) {
            MainActivity.itemsArrayList.addAll(arraylist);
        } else {
            for (Items wp : arraylist) {
                if (wp.getLabel().toLowerCase(Locale.getDefault()).contains(charText)) {
                    MainActivity.itemsArrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
