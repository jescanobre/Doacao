package dsdm.ufc.doacao.entidades;

public class Solicitacao {
    String mensagem;
    String idUsuario;
    String idObjeto;
    Boolean resultado;


    public Solicitacao(String mensagem, String usuarioId, boolean b) {
        this.mensagem = mensagem;
        this.idUsuario = usuarioId;
        this.resultado = b;
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

}
