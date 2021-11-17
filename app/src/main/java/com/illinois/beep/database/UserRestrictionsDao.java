package com.illinois.beep.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserRestrictionsDao {

    @Query("SELECT restriction FROM UserRestriction WHERE personName LIKE :name")
    List<String> getRestrictions(String name);

    @Query("SELECT favorite FROM userrestriction WHERE personName =:personName AND restriction =:restriction")
    int isFavorite(String personName, String restriction);

    @Query("SELECT * FROM userrestriction WHERE personName LIKE :name AND restriction LIKE :restriction LIMIT 1")
    UserRestriction getObject(String name, String restriction);

    @Query("UPDATE userrestriction SET favorite = 1 WHERE personName =:personName AND restriction =:restriction")
    void favorite(String personName, String restriction);

    @Query("UPDATE userrestriction SET favorite = 0 WHERE personName =:personName AND restriction =:restriction")
    void unfavorite(String personName, String restriction);

    @Insert
    void insertOne(UserRestriction userRestriction);

    @Delete
    void delete(UserRestriction userRestriction);

    @Query("DELETE FROM userrestriction WHERE personName =:personName AND restriction =:restriction")
    void delete(String personName, String restriction);

}
