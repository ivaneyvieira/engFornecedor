package br.com.astrosoft.fornecedor.viewmodel.fornecedor

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class FornecedorViewModel(view: IFornecedorView) : ViewModel<IFornecedorView>(view) {
  val tabFornecedorListViewModel = TabFornecedorListViewModel(this)
  val tabFornecedorPendenciaViewModel = TabFornecedorPendenciaViewModel(this)
  val tabFornecedorConcluidoViewModel = TabFornecedorConcluidoViewModel(this)

  override fun listTab() = listOf(
    view.tabFornecedorList, view.tabFornecedorPendencia, view.tabFornecedorConcluido,
                                 )
}

interface IFornecedorView : IView {
  val tabFornecedorList: ITabFornecedorList
  val tabFornecedorPendencia: ITabFornecedorPendencia
  val tabFornecedorConcluido: ITabFornecedorConcluido
}

