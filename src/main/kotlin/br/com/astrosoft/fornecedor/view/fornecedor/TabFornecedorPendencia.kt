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
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.ITabFornecedorPendencia
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.TabFornecedorPendenciaViewModel
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
import com.vaadin.flow.component.icon.VaadinIcon.TRASH
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.treegrid.TreeGrid
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT

@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class TabFornecedorPendencia(val viewModel: TabFornecedorPendenciaViewModel) :
        TabPanelTree<Fornecedor>(Fornecedor::class), ITabFornecedorPendencia {
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
    addColumnButton(FILE_TABLE, "Arquivo", "Arquivo") { fornecedor ->
      DlgEditFilePendencia(viewModel).editFile(fornecedor) {
        viewModel.updateView()
      }
    }.setClassNameGenerator {
      if (it.listFilePendencias().isNotEmpty()) "marcaDiferenca" else ""
    }

    addColumnButton(TRASH, "Remove", "Remove") { fornecedor ->
      viewModel.remove(fornecedor)
    }

    addColumnButton(VaadinIcon.ARROW_RIGHT, "Concluir", "Concluir") { fornecedor ->
      viewModel.marcaConcluir(fornecedor)
    }

    this.withEditor(Fornecedor::class, openEditor = { _ ->
      (getColumnBy(Fornecedor::observacao).editorComponent as? Focusable<*>)?.focus()
    }, closeEditor = { binder ->
      viewModel.updateFornecedor(binder.bean)
      this.dataProvider.refreshItem(binder.bean)
    })

    fornecedorLoja()
    fornecedorCodigo()
    fornecedorCliente()
    fornecedorNome()
    fornecedorObs().textFieldEditor()
  }

  override fun filtro(): FiltroFornecedor =
          FiltroFornecedor(query = edtFiltro.value ?: "", status = EStatusFornecedor.Pendencia)

  override fun updateFiltro(list: List<Fornecedor>) {
    updateGrid(list, Fornecedor::findFornecedorLoja)
  }

  override fun isAuthorized(user: IUser): Boolean {
    val username = user as? UserSaci
    return username?.fornecedorPendencia == true
  }

  override val label: String
    get() = "Pendências"

  override fun updateComponent() {
    viewModel.updateView()
  }
}
