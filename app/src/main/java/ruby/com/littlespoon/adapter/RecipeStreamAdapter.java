package ruby.com.littlespoon.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ruby.com.littlespoon.Navigate;
import ruby.com.littlespoon.R;
import ruby.com.littlespoon.model.AgeCategory;
import ruby.com.littlespoon.model.ProcessTypeCategory;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.User;
import ruby.com.littlespoon.room.db.BabySpoonViewModel;

/**
 * Created by Ruby on 2/20/2018.
 */

public class RecipeStreamAdapter extends RecyclerView.Adapter<RecipeStreamAdapter.ViewHolder> {

    private Context context;
    private List<Recipe> items;


    private Navigate navigate;
    private ImageView showOptionImage;

    private int userId;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public TextView time;
        public TextView recipeName;
        public TextView category;
        public TextView likeCount;
        public ImageView recipePict;
        public LinearLayout showRecipe;
        public ImageView likeButton;
        public ImageView showOptionImage;


        public ViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username);
            time = (TextView) view.findViewById(R.id.time);
            recipeName = (TextView) view.findViewById(R.id.recipeName);
            category = (TextView) view.findViewById(R.id.category);
            likeCount = (TextView) view.findViewById(R.id.likeCount);
            recipePict = (ImageView) view.findViewById(R.id.recipeImage);
            likeButton = (ImageView) view.findViewById(R.id.likeImage);
            showRecipe = (LinearLayout) view.findViewById(R.id.showRecipe);
            showOptionImage = (ImageView) view.findViewById(R.id.option);
        }
    }

    public RecipeStreamAdapter(Context context, Navigate navigate){
        this.context = context;
        if(items != null  && items.size() > 0 ){
            this.items = items;
        }
        this.navigate = navigate;
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userid", 0);

    }

    public void setItems(List<Recipe> items) {
        this.items = items;
    }

    @Override
    public RecipeStreamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recipe_stream,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecipeStreamAdapter.ViewHolder holder, final int position) {
        final Recipe latestRecipe = items.get(position);
        holder.username.setText(latestRecipe.getUser().getUsername());
        holder.time.setText(latestRecipe.getCreated_at().toString());
        holder.recipeName.setText(latestRecipe.getName());
        holder.likeCount.setText(latestRecipe.getCountlike());
        final boolean isLiked = items.get(position).isLiked();

        if(isLiked){
            holder.likeButton.setImageResource(R.drawable.likecolored);
        }else{
            holder.likeButton.setImageResource(R.drawable.like);
        }
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLiked){
                    holder.likeButton.setImageResource(R.drawable.like);
                    Intent intent = new Intent();
                    intent.putExtra("ISLIKED",false);
                    intent.putExtra("RECIPE_ID", items.get(position).getId());
                    context.sendBroadcast(intent);
                }else {
                    holder.likeButton.setImageResource(R.drawable.likecolored);
                    Intent intent = new Intent();
                    intent.putExtra("ISLIKED",true);
                    intent.putExtra("RECIPE_ID", items.get(position).getId());
                    context.sendBroadcast(intent);
                }

            }
        });
        holder.showRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               navigate.streamShowRecipe(latestRecipe.getId());
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

        final boolean isMyRecipe = items.get(position).getUserid() == userId;
        holder.showOptionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                final MenuInflater inflater = popup.getMenuInflater();
                if(isMyRecipe){
                    inflater.inflate(R.menu.edit_post, popup.getMenu());
                }else{
                    inflater.inflate(R.menu.post, popup.getMenu());
                }


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent = new Intent();
                        intent.putExtra("RECIPE_ID",items.get(position).getId());
                        switch (menuItem.getItemId()) {
                            case R.id.edit:
                                intent.putExtra("POST","EDIT");
                                context.sendBroadcast(intent);
                                return true;

                            case R.id.delete:
                                intent.putExtra("POST","DELETE");
                                context.sendBroadcast(intent);
                                return true;

                            case R.id.hide:
                                intent.putExtra("POST","HIDE");
                                context.sendBroadcast(intent);
                                return true;

                            case R.id.unfollowPeople :
                                intent.putExtra("POST","UNFOLLOW_PEOPLE");
                                context.sendBroadcast(intent);
                                return true;

                            case R.id.unfollowPost:
                                intent.putExtra("POST","UNFOLLOW_POST");
                                context.sendBroadcast(intent);
                                return true;

                            default:
                                return false;
                        }
                    }
                });

                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }




}
