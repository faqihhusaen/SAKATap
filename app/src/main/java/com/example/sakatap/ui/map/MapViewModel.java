package com.example.sakatap.ui.map;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.repository.repository;

public class MapViewModel extends AndroidViewModel {

    private com.example.sakatap.repository.repository repository;
    private MutableLiveData<Uri> imageurl;

    public MapViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
        imageurl = repository.getImageurl();
    }

    public void getimageurl(String nama) {
        repository.getimageurl(nama);
    }

    public MutableLiveData<Uri> getImageurl() {
        return imageurl;
    }
}
