package com.illinois.beep.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class represents a dietary restriction and the user associated with it
 */
@Entity(primaryKeys={"personName", "restriction"})
public class UserRestriction {
    @PrimaryKey
    public String personName;
    public String restriction;
    public int favorite;

    public UserRestriction(@NonNull String personName, @NonNull String restriction){
        this.personName = personName;
        this.restriction = restriction;
        favorite = 0;
    }
}
