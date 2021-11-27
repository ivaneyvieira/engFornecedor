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
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.ITabFornecedorList
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.TabFornecedorListViewModel
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelTree
import br.com.astrosoft.framework.view.addColumnButton
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.icon.VaadinIcon.FILE_TABLE
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.treegrid.TreeGrid
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT

@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class TabFornecedorList(val viewModel: TabFornecedorListViewModel) : TabPanelTree<Fornecedor>(Fornecedor::class),
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

  override fun TreeGrid<Fornecedor>.gridPanel() { //setSelectionMode(MULTI)

    addColumnButton(FILE_TABLE, "Notas", "Notas") { fornecedor ->
      DlgNota(viewModel).showDialogNota(fornecedor) {
        viewModel.updateView()
      }
    }.setClassNameGenerator {
      if (it.findNotas().isNotEmpty()) "marcaDiferenca" else ""
    }

    addColumnButton(FILE_TABLE, "Contrato", "Contrato") { fornecedor ->
      DlgEditFileContrato(viewModel).editFile(fornecedor) {
        viewModel.updateView()
      }
    }.setClassNameGenerator {
      if (it.listFileContratos().isNotEmpty()) "marcaDiferenca" else ""
    }

    addColumnButton(VaadinIcon.ARROW_RIGHT, "Pendência", "Pend") { fornecedor ->
      viewModel.marcaPendencia(fornecedor)
    }.setClassNameGenerator {
      if (it.status == EStatusFornecedor.Pendencia) "marcaDiferenca" else ""
    }

    fornecedorCodigo()
    fornecedorLoja()
    fornecedorCliente()
    fornecedorNome()
    fornecedorObs()
  }

  override fun filtro(): FiltroFornecedor =
          FiltroFornecedor(query = edtFiltro.value ?: "", status = EStatusFornecedor.Normal)

  override fun updateFiltro(list: List<Fornecedor>) {
    updateGrid(list, Fornecedor::findFornecedorLoja)
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
