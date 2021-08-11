package com.example.sakatap.ui.welcome;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.repository.repository;

public class WelcomeViewModel extends AndroidViewModel {

    private repository repository;
    private MutableLiveData<Boolean> callback;

    public WelcomeViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
        callback = repository.getCallback();
    }

    public void checkverified() {
        repository.checkemailverified();
    }

    public void logout() {
        repository.logout();
    }

    public MutableLiveData<Boolean> getCallback() {
        return callback;
    }

}
