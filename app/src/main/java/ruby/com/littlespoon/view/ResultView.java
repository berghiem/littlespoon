package ruby.com.littlespoon.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import ruby.com.littlespoon.Navigate;
import ruby.com.littlespoon.R;
import ruby.com.littlespoon.adapter.CategoryAdapter;
import ruby.com.littlespoon.adapter.FoodAdapter;
import ruby.com.littlespoon.adapter.RecipeStreamAdapter;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.room.db.BabySpoonViewModel;

public class ResultView extends android.support.v4.app.Fragment  {


    private RecyclerView ageCategoryRecycle;
    private RecyclerView foodCategoryRecycle;
    private RecyclerView resultrecycle;


    private TextInputEditText searchInput;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.LayoutManager layoutManager2;
    private RecyclerView.LayoutManager layoutManager3;

    private Navigate navigate;
    private Button showMoreButton;
    private BabySpoonViewModel viewModel;

    private Context context;
    private Navigate clickImageCallBack;
    private CategoryAdapter categoryAdapter;
    private FoodAdapter foodTypeAdapter;
    private RecipeStreamAdapter resultAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.navigate = (Navigate)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.result,
                container, false);

        ageCategoryRecycle = (RecyclerView) view.findViewById(R.id.agecategory);
        foodCategoryRecycle = (RecyclerView) view.findViewById(R.id.fcategory);
        searchInput = (TextInputEditText) view.findViewById(R.id.search);
        resultrecycle = (RecyclerView) view.findViewById(R.id.result);
        showMoreButton = view.findViewById(R.id.showmore);


        layoutManager1 =  new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false);
        layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false);
        layoutManager3 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false);

        resultAdapter = new RecipeStreamAdapter(context, navigate);
        resultrecycle.setLayoutManager(layoutManager1);
        resultrecycle.setAdapter(resultAdapter);

        categoryAdapter = new CategoryAdapter( context, navigate, true);
        ageCategoryRecycle.setLayoutManager(layoutManager2);
        ageCategoryRecycle.setAdapter(categoryAdapter);

        foodTypeAdapter =  new FoodAdapter(context,navigate, true);
        foodCategoryRecycle.setLayoutManager(layoutManager3);
        foodCategoryRecycle.setAdapter(foodTypeAdapter);

        viewModel= ViewModelProviders.of(this).get(BabySpoonViewModel.class);



        return view;
    }

    public void showRecipeByAge(int ageId) {

        viewModel.getRecipeByAge(ageId).observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                resultAdapter.setItems(recipes);
                resultAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showResultx(int id, int type, String keyword){
        if(type == 1) {
            viewModel.getRecipeByAge(id).observe(this, new Observer<List<Recipe>>() {
                @Override
                public void onChanged(@Nullable List<Recipe> recipes) {
                    resultAdapter.setItems(recipes);
                    resultAdapter.notifyDataSetChanged();
                }
            });
        }

        if(type == 2){
            viewModel.getRecipByFoodProcess(id).observe(this, new Observer<List<Recipe>>() {
                @Override
                public void onChanged(@Nullable List<Recipe> recipes) {
                    resultAdapter.setItems(recipes);
                    resultAdapter.notifyDataSetChanged();
                }
            });
        }

        if(type == 3){
         /*   viewModel.getRecipByKeyword(keyword).observe(this, new Observer<List<Recipe>>() {
                @Override
                public void onChanged(@Nullable List<Recipe> recipes) {
                    resultAdapter.setItems(recipes);
                    resultAdapter.notifyDataSetChanged();
                }
            });*/
        }
    }

    public void showResult(int age, int  foodType, String keyword){


        if(age == 0 && foodType == 0){
         /*   viewModel.getRecipByKeyword(keyword).observe(this, new Observer<List<Recipe>>() {
                @Override
                public void onChanged(@Nullable List<Recipe> recipes) {
                    resultAdapter.setItems(recipes);
                    resultAdapter.notifyDataSetChanged();
                }
            });*/
        } else if(age == 0 && foodType != 0) {
            viewModel.getRecipByFoodProcess(foodType).observe(this, new Observer<List<Recipe>>() {
                @Override
                public void onChanged(@Nullable List<Recipe> recipes) {
                    resultAdapter.setItems(recipes);
                    resultAdapter.notifyDataSetChanged();
                }
            });
        }else if(age != 0 && foodType == 0){
            viewModel.getRecipeByAgeAndKeyword( keyword, age);
        }else {
            viewModel.getRecipeByAll(age, foodType, keyword);
        }


    }


}
