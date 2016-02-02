package com.baseballstandings.logify;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Scott on 1/30/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Division> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView dName;
        private TextView leagueName;
        private LinearLayout layout;
        public ViewHolder(View v) {
            super(v);
            dName = (TextView) v.findViewById(R.id.divisionNameTextView);
            leagueName = (TextView) v.findViewById(R.id.leagueNameTextView);
            layout = (LinearLayout)v.findViewById(R.id.teamsLinearLayout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, List<Division> myDataset) {
        this.mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.standings_recycle_view_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if(position == 0){
            holder.leagueName.setText("National League");
            holder.leagueName.setVisibility(View.VISIBLE);
        }else if(position == 3){
            holder.leagueName.setText("American League");
            holder.leagueName.setVisibility(View.VISIBLE);
        }else{
            holder.leagueName.setText("");
            holder.leagueName.setVisibility(View.INVISIBLE);
        }

       holder.dName.setText(mDataset.get(position).getName());

        holder.layout.addView(new TeamRow(context,
                mDataset.get(position).getTeams().get(0)), holder.layout.getChildCount());
        holder.layout.addView(new TeamRow(context,
                mDataset.get(position).getTeams().get(1)), holder.layout.getChildCount());
        holder.layout.addView(new TeamRow(context,
                mDataset.get(position).getTeams().get(2)), holder.layout.getChildCount());
        holder.layout.addView(new TeamRow(context,
                mDataset.get(position).getTeams().get(3)), holder.layout.getChildCount());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    class TeamRow extends LinearLayoutCompat {

        private TextView rank,name,record,pct;

        public TeamRow(Context context,Team team) {
            super(context);
            init(team);
        }

        public TeamRow(Context context, AttributeSet attrs,Team team) {
            super(context, attrs);
            init(team);
        }

        public TeamRow(Context context, AttributeSet attrs, int defStyleAttr, Team team) {
            super(context, attrs, defStyleAttr);
            init(team);
        }

        public void init(Team team){

            LayoutInflater.from(getContext()).inflate(R.layout.team_standing_row,this,true);

            rank = (TextView) this.findViewById(R.id.gamesBackTextView);
            name = (TextView) this.findViewById(R.id.teamNameTextView);
            record = (TextView) this.findViewById(R.id.recordTextView);
            pct = (TextView) this.findViewById(R.id.winPctTextView);

            rank.setText(team.getGame_back());
            name.setText(team.getName());
            record.setText(team.getWins() + "-" + team.getLosses());
            pct.setText(team.getWin_pct() + "%");
        }
    }
}
