package com.illinois.beep.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserRestrictionsDao {

    /**
     * @return all user restrictions contained in database
     */
    @Query("SELECT * FROM UserRestriction")
    LiveData<List<UserRestriction>> getAll();

    /**
     * @return all users contained in database
     */
    @Query("SELECT DISTINCT personName FROM UserRestriction")
    List<String> getAllUsers();

    /**
     * @param personName name of person whose restrictions you want to lookup
     * @return list of restrictions for person with the passed name
     */
    @Query("SELECT restriction FROM UserRestriction WHERE personName LIKE :personName")
    List<String> getRestrictions(String personName);

    /**
     * @param personName name of person whose restrictions you want to lookup
     * @return list of restrictions for person with the passed name
     */
    @Query("SELECT * FROM UserRestriction WHERE personName LIKE :personName")
    List<UserRestriction> getRestrictionsObjects(String personName);

    /**
     * @param personName name of person whose restrictions you want to lookup
     * @return list of restrictions for person with the passed name
     */
    @Query("SELECT * FROM UserRestriction WHERE personName LIKE :personName")
    LiveData<List<UserRestriction>> getUserRestrictions(String personName);

    /**
     * @param personName name of person which you want to lookup
     * @param restriction restriction which you want to lookup
     * @return true if is favorite restriction, false otherwise
     */
    @Query("SELECT favorite FROM userrestriction WHERE personName =:personName AND restriction =:restriction")
    int isFavorite(String personName, String restriction);

    /**
     * @param personName name of person which you want to lookup
     * @param restriction restriction which you want to lookup
     * @return true if is favorite restriction, false otherwise
     */
    @Query("SELECT * FROM userrestriction WHERE personName LIKE :personName AND restriction LIKE :restriction LIMIT 1")
    UserRestriction getUserRestriction(String personName, String restriction);

    /**
     * @param personName name of person
     * @param restriction restriction which you want to set as favorite
     */
    @Query("UPDATE userrestriction SET favorite = 1 WHERE personName =:personName AND restriction =:restriction")
    void favorite(String personName, String restriction);

    /**
     * @param personName name of person
     * @param restriction restriction which you want to remove from favorite
     */
    @Query("UPDATE userrestriction SET favorite = 0 WHERE personName =:personName AND restriction =:restriction")
    void unfavorite(String personName, String restriction);

    /**
     * @param userRestriction object which represent a user restriction and you want to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOne(UserRestriction userRestriction);

    /**
     * @param userRestriction object which represent a user restriction and you want to delete
     */
    @Delete
    void delete(UserRestriction userRestriction);

    /**
     * @param personName name of user whose restriction you want to delete
     * @param restriction restriction which you want to delete
     */
    @Query("DELETE FROM userrestriction WHERE personName =:personName AND restriction =:restriction")
    void delete(String personName, String restriction);

    /**
     * @param personName name of user who you want to delete
     */
    @Query("DELETE FROM userrestriction WHERE personName =:personName")
    void delete(String personName);

}
