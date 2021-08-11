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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakatap.Models.Bangunan;
import com.example.sakatap.R;
import com.example.sakatap.ui.overview.OverviewFragment;
import com.example.sakatap.ui.overview.OverviewViewModel;
import com.example.sakatap.ui.tempat.GuaViewModel;

import java.io.IOException;


public class GuideFragment extends Fragment {

    public interface OnDataPass {
        public void onDataPass(String data);
    }

    public interface OnIDPass {
        public void onIDPass(int id);
    }

    public interface OnGuidePass {
        public void onGuidePass(String guide);
    }

    public interface OnAudioPass {
        public void onAudioPass(String audio);
    }

    private GuideViewModel viewModel;
    private Button tombol_next;
    private ImageButton tombol_play;
    private TextView text_petunjuk;
    private int id;
    private int length;
    private String guide;
    private String audio;
    private MediaPlayer mediaPlayer;
    OnDataPass dataPasser;
    OnIDPass idpasser;
    OnGuidePass guidePasser;
    OnAudioPass audiopasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
        idpasser = (OnIDPass) context;
        guidePasser = (OnGuidePass) context;
        audiopasser = (OnAudioPass) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(GuideViewModel.class);
        id = getArguments().getInt("id");
        guide = getArguments().getString("guide");
        audio = getArguments().getString("audio");
        length = -1;

        mediaPlayer = new MediaPlayer();

        passData("guide");
        passID(id);
        passGuide(guide);
        passAudio(audio);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tombol_next = getView().findViewById(R.id.guide_next);
        text_petunjuk = getView().findViewById(R.id.guide_petunjuk);
        tombol_play = getView().findViewById(R.id.guide_play_button);

        tombol_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(id);
            }
        });

        tombol_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getaudiourl(audio);
                viewModel.getAudiourl().observe(getViewLifecycleOwner(), new Observer<Uri>() {
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
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    mediaPlayer.reset();
                                }
                            });
                        } catch (IOException e) {
                            Log.d("AUDIO", "Error setDataSourcw audio", e);
                        }
                    }
                });
            }

        });

        viewModel.getguide(guide);
        viewModel.getGuide().observe(getViewLifecycleOwner(), new Observer<Bangunan>() {
            @Override
            public void onChanged(Bangunan bangunan) {
                String hasil = bangunan.getGuide();
                hasil = hasil.replace("\\n", "\n");
                text_petunjuk.setText(hasil);
            }
        });
    }

    public void passData(String data) {
        dataPasser.onDataPass(data);
    }

    public void passID(int id) {
        idpasser.onIDPass(id);
    }

    public void passGuide(String guide) {
        guidePasser.onGuidePass(guide);
    }

    public void passAudio(String audio) {
        audiopasser.onAudioPass(audio);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
}