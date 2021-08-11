package com.example.sakatap;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sakatap.ui.Profile.ProfileFragment;
import com.example.sakatap.ui.guide.GuideFragment;
import com.example.sakatap.ui.map.MapFragment;
import com.example.sakatap.ui.mulai.AwalFragment;
import com.example.sakatap.ui.overview.OverviewFragment;
import com.example.sakatap.ui.souvenir.SouvenirFragment;
import com.example.sakatap.ui.tempat.CandiMiniaturFragment;
import com.example.sakatap.ui.tempat.CandiPembakaranFragment;
import com.example.sakatap.ui.tempat.GapuraKeduaFragment;
import com.example.sakatap.ui.tempat.GapuraPertamaFragment;
import com.example.sakatap.ui.tempat.GapuraPertamaFragmentDirections;
import com.example.sakatap.ui.tempat.GuaFragment;
import com.example.sakatap.ui.tempat.KolamKeputrenFragment;
import com.example.sakatap.ui.tempat.PasebanFragment;
import com.example.sakatap.ui.tempat.PendapaFragment;
import com.example.sakatap.ui.tempat.SejarahPenghunianFragment;
import com.example.sakatap.ui.tempat.TaludFragment;
import com.example.sakatap.ui.tempat.UmpakFragment;
import com.example.sakatap.ui.welcome.WelcomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnDataPass, GapuraPertamaFragment.OnDataPass, GuideFragment.OnDataPass, GuideFragment.OnIDPass, GuideFragment.OnGuidePass
, GuideFragment.OnAudioPass, SejarahPenghunianFragment.OnDataPass, GapuraKeduaFragment.OnDataPass, TaludFragment.OnDataPass, CandiPembakaranFragment.OnDataPass, UmpakFragment.OnDataPass, PasebanFragment.OnDataPass
, PendapaFragment.OnDataPass, CandiMiniaturFragment.OnDataPass, KolamKeputrenFragment.OnDataPass, GuaFragment.OnDataPass {

    private BottomNavigationView bottomNav;
    private Toolbar mytoolbar;
    private NavigationView navigationView;
    String data;
    int nextid;;
    String guide;
    String audio;
    String tag;

    @Override
    public void onDataPass(String data) {
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = "overview";
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

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                label:
                switch (item.getItemId()) {
                    case R.id.bottom_tur:
                        switch (data) {
                            case "overview":
                                selectedFragment = new OverviewFragment();
                                tag = OverviewFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "gapura1":
                                selectedFragment = new GapuraPertamaFragment();
                                tag = GapuraPertamaFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "sejarah_penghunian":
                                selectedFragment = new SejarahPenghunianFragment();
                                tag = SejarahPenghunianFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "gapura2":
                                selectedFragment = new GapuraKeduaFragment();
                                tag = GapuraKeduaFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "talud":
                                selectedFragment = new TaludFragment();
                                tag = TaludFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "candi_pembakaran":
                                selectedFragment = new CandiPembakaranFragment();
                                tag = CandiPembakaranFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "umpak":
                                selectedFragment = new UmpakFragment();
                                tag = UmpakFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "paseban":
                                selectedFragment = new PasebanFragment();
                                tag = PasebanFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "pendapa":
                                selectedFragment = new PendapaFragment();
                                tag = PendapaFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "candi_miniatur":
                                selectedFragment = new CandiMiniaturFragment();
                                tag = CandiMiniaturFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "kolam_keputren":
                                selectedFragment = new KolamKeputrenFragment();
                                tag = KolamKeputrenFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "gua":
                                selectedFragment = new GuaFragment();
                                tag = GuaFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                            case "guide":
                                selectedFragment = new GuideFragment();
                                Bundle args = new Bundle();
                                args.putInt("id", nextid);
                                args.putString("guide", guide);
                                args.putString("audio", audio);
                                selectedFragment.setArguments(args);
                                tag = GuideFragment.class.getSimpleName();
                                replaceFragment(selectedFragment, tag);
                                return true;
                        }
                    case R.id.bottom_map :
                        selectedFragment = new MapFragment();
                        tag = MapFragment.class.getSimpleName();
                        replaceFragment(selectedFragment, tag);
                        return true;
                    case R.id.bottom_profile :
                        selectedFragment = new ProfileFragment();
                        tag = ProfileFragment.class.getSimpleName();
                        replaceFragment(selectedFragment, tag);
                        return true;
                    case R.id.bottom_souvenir :
                        selectedFragment = new SouvenirFragment();
                        tag = SouvenirFragment.class.getSimpleName();
                        replaceFragment(selectedFragment, tag);
                        return true;
                }

                replaceFragment(new AwalFragment(), AwalFragment.class.getSimpleName());
                return false;
            }
        });

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
                    case R.id.overviewFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.gapuraPertamaFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.sejarahPenghunianFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.gapuraKeduaFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.taludFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.candiPembakaranFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.umpakFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.pasebanFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.pendapaFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.candiMiniaturFragment :
                        bottomNav.setVisibility(View.VISIBLE);
                        mytoolbar.setVisibility(View.GONE);
                        break;
                    case R.id.guaFragment :
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

    @Override
    public void onIDPass(int id) {
        nextid = id;
    }

    @Override
    public void onGuidePass(String guide) {
        this.guide = guide;
    }

    @Override
    public void onAudioPass(String audio) {
        this.audio = audio;
    }

    private void replaceFragment (Fragment fr, String tag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Fragment curFrag = getSupportFragmentManager().getPrimaryNavigationFragment();
        if (curFrag != null) {
            fragmentTransaction.hide(curFrag);
        }

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = fr;
            fragmentTransaction.add(R.id.navHostFragment, fragment, tag);
        } else {
            fragmentTransaction.show(fragment).commit();
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }
}