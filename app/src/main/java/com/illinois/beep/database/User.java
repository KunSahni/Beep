package com.illinois.beep.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class represent a persona which can be added to the app
 */
@Entity
public class User {
    @PrimaryKey
    public String personName;

    public User(@NonNull String personName) {
        this.personName = personName;
    }
}