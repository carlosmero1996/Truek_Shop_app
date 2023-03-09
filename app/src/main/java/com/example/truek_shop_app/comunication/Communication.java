package com.example.truek_shop_app.comunication;

import android.content.Intent;

public interface Communication {
    void showDetails(Intent i);
    void exportInvoice(int idCli, int idOrden, String fileName);
}
