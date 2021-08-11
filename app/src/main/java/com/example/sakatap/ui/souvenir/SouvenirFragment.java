package com.example.sakatap.ui.souvenir;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakatap.Models.Souvenir;
import com.example.sakatap.Models.UserSouvenir;
import com.example.sakatap.R;
import com.example.sakatap.ui.map.MapViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SouvenirFragment extends Fragment {

    private SouvenirViewModel viewModel;
    private ImageView souvenir1;
    private ImageView souvenir2;
    private ImageView souvenir3;
    private TextView nama1;
    private TextView nama2;
    private TextView nama3;
    private TextView text_poin;
    private Button ambil1;
    private Button ambil2;
    private Button ambil3;

    private String nama_souvenir1;
    private String nama_souvenir2;
    private String nama_souvenir3;
    private int harga_souvenir1;
    private int harga_souvenir2;
    private int harga_souvenir3;
    private String image_souvenir1;
    private String image_souvenir2;
    private String image_souvenir3;
    private String animasi_souvenir1;
    private String animasi_souvenir2;
    private String animasi_souvenir3;
    private int poin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_souvenir, container, false);
        souvenir1 = view.findViewById(R.id.souvenir1);
        souvenir2 = view.findViewById(R.id.souvenir2);
        souvenir3 = view.findViewById(R.id.souvenir3);
        nama1 = view.findViewById(R.id.nama1);
        nama2 = view.findViewById(R.id.nama2);
        nama3 = view.findViewById(R.id.nama3);
        text_poin = view.findViewById(R.id.souvenir_poin);
        ambil1 = view.findViewById(R.id.souvenir_ambil1);
        ambil2 = view.findViewById(R.id.souvenir_ambil2);
        ambil3 = view.findViewById(R.id.souvenir_ambil3);

        ambil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.ambil("souvenir1", harga_souvenir1);
                viewModel.getCallback().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean == true) {
                            GetUserSouvenir();
                            getpoin();
                        }
                        else {
                            //Do Nothing
                        }
                    }
                });
            }
        });

        ambil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.ambil("souvenir2", harga_souvenir2);
                viewModel.getCallback().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean == true) {
                            GetUserSouvenir();
                            getpoin();
                        }
                        else {
                            //Do Nothing
                        }
                    }
                });
            }
        });

        ambil3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.ambil("souvenir3", harga_souvenir3);
                viewModel.getCallback().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean == true) {
                            GetUserSouvenir();
                            getpoin();
                        }
                        else {
                            //Do Nothing
                        }
                    }
                });
            }
        });

        souvenir1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Animasi", animasi_souvenir1);
                bundle.putString("judul", nama_souvenir1);
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.showFragment, bundle);
            }
        });

        souvenir2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Animasi", animasi_souvenir2);
                bundle.putString("judul", nama_souvenir2);
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.showFragment, bundle);
            }
        });

        souvenir3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Animasi", animasi_souvenir3);
                bundle.putString("judul", nama_souvenir3);
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.showFragment, bundle);
            }
        });

    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SouvenirViewModel.class);

        viewModel.getsouvenir();
        viewModel.getSouvenir().observe(SouvenirFragment.this, new Observer<List<Souvenir>>() {
            @Override
            public void onChanged(List<Souvenir> souvenirs) {
                nama_souvenir1 = souvenirs.get(0).getNama();
                nama_souvenir2 = souvenirs.get(1).getNama();
                nama_souvenir3 = souvenirs.get(2).getNama();
                harga_souvenir1 = souvenirs.get(0).getHarga();
                harga_souvenir2 = souvenirs.get(1).getHarga();
                harga_souvenir3 = souvenirs.get(2).getHarga();
                image_souvenir1 = souvenirs.get(0).getImage();
                image_souvenir2 = souvenirs.get(1).getImage();
                image_souvenir3 = souvenirs.get(2).getImage();
                animasi_souvenir1 = souvenirs.get(0).getAnimasi();
                animasi_souvenir2 = souvenirs.get(1).getAnimasi();
                animasi_souvenir3 = souvenirs.get(2).getAnimasi();

                nama1.setText(nama_souvenir1);
                nama2.setText(nama_souvenir2);
                nama3.setText(nama_souvenir3);

                ambil1.setText(String.valueOf(harga_souvenir1));
                ambil2.setText(String.valueOf(harga_souvenir2));
                ambil3.setText(String.valueOf(harga_souvenir3));

                Picasso.get().load(image_souvenir1).into(souvenir1);
                Picasso.get().load(image_souvenir2).into(souvenir2);
                Picasso.get().load(image_souvenir3).into(souvenir3);

            }
        });
        GetUserSouvenir();
        getpoin();
    }

    public void getpoin() {
        viewModel.getpoin();
        viewModel.getDatapoin().observe(SouvenirFragment.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                poin = integer;
                text_poin.setText(String.valueOf(poin));
            }
        });
    }

    public void GetUserSouvenir() {
        viewModel.getusersouvenir();
        viewModel.getUsersouvenir().observe(SouvenirFragment.this, new Observer<UserSouvenir>() {
            @Override
            public void onChanged(UserSouvenir userSouvenir) {
                if(userSouvenir.getSouvenir1() == false) {
                    ambil1.setClickable(true);
                    souvenir1.setClickable(false);
                }
                else if(userSouvenir.getSouvenir1() == true) {
                    ambil1.setClickable(false);
                    ambil1.setText("Diklaim");
                    souvenir1.setClickable(true);
                }
                if(userSouvenir.getSouvenir2() == false) {
                    ambil2.setClickable(true);
                    souvenir2.setClickable(false);
                }
                else if(userSouvenir.getSouvenir2() == true) {
                    ambil2.setClickable(false);
                    ambil2.setText("Diklaim");
                    souvenir2.setClickable(true);
                }
                if(userSouvenir.getSouvenir3() == false) {
                    ambil3.setClickable(true);
                    souvenir3.setClickable(false);
                }
                else if(userSouvenir.getSouvenir3() == true) {
                    ambil3.setClickable(false);
                    ambil3.setText("Diklaim");
                    souvenir3.setClickable(true);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}