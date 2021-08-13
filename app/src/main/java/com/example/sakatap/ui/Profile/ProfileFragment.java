package com.example.sakatap.ui.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sakatap.Models.Users;
import com.example.sakatap.R;


public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;
    private ImageView foto;
    private TextView tombol_ubah;
    private TextView tombol_keluar;
    private TextView text_username;
    private TextView text_email;
    private TextView text_nama;
    private TextView text_kelamin;
    private TextView text_TTL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        foto = getView().findViewById(R.id.profil_foto);
        tombol_ubah = getView().findViewById(R.id.profil_ubahpass);
        tombol_keluar = getView().findViewById(R.id.profil_keluar);
        text_username = getView().findViewById(R.id.usernameprofile);
        text_email = getView().findViewById(R.id.emailprofil);
        text_nama = getView().findViewById(R.id.namaprofil);
        text_kelamin = getView().findViewById(R.id.kelaminprofil);
        text_TTL = getView().findViewById(R.id.TTLprofil);

        tombol_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.ubahPasswordFragment);
            }
        });

        tombol_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.logout();
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.awalFragment);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        viewModel.getuserdata();
        viewModel.getUserdata().observe(ProfileFragment.this, new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                text_username.setText(users.getUsername());
                text_email.setText(users.getEmail());
                text_nama.setText(users.getNama());
                text_kelamin.setText(users.getKelamin());
                text_TTL.setText(users.getTTL());
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}