package dsdm.ufc.doacao.fragments.donate;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
//import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import dsdm.ufc.doacao.MainActivity;
import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.custom_adapters.ImageAdapter;
import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.fragments.Donate;
import dsdm.ufc.doacao.managers.SessionManager;

public class RevisionDonate extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private EditText edtTitle;
    private EditText edtDescription;
    private RadioButton rbUsed;
    private RadioButton rbNotUsed;
    private SessionManager session;
    private GoogleApiClient mGoogleApiClient;
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_donate);
        callConnection();
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

//<<<<<<< HEAD

                        objeto.salvar(getApplicationContext(), Donate.getImages(), latitude, longitude);
//=======
//                        objeto.salvar(getApplicationContext(), Donate.getImages() );
//
//>>>>>>> master
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

        callConnection();

    }

    public void displayImages() {
        List<Bitmap> bitmaps = Donate.getImages();
        ImageAdapter adapter = new ImageAdapter(getApplicationContext());
        adapter.add(bitmaps);
        GridView gridView = findViewById(R.id.display_images);
        gridView.setAdapter(adapter);
    }

    private synchronized void callConnection() {
        System.out.println("TO FORA? ====================================== CHAMOU");

        mGoogleApiClient = new GoogleApiClient.Builder(this).addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


    }



    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        System.out.println("TO FORA? ======================================OIA");
        System.out.println("TO FORA? ======================================OIA" + l);
        if(l!=null){
            System.out.println("TO DENTRO? OIA");
            Log.i("LOGa", "Latitude: " + l.getLatitude());
            latitude = l.getLatitude();
            Log.i("LOGa", "Longitude: " + l.getLongitude());
            longitude = l.getLongitude();
        }
    }

    public void onConnectionSuspended(int i) {

    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
