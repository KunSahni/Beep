package com.illinois.beep.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    /**
     * @return all users contained in databases
     */
    @Query("SELECT * FROM user")
    List<User> getAll();

    /**
     * @param name name of the person to lookup
     * @return User object based on name parameter
     */
    @Query("SELECT * FROM user WHERE personName LIKE :name LIMIT 1")
    User findByName(String name);

    /**
     * @param users collection of users to be inserted
     */
    @Insert
    void insertAll(User... users);

    /**
     * @param user user which user wants to insert
     */
    @Insert
    void insertOne(User user);

    /**
     * @param user user which user wants to delete
     */
    @Delete
    void delete(User user);
}
