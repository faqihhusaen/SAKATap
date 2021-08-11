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


public class GapuraPertamaFragment extends Fragment {

    public interface OnDataPass {
        public void onDataPass(String data);
    }

    private GapuraPertamaViewModel viewModel;
    private ViewPager foto_situs;
    private Button tombol_next;
    private ImageButton tombol_play;
    private ImageButton tombol_pause;
    private TextView text_narasi;
    private int length;
    private ImageAdapter adapter;
    OnDataPass dataPasser;
    private MediaPlayer mediaPlayer;

    private final String path = "gapura1";
    private final String audiopath = "Narasi/3Gapura_I.mp3";
    private final String guidepath = "Guide/Guide3.mp3";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(GapuraPertamaViewModel.class);
        viewModel.getnarasi("Gapura1");
        viewModel.getNarasi().observe(GapuraPertamaFragment.this, new Observer<Bangunan>() {
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
        passData("gapura1");
        length = -1;
        mediaPlayer = new MediaPlayer();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gapura_utama, container, false);
        foto_situs = view.findViewById(R.id.gapura_pertama_foto_situs);
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

        tombol_next = getView().findViewById(R.id.gapura_pertama_next);
        tombol_play = getView().findViewById(R.id.gapura_pertama_play_button);
        tombol_pause = getView().findViewById(R.id.gapura_pertama_pause_button);
        text_narasi = getView().findViewById(R.id.gapura_pertama_narasi);

        tombol_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextid = R.id.action_guideFragment_to_sejarahPenghunianFragment;
                Bundle bundle = new Bundle();
                bundle.putString("guide", "Gapura1");
                bundle.putString("audio", guidepath );
                bundle.putInt("id", nextid );
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_gapuraPertamaFragment_to_guideFragment, bundle);
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
                                mediaPlayer.reset();
                                mediaPlayer.setDataSource(getActivity(), uri);
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                        mediaPlayer.reset();
                                        length = -1;
                                    }
                                });
                            } catch (IOException e) {
                                Toast.makeText(getContext(), "Terjadi kesalahan pemutaran audio" + e, Toast.LENGTH_SHORT).show();
                                Log.d("AUDIO", "Error memutar audio", e);
                            }
                        }
                    });
                }
                else {
                    if(!mediaPlayer.isPlaying() && length < mediaPlayer.getDuration()) {
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
    }

    public void passData(String data) {
        dataPasser.onDataPass(data);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        mediaPlayer = new MediaPlayer();
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