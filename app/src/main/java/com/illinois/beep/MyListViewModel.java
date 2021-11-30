package com.illinois.beep;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyListViewModel extends ViewModel {

    private MutableLiveData<List<MyListItem>> myList;
    public MutableLiveData<List<MyListItem>> getMyList() {
        if (myList == null) {
            myList = new MutableLiveData<>();
            loadMyList();
        }
        return myList;
    }

    private void loadMyList() {
        // Do an asynchronous operation to fetch users.
        List<MyListItem> items = new ArrayList<>();
        int counter = 3;
        for (Product product: ProductDatabase.getDb().values()) {
            items.add(new MyListItem(product, 1));
            counter -= 1;
            if (counter == 0) break; // only add the some elements for demo
        }

        myList.postValue(items);
    }
}
