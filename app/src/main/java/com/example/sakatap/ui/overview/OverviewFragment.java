package com.example.sakatap.ui.overview;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BadParcelableException;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakatap.Adapter.ImageAdapter;
import com.example.sakatap.Models.Bangunan;
import com.example.sakatap.Models.ImageURL;
import com.example.sakatap.R;
import com.example.sakatap.ui.login.LoginViewModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OverviewFragment extends Fragment {

    public interface OnDataPass {
        public void onDataPass(String data);
    }

    private OverviewViewModel viewModel;
    private ImageAdapter adapter;
    private ViewPager foto_situs;
    private Button tombol_next;
    private ImageButton tombol_play;
    private ImageButton tombol_pause;
    private TextView text_narasi;
    private int length;
    OnDataPass dataPasser;
    private MediaPlayer mediaPlayer;

    private final String path = "overview";
    private final String audiopath = "Narasi/1Overview.mp3";
    private final String guidepath = "Guide/Guide2.mp3";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(OverviewViewModel.class);
        passData("overview");
        length = -1;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
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
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        foto_situs = view.findViewById(R.id.overview_pager);
        tombol_next = view.findViewById(R.id.overview_next);
        tombol_play = view.findViewById(R.id.overview_play_button);
        tombol_pause = view.findViewById(R.id.overview_pause_button);
        text_narasi = view.findViewById(R.id.overview_narasi);

        tombol_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextid = R.id.action_guideFragment_to_gapuraPertamaFragment;
                Bundle bundle = new Bundle();
                bundle.putString("guide", "Overview");
                bundle.putString("audio", guidepath );
                bundle.putInt("id", nextid );
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_overviewFragment_to_guideFragment, bundle);
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
                    Toast.makeText(getContext(), "Audio belum dimainkan", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        viewModel.getnarasi("Overview");
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
    }

    public void passData(String data) {
        dataPasser.onDataPass(data);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();

        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }


}