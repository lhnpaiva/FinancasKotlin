package alura.com.financask.ui.dialog

import alura.com.financask.R
import alura.com.financask.delegate.TransacaoDelegate
import alura.com.financask.extension.converteParaCalendar
import alura.com.financask.extension.formataParaBrasileiro
import alura.com.financask.model.Tipo
import alura.com.financask.model.Transacao
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*


abstract class FormularioTransacoesDialog(
    private val context: Context,
    private val viewGroup: ViewGroup
) {
    val viewCriada = criaLayout()
    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoCategoria = viewCriada.form_transacao_categoria
    protected val campoData = viewCriada.form_transacao_data
    abstract protected val tituloBotaoPositivo : String


    fun chama(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, delegate)
    }


    private fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        var titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(
                tituloBotaoPositivo,
                DialogInterface.OnClickListener { _, _ ->

                    val valorEmTexto = campoValor.text.toString()
                    val dataEmTexto = campoData.text.toString()
                    val valor = convertecampoValor(valorEmTexto)
                    val data = dataEmTexto.converteParaCalendar()


                    val categoriaEmTexto = campoCategoria.selectedItem.toString()

                    var transacaoCriada = Transacao(
                        tipo = tipo,
                        valor = valor, data = data, categoria = categoriaEmTexto
                    )

                    delegate(transacaoCriada)


                })
            .setNegativeButton("Sair", null)
            .show()
    }

    abstract protected fun tituloPor(tipo: Tipo): Int


    private fun convertecampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, "Impossível formatar texto", Toast.LENGTH_LONG)
                .show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {
        val categorias = categoriasPor(tipo)

        val adapter = ArrayAdapter
            .createFromResource(
                context,
                categorias,
                android.R.layout.simple_spinner_dropdown_item
            )

        campoCategoria.adapter = adapter
    }

    protected fun categoriasPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa

    }

    private fun configuraCampoData() {
        var hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData
            .setText(hoje.formataParaBrasileiro())
        campoData
            .setOnClickListener {
                DatePickerDialog(context,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        var dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(year, month, dayOfMonth)
                        campoData.setText(dataSelecionada.formataParaBrasileiro())
                    }
                    , ano, mes, dia)
                    .show()
            }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context).inflate(
            R.layout.form_transacao,
            viewGroup,
            false
        )
    }
}