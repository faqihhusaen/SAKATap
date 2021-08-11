package com.example.sakatap.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.Models.Users;
import com.example.sakatap.repository.repository;

public class lupapasswordViewModel extends AndroidViewModel {

    private com.example.sakatap.repository.repository repository;
    private MutableLiveData<Boolean> callback;

    public lupapasswordViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
        callback = repository.getCallback();
    }

    public void lupapassword(String email) {
        repository.lupapassword(email);
    }

    public MutableLiveData<Boolean> getCallback() {
        return callback;
    }
}
