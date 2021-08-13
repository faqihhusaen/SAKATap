package com.example.sakatap.ui.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sakatap.R;


public class UbahPasswordFragment extends Fragment {

   private UbahPasswordViewModel viewModel;
   private EditText edit_oldpass;
   private EditText edit_newpass;
   private Button tombol_ubah;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ubah_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_oldpass = getView().findViewById(R.id.ubah_passlama);
        edit_newpass = getView().findViewById(R.id.ubah_passbaru);
        tombol_ubah = getView().findViewById(R.id.ubah_ubah);

        tombol_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ubahpassword();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(UbahPasswordViewModel.class);
    }

    public void ubahpassword() {
        String oldpass = edit_oldpass.getText().toString();
        String newpass = edit_newpass.getText().toString();

        viewModel.ubahpassword(oldpass, newpass);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}