package com.example.sakatap.ui.guide;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sakatap.Models.Bangunan;
import com.example.sakatap.R;
import com.example.sakatap.ShareViewModel;

import java.io.IOException;


public class GuideFragment extends Fragment {

    private GuideViewModel viewModel;
    private ShareViewModel shareViewModel;
    private Button tombol_next;
    private Button tombol_back;
    private ImageButton tombol_play;
    private TextView text_petunjuk;
    private MediaPlayer mediaPlayer;
    private long mLastClickTime = 0;

    private String[] array_guide = {"Overview", "Gapura1", "Sejarah_penghunian", "Gapura2", "Talud", "Candi_pembakaran", "Umpak_batu", "Paseban", "Pendapa", "Candi_miniatur", "Kolam_dan_keputren", "Gua"};
    private String[] array_guidepath = {"Guide/Guide2.mp3", "Guide/Guide3.mp3", "Guide/Guide4.mp3", "Guide/Guide5.mp3","Guide/Guide6.mp3", "Guide/Guide7.mp3", "Guide/Guide8.mp3", "Guide/Guide9.mp3",
            "Guide/Guide10.mp3", "Guide/Guide11.mp3", "Guide/Guide12.mp3", "Guide/Guide13.mp3"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(GuideViewModel.class);
        shareViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        mediaPlayer = new MediaPlayer();
        loadguide();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        tombol_next = view.findViewById(R.id.guide_next);
        tombol_back = view.findViewById(R.id.guide_back);
        text_petunjuk = view.findViewById(R.id.guide_petunjuk);
        tombol_play = view.findViewById(R.id.guide_play_button);

        tombol_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareViewModel.increment();
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                shareViewModel.getPosition().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if(integer == 12) {
                            Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.nfcAkhirFragment);
                        }
                        else {
                            Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.turFragment);
                        }
                    }
                });
            }
        });

        tombol_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.turFragment);
            }
        });

        tombol_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                loadaudio();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void loadaudio() {
        shareViewModel.getPosition().observe(GuideFragment.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer < array_guidepath.length) {
                    viewModel.getaudiourl(array_guidepath[integer]);
                    viewModel.getAudiourl().observe(GuideFragment.this, new Observer<Uri>() {
                        @Override
                        public void onChanged(Uri uri) {
                            try {
                                mediaPlayer.reset();
                                mediaPlayer.setDataSource(getActivity(), uri);
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                mediaPlayer.prepare();
                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                                    @Override
                                    public void onPrepared(MediaPlayer playerM){
                                        mediaPlayer.start();
                                    }
                                });
                            } catch (IOException e) {
                                Log.d("AUDIO", "Error setDataSourcw audio", e);
                            }

                        }
                    });
                }
            }
        });
    }

    public void loadguide() {
        shareViewModel.getPosition().observe(GuideFragment.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer < array_guide.length) {
                    viewModel.getguide(array_guide[integer]);
                    viewModel.getGuide().observe(GuideFragment.this, new Observer<Bangunan>() {
                        @Override
                        public void onChanged(Bangunan bangunan) {
                            String hasil = bangunan.getGuide();
                            hasil = hasil.replace("\\n", "\n");
                            text_petunjuk.setText(hasil);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        viewModel = new ViewModelProvider(requireActivity()).get(GuideViewModel.class);
        shareViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        mediaPlayer = new MediaPlayer();
        loadguide();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}