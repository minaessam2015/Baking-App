package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mina essam on 28-Jun-17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private Context context;
    private int recipePosition;
    private Recipe recipe;
    public IngredientAdapter(Context context,int position,Recipe recipe){
        this.context=context;
        this.recipePosition=position;
        this.recipe=recipe;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ingredient_row,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ingredientNum.setText(position+1+"");
        Ingredient ingredient=recipe.getIngredientAt(position);
        holder.ingredientContent.setText(ingredient.getQuantity()+"    "+ingredient.getMeasure()+"  of  "+ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        return recipe.getIngredients().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_number)
        TextView ingredientNum;
        @BindView(R.id.ingredient_content)
        TextView ingredientContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            ingredientContent=(TextView) itemView.findViewById(R.id.ingredient_content);
            ingredientNum=(TextView)itemView.findViewById(R.id.ingredient_number);
        }
    }
}
