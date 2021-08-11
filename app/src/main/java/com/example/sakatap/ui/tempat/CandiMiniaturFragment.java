package com.example.sakatap.ui.tempat;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakatap.Adapter.ImageAdapter;
import com.example.sakatap.Models.Bangunan;
import com.example.sakatap.R;
import com.example.sakatap.ui.overview.OverviewFragment;
import com.example.sakatap.ui.overview.OverviewViewModel;

import java.io.IOException;
import java.util.List;

public class CandiMiniaturFragment extends Fragment {

    private CandiMiniaturViewModel viewModel;
    private ImageAdapter adapter;
    private ViewPager foto_situs;
    private Button tombol_next;
    private ImageButton tombol_play;
    private ImageButton tombol_pause;
    private TextView text_narasi;
    private int length;
    OnDataPass dataPasser;
    private MediaPlayer mediaPlayer;

    private final String path = "candi_miniatur";
    private final String audiopath = "Narasi/10Candi_Miniatur.mp3";
    private final String guidepath = "Guide/Guide11.mp3";

    public interface OnDataPass {
        public void onDataPass(String data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CandiMiniaturViewModel.class);
        length = -1;
        passData("candi_miniatur");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_candi_miniatur, container, false);
        tombol_next = view.findViewById(R.id.miniatur_next);
        tombol_play = view.findViewById(R.id.miniatur_play_button);
        tombol_pause = view.findViewById(R.id.miniatur_pause_button);
        text_narasi = view.findViewById(R.id.miniatur_narasi);

        viewModel.getnarasi("Candi_miniatur");
        viewModel.getNarasi().observe(getViewLifecycleOwner(), new Observer<Bangunan>() {
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

        tombol_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.reset();
                mediaPlayer.release();
                int nextid = R.id.action_guideFragment_to_kolamKeputrenFragment;
                Bundle bundle = new Bundle();
                bundle.putString("guide", "Candi_miniatur");
                bundle.putString("audio", guidepath );
                bundle.putInt("id", nextid );
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_candiMiniaturFragment_to_guideFragment, bundle);
            }
        });

        tombol_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(length == -1) {
                    viewModel.getaudiourl(audiopath);
                    viewModel.getAudiourl().observe(getViewLifecycleOwner(), new Observer<Uri>() {
                        @Override
                        public void onChanged(Uri uri) {
                            try {
                                mediaPlayer.setDataSource(getActivity(), uri);
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                            } catch (IOException e) {
                                Toast.makeText(getContext(), "Terjadi kesalahan pemutaran audio" + e, Toast.LENGTH_SHORT).show();
                                Log.d("AUDIO", "Error memutar audio", e);
                            }
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
        });

        tombol_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    length = mediaPlayer.getCurrentPosition();
                }
                else {
                    Toast.makeText(getContext(), "Audio belum dimainkan", Toast.LENGTH_SHORT);
                }
            }
        });

        foto_situs = view.findViewById(R.id.miniatur_foto_situs);
        viewModel.getlistimageurl(path);
        viewModel.getListimageurl().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                String[] arr = new String[strings.size()];
                arr = strings.toArray(arr);
                adapter = new ImageAdapter(getActivity(), arr);
                foto_situs.setAdapter(adapter);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void passData(String data) {
        dataPasser.onDataPass(data);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}