package com.example.sakatap.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakatap.R;

public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;
    private Button tombol_masuk;
    private TextView tombol_lupa_password;
    private TextView tombol_daftar;
    private EditText edit_email;
    private EditText edit_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        tombol_masuk = view.findViewById(R.id.login_masuk);
        tombol_daftar = view.findViewById(R.id.login_daftar);
        tombol_lupa_password = view.findViewById(R.id.login_lupa_password);
        edit_email = view.findViewById(R.id.login_email);
        edit_password = view.findViewById(R.id.login_password);

        tombol_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        tombol_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });

        tombol_lupa_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_loginFragment_to_lupapasswordFragment);
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
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

    }

    public void login() {
        String email = edit_email.getText().toString();
        String password = edit_password.getText().toString();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            viewModel.login(email, password);
            viewModel.getcallback().observe(LoginFragment.this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean == true) {
                        Navigation.findNavController(getActivity(), R.id.navHostFragment).popBackStack(R.id.loginFragment, true);
                        Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.welcomeFragment);
                        Toast.makeText(getContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Password atau email yang dimasukkan salah", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(getContext(), "Email dan Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}