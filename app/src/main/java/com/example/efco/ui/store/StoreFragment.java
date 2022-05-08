package com.example.efco.ui.store;

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
import com.example.efco.databinding.FragmentStoreBinding;

public class StoreFragment extends Fragment {

    private StoreViewModel storeViewModel;
    private FragmentStoreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        storeViewModel =
                new ViewModelProvider(this).get(StoreViewModel.class);

        binding = FragmentStoreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemData[] myItemData = new ItemData[]{
                new ItemData("Fire-Boltt BSW001 Smart Watch","200 points",R.drawable.watch),
                new ItemData("BPL 32 inch HD Ready LED Android Smart TV","590 Points",R.drawable.tv),
                new ItemData("Lapcare L-70 Optical Wired Mouse","89 points",R.drawable.mouse),
                new ItemData("Hisense 7.0 Kg Fully-Automatic Front Loading Washing Machine","799 points",R.drawable.washing),
                new ItemData("LG 687 L Inverter Frost Free Side by Side Refrigerator","2600 points",R.drawable.referi),
                new ItemData("Asus 23.8 inch FreeSync HDR Eye Care Gaming Monitor","1190 points",R.drawable.tv1),
                new ItemData("Sansui 7 Kg Semi Automatic Top Loading Washing Machine","550 points",R.drawable.washing1),
                new ItemData("Haier 276 litres 3 Star Double Door Refrigerator","1780 points",R.drawable.referi1),
        };

        ItemAdapter itemAdapter=new ItemAdapter(myItemData,StoreFragment.this);
        recyclerView.setAdapter(itemAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}