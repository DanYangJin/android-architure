package com.shouzhan.design.di;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.util.ArrayMap;
import com.shouzhan.design.ui.home.viewmodel.MainViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author danbin
 * @version ViewModelsFactory.java, v 0.1 2019-02-24 下午4:19 danbin
 */
@Singleton
public class ViewModelsFactory implements ViewModelProvider.Factory {

    private final ArrayMap<Class, Callable<? extends ViewModel>> creators;

    @Inject
    public ViewModelsFactory(ViewModelSubComponent viewModelSubComponent) {
        creators = new ArrayMap<>();
        // View models cannot be injected directly because they won't be bound to the owner's view model scope.
        creators.put(MainViewModel.class, () -> viewModelSubComponent.homeViewModel());
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