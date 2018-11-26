package dsdm.ufc.doacao.entidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.google.firebase.database.Exclude;
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

    @Exclude
    private final String REFERENCE_OBJECT = "objeto";
    @Exclude
    private final String REFERENCE_IMAGE  = "imagens";

    public Objeto() {
        estado = false;
        imagens = new ArrayList<String>();
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
        hash = hash*17  + ehUsado.hashCode();
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

    public void salvar(final Context context, List<Bitmap> bitmaps) {
        StorageReference referenciaFirebase = ConfiguracaoFirebase.getStorageReference();
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();

        final DatabaseReference objectRef = databaseReference.child(REFERENCE_OBJECT);
        final StorageReference imageRef  = referenciaFirebase.child(REFERENCE_IMAGE);

        id = String.valueOf(hashCode());

        objectRef.child(id).setValue(this);
        objectRef.child(id).child(REFERENCE_IMAGE).setValue(imagens);

        Log.w("SAVE", String.valueOf(bitmaps.size()));

        if( bitmaps != null && bitmaps.size() > 0 ) {
            for ( Bitmap bitmap : bitmaps ) {
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
