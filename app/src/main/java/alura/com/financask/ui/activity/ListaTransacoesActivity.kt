package alura.com.financask.ui.activity

import alura.com.financask.R
import alura.com.financask.model.Tipo
import alura.com.financask.model.Transacao
import alura.com.financask.ui.adapter.ListaTransacoesAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)
        val transacoes: List<Transacao> = transacoesDeExemplo()
        configuraLista(transacoes)


    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
            Transacao(valor = BigDecimal(20.50), tipo = Tipo.DESPESA),
            Transacao(
                valor = BigDecimal(100.0),
                categoria = "almoço de final de semana",
                tipo = Tipo.RECEITA
            )
        )
    }
}
