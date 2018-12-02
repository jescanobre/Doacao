package dsdm.ufc.doacao.entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dsdm.ufc.doacao.DAO.ConfiguracaoFirebase;

public class Usuarios {
    String id;

    String nome;
    String email;
    String senha;
    List<Objeto> meusObjs;
    List<Objeto> recebidos;

    public List<Objeto> getRecebidos() {
        return recebidos;
    }

    public void setRecebidos(List<Objeto> recebidos) {
        this.recebidos = recebidos;
    }

    public List<Objeto> getMeusObjs() {
        return meusObjs;
    }

    public void setMeusObjs(List<Objeto> meusObjs) {
        this.meusObjs = meusObjs;
    }

    @Exclude
    public final static String KEY_ID    = "id";
    @Exclude
    public final static String KEY_NAME  = "name";
    @Exclude
    public final static String KEY_EMAIL = "email";
    @Exclude
    public final static String KEY_SENHA = "senha";
    @Exclude
    public final static String KEY_MEUSOBJS = "meus_objs";
    @Exclude
    public final static String KEY_RECEBIDOS = "recebidos";

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();

        referenciaFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);

    }

    @Exclude

    public Map<String,Object> toMap(){
        HashMap<String,Object> hashMapUsuario = new HashMap<>();
        hashMapUsuario.put("id",getId());
        hashMapUsuario.put("nome",getNome());
        hashMapUsuario.put("email",getEmail());
        hashMapUsuario.put("senha",getSenha());
        hashMapUsuario.put("Meus Objetos",getMeusObjs());


        return hashMapUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", meus objetos='" + meusObjs + '\'' +

                '}';
    }
}
