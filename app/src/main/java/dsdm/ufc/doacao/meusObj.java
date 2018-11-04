package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class meusObj extends AppCompatActivity {

    ListView listaObjs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_obj);

        listaObjs = (ListView)findViewById(R.id.listaObjs);

        final String lista[] = new String[]{"Cadeira de Balanço","Bicicleta"};


        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        listaObjs.setAdapter(arrayAdapter);

        listaObjs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(lista[position].equals("Cadeira de Balanço")){
                    Intent i = new Intent(meusObj.this,meuObj.class);

                    startActivity(i);
                }
            }
        });
    }
}
