package com.tip.MenuSemanal.ui.ingredientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IngredientesViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public IngredientesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ingredientes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}