package br.com.astrosoft.fornecedor.model.beans

import br.com.astrosoft.fornecedor.model.saci

class Fornecedor(
  val vendno: Int,
  val custno: Int,
  val fornecedor: String,
  val fornecedorSap: Int,
  val email: String,
  var obs: String,
                ) {
  val labelTitle: String
    get() = "Fornecedor: $vendno - $fornecedor"

  fun findNotas(): List<NotaEntrada> = saci.findNotas(filtroNotas())

  private fun filtroNotas() = FiltroNotaEntrada(vendno)

  companion object {
    fun findFornecedor(filtro: FiltroFornecedor): List<Fornecedor> = saci.findFornecedores(filtro)
  }
}

data class FiltroFornecedor(val query: String)