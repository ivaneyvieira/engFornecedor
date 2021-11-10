package br.com.astrosoft.fornecedor.view.fornecedor.columns

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

  fun Grid<NotaEntrada>.notaDataNota() = addColumnLocalDate(NotaEntrada::dataNF) {
    this.setHeader("Data NF")
  }

  fun Grid<NotaEntrada>.notaNota() = addColumnString(NotaEntrada::nota) {
    this.setHeader("Nota Fiscal")
  }

  fun Grid<NotaEntrada>.notaObservacao() = addColumnString(NotaEntrada::obs) {
    this.setHeader("Observação")
  }

  fun Grid<NotaEntrada>.notaValor() = addColumnDouble(NotaEntrada::valor) {
    this.setHeader("Valor")
  }

  fun Grid<NotaEntrada>.notaLido() = addColumnString(NotaEntrada::lido) {
    this.setHeader("Consumo")
  }

  fun Grid<NotaEntrada>.notaConsumo() = addColumnString(NotaEntrada::consumo) {
    this.setHeader("Consumo")
  }

  fun Grid<NotaEntrada>.notaDemanda() = addColumnString(NotaEntrada::demanda) {
    this.setHeader("Demanda")
  }

  fun Grid<NotaEntrada>.notaPeriodo() = addColumnString(NotaEntrada::periodo) {
    this.setHeader("Período")
  }

  fun Grid<NotaEntrada>.notaRef() = addColumnString(NotaEntrada::ref) {
    this.setHeader("Ref.")
  }

  fun Grid<NotaEntrada>.notaCod() = addColumnString(NotaEntrada::cod) {
    this.setHeader("Cod.")
  }

  fun Grid<NotaEntrada>.notaVencimento() = addColumnLocalDate(NotaEntrada::vencimento) {
    this.setHeader("Vencimento")
  }
}