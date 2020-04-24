package alura.com.financask.ui.activity

import alura.com.financask.R
import alura.com.financask.extension.formataParaBrasileiro
import alura.com.financask.model.Tipo
import alura.com.financask.model.Transacao
import alura.com.financask.ui.ResumoView
import alura.com.financask.ui.adapter.ListaTransacoesAdapter
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)
        configuraResumo()
        configuraLista()


        lista_transacoes_adiciona_receita
            .setOnClickListener {
                val view: View = window.decorView
                val viewCriada = LayoutInflater.from(this).inflate(
                    R.layout.form_transacao,
                    view as ViewGroup,
                    false
                )
                val ano = 2019
                val mes = 5
                val dia = 23

                var hoje = Calendar.getInstance()


                viewCriada.form_transacao_data
                    .setText(hoje.formataParaBrasileiro())
                viewCriada.form_transacao_data
                    .setOnClickListener {
                        DatePickerDialog(this,
                            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                                var dataSelecionada = Calendar.getInstance()
                                dataSelecionada.set(year, month, dayOfMonth)
                                viewCriada.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())
                            }
                            , ano, mes, dia)
                            .show()
                    }

                val adapter = ArrayAdapter
                    .createFromResource(
                        this,
                        R.array.categorias_de_receita,
                        android.R.layout.simple_spinner_dropdown_item
                    )

                viewCriada.form_transacao_categoria.adapter = adapter

                AlertDialog.Builder(this)
                    .setTitle(R.string.adiciona_receita)
                    .setView(viewCriada)
                    .setPositiveButton(
                        "Adicionar",
                        DialogInterface.OnClickListener { dialogInterface, i ->

                            val valorEmTexto = viewCriada
                                .form_transacao_valor.text.toString()

                            val valor = try {
                                BigDecimal(valorEmTexto)
                            } catch (exception: NumberFormatException) {
                                Toast.makeText(this, "Impossível formatar texto", Toast.LENGTH_LONG)
                                    .show()
                                BigDecimal.ZERO
                            }

                            val dataEmTexto = viewCriada.form_transacao_data.text.toString()
                            val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
                            val dataConvertida: Date = formatoBrasileiro.parse(dataEmTexto)
                            val data = Calendar.getInstance()
                            data.time = dataConvertida

                            val categoriaEmTexto =
                                viewCriada.form_transacao_categoria.selectedItem.toString()

                            var transacaoCriada = Transacao(
                                tipo = Tipo.RECEITA,
                                valor = valor, data = data, categoria = categoriaEmTexto
                            )

                            atualizaTransacoes(transacaoCriada)
                            lista_transacoes_adiciona_menu.close(true)


                        })
                    .setNegativeButton("Sair", null)
                    .show()

            }
    }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view = window.decorView
        val atualizaResumo = ResumoView(this, view, transacoes)
        atualizaResumo.atualiza()
    }


    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }


}
