package br.com.astrosoft.fornecedor.view.fornecedor

import br.com.astrosoft.fornecedor.model.beans.Fornecedor
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.ITabFornecedorList
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.TabFornecedorListViewModel
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabFornecedorList(viewModel: TabFornecedorListViewModel) : TabPanelGrid<Fornecedor>(Fornecedor::class),
        ITabFornecedorList {
  override fun HorizontalLayout.toolBarConfig() {
    TODO("Not yet implemented")
  }

  override fun Grid<Fornecedor>.gridPanel() {
    TODO("Not yet implemented")
  }

  override fun isAuthorized(user: IUser): Boolean {
    TODO("Not yet implemented")
  }

  override val label: String
    get() = TODO("Not yet implemented")

  override fun updateComponent() {
    TODO("Not yet implemented")
  }
}
