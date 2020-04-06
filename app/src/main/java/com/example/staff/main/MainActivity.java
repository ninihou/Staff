package com.example.staff.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import java.util.HashSet;
import java.util.Set;

import static com.example.staff.main.Util.PAGES;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOGIN = 1;
//    private static final int MY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onLogin();
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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        askPermissions();
//    }
//
//    private void askPermissions() {
//        String[] permissions = {
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION
//        };
//
//        Set<String> permissionsRequest = new HashSet<>();
//        for (String permission : permissions) {
//            int result = ContextCompat.checkSelfPermission(this, permission);
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                permissionsRequest.add(permission);
//            }
//        }
//
//        if (!permissionsRequest.isEmpty()) {
//            ActivityCompat.requestPermissions(this,
//                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
//                    MY_REQUEST_CODE);
//        }
//    }

    private void onLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(loginIntent, REQUEST_LOGIN);
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
