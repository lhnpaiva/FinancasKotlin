package alura.com.financask.delegate

import alura.com.financask.model.Transacao

interface TransacaoDelegate {
    fun delegate(transacao: Transacao)

}