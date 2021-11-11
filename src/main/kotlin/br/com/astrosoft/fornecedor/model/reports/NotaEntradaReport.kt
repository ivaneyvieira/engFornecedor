package br.com.astrosoft.fornecedor.model.reports

import br.com.astrosoft.fornecedor.model.beans.NotaEntrada
import br.com.astrosoft.framework.model.reports.PropriedadeRelatorio
import br.com.astrosoft.framework.model.reports.ReportBuild
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.CENTER
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.RIGHT
import net.sf.dynamicreports.report.constant.PageOrientation.PORTRAIT

class NotaEntradaReport(val notas: List<NotaEntrada>, val subTitulo: String) : ReportBuild<NotaEntrada>() {
  init {
    columnInt(NotaEntrada::loja, width = 30, title = "Loja")
    columnInt(NotaEntrada::invno, width = 40, title = "NI")
    columnString(NotaEntrada::nota, aligment = CENTER, title = "Nota Fiscal")
    columnString(NotaEntrada::dataEmissaoStr, aligment = CENTER, title = "Emissão")
    columnString(NotaEntrada::dataEntradaStr, aligment = CENTER, title = "Entrada")
    columnString(NotaEntrada::dataVencimentoStr, aligment = CENTER, title = "Vencimento")
    columnString(NotaEntrada::consumo, aligment = RIGHT, title = "Consumo")
    columnString(NotaEntrada::ref, aligment = RIGHT, title = "Referência")
    columnDouble(NotaEntrada::valor, width = 50, title = "Valor")
  }

  override val propriedades: PropriedadeRelatorio
    get() = PropriedadeRelatorio(
      titulo = "Notas Fiscais",
      subTitulo = subTitulo,
      detailFonteSize = 8,
      pageOrientation = PORTRAIT,
                                )

  override fun listDataSource(): List<NotaEntrada> {
    return notas
  }

  companion object {
    fun processaRelatorio(notas: List<NotaEntrada>, subTitulo: String): ByteArray {
      val report = NotaEntradaReport(notas, subTitulo).makeReport()
      val printList = listOf(report.toJasperPrint())
      return renderReport(printList)
    }
  }
}