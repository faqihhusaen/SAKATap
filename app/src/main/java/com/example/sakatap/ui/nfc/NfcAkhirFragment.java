package com.example.sakatap.ui.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.Ndef;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakatap.Models.NfcIntent;
import com.example.sakatap.R;
import com.example.sakatap.ShareViewModel;


public class NfcAkhirFragment extends Fragment {

    private TextView text_keterangan;
    ShareViewModel shareViewModel;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shareViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        shareViewModel.getMyintent().observe(NfcAkhirFragment.this, new Observer<NfcIntent>() {
            @Override
            public void onChanged(NfcIntent nfcIntent) {
                Ndef ndef = Ndef.get(nfcIntent.getTag());
                try {
                    ndef.connect();
                    Parcelable[] messages = nfcIntent.getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

                    if (messages != null) {
                        NdefMessage[] ndefMessages = new NdefMessage[messages.length];
                        for (int i = 0; i < messages.length; i++) {
                            ndefMessages[i] = (NdefMessage) messages[i];
                        }
                        NdefRecord record = ndefMessages[0].getRecords()[0];

                        byte[] payload = record.getPayload();
                        String text = new String(payload);
                        if(text.equals("\u0002enakhirtur")) {
                            Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_nfcAwalFragment_to_overviewFragment);
                        }
                        Log.e("tag", "vahid" + text);
                        ndef.close();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Cannot Read From Tag.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nfc_akhir, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text_keterangan = getView().findViewById(R.id.nfc_akhir_keterangan1);

        text_keterangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_nfcAwalFragment_to_overviewFragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        nfcAdapter.enableForegroundDispatch(getActivity(), pendingIntent, null, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(getActivity());
        }
    }
}