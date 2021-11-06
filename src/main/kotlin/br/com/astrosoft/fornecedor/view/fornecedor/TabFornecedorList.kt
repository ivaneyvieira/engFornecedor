package br.com.astrosoft.fornecedor.view.fornecedor

import br.com.astrosoft.fornecedor.model.beans.FiltroFornecedor
import br.com.astrosoft.fornecedor.model.beans.Fornecedor
import br.com.astrosoft.fornecedor.model.beans.UserSaci
import br.com.astrosoft.fornecedor.view.fornecedor.columns.FornecedorViewColumns.fornecedorCliente
import br.com.astrosoft.fornecedor.view.fornecedor.columns.FornecedorViewColumns.fornecedorCodigo
import br.com.astrosoft.fornecedor.view.fornecedor.columns.FornecedorViewColumns.fornecedorNome
import br.com.astrosoft.fornecedor.view.fornecedor.columns.FornecedorViewColumns.fornecedorObs
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.ITabFornecedorList
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.TabFornecedorListViewModel
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnButton
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode.MULTI
import com.vaadin.flow.component.icon.VaadinIcon.FILE_TABLE
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT

class TabFornecedorList(val viewModel: TabFornecedorListViewModel) : TabPanelGrid<Fornecedor>(Fornecedor::class),
        ITabFornecedorList {
  private lateinit var edtFiltro: TextField

  override fun HorizontalLayout.toolBarConfig() {
    edtFiltro = textField("Filtro") {
      width = "300px"
      valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }
  }

  override fun Grid<Fornecedor>.gridPanel() {
    setSelectionMode(MULTI)
    addColumnButton(FILE_TABLE, "Notas", "Notas") { fornecedor ->
      DlgNota(viewModel).showDialogNota(fornecedor) {
        viewModel.updateView()
      }
    }

    fornecedorCodigo()
    fornecedorCliente()
    fornecedorNome()
    fornecedorObs()
  }

  override fun filtro(): FiltroFornecedor = FiltroFornecedor(query = edtFiltro.value ?: "")

  override fun updateFiltro(list: List<Fornecedor>) {
    updateGrid(list)
  }

  override fun isAuthorized(user: IUser): Boolean {
    val username = user as? UserSaci
    return username?.fornecedorList == true
  }

  override val label: String
    get() = "Fornecedor"

  override fun updateComponent() {
    viewModel.updateView()
  }
}
