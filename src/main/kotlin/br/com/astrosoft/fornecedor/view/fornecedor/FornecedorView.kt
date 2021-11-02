package br.com.astrosoft.fornecedor.view.fornecedor

import br.com.astrosoft.fornecedor.model.beans.UserSaci
import br.com.astrosoft.fornecedor.view.DevFornecedorLayout
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.FornecedorViewModel
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.IFornecedorView
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.ViewLayout
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(layout = DevFornecedorLayout::class)
@PageTitle("Fornecedor")
@CssImport("./styles/gridTotal.css")
class FornecedorView : ViewLayout<FornecedorViewModel>(), IFornecedorView {
  override val viewModel: FornecedorViewModel = FornecedorViewModel(this)
  override val tabFornecedorList = TabFornecedorList(viewModel.tabFornecedorListViewModel)

  override fun isAccept(user: IUser): Boolean {
    val userSaci = user as? UserSaci ?: return false
    return userSaci.fornecedorList
  }

  init {
    addTabSheat(viewModel)
  }
}

