package com.doniapriano.fragmentbottomnavandhttpurlconnectio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderWarDetails> {

    private Context context;
    private List<ModelWarDetails> listWarDetails;

    RecyclerViewAdapter(Context context, List<ModelWarDetails> listWarDetails) {
        this.context = context;
        this.listWarDetails = listWarDetails;
    }

    @Override
    public ViewHolderWarDetails onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view,parent,false);
        ViewHolderWarDetails viewHolderWarDetails = new ViewHolderWarDetails(view);

        return viewHolderWarDetails;
    }

    @Override
    public void onBindViewHolder(ViewHolderWarDetails holder, int position) {
        holder.txtName.setText(listWarDetails.get(position).getName());
        holder.txtLocation.setText(listWarDetails.get(position).getLocation());
        holder.txtAttackerKing.setText(listWarDetails.get(position).getAttacker_king());
        holder.txtDefenderKing.setText(listWarDetails.get(position).getDefender_king());
    }
    
    @Override
    public int getItemCount() {
        return listWarDetails.size();
    }

    public class ViewHolderWarDetails extends RecyclerView.ViewHolder{
        TextView txtName, txtLocation, txtAttackerKing,txtDefenderKing;
        CardView layoutCard;

        ViewHolderWarDetails(View v) {
            super(v);
            txtName = (TextView) v.findViewById(R.id.txtName);
            txtLocation = (TextView) v.findViewById(R.id.txtLocation);
            txtAttackerKing = (TextView) v.findViewById(R.id.txtAttackerKing);
            txtDefenderKing = (TextView) v.findViewById(R.id.txtDefenderKing);
            layoutCard = (CardView) v.findViewById(R.id.layoutCard);
        }
    }
}