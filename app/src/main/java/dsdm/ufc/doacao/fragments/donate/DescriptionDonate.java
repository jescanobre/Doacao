package dsdm.ufc.doacao.fragments.donate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.entidades.Objeto;

public class DescriptionDonate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_donate);
    }

    public void btnDescriptionOnClick(View view) {
        EditText editText = findViewById(R.id.edt_description);
        String description = editText.getText().toString();
        Intent intent = new Intent( DescriptionDonate.this, StatusDonate.class );
        Objeto objeto = (Objeto) getIntent().getSerializableExtra("objeto");
        objeto.setDescricao(description);
        intent.putExtra("objeto", objeto);
        startActivity(intent);
    }
}
