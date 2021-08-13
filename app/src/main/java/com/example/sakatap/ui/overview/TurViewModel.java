package com.example.sakatap.ui.overview;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.Models.Bangunan;
import com.example.sakatap.Models.ImageURL;
import com.example.sakatap.repository.repository;

import java.util.List;

public class TurViewModel extends AndroidViewModel {

    private repository repository;
    private MutableLiveData<Uri> Imageurl;
    private MutableLiveData<Bangunan> narasi;
    private MutableLiveData<Uri> audiourl;
    private MutableLiveData<List<String>> listimageurl;

    public TurViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
        narasi = repository.getData();
        Imageurl = repository.getImageurl();
        listimageurl = repository.getListimageurl();
        audiourl = repository.getAudiourl();
    }

    public void getnarasi(String documentid) {
        repository.ReadNarasi(documentid);
    }

    public void getimageurl(String nama) {
        repository.getimageurl(nama);
    }

    public void getlistimageurl(String path) {
        repository.getlistimageurl(path);
    }

    public void getaudiourl(String path) {
        repository.getAudiourluri(path);
    }

    public MutableLiveData<Uri> getImageurl() {
        return Imageurl;
    }

    public MutableLiveData<Bangunan> getNarasi() {
        return narasi;
    }

    public MutableLiveData<List<String>> getListimageurl() {
        return listimageurl;
    }

    public MutableLiveData<Uri> getAudiourl() {
        return audiourl;
    }
}
