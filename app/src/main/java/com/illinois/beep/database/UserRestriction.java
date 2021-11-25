package com.illinois.beep.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class represents a dietary restriction and the user associated with it
 */
@Entity(primaryKeys={"personName", "restriction"})
public class UserRestriction {

    @ColumnInfo(name = "personName")
    @NonNull
    private String personName;

    @ColumnInfo(name = "restriction")
    @NonNull
    private String restriction; //if restriction=="add" then it's used to indicate the add restriction row.

    @ColumnInfo(name = "favorite")
    private int favorite;

    public UserRestriction(@NonNull String personName,@NonNull String restriction){
        this.personName = personName;
        this.restriction = restriction;
        favorite = 0;
    }

    @NonNull
    public String getPersonName() {
        return personName;
    }

    @NonNull
    public String getRestriction() {
        return restriction;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
