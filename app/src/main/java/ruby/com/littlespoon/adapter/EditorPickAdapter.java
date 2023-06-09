package ruby.com.littlespoon.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ruby.com.littlespoon.Navigate;
import ruby.com.littlespoon.R;
import ruby.com.littlespoon.api.call.response.EditorPick;
import ruby.com.littlespoon.model.AgeCategory;
import ruby.com.littlespoon.model.ProcessTypeCategory;
import ruby.com.littlespoon.model.Recipe;

/**
 * Created by Ruby on 2/20/2018.
 */

public class EditorPickAdapter  extends RecyclerView.Adapter<EditorPickAdapter.ViewHolder>{

    private Context context;
    private List<EditorPick> items;
    private List<AgeCategory> categories;
    private List<ProcessTypeCategory> processList;
    private Navigate navigate;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public TextView time;
        public TextView recipeName;
        public TextView category;
        public TextView likeCount;
        public ImageView recipePict;
        public ImageView likeButton;


        public ViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username);
            time = (TextView) view.findViewById(R.id.time);
            recipeName = (TextView) view.findViewById(R.id.recipeName);
            category = (TextView) view.findViewById(R.id.category);
            likeCount = (TextView) view.findViewById(R.id.likeCount);
            recipePict = (ImageView) view.findViewById(R.id.recipeImage);
           // likeButton = (ImageView) view.findViewById(R.id.likeButton);

        }
    }

    public EditorPickAdapter(Context context, Navigate navigate){
        this.context = context;
        if(items != null  && items.size() > 0 ){
            this.items = items;
        }
        this.navigate = navigate;

    }

    public void setItems(List<EditorPick> items) {
        this.items = items;
    }

    public void setCategories(List<AgeCategory> categories) {
        this.categories = categories;
    }

    public void setProcessList(List<ProcessTypeCategory> processList) {
        this.processList = processList;
    }

    @Override
    public EditorPickAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.editor_pick,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(EditorPickAdapter.ViewHolder holder, int position) {
        final Recipe latestRecipe = items.get(position).getRecipe();
        holder.username.setText(latestRecipe.getUser().getUsername());
        holder.time.setText(latestRecipe.getCreated_at().toString());
        holder.recipeName.setText(latestRecipe.getName());
        holder.likeCount.setText(latestRecipe.getCountlike()+" likes");
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        holder.category.setText(latestRecipe.getAgeCategoryId());
        Picasso.with(context)
                .load(latestRecipe.getImageUri())
                //.placeholder(R.drawable.user_placeholder)
                .error(R.drawable.error)
                .into(holder.recipePict);

        holder.recipePict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate.streamShowRecipe(latestRecipe.getId());
            }
        });



    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

