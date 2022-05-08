package com.example.efco.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.efco.ReadWriteUserDetails;
import com.example.efco.databinding.FragmentProfileBinding;
import com.example.efco.ui.AppActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private TextView textViewpoints,textViewFullName,textViewEmail,textViewMobile,textViewAddress;
//    private ProgressBar progressBar;
    private String fullName,email,mobile,address;
    private int points;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textViewFullName = binding.showFullName;
        textViewEmail=binding.showEmail;
        textViewMobile=binding.showPhone;
        textViewAddress=binding.showAddress;
        textViewpoints=binding.showPoints;
//        progressBar=binding.
//        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser==null)
        {
            Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
        else
        {
            showUserProfile(firebaseUser);
        }
        return root;
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userId=firebaseUser.getUid();
        CollectionReference dbRef = FirebaseFirestore.getInstance().collection("Users");
        dbRef.document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Map<String, Object> user = new HashMap<>();
                user= value.getData();
                if(user!=null)
                {
                    fullName=(String) user.get("Full Name");
                    email=firebaseUser.getEmail();
                    mobile=(String) user.get("Phone Number");
                    address=(String) user.get("Address");
                    points=Integer.parseInt((String)user.get("Points"));

                    textViewpoints.setText("Current Points : "+points);
                    textViewFullName.setText(fullName);
                    textViewAddress.setText(address);
                    textViewEmail.setText(email);
                    textViewMobile.setText(mobile);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
       super.onDestroyView();
        binding = null;
    }

}