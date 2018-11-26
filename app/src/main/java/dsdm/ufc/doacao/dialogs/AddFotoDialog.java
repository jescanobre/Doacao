package dsdm.ufc.doacao.dialogs;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.List;

import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.custom_adapters.ImageAdapter;
import dsdm.ufc.doacao.fragments.Donate;
import dsdm.ufc.doacao.managers.PermissionManager;

public class AddFotoDialog extends DialogFragment {

    private final int PICK_IMAGE_REQUEST    = 21;
    private final int REQUEST_IMAGE_CAPTURE = 22;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_foto, null);
        builder.setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddFotoDialog.this.getDialog().cancel();
                    }
                });

        Button btnGallery = view.findViewById(R.id.add_foto_galeria);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        Button btnCamera = view.findViewById(R.id.add_foto_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent();
            }
        });

        return builder.create();
    }

    public void chooseImage() {
        if( !PermissionManager.checkPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ) {
            PermissionManager.requestPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE, 21,
                    "Permissão de leitura", "Precisamos da permissão para podermos fazer upload da image.");
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Choose a Image"), this.PICK_IMAGE_REQUEST);
        }
    }

    public void takePictureIntent() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == this.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.getData() != null ) {
            Uri path = data.getData();

            GridView gridView = getActivity().findViewById(R.id.layout_of_images);
            ImageAdapter imageAdapter = (ImageAdapter) gridView.getAdapter();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);

                imageAdapter.add(bitmap);
                imageAdapter.notifyDataSetChanged();

                Donate.getImages().add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if( requestCode == this.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK
                && data != null && data.getExtras() != null ) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            GridView gridView = getActivity().findViewById(R.id.layout_of_images);
            ImageAdapter imageAdapter = (ImageAdapter) gridView.getAdapter();

            imageAdapter.add(bitmap);
            imageAdapter.notifyDataSetChanged();

            Donate.getImages().add(bitmap);
        }
    }
}