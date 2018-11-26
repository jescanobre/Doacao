package dsdm.ufc.doacao.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.custom_adapters.ImageAdapter;
import dsdm.ufc.doacao.dialogs.AddFotoDialog;
import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.fragments.donate.TitleDonate;

public class Donate extends Fragment {

    private ImageAdapter imageAdapter;
    private static List<Bitmap> images = new ArrayList<Bitmap>();;

    public static Donate newInstance() {
        Donate fragment = new Donate();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate, container, false);
        Button button   = view.findViewById(R.id.btn_add_foto);
        Button btn_done = view.findViewById(R.id.btn_add_doacao);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fotoDialog = new AddFotoDialog();
                fotoDialog.show(getFragmentManager(), "AddFotoDialog");
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TitleDonate.class);
                intent.putExtra("objeto", new Objeto());
                startActivity(intent);
            }
        });


        GridView gridView = view.findViewById(R.id.layout_of_images);
        this.imageAdapter = new ImageAdapter(getContext());

        gridView.setAdapter(this.imageAdapter);
        return view;
    }

    public ImageAdapter getAdapter() {
        return this.imageAdapter;
    }

    public static List<Bitmap> getImages() {
        return images;
    }
}
