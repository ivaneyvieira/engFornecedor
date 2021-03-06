package br.com.astrosoft.fornecedor.model.beans

import br.com.astrosoft.fornecedor.model.saci
import br.com.astrosoft.framework.util.format
import java.time.LocalDate

data class NotaEntrada(val invno: Int,
                       val loja: Int,
                       val siglaLoja: String,
                       val dataEntrada: LocalDate,
                       val dataEmissao: LocalDate,
                       val nota: String,
                       val valor: Double,
                       val obs: String,
                       val vendno: Int,
                       val dataVencimento: LocalDate?,
                       var obsEdit: String,
                       val litros: Int?) {
  fun listFiles() = saci.selectFile(this)

  private val consumoDireto
    get() = "CONSUMO: *([0-9]+)?".regexGroup(obs)
  private val consumoLeitura
    get() = "LIDO *[0-9]+ ?- ?[0-9]+ ?: ?([0-9]*)".regexGroup(obs)
  val consumo
    get() = consumoDireto ?: consumoLeitura
  val lido
    get() = "LIDO *([0-9]+ ?- ?[0-9]+) ?: ?[0-9]*".regexGroup(obs)
  val demanda
    get() = "DEMANDA: *([0-9/]+)?".regexGroup(obs)
  val periodo
    get() = "DE *([0-9/]+ *A *[0-9/]+)".regexGroup(obs)
  val ref
    get() = "REF.? *([0-9/]+)?".regexGroup(obs)
  val cod
    get() = "COD.? *([0-9\\-]+)?".regexGroup(obs)

  private fun String.regexGroup(text: String, pos: Int = 1) = this.toRegex().find(text)?.groupValues?.getOrNull(pos)

  val dataEmissaoStr
    get() = dataEmissao.format()
  val dataEntradaStr
    get() = dataEntrada.format()
  val dataVencimentoStr
    get() = dataVencimento.format()

  fun saveNotas() = saci.saveNotas(this)
}

data class FiltroNotaEntrada(val vendno: Int, val loja: Int?)