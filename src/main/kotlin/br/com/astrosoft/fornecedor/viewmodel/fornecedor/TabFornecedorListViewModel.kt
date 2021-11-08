package br.com.astrosoft.fornecedor.viewmodel.fornecedor

import br.com.astrosoft.fornecedor.model.beans.FiltroFornecedor
import br.com.astrosoft.fornecedor.model.beans.Fornecedor
import br.com.astrosoft.framework.viewmodel.ITabView


class TabFornecedorListViewModel(val viewModel: FornecedorViewModel) {
  fun updateView() {
    val filtro = subView.filtro()
    val list = Fornecedor.findFornecedor(filtro)
    subView.updateFiltro(list)
  }

  private val subView
    get() = viewModel.view.tabFornecedorList
}

interface ITabFornecedorList : ITabView{
  fun filtro() : FiltroFornecedor
  fun updateFiltro(list: List<Fornecedor>)
}