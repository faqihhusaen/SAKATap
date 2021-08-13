package com.example.sakatap.ui.login;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakatap.R;


public class lupapasswordFragment extends Fragment {

   private lupapasswordViewModel viewModel;
   private EditText edit_email;
   private Button tombol_kirim;
   private TextView tombol_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lupa_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_email = getView().findViewById(R.id.lupa_email);
        tombol_kirim = getView().findViewById(R.id.lupa_kirim);
        tombol_login = getView().findViewById(R.id.lupa_login);

        tombol_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lupapassword();
            }
        });

        tombol_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_lupapasswordFragment_to_loginFragment);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(lupapasswordViewModel.class);
    }

    public void lupapassword() {
        String email = edit_email.getText().toString();
        viewModel.lupapassword(email);
        viewModel.getCallback().observe(lupapasswordFragment.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == true) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Link Reset Password");
                    alert.setMessage("Link Reset Password Sudah Terkirim, Silahkan Cek Email anda \n Sudah selesai? Silahkan login kembali");
                    alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_lupapasswordFragment_to_loginFragment);
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Pengiriman link reset password gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}