package com.example.popeyes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popeyes.R;
import com.example.popeyes.adapter.CategoryAdapter;
import com.example.popeyes.auth.LoginActivity;
import com.example.popeyes.auth.RegisterActivity;
import com.example.popeyes.preference.UserPreference;
import com.example.popeyes.utils.FoodItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ImageView optionMenu;
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private ArrayList<FoodItem> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserPreference.getInstance(getApplicationContext());
        findViews();
        setAdapter();
        addItem();
    }

    private void findViews() {
        optionMenu = findViewById(R.id.optionMenu);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        optionMenu.setOnClickListener(this);
    }

    private void setAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new CategoryAdapter(mList, new CategoryAdapter.OnClicked() {
            @Override
            public void onClickListener(int id, String title) {
                launchDesc(id, title);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private JSONObject parseJSONObject() {
        JSONObject object;
        try {
            InputStream is = getAssets().open("category.json");
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

    private void addItem() {
        JSONArray array = parseJSONObject().optJSONArray("data");
        if (array != null) {
            Log.w(TAG, array.toString());
            for (int i = 0; i < array.length(); i++) {
                mList.add(new FoodItem
                        .Builder()
                        .setFromJSONObject(array.optJSONObject(i))
                        .build()
                );
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void launchDesc(int id, String title) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    private void optionMenu() {
        PopupMenu popupMenu = new PopupMenu(this, optionMenu);
        popupMenu.getMenuInflater().inflate(R.menu.option_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.loginMenu:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        return true;
                    case R.id.registerMenu:
                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                        return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.optionMenu) {
            optionMenu();
        }
    }
}



