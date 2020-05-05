package alura.com.financask.ui

import alura.com.financask.R
import alura.com.financask.extension.formataParaBrasileiro
import alura.com.financask.model.Resumo
import alura.com.financask.model.Transacao
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_lista_transacoes.view.*
import java.math.BigDecimal

class ResumoView(
    context: Context,
    private val view: View,
    transacoes: List<Transacao>
) {
    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita() {
        val totalReceita = resumo.receita
        with(view.resumo_card_receita) {
            setTextColor(corReceita)
            text = totalReceita.formataParaBrasileiro()
        }

    }


    private fun adicionaDespesa() {
        val totalDespesa = resumo.despesa
        with(view.resumo_card_despesa) {
            setTextColor(corDespesa)
            text = totalDespesa.formataParaBrasileiro()
        }

    }

    private fun adicionaTotal() {
        val valorTotal = resumo.total
        val cor = corPor(valorTotal)
        with(view.resumo_card_total) {
            setTextColor(cor)
            text = valorTotal.formataParaBrasileiro()
        }
    }

    private fun corPor(valor: BigDecimal): Int {
        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return corDespesa
    }


}