package com.example.sakatap.ui.Profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.Models.Users;
import com.example.sakatap.repository.repository;

public class UbahPasswordViewModel extends AndroidViewModel {

    private com.example.sakatap.repository.repository repository;

    public UbahPasswordViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
    }

    public void ubahpassword(String passlama, String passbaru) {
        repository.updatepassword(passlama, passbaru);
    }

}
