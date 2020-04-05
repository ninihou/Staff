package com.example.staff.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.staff.Pages;
import com.example.staff.R;
import com.example.staff.member.LoginActivity;

import static com.example.staff.main.Util.PAGES;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gvCategories = findViewById(R.id.gvPages);
        gvCategories.setAdapter(new MyGridViewAdapter(this));
        gvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class<? extends Activity> activityClass = PAGES[position].getFirstActivity();
                Intent intent = new Intent(MainActivity.this, activityClass);
                startActivity(intent);
            }
        });
    }

    private class MyGridViewAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        public MyGridViewAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return PAGES[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.gridview_main, parent, false);
            }
            Pages page = PAGES[position];
            ImageView ivCategory = convertView.findViewById(R.id.ivLogo);
            ivCategory.setImageResource(page.getImage());
            TextView tvTitle = convertView.findViewById(R.id.tvTitle);
            tvTitle.setText(page.getTitle());
            return convertView;
        }
    }

    public void onLogoutClick(View view){
        SharedPreferences pref = getSharedPreferences(Util.PREF_FILE,
                MODE_PRIVATE);
        pref.edit().putBoolean("login", false).apply();
//        view.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
