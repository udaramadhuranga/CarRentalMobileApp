package com.cupcakes.kandycupcakes.IT19210698;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cupcakes.kandycupcakes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class vehiAdapter extends RecyclerView.Adapter<vehiAdapter.vehiHolder>{

    ArrayList<vehicles> vehicleList;
    Context mcontext;


    public vehiAdapter(Context mcontext, ArrayList<vehicles> vehicleList){
        this.vehicleList = vehicleList;
        this.mcontext = mcontext;

    }

    @NonNull
    @Override
    public vehiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewlayout,parent,false);
         return new vehiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final vehiHolder holder, final int position) {

        holder.tname.setText(vehicleList.get(position).getName());
        holder.ttransmisson.setText(vehicleList.get(position).getTransmisson());
        holder.tpassengers.setText(String.valueOf(vehicleList.get(position).getPassengers()));
        holder.tprice.setText(String.valueOf(vehicleList.get(position).getPrice()));
        holder.tbags.setText(String.valueOf(vehicleList.get(position).getBags()));
        Picasso.with(mcontext).load(vehicleList.get(position).getImageUrl()).into(holder.ivehicleimg);

        holder.btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.btnbook.getContext(), booking.class);
                intent.putExtra("vehi_price",holder.tprice.getText().toString());
                intent.putExtra("key",vehicleList.get(position).getKey());
                holder.btnbook.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {

        return vehicleList.size();
    }




    class vehiHolder extends RecyclerView.ViewHolder{
        TextView tname,tpassengers,ttransmisson,tprice,tbags;
        ImageView ivehicleimg;
        Button btnbook;

        public vehiHolder(@NonNull View itemView) {
            super(itemView);
            tname = (TextView) itemView.findViewById(R.id.name);
            tpassengers = (TextView) itemView.findViewById(R.id.passengers);
            ttransmisson = (TextView) itemView.findViewById(R.id.transmission);
            tprice = (TextView) itemView.findViewById(R.id.price);
            ivehicleimg = (ImageView) itemView.findViewById(R.id.image);
            tbags = (TextView) itemView.findViewById(R.id.bags);
            btnbook = (Button) itemView.findViewById(R.id.btnbooknow);


        }

    }
}
