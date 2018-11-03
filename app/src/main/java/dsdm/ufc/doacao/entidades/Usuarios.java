package dsdm.ufc.doacao.entidades;

import java.util.ArrayList;

public class Usuarios {
    Number id;
    String email;
    String senha;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
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
}
