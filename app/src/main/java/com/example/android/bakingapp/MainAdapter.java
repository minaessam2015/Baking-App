package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mina essam on 14-Jun-17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>  {


    interface CardClickListener{
         void onCardClickListener(int position);
    }

    private Context context;
    private List<Recipe> recipes=new ArrayList<>();
    private CardClickListener listener;

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public MainAdapter(Context context){
        this.context=context;
    }
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.main_cell_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe=recipes.get(position);
        String imageUrl=recipe.getImageUrl();
        if(imageUrl.equals("")) imageUrl="anyfakestring";
        Picasso.with(context).load(imageUrl).error(R.drawable.brownie).into(holder.imageView);
        holder.textView.setText(recipe.getName());
    }

    public void setListener(CardClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        if(recipes!=null)
        return recipes.size();
        else return 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.recipe_image) public  ImageView imageView;
        @BindView(R.id.recipe_title) public  TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            Log.d("ViewHolder","Butterknife invoked");
            imageView=(ImageView)itemView.findViewById(R.id.recipe_image);
            textView=(TextView)itemView.findViewById(R.id.recipe_title);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onCardClickListener(getAdapterPosition());
        }
    }
}
