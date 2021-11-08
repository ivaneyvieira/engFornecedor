package br.com.astrosoft.fornecedor.model.beans

import java.time.LocalDate

class NotaEntrada(
  val loja: Int,
  val siglaLoja: String,
  val dataNF: LocalDate,
  val nota: String,
  val valor: Double,
  val obs: String,
  val vendno: Int,
                 )

data class FiltroNotaEntrada(val vendno: Int)