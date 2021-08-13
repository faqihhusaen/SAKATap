package com.example.sakatap;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ShareViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> position = new MutableLiveData<>();

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

    public MutableLiveData<Integer> getPosition() {
        return position;
    }
}
