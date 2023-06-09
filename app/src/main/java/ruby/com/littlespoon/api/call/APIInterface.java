package ruby.com.littlespoon.api.call;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ruby.com.littlespoon.api.call.request.Login;
import ruby.com.littlespoon.api.call.request.Register;
import ruby.com.littlespoon.api.call.response.EditorPick;
import ruby.com.littlespoon.api.call.response.OnUpdate;
import ruby.com.littlespoon.api.call.response.Sign;
import ruby.com.littlespoon.model.AgeCategory;
import ruby.com.littlespoon.model.Announcement;
import ruby.com.littlespoon.model.ProcessTypeCategory;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.User;

public interface APIInterface {

    @POST("auth/signup")
    Call<Sign> register(@Body Register user);

    @POST
    Call<Sign> login(@Body Login login);

    @GET
    Call<ruby.com.littlespoon.model.OnUpdate> getOnUpdate(@Query("token") String token);

    @GET
    Call<List<User>> getUserList(@Query("token") String token);


    @GET
    Call<List<Recipe>> getLatestRecipe(@Query("token") String token);

    @GET
    Call<List<AgeCategory>> getAgeCategory(@Query("token") String token);

    @GET
    Call<List<ProcessTypeCategory>> getProcessTypeCategory(@Query("token") String token);


    @GET
    Call<List<EditorPick>> getEditorPick(@Query("token") String token);


    @GET
    Call<List<Announcement>> getAnnouncement(@Query("token") String token);

    @GET
    Call<List<Recipe>> getRecipeByKeyword(@Query("token") String token, @Query("keyword") String keyword);

    @GET
    Call<List<Recipe>> getRecipeByAgeAndKeyword(@Query("token") String token, @Query("age") int age, @Query("keyword") String keyword);

    @GET
    Call<List<Recipe>> getRecipeByFoodPAndKeyword(@Query("token") String token, @Query("foodp") int foodp, @Query("keyword") String keyword);

    @GET
    Call<List<Recipe>> getRecipeByAll(@Query("token") String token, @Query("age") int age, @Query("foodP") int foodp, @Query("keyword") String keyword);

    @GET
    Call<User> getUser(@Query("token") String token,@Query("token") int id);






}