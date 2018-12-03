package dsdm.ufc.doacao.fragments.donate;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;

import java.util.List;

import dsdm.ufc.doacao.MainActivity;
import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.custom_adapters.ImageAdapter;
import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.fragments.Donate;
import dsdm.ufc.doacao.managers.SessionManager;

public class RevisionDonate extends AppCompatActivity {

    private EditText edtTitle;
    private EditText edtDescription;
    private RadioButton rbUsed;
    private RadioButton rbNotUsed;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_donate);

        session = new SessionManager(getApplicationContext());

        Objeto objeto = (Objeto) getIntent().getSerializableExtra("objeto");

        String title       = objeto.getTitulo();
        String description = objeto.getDescricao();
        Boolean status     = objeto.getEhUsado();

        edtTitle       = findViewById(R.id.edt_revision_title);
        edtDescription = findViewById(R.id.edt_revision_description);
        rbUsed         = findViewById(R.id.rbtn_used);
        rbNotUsed      = findViewById(R.id.rbtn_not_used);

        edtTitle.setText( title );
        edtDescription.setText( description );
        if( status )
            rbUsed.setChecked( true );
        else
            rbNotUsed.setChecked( true );

        displayImages();
    }

    public void btnRevisionSend(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RevisionDonate.this);
        builder.setMessage("Deseja finalizar doação?")
                .setTitle("Confirmação")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Objeto objeto = (Objeto) getIntent().getSerializableExtra("objeto");
                        updateObjeto(objeto);


                        Log.w("User", session.getUser().toString());
                        objeto.setIdDoador(session.getUser().getId());

                        objeto.salvar(getApplicationContext(), Donate.getImages() );

                        Intent intent = new Intent(RevisionDonate.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateObjeto( Objeto objeto ) {
        String title       = edtTitle.getText().toString();
        String description = edtDescription.getText().toString();
        Boolean status     = rbUsed.isChecked();
        if( !objeto.getTitulo().equals(title) )
            objeto.setTitulo(title);
        if( !objeto.getDescricao().equals(description) )
            objeto.setDescricao(description);
        if( !objeto.getEhUsado().equals(status) )
            objeto.setEhUsado(status);
    }

    public void displayImages() {
        List<Bitmap> bitmaps = Donate.getImages();
        ImageAdapter adapter = new ImageAdapter(getApplicationContext());
        adapter.add(bitmaps);
        GridView gridView = findViewById(R.id.display_images);
        gridView.setAdapter(adapter);
    }
}
