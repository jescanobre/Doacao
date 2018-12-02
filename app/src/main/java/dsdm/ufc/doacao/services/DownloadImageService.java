package dsdm.ufc.doacao.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.managers.GlideApp;

public class DownloadImageService extends IntentService {

    public static final String EXTRA_OBJETO = "objeto";
    public static final String EXTRA_URL  = "url";

    public DownloadImageService() {
        super("DownloadImageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

        }
    }

}
