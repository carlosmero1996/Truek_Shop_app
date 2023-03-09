package com.example.truek_shop_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.truek_shop_app.entity.GenericResponse;
import com.example.truek_shop_app.entity.service.Cliente;
import com.example.truek_shop_app.repository.ClienteRepository;


public class ClienteViewModel extends AndroidViewModel {
    private final ClienteRepository repository;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = ClienteRepository.getInstance();
    }

    public LiveData<GenericResponse<Cliente>> guardarCliente(Cliente c){
        return  repository.guardarCliente(c);
    }
}
