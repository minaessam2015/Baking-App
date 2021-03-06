package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mina essam on 15-Jun-17.
 */

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.ViewHolder> {
    private Context context;
    private int index;
    private ViewClickListener listener;
    private Recipe recipe;

    public void setListener(ViewClickListener listener) {
        this.listener = listener;
    }

    interface ViewClickListener{
        void onViewClickListener(int stepPosition);
    }

    public RecipeDetailsAdapter (Context context,int index,Recipe recipe){
        this.context=context;
        this.index=index;
        this.recipe=recipe;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(context).inflate(R.layout.recipe_details_row,parent,false);
            ViewHolder holder=new ViewHolder(view);
            return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position==0){
            holder.textView.setText("Ingredients");
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.lightGreen));
        }else {
            holder.textView.setText(""+position+" - "+recipe.getSteps().get(position-1).getShortDes());
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.blue));
            Log.d("RecipeDetailsAdapter"," Video Url   "+recipe.getStepAt(position-1).getVideoUrl());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
       /* if(SharedRecipes.sharedRecipes!=null){
        Recipe recipe= SharedRecipes.sharedRecipes.get(index);
        return recipe.getSteps().size()+1;}
        else {
            return 0;
        }*/
       return recipe.getSteps().size()+1;
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_details_row)
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            textView=(TextView)itemView.findViewById(R.id.recipe_details_row);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onViewClickListener(getAdapterPosition());
                    Log.d("RecipeDetailsAdapter","adapter position  "+getAdapterPosition());
                }
            });
        }
    }
}
