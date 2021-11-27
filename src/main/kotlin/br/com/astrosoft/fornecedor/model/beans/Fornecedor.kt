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
  fun labelTitle(subTitle: String? = null): String {
    val descricaoLoja = if (loja == null) "Todas as lojas" else "Loja $loja"
    return "${if (subTitle == null) "" else "$subTitle : "}Fornecedor: $vendno - $fornecedor $descricaoLoja"
  }

  fun findNotas(): List<NotaEntrada> = saci.findNotas(filtroNotas())

  private fun filtroNotas() = FiltroNotaEntrada(vendno, loja)

  fun findFornecedorLoja() =
          if (loja == null) findFornecedorLoja(FiltroFornecedor(vendno.toString(), status = EStatusFornecedor.Normal))
          else emptyList()

  fun listFileContratos() = saci.selectFile(this, 7777)

  fun listFilePendencias() = saci.selectFile(this, 6666)

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

data class FiltroFornecedor(val query: String, val status: EStatusFornecedor)

enum class EStatusFornecedor(val cod: Int) {
  Normal(0), Pendencia(1), Concluido(2);

  companion object {
    fun find(cod: Int): EStatusFornecedor? = values().firstOrNull { it.cod == cod }
  }
}