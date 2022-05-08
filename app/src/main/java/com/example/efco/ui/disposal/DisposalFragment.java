package com.example.efco.ui.disposal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.efco.R;
import com.example.efco.databinding.FragmentDisposalBinding;
import com.example.efco.ui.store.ItemAdapter;
import com.example.efco.ui.store.ItemData;
import com.example.efco.ui.store.StoreFragment;

public class DisposalFragment extends Fragment {

    private DisposalViewModel disposalViewModel;
    private FragmentDisposalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        disposalViewModel =
                new ViewModelProvider(this).get(DisposalViewModel.class);

        binding = FragmentDisposalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        RecyclerView recyclerView = binding.recyclerView1;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        com.example.efco.ui.disposal.ClientData[] myClientData = new com.example.efco.ui.disposal.ClientData[]{
                new com.example.efco.ui.disposal.ClientData("HP","7587807575", "Habibullah Rd, T Nagar, Chennai, Tamil Nadu"),
                new com.example.efco.ui.disposal.ClientData("Sony","2293288256","Dr.Subbaraya Nagar, Kodambakkam, Chennai, Tamil Nadu"),
                new com.example.efco.ui.disposal.ClientData("Microsoft","8990809908","Rathna Nagar, Teynampet, Chennai, Tamil Nadu"),
//                new com.example.efco.ui.disposal.ClientData("Hisense 7.0 Kg Fully-Automatic Front Loading Washing Machine","799 points",R.drawable.washing),
//                new com.example.efco.ui.disposal.ClientData("LG 687 L Inverter Frost Free Side by Side Refrigerator","2600 points",R.drawable.referi),
//                new com.example.efco.ui.disposal.ClientData("Asus 23.8 inch FreeSync HDR Eye Care Gaming Monitor","1190 points",R.drawable.tv1),
//                new com.example.efco.ui.disposal.ClientData("Sansui 7 Kg Semi Automatic Top Loading Washing Machine","550 points",R.drawable.washing1),
                new ClientData("Amazon","2634626522","Ratna Nagar, Virugambakkam, Chennai, Tamil Nadu"),
        };

        com.example.efco.ui.disposal.ClientAdapter clientAdapter=new ClientAdapter(myClientData, DisposalFragment.this);
        recyclerView.setAdapter(clientAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}