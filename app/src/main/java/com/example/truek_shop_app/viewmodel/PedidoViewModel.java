package com.example.truek_shop_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.truek_shop_app.entity.GenericResponse;
import com.example.truek_shop_app.entity.service.Pedido;
import com.example.truek_shop_app.entity.service.dto.GenerarPedidoDTO;
import com.example.truek_shop_app.entity.service.dto.PedidoConDetallesDTO;
import com.example.truek_shop_app.repository.PedidoRepository;

import java.util.List;

import okhttp3.ResponseBody;

public class PedidoViewModel extends AndroidViewModel {
    private final PedidoRepository repository;

    public PedidoViewModel(@NonNull Application application) {
        super(application);
        this.repository = PedidoRepository.getInstance();
    }
    public LiveData<GenericResponse<List<PedidoConDetallesDTO>>> listarPedidosPorCliente(int idCli){
        return this.repository.listarPedidosPorCliente(idCli);
    }

    public LiveData<GenericResponse<GenerarPedidoDTO>> guardarPedido(GenerarPedidoDTO dto){
        return repository.save(dto);
    }
    public LiveData<GenericResponse<Pedido>> anularPedido(int id){
        return repository.anularPedido(id);
    }

    /**
     * Export invoice
     * @param idCli
     * @param idOrden
     * @return
     */
    public LiveData<GenericResponse<ResponseBody>> exportInvoice(int idCli, int idOrden){
        return repository.exportInvoice(idCli, idOrden);
    }
}
