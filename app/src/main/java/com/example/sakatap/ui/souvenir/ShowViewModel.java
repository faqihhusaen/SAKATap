package com.example.sakatap.ui.souvenir;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.repository.repository;

public class ShowViewModel extends AndroidViewModel {

    private com.example.sakatap.repository.repository repository;
    private MutableLiveData<Uri> Imageurl;

    public ShowViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
        Imageurl = repository.getImageurl();
    }

    public void getimageurl(String nama) {
        repository.getimageurl(nama);
    }

    public MutableLiveData<Uri> getImageurl() {
        return Imageurl;
    }
}
