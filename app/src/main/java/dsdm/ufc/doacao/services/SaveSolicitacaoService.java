package dsdm.ufc.doacao.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import dsdm.ufc.doacao.entidades.Solicitacao;
import com.google.firebase.database.Exclude;

public class SaveSolicitacaoService extends IntentService {

    public static final String EXTRA_SOLICITACAO = "solicitacao";

    public SaveSolicitacaoService() {
        super("SaveSolicitacaoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Solicitacao solicitacao = (Solicitacao) intent.getSerializableExtra(EXTRA_SOLICITACAO);

            solicitacao.salvar();
        }
    }
}
