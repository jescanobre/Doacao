package dsdm.ufc.doacao.entidades;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import dsdm.ufc.doacao.DAO.ConfiguracaoFirebase;

import com.google.firebase.database.Exclude;

public class Solicitacao implements Serializable {
    String mensagem;
    String idUsuario;
    String idObjeto;
    Boolean resultado;

    @Exclude
    public static final String REFERENCE_SOLICITACAO = "solicitacao";

    public Solicitacao() {

    }

    public Solicitacao(String mensagem, String usuarioId, boolean b) {
        this.mensagem = mensagem;
        this.idUsuario = usuarioId;
        this.resultado = b;
    }

    public Solicitacao(String mensagem, String idUsuario, String idObjeto, Boolean resultado) {
        this.mensagem = mensagem;
        this.idUsuario = idUsuario;
        this.idObjeto = idObjeto;
        this.resultado = resultado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Boolean getResultado() {
        return resultado;
    }

    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }

    public String getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(String idObjeto) {
        this.idObjeto = idObjeto;
    }

    public void salvar() {
        DatabaseReference reference = ConfiguracaoFirebase.getFirebase();

        reference.child(REFERENCE_SOLICITACAO).push().setValue(this);
    }

}
