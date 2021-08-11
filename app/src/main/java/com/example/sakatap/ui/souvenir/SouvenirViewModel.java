package com.example.sakatap.ui.souvenir;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.Models.Souvenir;
import com.example.sakatap.Models.UserSouvenir;
import com.example.sakatap.repository.repository;

import java.util.List;

public class SouvenirViewModel extends AndroidViewModel {

    private com.example.sakatap.repository.repository repository;
    private MutableLiveData<Uri> imageurl;
    private MutableLiveData<Integer> datapoin;
    private MutableLiveData<Boolean> callback;
    private MutableLiveData<List<Souvenir>> souvenir;
    private MutableLiveData<UserSouvenir> usersouvenir;

    public SouvenirViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
        imageurl = repository.getImageurl();
        datapoin = repository.getDatapoin();
        souvenir = repository.getSouvenir();
        usersouvenir = repository.getUsersouvenir();
        callback = repository.getCallback();
    }

    public void getimageurl(String nama) {
        repository.getimageurl(nama);
    }

    public void getpoin() {
        repository.getpoin();
    }

    public void addpoin(int poin, int mode) {
        repository.addpoin(poin, mode);
    }

    public void ambil(String souvenirid, int harga) {
        repository.ambil(souvenirid, harga);
    }

    public void getsouvenir() {
        repository.getsouvenir();
    }

    public void getusersouvenir() {
        repository.getusersouvenir();
    }

    public void updatausersouvenir(String souvenirid) {
        repository.updateUserSouvenir(souvenirid);
    }

    public MutableLiveData<Uri> getImageurl() {
        return imageurl;
    }

    public MutableLiveData<Integer> getDatapoin() {
        return datapoin;
    }

    public MutableLiveData<List<Souvenir>> getSouvenir() {
        return souvenir;
    }

    public MutableLiveData<UserSouvenir> getUsersouvenir() {
        return usersouvenir;
    }

    public MutableLiveData<Boolean> getCallback() {
        return callback;
    }
}
