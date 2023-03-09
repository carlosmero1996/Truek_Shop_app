package com.example.truek_shop_app.activity.ui.inicio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.truek_shop_app.R;
import com.example.truek_shop_app.adapter.CategoriaAdapter;
import com.example.truek_shop_app.adapter.PlatillosRecomendadosAdapter;
import com.example.truek_shop_app.adapter.SliderAdapter;
import com.example.truek_shop_app.comunication.Communication;
import com.example.truek_shop_app.comunication.MostrarBadgeCommunication;
import com.example.truek_shop_app.entity.SliderItem;
import com.example.truek_shop_app.entity.service.DetallePedido;
import com.example.truek_shop_app.entity.service.Platillo;
import com.example.truek_shop_app.utils.Carrito;
import com.example.truek_shop_app.viewmodel.CategoriaViewModel;
import com.example.truek_shop_app.viewmodel.PlatilloViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class InicioFragment extends Fragment implements Communication, MostrarBadgeCommunication {
    private CategoriaViewModel categoriaViewModel;
    private PlatilloViewModel platilloViewModel;
    private RecyclerView rcvPlatillosRecomendados;
    private PlatillosRecomendadosAdapter adapter;
    private List<Platillo> platillos = new ArrayList<>();
    private GridView gvCategorias;
    private CategoriaAdapter categoriaAdapter;
    private SliderView svCarrusel;
    private SliderAdapter sliderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initAdapter();
        loadData();
    }

    private void init(View v){
        svCarrusel = v.findViewById(R.id.svCarrusel);
        ViewModelProvider vmp = new ViewModelProvider(this);
        //Categorías
        categoriaViewModel = vmp.get(CategoriaViewModel.class);
        gvCategorias = v.findViewById(R.id.gvCategorias);
        //Platillos
        rcvPlatillosRecomendados = v.findViewById(R.id.rcvPlatillosRecomendados);
        rcvPlatillosRecomendados.setLayoutManager(new GridLayoutManager(getContext(), 1));
        platilloViewModel = vmp.get(PlatilloViewModel.class);

    }
    private void initAdapter() {
        //Carrusel de Imágenes
        sliderAdapter = new SliderAdapter(getContext());
        svCarrusel.setSliderAdapter(sliderAdapter);
        svCarrusel.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        svCarrusel.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        svCarrusel.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        svCarrusel.setIndicatorSelectedColor(Color.WHITE);
        svCarrusel.setIndicatorUnselectedColor(Color.GRAY);
        svCarrusel.setScrollTimeInSec(4); //set scroll delay in seconds :
        svCarrusel.startAutoCycle();
        //Categorías
        categoriaAdapter = new CategoriaAdapter(getContext(), R.layout.item_categorias, new ArrayList<>());
        gvCategorias.setAdapter(categoriaAdapter);
        //Platillos
        adapter = new PlatillosRecomendadosAdapter(platillos, this, this);
        rcvPlatillosRecomendados.setAdapter(adapter);
    }
    private void loadData() {

        List<SliderItem> lista = new ArrayList<>();
        lista.add(new SliderItem(R.drawable.img_carro, "Autos"));
        lista.add(new SliderItem(R.drawable.img_moda, "Ropa"));
        lista.add(new SliderItem(R.drawable.img_computadoras, "Computadoras"));
        lista.add(new SliderItem(R.drawable.img_muebles, "Muebles"));
        lista.add(new SliderItem(R.drawable.img_electrodomestico, "Electrodomesticos"));
        sliderAdapter.updateItem(lista);
        categoriaViewModel.listarCategoriasActivas().observe(getViewLifecycleOwner(), response -> {
            if(response.getRpta() == 1){
                categoriaAdapter.clear();
                categoriaAdapter.addAll(response.getBody());
                categoriaAdapter.notifyDataSetChanged();
            }else{
                System.out.println("Error al obtener las categorías activas");
            }
        });
        platilloViewModel.listarPlatillosRecomendados().observe(getViewLifecycleOwner(), response -> {
            adapter.updateItems(response.getBody());
        });

    }


    @Override
    public void showDetails(Intent i) {
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    public void exportInvoice(int idCli, int idOrden, String fileName) {

    }

    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void add(DetallePedido dp) {
        successMessage(Carrito.agregarPlatillos(dp));
        BadgeDrawable badgeDrawable = BadgeDrawable.create(this.getContext());
        badgeDrawable.setNumber(Carrito.getDetallePedidos().size());
        BadgeUtils.attachBadgeDrawable(badgeDrawable, getActivity().findViewById(R.id.toolbar), R.id.bolsaCompras);
    }
    public void successMessage(String message) {
        new SweetAlertDialog(this.getContext(),
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }
}