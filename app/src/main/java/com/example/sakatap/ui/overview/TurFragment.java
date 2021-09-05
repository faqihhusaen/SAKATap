package com.example.sakatap.ui.overview;

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
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakatap.Adapter.ImageAdapter;
import com.example.sakatap.Models.Bangunan;
import com.example.sakatap.R;
import com.example.sakatap.ShareViewModel;

import java.io.IOException;
import java.util.List;


public class TurFragment extends Fragment {

    private TurViewModel viewModel;
    private ShareViewModel shareViewModel;
    private ImageAdapter adapter;
    private ViewPager foto_situs;
    private Button tombol_next;
    private Button tombol_back;
    private ImageButton tombol_play;
    private ImageButton tombol_pause;
    private TextView text_narasi;
    private int length;
    private MediaPlayer mediaPlayer;
    private String[] array_narasi = {"Overview", "Gapura1", "Sejarah_penghunian", "Gapura2", "Talud", "Candi_pembakaran", "Umpak_batu", "Paseban", "Pendapa", "Candi_miniatur", "Kolam_dan_keputren", "Gua"};
    private String[] array_imageurl = {"overview", "gapura1", "sejarah_penghunian", "gapura2", "talud", "candi_pembakaran", "umpak", "paseban", "pendapa", "candi_miniatur", "kolam_keputren", "gua"};
    private String[] array_audiopath = {"Narasi/1Overview.mp3", "Narasi/3Gapura_I.mp3", "Narasi/2Sejarah_Penghunian.mp3", "Narasi/4Gapura_II.mp3", "Narasi/5Talut.mp3", "Narasi/6Candi_Pembakaran.mp3",
                                        "Narasi/17Umpak_Batu.mp3", "Narasi/8Paseban.mp3", "Narasi/9Pendapa.mp3", "Narasi/10Candi_Miniatur.mp3", "Narasi/11Kolam_dan_keputren.mp3", "Narasi/12Gua.mp3" };
    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TurViewModel.class);
        shareViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        shareViewModel.setCondition("tur");
        length = -1;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        loadnarasi();
        loadimage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tur, container, false);
        foto_situs = view.findViewById(R.id.overview_pager);
        tombol_next = view.findViewById(R.id.overview_next);
        tombol_back = view.findViewById(R.id.overview_back);
        tombol_play = view.findViewById(R.id.overview_play_button);
        tombol_pause = view.findViewById(R.id.overview_pause_button);
        text_narasi = view.findViewById(R.id.overview_narasi);

        tombol_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_overviewFragment_to_guideFragment);
            }
        });

        tombol_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareViewModel.decrement();
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_overviewFragment_to_guideFragment);
            }
        });

        tombol_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadaudio();
            }
        });

        tombol_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        viewModel = new ViewModelProvider(requireActivity()).get(TurViewModel.class);
        shareViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        shareViewModel.setCondition("tur");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        loadnarasi();
        loadimage();
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

    public void loadnarasi() {
        shareViewModel.getPosition().observe(TurFragment.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer < array_narasi.length) {
                    viewModel.getnarasi(array_narasi[integer]);
                    viewModel.getNarasi().observe(TurFragment.this, new Observer<Bangunan>() {
                        @Override
                        public void onChanged(Bangunan bangunan) {
                            if (bangunan != null) {
                                String judul = bangunan.getJudul();
                                String narasi = bangunan.getNarasi();
                                narasi = narasi.replace("\\n", "\n");
                                String hasil = judul + "\n\n" + narasi;
                                text_narasi.setText(hasil);
                            }
                        }
                    });
                }
            }
        });

    }

    public void loadimage() {
        shareViewModel.getPosition().observe(TurFragment.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer < array_imageurl.length) {
                    viewModel.getlistimageurl(array_imageurl[integer]);
                    viewModel.getListimageurl().observe(TurFragment.this, new Observer<List<String>>() {
                        @Override
                        public void onChanged(List<String> strings) {
                            String[] arr = new String[strings.size()];
                            arr = strings.toArray(arr);
                            adapter = new ImageAdapter(getActivity(), arr);
                            foto_situs.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }

    public void loadaudio() {
        if(length == -1) {
            shareViewModel.getPosition().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(integer < array_audiopath.length) {
                        viewModel.getaudiourl(array_audiopath[integer]);
                        viewModel.getAudiourl().observe(getViewLifecycleOwner(), new Observer<Uri>() {
                            @Override
                            public void onChanged(Uri uri) {
                                try {
                                    mediaPlayer.reset();
                                    mediaPlayer.setDataSource(getActivity(), uri);
                                    mediaPlayer.prepare();
                                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                                        @Override
                                        public void onPrepared(MediaPlayer playerM){
                                            mediaPlayer.start();
                                        }
                                    });

                                } catch (IOException e) {
                                    Log.d("AUDIO", "Error setDataSource audio", e);
                                }
                            }
                        });}
                }
            });

        }
        else {
            if(!mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(length);
                mediaPlayer.start();
            }
        }
    }

    public void pause() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
        }
        else {
            Toast.makeText(getContext(), "Audio belum dimainkan", Toast.LENGTH_SHORT).show();
        }
    }


}