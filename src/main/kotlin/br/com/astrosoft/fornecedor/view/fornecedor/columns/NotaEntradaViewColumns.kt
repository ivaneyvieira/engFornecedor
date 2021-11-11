package br.com.astrosoft.fornecedor.view.fornecedor.columns

import br.com.astrosoft.fornecedor.model.beans.NotaEntrada
import br.com.astrosoft.framework.view.*
import com.vaadin.flow.component.grid.Grid

object NotaEntradaViewColumns {
  fun Grid<NotaEntrada>.notaNI() = addColumnInt(NotaEntrada::invno) {
    this.setHeader("NI")
  }

  fun Grid<NotaEntrada>.notaLoja() = addColumnInt(NotaEntrada::loja) {
    this.setHeader("Loja")
  }

  fun Grid<NotaEntrada>.notaDataEntrada() = addColumnLocalDate(NotaEntrada::dataEntrada) {
    this.setHeader("Entrada")
    this.center()
  }

  fun Grid<NotaEntrada>.notaDataEmissao() = addColumnLocalDate(NotaEntrada::dataEmissao) {
    this.setHeader("Emissão")
    this.center()
  }

  fun Grid<NotaEntrada>.notaNota() = addColumnString(NotaEntrada::nota) {
    this.setHeader("Nota Fiscal")
    this.right()
  }

  fun Grid<NotaEntrada>.notaObservacao() = addColumnString(NotaEntrada::obs) {
    this.setHeader("Observação")
  }

  fun Grid<NotaEntrada>.notaObsEditor() = addColumnString(NotaEntrada::obsEdit) {
    this.setHeader("Obs")
  }

  fun Grid<NotaEntrada>.notaValor() = addColumnDouble(NotaEntrada::valor) {
    this.setHeader("Valor")
  }

  fun Grid<NotaEntrada>.notaLido() = addColumnString(NotaEntrada::lido) {
    this.setHeader("Leitura")
  }

  fun Grid<NotaEntrada>.notaConsumo() = addColumnString(NotaEntrada::consumo) {
    this.setHeader("Consumo")
    this.right()
  }

  fun Grid<NotaEntrada>.notaDemanda() = addColumnString(NotaEntrada::demanda) {
    this.setHeader("Demanda")
    this.right()
  }

  fun Grid<NotaEntrada>.notaPeriodo() = addColumnString(NotaEntrada::periodo) {
    this.setHeader("Período")
    this.center()
  }

  fun Grid<NotaEntrada>.notaRef() = addColumnString(NotaEntrada::ref) {
    this.setHeader("Referência")
  }

  fun Grid<NotaEntrada>.notaCod() = addColumnString(NotaEntrada::cod) {
    this.setHeader("Cod.")
  }

  fun Grid<NotaEntrada>.notaLitros() = addColumnInt(NotaEntrada::litros) {
    this.setHeader("Litros")
  }

  fun Grid<NotaEntrada>.notaVencimento() = addColumnLocalDate(NotaEntrada::dataVencimento) {
    this.setHeader("Vencimento")
    this.center()
  }
}