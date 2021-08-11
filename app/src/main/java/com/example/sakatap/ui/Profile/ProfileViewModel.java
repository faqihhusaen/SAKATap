package com.example.sakatap.ui.Profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.Models.Bangunan;
import com.example.sakatap.Models.Users;
import com.example.sakatap.repository.repository;

public class ProfileViewModel extends AndroidViewModel {

    private com.example.sakatap.repository.repository repository;
    private MutableLiveData<Users> userdata;

    public ProfileViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
        userdata = repository.getDatauser();
    }

    public void getuserdata() {
        repository.getuserdata();
    }

    public void logout() {
        repository.logout();
    }

    public MutableLiveData<Users> getUserdata() {
        return userdata;
    }
}
