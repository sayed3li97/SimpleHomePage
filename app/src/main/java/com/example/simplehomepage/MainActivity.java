package com.example.simplehomepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.simplehomepage.Adapters.ListViewAdapter;
import com.example.simplehomepage.Adapters.ViewPagerAdapter;
import com.example.simplehomepage.Model.Items;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener  {
    //    ViewPager viewPager;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    // Declare Variables
    private ListView list;
    private ListViewAdapter adapter;
    private SearchView editsearch;
    private String[] labelsList;
    private String[] imagesList;
    public static ArrayList<Items> itemsArrayList = new ArrayList<Items>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();

        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

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

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // Generate sample data

        labelsList = new String[]{
                "Sayed",
                "Ali",
                "Mohamaed",
                "Yousif",
                "Najma", "" +
                "Abc",
                "Elon Musk",
                "Steve",
                "Jobs",
                "Manama",
                "Bahrain"};
        imagesList = new String[]{
                String.valueOf(R.drawable.avatar1),
                String.valueOf(R.drawable.avatar2),
                String.valueOf(R.drawable.avatar3),
                String.valueOf(R.drawable.avatar4),
                String.valueOf(R.drawable.avatar5),
                String.valueOf(R.drawable.avatar6),
                String.valueOf(R.drawable.avatar7),
                String.valueOf(R.drawable.avatar2),
                String.valueOf(R.drawable.avatar8),
                String.valueOf(R.drawable.avatar9),
                String.valueOf(R.drawable.avatar15),
        };

        list = (ListView) findViewById(R.id.listview);

        itemsArrayList = new ArrayList<>();

        for (int i = 0; i < labelsList.length; i++) {
            Items items = new Items(labelsList[i], imagesList[i]);
            itemsArrayList.add(items);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this);

        // Binds the Adapter to the ListView
        list.setAdapter((ListAdapter) adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, itemsArrayList.get(position).getLabel(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}


