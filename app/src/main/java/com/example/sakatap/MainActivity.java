package com.example.sakatap;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.sakatap.Models.NfcIntent;
import com.example.sakatap.ui.nfc.NfcAwalFragment;
import com.example.sakatap.ui.overview.TurFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private Toolbar mytoolbar;
    private NavigationView navigationView;
    private ShareViewModel shareViewModel;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareViewModel = new ViewModelProvider(this).get(ShareViewModel.class);
        bottomNav = findViewById(R.id.bottom_navigation);
        mytoolbar = findViewById(R.id.mytoolbar);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null) {
            Toast.makeText(this, "NFC tidak tersedia", Toast.LENGTH_SHORT).show();
        }
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
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
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.nfcAkhirFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.welcomeFragment :
                        bottomNav.setVisibility(View.VISIBLE);
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
                        bottomNav.setVisibility(View.GONE);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        NfcIntent nfcIntent = new NfcIntent(intent, tag);
        shareViewModel.Setintent(nfcIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}