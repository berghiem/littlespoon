package ruby.com.littlespoon.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ruby.com.littlespoon.Navigate;
import ruby.com.littlespoon.R;
import ruby.com.littlespoon.model.ProcessTypeCategory;

/**
 * Created by Ruby on 2/20/2018.
 */

public class FoodAdapter  extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    public List<ProcessTypeCategory> items;
    private Context context;
    private List<Integer> colors;
    private Navigate action;
    private boolean isResult;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView text;
        public ViewHolder(View view) {
            super(view);
            text = (TextView) view;
        }

    }

    public FoodAdapter( Context context, Navigate showRecipe, boolean isResult){
        this.context = context;
        this.action = showRecipe;
        this.isResult = isResult;

    }


    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.food_type,parent,false);
        ViewHolder viewHolder = new ViewHolder( v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ProcessTypeCategory category = items.get(position);
        holder.text.setText(category.getName());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isResult){

                }else {
                   // action.showRecipe(category.getId(), 2, "");
                    action.collect(false, items.get(position).getId());
                }
            }
        });

    }

    public void setItems(List<ProcessTypeCategory> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

