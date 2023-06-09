package ruby.com.littlespoon.view;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import ruby.com.littlespoon.R;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.room.db.ShareViewModel;

/**
 * Created by Ruby on 2/11/2018.
 */

public class RecipeView extends android.support.v4.app.Fragment implements View.OnClickListener {

    private LinearLayout addComment1;
    private ImageView addComment2;
    private ImageView recipeImage;
    private TextView recipeName;
    private TextView username;
    private ImageView likeImage;
    private TextView countLike;
    private TextView countComment;
    private TextView share;
    private TextView age;
    private TextView process;
    private TextView dish;

    private RecyclerView ingredientList;
    private RecyclerView directionList;
    private RecyclerView commentList;

    private ShareViewModel viewModel;
    private Context context;
    private ImageView showOptionImage;
    private Dialog dialog;
    private PopupMenu popup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe,
                container, false);
        context = this.getContext();

        addComment1 = (LinearLayout) view.findViewById(R.id.addcomment1);
        addComment2 = (ImageView) view.findViewById(R.id.addComment2);
        viewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        showOptionImage = (ImageView) view.findViewById(R.id.option);

        recipeImage = (ImageView) view.findViewById(R.id.recipeImage);
        recipeName = (TextView) view.findViewById(R.id.recipeName);
        username = (TextView) view.findViewById(R.id.username);
        likeImage = (ImageView) view.findViewById(R.id.likeImage);
        countLike = (TextView) view.findViewById(R.id.countlike);
        countComment = (TextView) view.findViewById(R.id.countComment);
        share = (TextView) view.findViewById(R.id.share);
        age = (TextView) view.findViewById(R.id.age);
        process = (TextView) view.findViewById(R.id.process);
        dish = (TextView) view.findViewById(R.id.dish);

        ingredientList = (RecyclerView) view.findViewById(R.id.ingredientList);
        directionList = (RecyclerView) view.findViewById(R.id.directionList);
        commentList = (RecyclerView) view.findViewById(R.id.commentList);


        dialog = new Dialog(context);
        dialog.setContentView(R.layout.comment);
        final TextInputEditText comment = dialog.findViewById(R.id.commentByMe);
        Button sendComment = dialog.findViewById(R.id.sendComment);
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //send comment
                String text = comment.getText().toString();

                dialog.dismiss();
            }
        });
        popup = new PopupMenu(context, view);
        viewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        viewModel.getSelected().observe(this, item -> {
                setRecipe(item);
        });


        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        showOptionImage.setOnClickListener(this);
        addComment1.setOnClickListener(this);
        addComment2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addcomment1:
                dialog.show();
                break;
            case R.id.addComment2:
                dialog.show();
                break;
            case R.id.option:
               popup.show();
               break;
         }
    }

    public void setRecipe(Recipe recipe){

       // recipeImage = (ImageView) view.findViewById(R.id.recipeImage);
        recipeName.setText(recipe.getName());
        username.setText(recipe.getUser().getUsername());
        if(recipe.isLiked()){
            likeImage.setImageResource(R.drawable.likecolored);
        }else {
            likeImage.setImageResource(R.drawable.like);
        }
        countLike.setText(recipe.getCountlike()+"");
   //     countComment.setText();
    //    share = (TextView) view.findViewById(R.id.share);
        age.setText(recipe.getAgeCategory().getName());
        process.setText(recipe.getProcessTypeCategory().getName());
        dish.setText(recipe.getDishCategory().getName());

        final MenuInflater inflater = popup.getMenuInflater();
        boolean isMyRecipe = recipe.isLiked();
        if (isMyRecipe) {
            inflater.inflate(R.menu.edit_post, popup.getMenu());
        } else {
            inflater.inflate(R.menu.post, popup.getMenu());
        }


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent();
                intent.putExtra("RECIPE_ID", recipe.getId());
                switch (menuItem.getItemId()) {
                    case R.id.edit:
                        intent.putExtra("POST", "EDIT");
                        context.sendBroadcast(intent);
                        return true;

                    case R.id.delete:
                        intent.putExtra("POST", "DELETE");
                        context.sendBroadcast(intent);
                        return true;

                    case R.id.hide:
                        intent.putExtra("POST", "HIDE");
                        context.sendBroadcast(intent);
                        return true;

                    case R.id.unfollowPeople:
                        intent.putExtra("POST", "UNFOLLOW_PEOPLE");
                        context.sendBroadcast(intent);
                        return true;

                    case R.id.unfollowPost:
                        intent.putExtra("POST", "UNFOLLOW_POST");
                        context.sendBroadcast(intent);
                        return true;

                    default:
                        return false;
                }
            }
        });


    }
}
