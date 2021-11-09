package br.com.astrosoft.fornecedor.model.beans

import br.com.astrosoft.fornecedor.model.saci
import java.time.LocalDate

class NotaEntrada(
  val invno: Int,
  val loja: Int,
  val siglaLoja: String,
  val dataNF: LocalDate,
  val nota: String,
  val valor: Double,
  val obs: String,
  val vendno: Int,
                 ){
  fun listFiles() = saci.selectFile(this)
}

data class FiltroNotaEntrada(val vendno: Int, val loja: Int?)