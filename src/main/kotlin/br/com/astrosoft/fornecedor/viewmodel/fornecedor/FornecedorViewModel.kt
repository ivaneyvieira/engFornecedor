package br.com.astrosoft.fornecedor.viewmodel.fornecedor

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class FornecedorViewModel(view: IFornecedorView) : ViewModel<IFornecedorView>(view) {
  val tabFornecedorListViewModel = TabFornecedorListViewModel(this)

  override fun listTab() = listOf(
    view.tabFornecedorList,
                                 )
}

interface IFornecedorView : IView {
  val tabFornecedorList: ITabFornecedorList
}

