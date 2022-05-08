package com.example.efco.ui.disposal;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.efco.R;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {
    ClientData[] clientData;
    Context context;

    public ClientAdapter(ClientData[] clientData, DisposalFragment activity) {
        this.clientData = clientData;
        this.context= activity.getActivity();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.my_client_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ClientData clientDataList = clientData[position];

        holder.textViewClientName.setText(clientDataList.getClientName());
        holder.textViewphone.setText(clientDataList.getPhone());
        holder.textViewaddress.setText(clientDataList.getAddress());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(context,Order.class);
//
//                context.startActivity(intent);
//
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return clientData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewaddress;
        TextView textViewClientName;
        TextView textViewphone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewaddress =itemView.findViewById(R.id.address);
            textViewClientName = itemView.findViewById(R.id.clientName);
            textViewphone = itemView.findViewById(R.id.Cphone);
        }
    }
}

