package fr.wcs.gosu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.MyViewHolder>{
    private List<CardModel> cardModelList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,clan,rank,power,color;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.viewName);
            clan = view.findViewById(R.id.viewClan);
            rank = view.findViewById(R.id.viewRank);
            power = view.findViewById(R.id.viewPower);
            color = view.findViewById(R.id.textViewColor);
        }
    }

    ListCardAdapter(List<CardModel> cardModelList) {
        this.cardModelList = cardModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CardModel card = cardModelList.get(position);
        holder.name.setText(card.getName());
        String textClan = card.getClan();
        String textClanGoblin = textClan + " Goblin";
        holder.clan.setText(textClanGoblin);
        holder.rank.setText(String.valueOf(card.getRank()));
        holder.power.setText(String.valueOf(card.getPower()));
        switch (textClan){
            case "Ancient": holder.color.setBackgroundColor(0xFF00FFFF);break;
            case "Alpha": holder.color.setBackgroundColor(0xFF00FF00);break;
            case "Dark": holder.color.setBackgroundColor(0xFF000000);break;
            case "Méka": holder.color.setBackgroundColor(0xFF0000FF);break;
            case "Fire": holder.color.setBackgroundColor(0xFFFF0000);break;
            default: break;
        }
    }

    @Override
    public int getItemCount() {return cardModelList.size();}
}
