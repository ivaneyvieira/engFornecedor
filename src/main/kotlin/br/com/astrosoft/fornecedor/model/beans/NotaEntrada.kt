package br.com.astrosoft.fornecedor.model.beans

import br.com.astrosoft.fornecedor.model.saci
import java.time.LocalDate

data class NotaEntrada(val invno: Int,
                  val loja: Int,
                  val siglaLoja: String,
                  val dataNF: LocalDate,
                  val nota: String,
                  val valor: Double,
                  val obs: String,
                  val vendno: Int,
                  val dataVencimento: LocalDate?) {
  fun listFiles() = saci.selectFile(this)

  val consumo
    get() = "CONSUMO: *([0-9]+)?".regexGroup(obs)
  val demanda
    get() = "DEMANDA: *([0-9/]+)?".regexGroup(obs)
  val periodo
    get() = "DE *([0-9/]+ *A *[0-9/]+)?".regexGroup(obs)
  val ref
    get() = "REF.? *([0-9/]+)?".regexGroup(obs)
  val cod
    get() = "COD.? *([0-9\\-]+)?".regexGroup(obs)
  val lido
    get() = "LIDO *([0-9]+ ?- ?[0-9]+ ?: ?[0-9]*)".regexGroup(obs)

  private fun String.regexGroup(text: String, pos: Int = 1) = this.toRegex().find(text)?.groupValues?.getOrNull(pos)
}

data class FiltroNotaEntrada(val vendno: Int, val loja: Int?)