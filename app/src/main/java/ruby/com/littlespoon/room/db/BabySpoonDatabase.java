package ruby.com.littlespoon.room.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import ruby.com.littlespoon.dao.AgeCategoryDao;
import ruby.com.littlespoon.dao.AnnouncementDao;
import ruby.com.littlespoon.dao.DishCategoryDao;
import ruby.com.littlespoon.dao.EditorPickDao;
import ruby.com.littlespoon.dao.EditorPickJoinRecipeDao;
import ruby.com.littlespoon.dao.OnUpdateDao;
import ruby.com.littlespoon.dao.ProcessTypeCategoryDao;
import ruby.com.littlespoon.dao.RecipeCommentDao;
import ruby.com.littlespoon.dao.RecipeDao;
import ruby.com.littlespoon.dao.RecipeIngredientsDao;
import ruby.com.littlespoon.dao.RecipeLikeDao;
import ruby.com.littlespoon.dao.RecipeStepDao;
import ruby.com.littlespoon.dao.UserDao;
import ruby.com.littlespoon.model.AgeCategory;
import ruby.com.littlespoon.model.Announcement;
import ruby.com.littlespoon.model.DishCategory;
import ruby.com.littlespoon.model.EditorsPick;
import ruby.com.littlespoon.model.OnUpdate;
import ruby.com.littlespoon.model.ProcessTypeCategory;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.RecipeComments;
import ruby.com.littlespoon.model.RecipeDish;
import ruby.com.littlespoon.model.RecipeIngredients;
import ruby.com.littlespoon.model.RecipeLikes;
import ruby.com.littlespoon.model.RecipeStep;
import ruby.com.littlespoon.model.User;


@Database(entities = {User.class, Recipe.class, RecipeIngredients.class,
        RecipeLikes.class, RecipeStep.class, AgeCategory.class,
        EditorsPick.class, ProcessTypeCategory.class, Announcement.class,
        DishCategory.class , RecipeDish.class, RecipeComments.class, OnUpdate.class
        }, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class BabySpoonDatabase extends RoomDatabase {

        public abstract UserDao userDao();
        public abstract RecipeDao recipeDao();
        public abstract RecipeIngredientsDao recipeIngredientsDao();
        public abstract RecipeStepDao recipeStepDao();
        public abstract RecipeLikeDao recipeLikeDao();
        public abstract RecipeCommentDao recipeCommentDao();
        public abstract DishCategoryDao dishCategoryDao();
        public abstract AgeCategoryDao ageCategoryDao();
        public abstract EditorPickDao editorsPickDao();
        public abstract AnnouncementDao announcementDao();
        public abstract ProcessTypeCategoryDao processTypeCategoryDao();
        public abstract EditorPickJoinRecipeDao recipeWithEditorPickDao();
        public abstract OnUpdateDao onUpdateDao();

        private static BabySpoonDatabase INSTANCE;


        static BabySpoonDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (BabySpoonDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                BabySpoonDatabase.class, "babyspoon_db")
                                // Wipes and rebuilds instead of migrating if no Migration object.
                                // Migration is not part of this codelab.
                                .fallbackToDestructiveMigration()
                                .addCallback(sRoomDatabaseCallback)
                                .build();

                    }
                }
            }
            return INSTANCE;
        }
        private static RoomDatabase.Callback sRoomDatabaseCallback =
                new RoomDatabase.Callback(){

                    @Override
                    public void onOpen (@NonNull SupportSQLiteDatabase db){
                        super.onOpen(db);
                        new PopulateDbAsync(INSTANCE).execute();
                    }
                };

        private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

            private final UserDao mDao;
            private final RecipeDao recipeDao;
            private final RecipeLikeDao recipeLikeDao;
            private final RecipeStepDao recipeStepDao;
            private final RecipeCommentDao recipeCommentDao;
            private final RecipeIngredientsDao recipeIngredientsDao;
            private final EditorPickDao editorPickDao;
            private final AgeCategoryDao ageCategoryDao;
            private final DishCategoryDao dishCategoryDao;
            private final ProcessTypeCategoryDao processTypeCategoryDao;
            private final AnnouncementDao announcementDao;
            private final EditorPickJoinRecipeDao recipeWithEditorPickDao;
            private final OnUpdateDao onUpdateDao;

            PopulateDbAsync(BabySpoonDatabase db) {
                mDao = db.userDao();
                recipeDao = db.recipeDao();
                recipeLikeDao = db.recipeLikeDao();
                recipeStepDao = db.recipeStepDao();
                recipeIngredientsDao = db.recipeIngredientsDao();
                editorPickDao = db.editorsPickDao();
                ageCategoryDao = db.ageCategoryDao();
                processTypeCategoryDao = db.processTypeCategoryDao();
                announcementDao = db.announcementDao();
                recipeWithEditorPickDao = db.recipeWithEditorPickDao();
                onUpdateDao = db.onUpdateDao();
                dishCategoryDao = db.dishCategoryDao();
                recipeCommentDao = db.recipeCommentDao();

            }

            @Override
            protected Void doInBackground(final Void... params) {
                mDao.deleteAll();
                //recipeDao.deleteAll();
                //recipeLikeDao.deleteAll();

                return null;
            }
        }

    }

