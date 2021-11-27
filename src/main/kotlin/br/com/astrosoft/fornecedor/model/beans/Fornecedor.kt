package br.com.astrosoft.fornecedor.model.beans

import br.com.astrosoft.fornecedor.model.saci

class Fornecedor(
  val vendno: Int,
  val custno: Int,
  val fornecedor: String,
  var obs: String,
  val loja: Int?,
  var observacao: String,
  var status: EStatusFornecedor,
                ) {
  val labelTitle: String
    get() {
      val descricaoLoja = if (loja == null) "Todas as lojas" else "Loja $loja"
      return "Fornecedor: $vendno - $fornecedor $descricaoLoja"
    }

  fun findNotas(): List<NotaEntrada> = saci.findNotas(filtroNotas())

  private fun filtroNotas() = FiltroNotaEntrada(vendno, loja)

  fun findFornecedorLoja() = if (loja == null) findFornecedorLoja(FiltroFornecedor(vendno.toString(), status = 0))
  else emptyList()

  fun listFiles() = saci.selectFile(this)

  fun update() {
    saci.updateFornecedor(this)
  }

  companion object {
    fun findFornecedor(filtro: FiltroFornecedor): List<Fornecedor> = saci.findFornecedores(filtro).groupBy {
      it.vendno
    }.map { ent ->
      val value = ent.value
      Fornecedor(vendno = value.firstOrNull()?.vendno ?: 0,
                 custno = value.firstOrNull()?.custno ?: 0,
                 fornecedor = value.firstOrNull()?.fornecedor ?: "",
                 obs = value.firstOrNull()?.obs ?: "",
                 loja = null,
                 status = value.maxOf { it.status },
                 observacao = "")
    }

    fun findFornecedorLoja(filtro: FiltroFornecedor): List<Fornecedor> = saci.findFornecedores(filtro)
  }
}

data class FiltroFornecedor(val query: String, val status: Int)

enum class EStatusFornecedor(val cod: Int) {
  Normal(0), Pendencia(1), Concluido(2);

  companion object {
    fun find(cod: Int): EStatusFornecedor? = values().firstOrNull { it.cod == cod }
  }
}