package dsdm.ufc.doacao.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dsdm.ufc.doacao.MainActivity;
import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.dialogs.AddFotoDialog;
import dsdm.ufc.doacao.fragments.donate.TitleDonate;

public class Donate extends Fragment {

    public Donate() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
                startActivity(intent);
            }
        });

        return view;
    }

}
