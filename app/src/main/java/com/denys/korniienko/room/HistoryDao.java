package com.denys.korniienko.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface  HistoryDao {

    @Query("SELECT * FROM history")
    Flowable<List<History>> getAllHistory();

    @Query("SELECT * FROM history WHERE id = :id")
    History getHistoryById(int id);

    @Insert
    void inserHistory(History user);

    @Update
    void updateHistory(History user);

    @Delete
    void deleteHistory(History user);

    @Query("DELETE FROM history")
    void deleteAllUsers();


}
