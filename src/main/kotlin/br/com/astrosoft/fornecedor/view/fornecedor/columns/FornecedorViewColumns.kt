package br.com.astrosoft.fornecedor.view.fornecedor.columns

import br.com.astrosoft.fornecedor.model.beans.Fornecedor
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.treegrid.TreeGrid

object FornecedorViewColumns {
  fun TreeGrid<Fornecedor>.fornecedorCodigo() = this.addHierarchyColumn(Fornecedor::vendno).apply {
    setHeader("Cód Forn")
  }

  fun Grid<Fornecedor>.fornecedorLoja() = addColumnInt(Fornecedor::loja) {
    this.setHeader("Loja")
  }

  fun Grid<Fornecedor>.fornecedorCliente() = addColumnInt(Fornecedor::custno) {
    this.setHeader("Cliente")
  }

  fun Grid<Fornecedor>.fornecedorNome() = addColumnString(Fornecedor::fornecedor) {
    this.setHeader("Fornecedor")
  }

  fun Grid<Fornecedor>.fornecedorObs() = addColumnString(Fornecedor::observacao) {
    this.setHeader("Observação")
    this.isAutoWidth = false
    this.width = "200px"
  }
}