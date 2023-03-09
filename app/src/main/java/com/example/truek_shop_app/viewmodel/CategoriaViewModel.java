package com.example.truek_shop_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.truek_shop_app.entity.GenericResponse;
import com.example.truek_shop_app.entity.service.Categoria;
import com.example.truek_shop_app.repository.CategoriaRepository;

import java.util.List;

public class CategoriaViewModel extends AndroidViewModel {
    private final CategoriaRepository repository;


    public CategoriaViewModel(@NonNull Application application) {
        super(application);
        this.repository = CategoriaRepository.getInstance();
    }

    public LiveData<GenericResponse<List<Categoria>>> listarCategoriasActivas(){
        return this.repository.listarCategoriasActivas();
    }
}
