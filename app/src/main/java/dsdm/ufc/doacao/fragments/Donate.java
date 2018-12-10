package dsdm.ufc.doacao.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.custom_adapters.ImageAdapter;
import dsdm.ufc.doacao.custom_views.MyImageView;
import dsdm.ufc.doacao.dialogs.AddFotoDialog;
import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.fragments.donate.TitleDonate;

public class Donate extends Fragment {

    private static List<Bitmap> images = new ArrayList<Bitmap>();
    private MyImageView imagesView[];

    public Donate() {
        imagesView = new MyImageView[5];
    }

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
        Button btn_done = view.findViewById(R.id.btn_add_doacao);

        imagesView[0] = (MyImageView) view.findViewById(R.id.image1);
        imagesView[1] = (MyImageView) view.findViewById(R.id.image2);
        imagesView[2] = (MyImageView) view.findViewById(R.id.image3);
        imagesView[3] = (MyImageView) view.findViewById(R.id.image4);
        imagesView[4] = (MyImageView) view.findViewById(R.id.image5);

        for(int i = 1; i < 5; i++)
            imagesView[i].setStrokeButtonWidth(15);

        for (int i = 0; i < 5; ++i) {
            imagesView[i].setAdjustViewBounds(true);
            imagesView[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imagesView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddFotoDialog fotoDialog = new AddFotoDialog();
                    fotoDialog.setImageView((ImageView) v);
                    fotoDialog.show(getFragmentManager(), "AddFotoDialog");
                }
            });
        }

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < 5; ++i) {
                    if( imagesView[i].getDrawable() != null ) {
                        Bitmap bitmap = ((BitmapDrawable) imagesView[i].getDrawable()).getBitmap();
                        Donate.getImages().add(bitmap);
                    }
                }

                Intent intent = new Intent(getActivity(), TitleDonate.class);
                intent.putExtra("objeto", new Objeto());
                startActivity(intent);
            }
        });


        return view;
    }

    public static List<Bitmap> getImages() {
        return images;
    }
}
