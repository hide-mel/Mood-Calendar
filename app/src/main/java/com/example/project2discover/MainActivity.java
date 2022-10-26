package com.example.project2discover;

import android.os.Bundle;

import com.example.project2discover.ui.discover.DiscoverFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.navigation.NavigationBarView
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

//    private ActivityMainBinding binding;
    private NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.navigation_: {
//                    Fragment linear_layout = LayoutDemoFragment.newInstance(LayoutDemoFragment.LINEAR_DEMO);
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.layout_fragment, linear_layout)
//                            .addToBackStack(null)
//                            .commit();
//                    return true;
//                }
                // discover fragment
                case R.id.navigation_discover: {
                    Fragment recycler_layout = DiscoverFragment.newInstance(DiscoverFragment.RECYCLER_VIEW);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layout_fragment, recycler_layout)
                            .addToBackStack(null)
                            .commit();
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        // default fragment ?
        Fragment recycler_layout = DiscoverFragment.newInstance(DiscoverFragment.RECYCLER_VIEW);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, recycler_layout)
                .addToBackStack(null)
                .commit();

        ButterKnife.bind(this);
    }


}