package com.example.simplehomepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.simplehomepage.Adapters.ListViewAdapter;
import com.example.simplehomepage.Adapters.ViewPagerAdapter;
import com.example.simplehomepage.Data.ListsData;
import com.example.simplehomepage.Model.Items;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener  {

    // Declare Variables
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


    private ListView list;
    private ListViewAdapter adapter;
    private SearchView editsearch;
    private String[] labelsList, labelsList2, labelsList3, labelsList4;
    private String[] imagesList, imagesList2, imagesList3, imagesList4;
    public static ArrayList<Items> itemsArrayList = new ArrayList<Items>();

    public static ArrayList<Items> itemsArrayList2 = new ArrayList<Items>();
    public static ArrayList<Items> itemsArrayList3 = new ArrayList<Items>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide the top bar
        getSupportActionBar().hide();

        //intilize visual components

        labelsList = ListsData.labelsList;

        imagesList = ListsData.imagesList;

        labelsList2 = ListsData.labelsList2;

        imagesList2 = ListsData.imagesList2;

        labelsList3 = ListsData.spacelabelsList;

        imagesList3 = ListsData.spaceimagesList;

        labelsList4 = ListsData.planetslabelsList;

        imagesList4 = ListsData.planetsimagesList;

        list = (ListView) findViewById(R.id.listview);


        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        itemsArrayList = new ArrayList<>();

        editsearch = (SearchView) findViewById(R.id.search);

        //Setup the list view with adapter and data
        setUpList();

        //Setup the slider with adapter and data
        setUpCarousel();





    }
    private void setUpList() {


        //Fill the list with initial data

        for (int i = 0; i < labelsList.length; i++) {
            Items items = new Items(labelsList[i], imagesList[i]);
            itemsArrayList.add(items);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this);

        // Binds the Adapter to the ListView
        list.setAdapter((ListAdapter) adapter);

        editsearch.setOnQueryTextListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this,
                        itemsArrayList.get(position).getLabel(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        setListViewHeightBasedOnChildren(list);

    }

    private void setUpCarousel() {

        //Assign adapters
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();

        dots = new ImageView[dotscount];

        // Setup the dots
        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);



        }
        //Assign the first dot to be the first
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));


                switch (position) {
                    case 0:
                        updateListItems(labelsList, imagesList);
                        break;
                    case 1:
                        updateListItems(labelsList3, imagesList3);
                        break;
                    case 2:
                        updateListItems(labelsList4, imagesList4);
                        break;
                    default:
                        updateListItems(labelsList, imagesList);
                }


            }


            //TODO: Update the items in the display list and in the Adapter
            private void updateListItems (String[] labelsList, String[] imagesList) {
                itemsArrayList.clear();
                for (int i = 0; i < labelsList.length; i++) {
                    Items items = new Items(labelsList[i], imagesList[i]);
                    itemsArrayList.add(items);
                }

                setListViewHeightBasedOnChildren(list);
                adapter.updateArryaList(itemsArrayList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    //TODO: This Method call the filter method when the search view text changes
    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    //TODO: Resize the nested list view dynamically
    public static void setListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1) );

        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}




