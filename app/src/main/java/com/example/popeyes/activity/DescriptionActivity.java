package com.example.popeyes.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popeyes.R;
import com.example.popeyes.adapter.MenuAdapter;
import com.example.popeyes.utils.FoodItem;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity {

    public static final String TAG = "DescriptionActivity";

    private int id;

    private RecyclerView mRecyclerView;
    private TextView titleTextView;

    private MenuAdapter mAdapter;
    private ArrayList<FoodItem> foodItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        if (getIntent() == null || getIntent().getExtras() == null) {
            Toast.makeText(this, "Item NOT FOUND!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViews();
        id = getIntent().getIntExtra("id", 0);
        titleTextView.setText(getIntent().getStringExtra("title"));
        setAdapter();
        getSingleItem();
    }

    private void findViews() {
        titleTextView = findViewById(R.id.titleTextView);
        mRecyclerView = findViewById(R.id.mRecyclerView);
    }

    private void setAdapter() {
        mAdapter = new MenuAdapter(foodItems, new MenuAdapter.OnClickListener() {
            @Override
            public void onOrderClick(@Nullable Integer id) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private JSONObject parseJSONObject() {
        JSONObject object;
        try {
            InputStream is = getAssets().open("menu.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            object = new JSONObject(json);
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
        return object;
    }

    private void getSingleItem() {
        JSONArray array = parseJSONObject().optJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.optJSONObject(i);
            if (object.optInt("id") == id) {
                JSONArray items = object.optJSONArray("items");
                Log.w(TAG, items.toString());
                for (int j = 0; j < items.length(); j++) {
                    foodItems.add(new FoodItem.Builder()
                            .setFromJSONObject(items.optJSONObject(j))
                            .build());
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
