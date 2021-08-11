package com.example.sakatap.ui.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.example.sakatap.ui.login.LoginViewModel;


public class SignupFragment extends Fragment {

    private SignupViewModel viewModel;
    private EditText edit_nama;
    private EditText edit_username;
    private EditText edit_email;
    private EditText edit_password;
    private EditText edit_jenis;
    private EditText edit_ttl;
    private Button tombol_buat_akun;
    private TextView tombol_masuk;
    private Boolean check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_nama = getView().findViewById(R.id.signup_nama);
        edit_username = getView().findViewById(R.id.signup_username);
        edit_email = getView().findViewById(R.id.signup_email);
        edit_password = getView().findViewById(R.id.signup_password);
        edit_jenis = getView().findViewById(R.id.signup_jenis_kelamin);
        edit_ttl = getView().findViewById(R.id.signup_tempat_tanggal_lahir);
        tombol_buat_akun = getView().findViewById(R.id.signup_signup);
        tombol_masuk = getView().findViewById(R.id.signup_login);

        tombol_buat_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = edit_nama.getText().toString();
                String username = edit_username.getText().toString();
                String email = edit_email.getText().toString();
                String password = edit_password.getText().toString();
                String jenis = edit_jenis.getText().toString();
                String ttl = edit_ttl.getText().toString();
                signup(email, password, username, nama, jenis, ttl);
            }
        });

        tombol_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_signupFragment_to_loginFragment);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SignupViewModel.class);
    }

    private void signup(String email, String password, String username, String nama,  String jenis, String ttl) {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(nama) && !TextUtils.isEmpty(jenis) && !TextUtils.isEmpty(ttl)) {
            viewModel.signup(email, password, username, nama, jenis, ttl);
            viewModel.getCallback().observe(SignupFragment.this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean == true) {
                        Toast.makeText(getContext(), "Akun telah berhasil dibuat", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_signupFragment_to_welcomeFragment);
                    }
                    else {
                        Toast.makeText(getContext(), "Terjadi kesalahan saat membuat akun", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(getContext(), "Form Isian Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }

    }

}