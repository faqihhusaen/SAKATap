package com.example.sakatap.ui.poin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sakatap.repository.repository;

public class PoinViewModel extends AndroidViewModel {

    private com.example.sakatap.repository.repository repository;

    public PoinViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
    }

    public void addpoin(int poin, int mode) {
        repository.addpoin(poin, mode);
    }
}
