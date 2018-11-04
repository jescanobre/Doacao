package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import dsdm.ufc.doacao.entidades.Objeto;

public class PerfilGeral extends AppCompatActivity {


    ListView objLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_geral);

        objLista = (ListView) findViewById(R.id.objetosLista);


        final String lista[] = new String[]{"Cadeira de Balanço","Bicicleta"};


        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        objLista.setAdapter(arrayAdapter);

        objLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(lista[position].equals("Cadeira de Balanço")){
                    Intent i = new Intent(PerfilGeral.this,ObjetoDetalhe.class);

                    startActivity(i);
                }
            }
        });
    }

}
