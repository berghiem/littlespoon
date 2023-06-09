package ruby.com.littlespoon.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ruby.com.littlespoon.Navigate;
import ruby.com.littlespoon.R;
import ruby.com.littlespoon.adapter.AnnouncementAdapter;
import ruby.com.littlespoon.adapter.CategoryAdapter;
import ruby.com.littlespoon.adapter.EditorPickAdapter;
import ruby.com.littlespoon.adapter.FoodAdapter;
import ruby.com.littlespoon.adapter.RecipeStreamAdapter;
import ruby.com.littlespoon.api.call.response.EditorPick;
import ruby.com.littlespoon.model.AgeCategory;
import ruby.com.littlespoon.model.EditorsPick;
import ruby.com.littlespoon.model.OnUpdate;
import ruby.com.littlespoon.model.ProcessTypeCategory;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.room.db.BabySpoonViewModel;

/**
 * Created by Ruby on 4/23/2018.
 */



public class HomeView extends android.support.v4.app.Fragment  {

    private RecyclerView announcementRecycle;
    private RecyclerView ageCategoryRecycle;
    private RecyclerView foodCategoryRecycle;
    private RecyclerView editorPickRecycle;

    private TextInputEditText searchInput;
    private RecyclerView latestRecipeStream;

    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.LayoutManager layoutManager2;
    private RecyclerView.LayoutManager layoutManager3;
    private RecyclerView.LayoutManager layoutManager4;
    private RecyclerView.LayoutManager layoutManager5;


    private Button showMoreButton;
    private Button filterButton;
    private BabySpoonViewModel viewModel;

    private Context context;
    private CategoryAdapter categoryAdapter;
    private FoodAdapter foodTypeAdapter;
    private RecipeStreamAdapter streamAdapter;
    private EditorPickAdapter editorPickAdapter;
    private Navigate navigate;
    private String token;
    private OnUpdate update;


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = context;
        this.navigate = (Navigate) context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home);


        announcementRecycle = (RecyclerView) view.findViewById(R.id.announcement);
        ageCategoryRecycle = (RecyclerView) view.findViewById(R.id.category);
        foodCategoryRecycle = (RecyclerView) view.findViewById(R.id.fcategory);
        editorPickRecycle = (RecyclerView) view.findViewById(R.id.editorPick);
        searchInput = (TextInputEditText) view.findViewById(R.id.search);
        latestRecipeStream = (RecyclerView) view.findViewById(R.id.latestRecipe);
        showMoreButton = view.findViewById(R.id.showmore);


        layoutManager1 =  new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false);
        layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false);
        layoutManager3 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false);
        layoutManager4 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false);
        layoutManager5 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false);


        AnnouncementAdapter announcementAdapter = new AnnouncementAdapter(context);
        announcementRecycle.setLayoutManager(layoutManager1);
        announcementRecycle.setAdapter(announcementAdapter);

        categoryAdapter = new CategoryAdapter( context, navigate, false);
        ageCategoryRecycle.setLayoutManager(layoutManager2);
        ageCategoryRecycle.setAdapter(categoryAdapter);

         foodTypeAdapter =  new FoodAdapter(context, navigate, false);
        foodCategoryRecycle.setLayoutManager(layoutManager4);
        foodCategoryRecycle.setAdapter(foodTypeAdapter);

         streamAdapter = new RecipeStreamAdapter(context,navigate );
        latestRecipeStream.setLayoutManager(layoutManager3);
        latestRecipeStream.setAdapter(streamAdapter);

         editorPickAdapter = new EditorPickAdapter(context, navigate);
        editorPickRecycle.setLayoutManager(layoutManager5);
        editorPickRecycle.setAdapter(editorPickAdapter);

        filterButton = (Button) view.findViewById(R.id.filter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate.showRecipe(searchInput.getText().toString());
            }
        });
        SharedPreferences sharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        this.token = sharedPref.getString(getString(R.string.token), "");



        viewModel= ViewModelProviders.of(this).get(BabySpoonViewModel.class);
        viewModel.getLatestRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
               streamAdapter.setItems(recipes);
            }
        });

        viewModel.getAges().observe(this, new Observer<List<AgeCategory>>() {
            @Override
            public void onChanged(@Nullable List<AgeCategory> categories) {
                categoryAdapter.setItems(categories);
            }
        });

        viewModel.getFoods().observe(this, new Observer<List<ProcessTypeCategory>>() {
            @Override
            public void onChanged(@Nullable List<ProcessTypeCategory> processTypeCategories) {
                foodTypeAdapter.setItems(processTypeCategories);
            }
        });

        viewModel.getOnUpdate().observe(this, new Observer<List<OnUpdate>>() {
            @Override
            public void onChanged(@Nullable List<OnUpdate> onUpdates) {
                if(onUpdates != null && onUpdates.size() > 0){
                    update = onUpdates.get(0);
                    if (!token.isEmpty()) {

                        if(update.isAge()){
                            viewModel.loadAgeCategory(token);
                        }

                        if(update.isAnnounce()){
                            viewModel.loadAnnouncement(token);
                        }
                        if(update.isEditorPick()){
                            viewModel.loadEditorPick(token);
                        }
                        if(update.isProcess()){
                            viewModel.loadFoodProcessType(token);
                        }
                        if(update.isRecipe()){
                            viewModel.loadLatestRecipe(token);
                        }

                        if(update.isUser()){
                          //  viewModel.loadUser(token, id);
                        }



                    }



                }else {

                }
            }
        });


        viewModel.getEditorPicks().observe(this, new Observer<List<EditorsPick>>() {
            @Override
            public void onChanged(@Nullable List<EditorsPick> e) {
                List<EditorsPick> editorsPicks = e;
                List<EditorPick> editorPickFulls = new ArrayList<>();

                if(editorsPicks != null && editorsPicks.size() > 0){

                    for(EditorsPick ep : editorsPicks){
                        EditorPick editorPick = new EditorPick();
                        editorPick.setId(ep.getId());
                        int recipeId = ep.getRecipeId();
                        List<Recipe> result = viewModel.getRecipeById(recipeId);
                        if(result !=null && result.size() > 0){
                            editorPick.setRecipe(result.get(0));
                        }
                        editorPick.setChoosedDate(ep.getChoosedDate());
                        editorPick.setTag(ep.getTag());
                        editorPickFulls.add(editorPick);

                    }
                }
                editorPickAdapter.setItems(editorPickFulls);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        this.token = sharedPref.getString(getString(R.string.token), "");

        if (!token.isEmpty()) {

            viewModel.loadUpdate(token);

        }else {
            token= "abc";
            viewModel.loadLatestRecipe(token);
            viewModel.loadAgeCategory(token);
            viewModel.loadFoodProcessType(token);
        }
    }


}
