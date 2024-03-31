package com.example.smd_assignment_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    Button btnAdd;
    RecyclerView rvRestraunts;
    RecyclerView.LayoutManager manager;
    public static RestrauntListAdapter rvAdapter;
    public static ArrayList<Restraunt> restrauntArrayList;
    private static final int Req_Code = 1;
    SharedPreferences spref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_Restraunt.class);
                startActivityForResult(intent, Req_Code);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });


    }

    private void init(){
        searchView = findViewById(R.id.SearchView);
        btnAdd = findViewById(R.id.btnAdd);
        rvRestraunts = findViewById(R.id.rvRestrauntsList);

        rvRestraunts.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        rvRestraunts.setLayoutManager(manager);

        spref = getSharedPreferences("spref", MODE_PRIVATE);
        editor = spref.edit();
        populateSharedPreference();

        restrauntArrayList = getRestaurantListFromSharedPreferences();

        rvAdapter = new RestrauntListAdapter(this, restrauntArrayList);
        rvRestraunts.setAdapter(rvAdapter);
    }

    private void populateSharedPreference(){

        String temp = spref.getString("restraunt_list", null);
        if (temp != null)
            return;

        SharedPreferences.Editor editor = spref.edit();
        String[] restaurantData = {
                "Burger King,Islamabad,123,Best,4,6",
                "Dagwood,Lahore,234,Noice,4.1",
                "Cheezious,PIA Road,345,Great,3.4",
                "KFC,Mall Road,456,Bad,1.2",
                "Subway,Islamabad,567,Not Good,2.6",
                "Broadway,Defence Road,678,Just ok,3.1\n",

        };

        String restaurantsString = String.join("\n", restaurantData);

        editor.putString("restraunt_list", restaurantsString);

        editor.apply();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Req_Code)
        {
            if(resultCode == RESULT_OK){
                rvAdapter.notifyDataSetChanged();

            }
        }
    }

    private ArrayList<Restraunt> getRestaurantListFromSharedPreferences() {

        ArrayList<Restraunt> restaurantList = new ArrayList<>();

        String restaurantsString = spref.getString("restraunt_list", null);

        if (restaurantsString != null) {
            String[] allRes = restaurantsString.split("\n");

            for (String res : allRes) {
                String[] split = res.split(",");
                if (split.length == 5) {
                    restaurantList.add(new Restraunt(split[0], split[1],
                            split[2], split[3], Double.parseDouble(split[4])));
                }
            }
        }
        return restaurantList;
    }

    private void filter(String text) {

        ArrayList<Restraunt> searchFiltered = new ArrayList<>();

        for (Restraunt restaurant : restrauntArrayList) {

            if (restaurant.getName().toLowerCase().contains(text.toLowerCase())) {
                searchFiltered.add(restaurant);
            }
        }

        rvAdapter.filterList(searchFiltered);
    }
}