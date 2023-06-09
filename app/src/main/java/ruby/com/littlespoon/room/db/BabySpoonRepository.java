package ruby.com.littlespoon.room.db;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ruby.com.littlespoon.api.call.APIInterface;
import ruby.com.littlespoon.api.call.RetrofitClient;
import ruby.com.littlespoon.api.call.response.EditorPick;
import ruby.com.littlespoon.dao.AgeCategoryDao;
import ruby.com.littlespoon.dao.AnnouncementDao;
import ruby.com.littlespoon.dao.DishCategoryDao;
import ruby.com.littlespoon.dao.EditorPickDao;
import ruby.com.littlespoon.dao.EditorPickJoinRecipeDao;
import ruby.com.littlespoon.dao.OnUpdateDao;
import ruby.com.littlespoon.dao.ProcessTypeCategoryDao;
import ruby.com.littlespoon.dao.RecipeCommentDao;
import ruby.com.littlespoon.dao.RecipeDao;
import ruby.com.littlespoon.dao.RecipeLikeDao;
import ruby.com.littlespoon.dao.UserDao;
import ruby.com.littlespoon.model.AgeCategory;
import ruby.com.littlespoon.model.Announcement;
import ruby.com.littlespoon.model.EditorsPick;
import ruby.com.littlespoon.model.OnUpdate;
import ruby.com.littlespoon.model.ProcessTypeCategory;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.RecipeComments;
import ruby.com.littlespoon.model.RecipeLikes;
import ruby.com.littlespoon.model.RecipeWithEditorPick;
import ruby.com.littlespoon.model.User;

public class BabySpoonRepository implements TaskCompleted{

    private UserDao userDao;
    private RecipeDao recipeDao;
    private AgeCategoryDao ageCategoryDao;
    private ProcessTypeCategoryDao foodCategoryDao;
    private EditorPickDao editorPickDao;
    private AnnouncementDao announcementDao;
    private EditorPickJoinRecipeDao recipeEditorPickDao;
    private OnUpdateDao onUpdateDao;
    private RecipeLikeDao likeDao;
    private DishCategoryDao dishCategoryDao;
    private RecipeCommentDao recipeCommentDao;

    private LiveData<List<User>> userlist;
    private LiveData<List<Recipe>> latestRecipe;
    private LiveData<List<AgeCategory>> ages;
    private LiveData<List<ProcessTypeCategory>> foods;
    private LiveData<List<EditorsPick>> editorPicks;
    private LiveData<List<Announcement>> announcements;
    private List<RecipeWithEditorPick> recipeWithEditorPicks;
    private LiveData<List<OnUpdate>> onUpdates;

    private int userId;

    BabySpoonRepository(Application application) {
        BabySpoonDatabase db = BabySpoonDatabase.getDatabase(application);
        userDao = db.userDao();
        recipeDao = db.recipeDao();
        ageCategoryDao = db.ageCategoryDao();
        foodCategoryDao = db.processTypeCategoryDao();
        editorPickDao = db.editorsPickDao();
        announcementDao = db.announcementDao();
        recipeEditorPickDao = db.recipeWithEditorPickDao();
        onUpdateDao = db.onUpdateDao();
        likeDao = db.recipeLikeDao();
        dishCategoryDao = db.dishCategoryDao();
        recipeCommentDao = db.recipeCommentDao();


        userlist = userDao.getAllUser();
        latestRecipe = recipeDao.getLatestRecipe();
        ages = ageCategoryDao.getAgeCategory();
        foods = foodCategoryDao.getProcessTypeCategory();
        editorPicks = editorPickDao.getEditorPick();
        announcements = announcementDao.getActiveAnnouncements();
        recipeWithEditorPicks = recipeEditorPickDao.getRecipeWithEditorPick();
        onUpdates = onUpdateDao.getOnUpdate();

        SharedPreferences sharedPreferences = application.getSharedPreferences("pref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userid", 0);


    }

    public LiveData<List<User>> getUserlist(){
            LiveData<List<User>> userList = userDao.getAllUser();
            userList = Transformations.map(userList, new Function<List<User>, List<User>>() {
                @Override
                public List<User> apply(List<User> input) {
                    for(User user : input){
                        user.setMyRecipes(userDao.getRecipeFromUser(user.getId()));
                        user.setMyInterest(likeDao.getRecipeLikesFromUser(user.getId()));
                    }
                    return input;
                }
            });
            return userList;
    }

    public LiveData<User> getUser(int id){
        LiveData<User> user = userDao.getUser(id);
        user = Transformations.map(user, new Function<User, User>() {
            @Override
            public User apply(User input) {
                input.setMyRecipes(userDao.getRecipeFromUser(input.getId()));
                input.setMyInterest(likeDao.getRecipeLikesFromUser(input.getId()));
                return input;
            }
        });

        return user;
    }

    public LiveData<Recipe> getRecipe(int id){
        LiveData<Recipe> recipe = recipeDao.getRecipeById(id);
        final RecipeLikes recipeLikes = likeDao.getRecipeLikes(id, userId);
        recipe = Transformations.map(recipe, new Function<Recipe, Recipe>() {
            @Override
            public Recipe apply(Recipe input) {
                input.setUser(recipeDao.getUser(input.getUserid()));
                input.setAgeCategory(recipeDao.getAgeCategory(input.getAgeCategoryId()));
                input.setProcessTypeCategory(recipeDao.getProcessTypeCategory(input.getProcesstypeId()));
                input.setDishCategory(dishCategoryDao.getDishCategoryById(input.getDishCategoryId()));
                //input.setUserlikes(recipeDao.getUs);
                if(recipeLikes != null){
                    input.setLiked(true);
                }
                return input;
            }
        });

        return recipe;
    }



    public LiveData<List<Recipe>> getLatestRecipe() {
        LiveData<List<Recipe>> recipeList = recipeDao.getLatestRecipe();
        recipeList = Transformations.map(recipeList, new Function<List<Recipe>, List<Recipe>>() {
            @Override
            public List<Recipe> apply(List<Recipe> input) {
                for(Recipe recipe : input){
                    recipe.setUser(recipeDao.getUser(recipe.getUserid()));
                    recipe.setAgeCategory(recipeDao.getAgeCategory(recipe.getAgeCategoryId()));
                    recipe.setProcessTypeCategory(recipeDao.getProcessTypeCategory(recipe.getProcesstypeId()));
                    int id = recipe.getId();

                    recipe.setDishCategory(dishCategoryDao.getDishCategoryById(recipe.getDishCategoryId()));

                }
                return input;
            }
        });

        List<Recipe> likedRecipe = likeDao.getRecipeLikesFromUser(userId);
             for(Recipe recipe : likedRecipe){
                 recipe.setLiked(true);
             }
        return recipeList;

    }




    public void saveRecipe(Recipe recipe){
        recipeDao.insertRecipe(recipe);
        recipeDao.insertUser(recipe.getUser());
    }

    public void saveRecipeList(List<Recipe> recipes){
        recipeDao.insertRecipes(recipes);
        for(Recipe recipe : recipes){
            recipeDao.insertUser(recipe.getUser());
        }
    }

    public LiveData<List<OnUpdate>> getOnUpdates() {
        return onUpdates;
    }

    public LiveData<List<AgeCategory>> getAges() {
        return ages;
    }

    public LiveData<List<ProcessTypeCategory>> getFoods() {
        return foods;
    }

    public LiveData<List<EditorsPick>> getEditorPick() {


        return editorPicks;
    }

    public LiveData<List<Announcement>> getAnnouncements() {
        return announcements;
    }



    public LiveData<List<Recipe>> getRecipeByAge(int ageId){
        LiveData<List<Recipe>>  recipes =  recipeDao.getRecipeByAge(ageId);
        return recipes;
    }

    public LiveData<List<Recipe>> getRecipeByFoodProcess(int foodProcessId){
        LiveData<List<Recipe>>  recipes =  recipeDao.getRecipeByFoodCategory(foodProcessId);
        return recipes;
    }

    public LiveData<List<Recipe>> getRecipeByKeyword(String token,String keyword){
        keyword = "%"+keyword+"%";
        LiveData<List<Recipe>>  recipes =  recipeDao.getRecipeByKeyword(keyword);

        new recipeByKeyword(recipeDao, token, keyword).execute();
        return recipes;
    }

    public LiveData<List<Recipe>> getRecipeByAll(int age, int foodType,String keyword){
        keyword = "%"+keyword+"%";
        LiveData<List<Recipe>>  recipes =  recipeDao.getRecipeByAll(keyword, age, foodType);
        return recipes;
    }

    public LiveData<List<Recipe>> getRecipeByFoodAndKeyword(String keyword , int proccesId)
    {
        keyword = "%"+keyword+"%";
        LiveData<List<Recipe>>  recipes =  recipeDao.getRecipeByFoodAndKeyword(keyword, proccesId);
        return recipes;
    }
    public LiveData<List<Recipe>> getRecipeByAgeAndKeyword(String keyword ,  int ageId){
        keyword = "%"+keyword+"%";
        LiveData<List<Recipe>>  recipes =  recipeDao.getRecipeByAgeAndKeyword(keyword, ageId);
        return recipes;
    }

    public void loadLatestRecipe(String token){
        new loadLatestRecipeAsyncTask(token,recipeDao).execute();
    }

    public List<Recipe> getRecipeById(int id){
      //  List<Recipe>  recipes =  recipeDao.getRecipeById(id);
        return null;
    }

    public void insertUser(List<User> userlist){

        User[] users = userlist.toArray(new User[userlist.size()]);
        new insertUserAsyncTask(userDao).execute(users);
    }



    @Override
    public List<Recipe>  onComplete(List<Recipe> recipes) {
        return  recipes;
    }

    public void loadAgeCategory(String token) {
        new loadAgeAsyncTask(ageCategoryDao ,token).execute();
    }

    public void loadProcessType(String token) {
        new loadProcessTypeAsyncTask(foodCategoryDao, token).execute();
    }

    public void loadEditorPick(String token) {
        new loadEditorPickAsyncTask(editorPickDao, token).execute();
    }

    public void loadAnnouncement(String token) {
        new loadAnnouncementAsyncTask(announcementDao, token).execute();

    }

    public void update(String token) {
        new onUpdateAsyncTask(onUpdateDao, token ).execute();
    }

    public void loadUser(String token, int id) {
        new loadUserAsyncTask(userDao,token,id).execute();
    }



    private class loadUserAsyncTask extends AsyncTask<Void, Void,Void>{

        private UserDao userDao;
        private String token;
        private int id;

        public loadUserAsyncTask(UserDao userDao, String token, int id) {
            this.userDao = userDao;
            this.token = token;
            this.id = id;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<User> call = service.getUser(token,  id);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.body()!= null ){
                        userDao.insert(response.body());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

            return null;
        }
    }

    private class loadAnnouncementAsyncTask extends AsyncTask<Void,Void,Void>{

        private AnnouncementDao announcementDao;
        private String token;

        public loadAnnouncementAsyncTask(AnnouncementDao dao, String token){
            this.token = token;
            this.announcementDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<List<Announcement>> call = service.getAnnouncement(token);
            call.enqueue(new Callback<List<Announcement>>() {
                @Override
                public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                    if(response.body() != null && response.body().size() > 0){
                        announcementDao.insert(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Announcement>> call, Throwable t) {

                }
            });

            return null;
        }
    }

    private class loadEditorPickAsyncTask extends  AsyncTask<Void,Void,Void>{

        private EditorPickDao editorPickDao;
        private String token;

        public loadEditorPickAsyncTask(EditorPickDao editorPickDao , String token){
            this.editorPickDao = editorPickDao;
            this.token = token;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<List<EditorPick>> call = service.getEditorPick(token);
            call.enqueue(new Callback<List<EditorPick>>() {
                @Override
                public void onResponse(Call<List<EditorPick>> call, Response<List<EditorPick>> response) {
                    if(response.body() != null && response.body().size() > 0){
                       List<EditorPick> list = response.body();
                       List<EditorsPick> picks = new ArrayList<>();
                       for(EditorPick e : list){
                           EditorsPick ep = new EditorsPick();
                           ep.setId(e.getId());
                           ep.setRecipe(e.getRecipe());
                           ep.setChoosedDate(e.getChoosedDate());
                           ep.setRecipeId(e.getRecipe().getId());
                           ep.setTag(e.getTag());
                           picks.add(ep);
                       }
                    }

                }

                @Override
                public void onFailure(Call<List<EditorPick>> call, Throwable t) {

                }
            });
            return null;
        }
    }

    private class loadProcessTypeAsyncTask extends AsyncTask<Void,Void,Void>{
        private ProcessTypeCategoryDao dao;
        private String token;

        public loadProcessTypeAsyncTask(ProcessTypeCategoryDao dao , String token){
            this.dao = dao;
            this.token = token;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<List<ProcessTypeCategory>> call = service.getProcessTypeCategory(token);
            call.enqueue(new Callback<List<ProcessTypeCategory>>() {
                @Override
                public void onResponse(Call<List<ProcessTypeCategory>> call, Response<List<ProcessTypeCategory>> response) {
                    if(response.body() != null && response.body().size() > 0){
                        dao.insert(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<ProcessTypeCategory>> call, Throwable t) {

                }
            });
            return null;
        }
    }

    private class onUpdateAsyncTask extends AsyncTask<Void,Void,Void>{
        private OnUpdateDao onUpdateDao;
        private String token;

        public onUpdateAsyncTask(OnUpdateDao onUpdateDao, String token) {
            this.onUpdateDao = onUpdateDao;
            this.token = token;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<OnUpdate> call = service.getOnUpdate(token);
            call.enqueue(new Callback<OnUpdate>() {
                @Override
                public void onResponse(Call<OnUpdate> call, Response<OnUpdate> response) {
                    if(response.body()!= null ){
                        onUpdateDao.insert(response.body());
                    }
                }

                @Override
                public void onFailure(Call<OnUpdate> call, Throwable t) {

                }
            });

            return null;
        }
    }


    private class loadAgeAsyncTask extends AsyncTask<Void,Void,Void>{

        private AgeCategoryDao ageCategoryDao;
        private String token;

        public loadAgeAsyncTask(AgeCategoryDao ageCategoryDao, String token){
            this.ageCategoryDao = ageCategoryDao;
            this.token = token;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<List<AgeCategory>> call = service.getAgeCategory(token);
            call.enqueue(new Callback<List<AgeCategory>>() {
                @Override
                public void onResponse(Call<List<AgeCategory>> call, Response<List<AgeCategory>> response) {
                    if(response.body() != null && response.body().size() > 0){
                        ageCategoryDao.insert(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<AgeCategory>> call, Throwable t) {

                }
            });
            return null;
        }
    }

    private class  loadLatestRecipeAsyncTask extends AsyncTask<Void,Void,Void>{

        private String token;
        private RecipeDao recipeDao;

        public loadLatestRecipeAsyncTask(String token, RecipeDao recipeDao){
            this.token = token;
            this.recipeDao  = recipeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<List<Recipe>> call = service.getLatestRecipe(token);
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        if(response.isSuccessful() && response.body() != null){
                                List<Recipe> recipes = response.body();
                                if(recipes.size() > 0){
                                    recipeDao.deleteAll();
                                    recipeDao.insertRecipes(recipes);

                                }
                        }else {
                            Log.w("getLatestRecipe", "call response is success BUT response body is NULL");
                        }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.e("getLatestRecipe", "FAILURE while call response");

                }
            });
            return null;
        }
    }

    private class insertUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao udao;

        public insertUserAsyncTask(UserDao userDao) {
            this.udao  = userDao;
        }


        @Override
        protected Void doInBackground(User... users) {
            udao.insert(users[0]);
            return null;
        }
    }


    private class recipeByKeyword extends AsyncTask<Void, Void, List<Recipe>> {

        private RecipeDao recipeDao;
        private String token;
        private String keyword;

        public recipeByKeyword(RecipeDao recipeDao, String token, String keyword){
            this.recipeDao  = recipeDao;
            this.token = token;
            this.keyword = keyword;
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {

            final List<Recipe> result = new ArrayList<>();

            APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
            Call<List<Recipe>> call = service.getRecipeByKeyword(token, keyword);
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if(response.body() != null && response.body().size() > 0){
                      //  recipeDao.insert(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {

                }
            });
            return result;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
        }
    }



}
