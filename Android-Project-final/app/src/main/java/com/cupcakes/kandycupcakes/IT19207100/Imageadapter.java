package com.cupcakes.kandycupcakes.IT19207100;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cupcakes.kandycupcakes.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;



    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.Tpassen.setText(String.valueOf(uploadCurrent.getPassengers()));
        holder.Bags.setText(String.valueOf(uploadCurrent.getBags()));
        holder.Tprice.setText(String.valueOf(uploadCurrent.getPrice()));
        holder.Ttrans.setText(String.valueOf(uploadCurrent.getTransmisson()));
        holder.Availb.setText(String.valueOf(uploadCurrent.getAvailable()));

        if(uploadCurrent.getAvailable().equals("Available")) {
            holder.Availb.setBackgroundColor(Color.parseColor("#3ddc84"));
        }
        else {
            holder.Availb.setBackgroundColor(Color.parseColor("#b00020"));
        }

       // holder.Ttrans.setText(String.valueOf(uploadCurrent.getTransmisson()));


        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnCreateContextMenuListener , MenuItem.OnMenuItemClickListener {
        public TextView textViewName,Tpassen,Tprice,Ttrans,Bags,Availb;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            Tpassen = itemView.findViewById(R.id.pasen);
            Tprice = itemView.findViewById(R.id.pprice);
            Ttrans = itemView.findViewById(R.id.trnsm);
            Bags=itemView.findViewById(R.id.bagg);
            Availb=itemView.findViewById(R.id.availablity);

            itemView.setOnClickListener(this);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener!=null){
                int position =getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem dowhatever = contextMenu.add(Menu.NONE,1,1,"Update");
            MenuItem delete = contextMenu.add(Menu.NONE,2,2,"Delete");

            dowhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener!=null){
                int position =getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    switch(menuItem.getItemId()){

                        case 1 :
                            mListener.onWhatEverClick(position);
                            return  true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return  true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{

        void onItemClick(int position);
        void onWhatEverClick(int position);
         void onDeleteClick(int position);
        }


    public void setOnItemClickListener(OnItemClickListener listener){

        mListener = listener;

    }



}