package com.illinois.beep.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public String personName;

    public User(@NonNull String personName) {
        this.personName = personName;
    }
}