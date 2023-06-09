package ruby.com.littlespoon.room.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Query;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import ruby.com.littlespoon.api.call.response.EditorPick;
import ruby.com.littlespoon.api.call.response.OnUpdate;
import ruby.com.littlespoon.dao.EditorPickJoinRecipeDao;
import ruby.com.littlespoon.model.AgeCategory;
import ruby.com.littlespoon.model.Announcement;
import ruby.com.littlespoon.model.EditorsPick;
import ruby.com.littlespoon.model.ProcessTypeCategory;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.User;

public class BabySpoonViewModel extends AndroidViewModel {

    private BabySpoonRepository repository;
    private LiveData<List<User>> userList;
    private LiveData<List<Recipe>> latestRecipes;
    private LiveData<List<AgeCategory>> ages;
    private LiveData<List<ProcessTypeCategory>> foods;
    private LiveData<List<EditorsPick>> editorPicks;
    private LiveData<List<Announcement>> announcements;
    private List<EditorPickJoinRecipeDao> recipeWithEditorPick;



    public BabySpoonViewModel(@NonNull Application application) {
        super(application);
        repository = new BabySpoonRepository(application);
        userList = repository.getUserlist();
        latestRecipes = repository.getLatestRecipe();
        ages = repository.getAges();
        foods = repository.getFoods();
        editorPicks = repository.getEditorPick();
        announcements = repository.getAnnouncements();

    }


    public LiveData<List<User>> getUserList() {
        return userList;
    }

    public LiveData<List<Recipe>> getLatestRecipes() {
        return latestRecipes;
    }

    public LiveData<List<AgeCategory>> getAges() {
        return ages;
    }

    public LiveData<List<ProcessTypeCategory>> getFoods() {
        return foods;
    }

    public LiveData<List<EditorsPick>> getEditorPicks() {
        return editorPicks;
    }

    public LiveData<List<Announcement>> getAnnouncements() {
        return announcements;
    }

    public List<Recipe> getRecipeById(int recipeId){
        List<Recipe> recipes = repository.getRecipeById(recipeId);
        return recipes;
    }

    public LiveData<List<Recipe>> getRecipeByAge(int ageId){
        LiveData<List<Recipe>> recipes = repository.getRecipeByAge(ageId);
        return recipes;
    }

    public LiveData<List<Recipe>> getRecipByFoodProcess(int foodprocessId){
        LiveData<List<Recipe>> recipes = repository.getRecipeByFoodProcess(foodprocessId);
        return recipes;
    }


    public LiveData<List<Recipe>> getRecipByKeyword(String token, String keyword){
        LiveData<List<Recipe>> recipes = repository.getRecipeByKeyword(token, keyword);
        return recipes;
    }

    public LiveData<Boolean> isRecipeLikedByMe(String token,int userId, int recipeId){
      //  LiveData<Boolean> isLiked = repository.isLikedByMe(token, userId,recipeId);
        return null;
    }

    public LiveData<List<Recipe>> getRecipeByFoodAndKeyword(String keyword , int proccesId)
    {
        LiveData<List<Recipe>> recipes = repository.getRecipeByFoodAndKeyword(keyword,proccesId);
        return recipes;
    }
    public LiveData<List<Recipe>> getRecipeByAgeAndKeyword(String keyword ,  int ageId){
        LiveData<List<Recipe>> recipes = repository.getRecipeByAgeAndKeyword(keyword, ageId);
        return recipes;
    }

    public LiveData<List<Recipe>> getRecipeByAll(int age, int foodType,String keyword){
        LiveData<List<Recipe>> recipes = repository.getRecipeByAll(age, foodType ,keyword);
        return recipes;
    }

    public void loadUser(String token, int id) {
        repository.loadUser(token, id);
    }

    public void loadLatestRecipe(String token){
            repository.loadLatestRecipe(token);
    }


    public void loadAgeCategory(String token) {
        repository.loadAgeCategory(token);
    }

    public void loadProcessType(String token){
        repository.loadProcessType(token);
    }

    public void loadFoodProcessType(String token) {
        repository.loadProcessType(token);
    }

    public void loadEditorPick(String token) {
        repository.loadEditorPick(token);
    }

    public void loadAnnouncement(String token) {
        repository.loadAnnouncement(token);
    }

    public LiveData<List<ruby.com.littlespoon.model.OnUpdate>> getOnUpdate(){
        LiveData<List<ruby.com.littlespoon.model.OnUpdate>> updates =  repository.getOnUpdates();
        return updates;
    }

    public void loadUpdate(String token){
        repository.update(token);
    }

}
