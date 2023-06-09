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
import ruby.com.littlespoon.model.AgeCategory;

/**
 * Created by Ruby on 2/20/2018.
 */

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public List<AgeCategory> items;
    private Context context;
    private List<Integer> colors;
    public Navigate action;
    private boolean isResult;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView text;
        public ViewHolder(View view) {
            super(view);
            text = (TextView) view;
        }

    }

    public CategoryAdapter( Context context, Navigate action , boolean isResult){
        this.context = context;
        this.action = action;
        this.isResult = isResult;

    }



    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.category,parent,false);
        ViewHolder viewHolder = new ViewHolder( v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String category = items.get(position).getName();
        holder.text.setText(category);
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isResult){

                }else {
                    //action.showRecipe(items.get(position).getId(), 1, "");
                    action.collect(true, items.get(position).getId());
                }
            }
        });

    }

    public void setItems(List<AgeCategory> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
