package com.example.sakatap.Models;

import android.content.Intent;
import android.nfc.Tag;

public class NfcIntent {
    private Intent intent;
    private Tag tag;

    public NfcIntent() {

    }

    public NfcIntent(Intent intent, Tag tag) {
        this.intent = intent;
        this.tag = tag;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
