package alura.com.financask.ui.dialog

import alura.com.financask.R
import alura.com.financask.model.Tipo
import android.content.Context
import android.view.ViewGroup

class AdicionaTransacaoDialog(
    viewGroup: ViewGroup,
    context: Context
) : FormularioTransacoesDialog(context, viewGroup) {
    override val tituloBotaoPositivo: String

        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {

        if (tipo == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }
}
