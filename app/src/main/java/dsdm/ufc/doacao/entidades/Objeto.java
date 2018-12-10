package dsdm.ufc.doacao.entidades;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.ArrayMap;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import dsdm.ufc.doacao.DAO.ConfiguracaoFirebase;

public class Objeto implements Serializable {
    String id;
    String titulo;
    String descricao;
    Boolean estado;
    Boolean ehUsado;
    List<String> imagens;
    Double latitude;
    Double longitude;


    String idDoador;
    String idReceptor;
    List<String> solitações;


    @Exclude
    public static final String REFERENCE_OBJECT = "objeto";
    @Exclude
    public static final String REFERENCE_IMAGE  = "imagens";

    @Exclude
    public static boolean isListenerAdded = false;

    public Objeto() {
        estado = false;
        imagens = new ArrayList<String>();
        solitações = new ArrayList<String>();
        idReceptor = "";
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getEhUsado() {
        return ehUsado;
    }

    public void setEhUsado(Boolean ehUsado) {
        this.ehUsado = ehUsado;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getIdDoador() {
        return idDoador;
    }

    public void setIdDoador(String idDoador) {
        this.idDoador = idDoador;
    }

    public String getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    public List<String> getSolitações() {
        return solitações;
    }

    public void setSolitações(List<String> solitações) {
        this.solitações = solitações;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objeto objeto = (Objeto) o;
        return Objects.equals(id, objeto.id) &&
                Objects.equals(estado, objeto.estado) &&
                Objects.equals(titulo, objeto.titulo) &&
                Objects.equals(descricao, objeto.descricao) &&
                Objects.equals(ehUsado, objeto.ehUsado) &&
                Objects.equals(imagens, objeto.imagens);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash*13 + titulo.hashCode();
        hash = hash*31  + descricao.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "Objeto{" +
                ", estado='" + estado + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ehUsado=" + ehUsado +
                ", imagens=" + imagens +
                '}';
    }

    public void salvar(final Context context, final List<Bitmap> bitmaps, Double latitude, Double longitude) {
        StorageReference referenciaFirebase = ConfiguracaoFirebase.getStorageReference();
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();

        final DatabaseReference objectRef = databaseReference.child(REFERENCE_OBJECT);
        final StorageReference imageRef  = referenciaFirebase.child(REFERENCE_IMAGE);

        id = objectRef.push().getKey();
        objectRef.child(id).setValue(this);
        objectRef.child(id).child(REFERENCE_IMAGE).setValue(imagens);

        objectRef.child(id).child("latitude").setValue(latitude);
        objectRef.child(id).child("longitude").setValue(longitude);




        Log.w("SAVE", String.valueOf(bitmaps.size()));

        if( bitmaps != null && bitmaps.size() > 0 ) {
            for ( final Bitmap bitmap : bitmaps ) {
                String name = String.valueOf(bitmap.hashCode()) + ".png";
                Log.w("IMAGE", name);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] data = outputStream.toByteArray();

                UploadTask uploadTask = imageRef.child(name).putBytes(data);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imagens.add(taskSnapshot.getMetadata().getPath());
                        Map<String, Object> update = new HashMap<String, Object>();
                        update.put("imagens", imagens);
                        objectRef.child(id).updateChildren(update);
                    }
                });

            }
        }
    }



}
