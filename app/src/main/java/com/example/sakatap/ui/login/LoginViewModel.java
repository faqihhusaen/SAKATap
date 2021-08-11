package com.example.sakatap.ui.login;

import android.app.Application;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.repository.repository;

public class LoginViewModel extends AndroidViewModel {

    private repository repository;
    private MutableLiveData<Boolean> callback;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
        callback = repository.getCallback();
    }

    public void login(String email, String password) {
        repository.login(email, password);
    }

    public MutableLiveData<Boolean> getcallback() {
        return callback;
    }
}

