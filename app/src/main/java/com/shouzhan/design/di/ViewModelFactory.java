package com.shouzhan.design.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.util.ArrayMap;
import com.shouzhan.design.ui.home.viewmodel.DaggerViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.Callable;


/**
 * @author danbin
 */
@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ArrayMap<Class, Callable<? extends ViewModel>> creators;

    @Inject
    public ViewModelFactory(ViewModelSubComponent viewModelSubComponent) {
        creators = new ArrayMap<>();

        creators.put(DaggerViewModel.class, () -> viewModelSubComponent.daggerViewModel());
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        Callable<? extends ViewModel> creator = creators.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class, Callable<? extends ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("Unknown model class " + modelClass);
        }
        try {
            return (T) creator.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}