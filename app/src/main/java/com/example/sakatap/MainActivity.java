package com.example.sakatap;

import android.os.Bundle;
import android.view.View;

import com.example.sakatap.ui.overview.TurFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private Toolbar mytoolbar;
    private NavigationView navigationView;
    String data;
    int nextid;;
    String guide;
    String audio;
    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        bottomNav = findViewById(R.id.bottom_navigation);
        mytoolbar = findViewById(R.id.mytoolbar);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.navHostFragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav, navHostFragment.getNavController());

        setSupportActionBar(mytoolbar);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                mytoolbar, navController, appBarConfiguration);


        navHostFragment.getNavController().addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.awalFragment :
                        bottomNav.setVisibility(View.GONE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.loginFragment :
                        bottomNav.setVisibility(View.GONE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.signupFragment :
                        bottomNav.setVisibility(View.GONE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.lupapasswordFragment :
                        bottomNav.setVisibility(View.GONE);
                        mytoolbar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nfcAwalFragment :
                        bottomNav.setVisibility(View.GONE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.nfcAkhirFragment :
                        bottomNav.setVisibility(View.GONE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.welcomeFragment :
                        bottomNav.setVisibility(View.GONE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.turFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.guideFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.mapFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.profileFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.souvenirFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.poinFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.showFragment :
                        bottomNav.setVisibility(View.GONE);
                        mytoolbar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.ubahPasswordFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.welcomeFragment);

    }

}