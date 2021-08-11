package com.example.sakatap.ui.signup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.repository.repository;

public class SignupViewModel extends AndroidViewModel {

    private repository repository;
    private MutableLiveData<Boolean> callback;
    private MutableLiveData<Boolean> check;

    public SignupViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
        callback = repository.getCallback();
        check = repository.getCheck();
    }

    public void signup(String email, String password, String username, String nama, String kelamin, String TTL) {
        repository.signup(email, password, username, nama, kelamin, TTL);
    }

    public MutableLiveData<Boolean> getCallback() {
        return callback;
    }

    public MutableLiveData<Boolean> getCheck() {
        return check;
    }
}
