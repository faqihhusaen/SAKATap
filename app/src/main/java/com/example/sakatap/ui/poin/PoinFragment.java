package com.example.sakatap.ui.poin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sakatap.R;
import com.example.sakatap.ShareViewModel;
import com.example.sakatap.ui.guide.GuideViewModel;


public class PoinFragment extends Fragment {

    private PoinViewModel viewModel;
    private ShareViewModel shareViewModel;
    private Button tombol_next;
    private TextView text_poin;
    private int jumlah_poin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poin, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        return true;
                    }
                }
                return false;
            }
        });

        tombol_next = view.findViewById(R.id.poin_next);
        text_poin = view.findViewById(R.id.poin_poin);

        tombol_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareViewModel.reset();
                viewModel.addpoin(jumlah_poin, 1);
                Navigation.findNavController(getActivity(), R.id.navHostFragment).navigate(R.id.action_poinFragment_to_souvenirFragment);
            }
        });

        text_poin.setText(String.valueOf(jumlah_poin));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(PoinViewModel.class);
        shareViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        shareViewModel.setCondition("welcome");
        jumlah_poin = 500;

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}