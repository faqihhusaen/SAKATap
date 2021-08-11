package com.example.sakatap.ui.guide;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.Models.Bangunan;
import com.example.sakatap.repository.repository;

public class GuideViewModel extends AndroidViewModel {

    private com.example.sakatap.repository.repository repository;
    private MutableLiveData<Bangunan> guide;
    private MutableLiveData<Uri> audiourl;

    public GuideViewModel(@NonNull Application application) {
        super(application);

        repository = new repository(application);
        guide = repository.getData();
        audiourl = repository.getAudiourl();
    }

    public void getguide(String documentid) {
        repository.ReadNarasi(documentid);
    }

    public void getaudiourl(String path) {
        repository.getAudiourluri(path);
    }

    public MutableLiveData<Bangunan> getGuide() {
        return guide;
    }

    public MutableLiveData<Uri> getAudiourl() {
        return audiourl;
    }
}
