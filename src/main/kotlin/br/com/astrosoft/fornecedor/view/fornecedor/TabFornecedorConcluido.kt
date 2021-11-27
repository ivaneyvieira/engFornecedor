package br.com.astrosoft.fornecedor.view.fornecedor

import br.com.astrosoft.fornecedor.model.beans.EStatusFornecedor
import br.com.astrosoft.fornecedor.model.beans.FiltroFornecedor
import br.com.astrosoft.fornecedor.model.beans.Fornecedor
import br.com.astrosoft.fornecedor.model.beans.UserSaci
import br.com.astrosoft.fornecedor.view.fornecedor.columns.FornecedorViewColumns.fornecedorCliente
import br.com.astrosoft.fornecedor.view.fornecedor.columns.FornecedorViewColumns.fornecedorCodigo
import br.com.astrosoft.fornecedor.view.fornecedor.columns.FornecedorViewColumns.fornecedorLoja
import br.com.astrosoft.fornecedor.view.fornecedor.columns.FornecedorViewColumns.fornecedorNome
import br.com.astrosoft.fornecedor.view.fornecedor.columns.FornecedorViewColumns.fornecedorObs
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.ITabFornecedorConcluido
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.TabFornecedorConcluidoViewModel
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelTree
import br.com.astrosoft.framework.view.addColumnButton
import br.com.astrosoft.framework.view.textFieldEditor
import br.com.astrosoft.framework.view.withEditor
import com.github.mvysny.karibudsl.v10.textField
import com.github.mvysny.kaributools.getColumnBy
import com.vaadin.flow.component.Focusable
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.icon.VaadinIcon.FILE_TABLE
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.treegrid.TreeGrid
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT

@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class TabFornecedorConcluido(val viewModel: TabFornecedorConcluidoViewModel) :
        TabPanelTree<Fornecedor>(Fornecedor::class), ITabFornecedorConcluido {
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

  override fun TreeGrid<Fornecedor>.gridPanel() {
    fornecedorLoja()
    fornecedorCodigo()
    fornecedorCliente()
    fornecedorNome()
    fornecedorObs()
  }

  override fun filtro(): FiltroFornecedor =
          FiltroFornecedor(query = edtFiltro.value ?: "", status = EStatusFornecedor.Concluido)

  override fun updateFiltro(list: List<Fornecedor>) {
    updateGrid(list, Fornecedor::findFornecedorLoja)
  }

  override fun isAuthorized(user: IUser): Boolean {
    val username = user as? UserSaci
    return username?.fornecedorConcluido == true
  }

  override val label: String
    get() = "Concluir"

  override fun updateComponent() {
    viewModel.updateView()
  }
}
