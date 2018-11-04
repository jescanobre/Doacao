package dsdm.ufc.doacao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import dsdm.ufc.doacao.entidades.Objeto;

public class PerfilGeral extends AppCompatActivity {


    ListView objLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_geral);

        objLista = (ListView) findViewById(R.id.objetosLista);

        ArrayList<String> lista = new ArrayList<>();

        lista.add("Cadeira de Balanço");
        lista.add("Bicicleta");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        objLista.setAdapter(arrayAdapter);


    }
}
