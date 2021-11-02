package br.com.astrosoft.fornecedor.viewmodel.fornecedor

import br.com.astrosoft.framework.viewmodel.ITabView


class TabFornecedorListViewModel(val viewModel: FornecedorViewModel) {
  val subView
    get() = viewModel.view.tabFornecedorList
}

interface ITabFornecedorList : ITabView