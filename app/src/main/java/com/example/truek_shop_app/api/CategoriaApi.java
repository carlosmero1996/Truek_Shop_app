package com.example.truek_shop_app.api;


import com.example.truek_shop_app.entity.GenericResponse;
import com.example.truek_shop_app.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaApi {
    String base = "api/categoria";

    @GET(base)
    Call<GenericResponse<List<Categoria>>> listarCategoriasActivas();
}
