package dsdm.ufc.doacao.fragments.donate;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import dsdm.ufc.doacao.MainActivity;
import dsdm.ufc.doacao.R;

public class RevisionDonate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_donate);

        String title       = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        Boolean status     = getIntent().getBooleanExtra( "status", true );

        EditText edtTitle = findViewById(R.id.edt_revision_title);
        EditText edtDescription = findViewById(R.id.edt_revision_description);
        RadioButton rbStatus;
        if( status )
            rbStatus = findViewById(R.id.rbtn_used);
        else
            rbStatus = findViewById(R.id.rbtn_not_used);

        edtTitle.setText( title );
        edtDescription.setText( description );
        rbStatus.setChecked( true );

    }

    public void btnRevisionSend(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RevisionDonate.this);
        builder.setMessage("Deseja finalizar doação?")
                .setTitle("Confirmação")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
}
