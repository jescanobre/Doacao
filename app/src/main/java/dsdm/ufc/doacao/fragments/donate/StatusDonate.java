package dsdm.ufc.doacao.fragments.donate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dsdm.ufc.doacao.R;

public class StatusDonate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_donate);
    }

    public void btnUsedOnCLick(View view) {
        int id = view.getId();
        Intent intent = new Intent(StatusDonate.this, RevisionDonate.class);
        if( id == R.id.btn_used ) {
            intent.putExtra("status", true);
        }
        else {
            intent.putExtra("status", false);
        }
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }
}
