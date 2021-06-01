package com.tip.MenuSemanal.ui.recetas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecetasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RecetasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is recetas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}