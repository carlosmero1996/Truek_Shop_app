package com.example.truek_shop_app.api;



import com.example.truek_shop_app.entity.GenericResponse;
import com.example.truek_shop_app.entity.service.Cliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteApi {

    String base = "api/cliente";
    @POST(base)
    Call<GenericResponse<Cliente>> guardarCliente(@Body Cliente c);
}
