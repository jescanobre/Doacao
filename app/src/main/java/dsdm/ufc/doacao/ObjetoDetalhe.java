package dsdm.ufc.doacao;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import dsdm.ufc.doacao.DAO.ConfiguracaoFirebase;
import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.entidades.Solicitacao;
import dsdm.ufc.doacao.entidades.Usuarios;
import dsdm.ufc.doacao.managers.GlideApp;
import dsdm.ufc.doacao.managers.SessionManager;

public class ObjetoDetalhe extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    Button euQuero;
    TextView usuarioNome;
    GridLayout gridLayout;
    private GoogleApiClient mGoogleApiClient;
    Double latitude;
    Double longitude;
    Double objLat;
    Double objLong;

    public static final String EXTRA_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto_detalhe);

        gridLayout = findViewById(R.id.grid_layout_images);

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference(Objeto.REFERENCE_OBJECT);
        DatabaseReference solicitacaoRef = FirebaseDatabase.getInstance().getReference(Solicitacao.REFERENCE_SOLICITACAO);


        Intent intent = getIntent();
        final String id = intent.getExtras().getString(EXTRA_ID);

        Query query1=mDatabaseRef.orderByChild("id").equalTo(id);


        final TextView txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        final TextView txtDescricao = (TextView) findViewById(R.id.txtDescricao);
        final TextView txtEstado = (TextView)findViewById(R.id.txtEstado);
        euQuero = (Button)findViewById(R.id.euQuero);

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    final Objeto models=data.getValue(Objeto.class);
                    String nome=models.getTitulo();
                    System.out.println("objeto detalhe nome : " + nome);
                    String descricao=models.getDescricao();
                    txtTitulo.setText(nome);
                    txtDescricao.setText(descricao);
                    if(models.getEstado().equals(true)){
                        txtEstado.setText("Doado");
                        euQuero.setEnabled(false);

                    }
                    procuraId(models.getIdDoador());

                    loadImages(models.getImagens());





                    objLat = models.getLatitude();
                    objLong = models.getLongitude();
                    callConnection();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.w("SOLICITACAO", id);
        solicitacaoRef.orderByChild("idObjeto").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SessionManager session = new SessionManager(getApplicationContext());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Solicitacao solicitacao = data.getValue(Solicitacao.class);
                    Log.w("solicitacao", solicitacao.toString());
                    if(solicitacao.getIdUsuario().equals(session.getUser().getId())) {
                        euQuero.setClickable(false);
                        euQuero.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        euQuero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ObjetoDetalhe.this,FazerSolicitacao.class);
                intent.putExtra(FazerSolicitacao.EXTRA_ID_OBJETO, id);
                startActivity(intent);
            }
        });



    }

    public void loadImages(List<String> images) {
        StorageReference storageReference = ConfiguracaoFirebase.getStorageReference();
        for (final String image : images ) {
            storageReference.child(image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    ImageView imageView = new ImageView(getApplicationContext());
                    GlideApp.with(getApplicationContext()).load(uri.toString()).override(300,300).into(imageView);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(10,10,10,10);
                    imageView.setLayoutParams(params);

                    gridLayout.addView(imageView);
                }
            });
        }
    }

    public void procuraId(final String userID){
        final DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("usuario");
        Query query2=refUser.orderByChild("id").equalTo(userID);
        final TextView usuarioNome = (TextView) findViewById(R.id.usuarioNome);
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){


                    final Usuarios models=data.getValue(Usuarios.class);
                    String nome=models.getNome();
                    usuarioNome.setText(nome);



                    usuarioNome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(ObjetoDetalhe.this,PerfilGeral.class);
                            i.putExtra("id",models.getId());
                            startActivity(i);
                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
        if(l!=null && objLat  != null && objLong != null){
            System.out.println("TO DENTRO? OIA");
            Log.i("LOGa", "Latitude: " + l.getLatitude());
            latitude = l.getLatitude();
            Log.i("LOGa", "Longitude: " + l.getLongitude());
            longitude = l.getLongitude();

            Location meuPonto = new Location("ponto A");
            meuPonto.setLatitude(latitude);
            meuPonto.setLongitude(longitude);
            Log.i("TOGa", "Latitude1: " + latitude);
            Log.i("TOGa", "Longitude1: " + longitude);


            Location pontoObj = new Location("ponto B");
            pontoObj.setLatitude(objLat);
            pontoObj.setLongitude(objLong);

            Log.i("TOGa", "Latitude2: " + objLat);
            Log.i("TOGa", "Longitude2: " + objLong);

            float distancia = meuPonto.distanceTo(pontoObj);
            TextView txtDistancia = (TextView)findViewById(R.id.txtDistancia);
            String oi = String.valueOf(distancia);
            txtDistancia.setText(distancia + " metros");
        }
    }

    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

