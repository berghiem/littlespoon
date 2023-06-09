package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.sql.Timestamp;
import java.util.List;

import ruby.com.littlespoon.model.Announcement;

@Dao
public interface AnnouncementDao {
    @Insert
    void insert(Announcement announcement);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Announcement> announcements);

    @Query("DELETE FROM announcement")
    void deleteAll();


    @Query(value = "DELETE FROM announcement WHERE id =:id")
    void deleteById(int id);

    @Query("SELECT * from announcement  ")
    LiveData<List<Announcement>> getAnnouncements();

    @Query("SELECT * from announcement  where isActive = 1 order by createdAt desc ")
    LiveData<List<Announcement>> getActiveAnnouncements();



    @Query("UPDATE announcement SET name =:name , caption =:cap , isActive =:status , imageUrl =:img , createdAt =:dated WHERE id IN (:id)")
    void updateAnnouncement(String name, String cap, boolean status, String img, Timestamp dated, int id);
}
