package dsdm.ufc.doacao;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import dsdm.ufc.doacao.entidades.Usuarios;
import dsdm.ufc.doacao.managers.SessionManager;

public class meuPerfil extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ListView objLista;
    SessionManager sessionManager;
    private GoogleApiClient mGoogleApiClient;
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);

        callConnection();
        sessionManager = new SessionManager(getApplicationContext());

        TextView txtNome = (TextView) findViewById(R.id.txtNome);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);


        final String lista[] = new String[]{"Cadeira de Balanço", "Bicicleta"};

        sessionManager = new SessionManager(this);
        Usuarios usuarioDados = sessionManager.getUser();
        System.out.println("aaaaaaaaaaaaaaaa " + usuarioDados.getNome());
        txtNome.setText(usuarioDados.getNome());
        txtEmail.setText(usuarioDados.getEmail());

        callConnection();

    }

    private synchronized void callConnection() {
        System.out.println("TO FORA? ====================================== CHAMOU");

        mGoogleApiClient = new GoogleApiClient.Builder(this).addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


    }



    @Override
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

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
