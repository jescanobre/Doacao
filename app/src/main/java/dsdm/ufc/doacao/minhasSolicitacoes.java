package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class minhasSolicitacoes extends AppCompatActivity {

    ListView sLista;
    minhasSolicitacoes mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_solicitacoes);
        mContext = this;
        sLista = (ListView)findViewById(R.id.sLista);

        final String soli[] = new String[]{"Ricardo"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,soli);
        sLista.setAdapter(arrayAdapter);

        sLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(soli[position].equals("Ricardo")){
                    Intent i = new Intent(mContext,solicitacaoDetalhe.class);

                    startActivity(i);
                }
            }
        });
    }
}
