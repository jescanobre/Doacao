package dsdm.ufc.doacao.managers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dsdm.ufc.doacao.entidades.Usuarios;
import dsdm.ufc.doacao.login;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String KEY_IS_LOGIN = "isLoggedIn";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(Usuarios usuario) {
        editor.putBoolean(KEY_IS_LOGIN, true);
        editor.putString(Usuarios.KEY_ID,    usuario.getId());
        editor.putString(Usuarios.KEY_NAME,  usuario.getNome());
        editor.putString(Usuarios.KEY_EMAIL, usuario.getEmail());
        editor.putString(Usuarios.KEY_SENHA, usuario.getSenha());


        editor.commit();
    }

    public Usuarios getUser() {
        Usuarios usuarios = new Usuarios();

        usuarios.setId(sharedPreferences.getString(Usuarios.KEY_ID, null));
        usuarios.setNome(sharedPreferences.getString(Usuarios.KEY_NAME, null));
        usuarios.setEmail(sharedPreferences.getString(Usuarios.KEY_EMAIL, null));
        usuarios.setSenha(sharedPreferences.getString(Usuarios.KEY_SENHA, null));

        return usuarios;
    }

    public void logout() {
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    public boolean isLoggedIn() {
        return this.sharedPreferences.getBoolean(KEY_IS_LOGIN, false);
    }

}
