package alura.com.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {


    val receita: BigDecimal get() = somaPor(Tipo.RECEITA)


    val despesa: BigDecimal get() = somaPor(Tipo.DESPESA)


    val total get() = receita.subtract(despesa)

    private fun somaPor(tipo: Tipo): BigDecimal {
        val somaDeTransacoesPorTipo = BigDecimal(transacoes
            .filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble() })
        return somaDeTransacoesPorTipo

    }
}