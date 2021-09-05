package com.example.sakatap;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sakatap.Models.NfcIntent;

public class ShareViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> position = new MutableLiveData<>(0);
    private MutableLiveData<String> condition = new MutableLiveData<>("welcome");
    private MutableLiveData<NfcIntent> myintent = new MutableLiveData<>();

    public ShareViewModel(@NonNull Application application) {
        super(application);
    }

    public void send(int update) {
        position.postValue(update);
    }

    public void increment() {
        Integer value = position.getValue();
        if (value != null) {
            position.postValue(value + 1);
        } else {
            //
        }
    }

    public void decrement() {
        Integer value = position.getValue();
        if (value != null) {
            if(value > 0) {
                position.postValue(value - 1);
            }
        } else {
            //
        }
    }

    public void reset() {
        position.postValue(0);
    }

    public void setCondition(String update) {
        condition.postValue(update);
    }

    public void Setintent(NfcIntent intent) {
        myintent.postValue(intent);
    }

    public MutableLiveData<String> getCondition() {
        return condition;
    }

    public MutableLiveData<Integer> getPosition() {
        return position;
    }

    public MutableLiveData<NfcIntent> getMyintent() {
        return myintent;
    }
}
