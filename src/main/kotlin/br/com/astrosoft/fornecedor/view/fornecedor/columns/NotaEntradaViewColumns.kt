package br.com.astrosoft.fornecedor.view.fornecedor.columns

import br.com.astrosoft.fornecedor.model.beans.Fornecedor
import br.com.astrosoft.fornecedor.model.beans.NotaEntrada
import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnString
import com.vaadin.flow.component.grid.Grid

object NotaEntradaViewColumns {
  fun Grid<NotaEntrada>.notaNI() = addColumnInt(NotaEntrada::invno) {
    this.setHeader("NI")
  }

  fun Grid<NotaEntrada>.notaLoja() = addColumnInt(NotaEntrada::loja) {
    this.setHeader("Loja")
  }

  fun Grid<NotaEntrada>.notaDataNota()= addColumnLocalDate(NotaEntrada::dataNF) {
    this.setHeader("Data NF")
  }

  fun Grid<NotaEntrada>.notaNota()= addColumnString(NotaEntrada::nota) {
    this.setHeader("Nota Fiscal")
  }

  fun Grid<NotaEntrada>.notaObservacao()= addColumnString(NotaEntrada::obs) {
    this.setHeader("Observação")
  }

  fun Grid<NotaEntrada>.notaValor()= addColumnDouble(NotaEntrada::valor) {
    this.setHeader("Valor")
  }
}