package br.com.astrosoft.fornecedor.view.fornecedor

import br.com.astrosoft.fornecedor.model.beans.Fornecedor
import br.com.astrosoft.fornecedor.model.beans.NotaEntrada
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaCod
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaConsumo
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaDataEmissao
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaDataEntrada
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaDemanda
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaLido
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaLoja
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaNI
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaNota
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaObsEditor
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaObservacao
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaPeriodo
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaRef
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaValor
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaVencimento
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.TabFornecedorListViewModel
import br.com.astrosoft.framework.util.format
import br.com.astrosoft.framework.view.SubWindowForm
import br.com.astrosoft.framework.view.addColumnButton
import br.com.astrosoft.framework.view.textFieldEditor
import br.com.astrosoft.framework.view.withEditor
import com.github.mvysny.kaributools.getColumnBy
import com.vaadin.flow.component.Focusable
import com.vaadin.flow.component.Html
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridSortOrder
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.data.provider.SortDirection

@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class DlgNota(val viewModel: TabFornecedorListViewModel) {
  fun showDialogNota(fornecedor: Fornecedor?, onClose: (Dialog) -> Unit) {
    fornecedor ?: return
    lateinit var gridNota: Grid<NotaEntrada>
    val listNotas = fornecedor.findNotas()
    val form = SubWindowForm(fornecedor.labelTitle, toolBar = {}, onClose = onClose) {
      gridNota = createGridNotas(listNotas)
      gridNota
    }
    form.open()
  }

  private fun createGridNotas(listNotas: List<NotaEntrada>): Grid<NotaEntrada> {
    val gridDetail = Grid(NotaEntrada::class.java, false)
    return gridDetail.apply {
      addThemeVariants(GridVariant.LUMO_COMPACT)
      isMultiSort = false
      setSelectionMode(Grid.SelectionMode.MULTI)
      setItems(listNotas)

      this.withEditor(NotaEntrada::class, openEditor = { _ ->
        (getColumnBy(NotaEntrada::obsEdit).editorComponent as? Focusable<*>)?.focus()
      }, closeEditor = { binder ->
        viewModel.salvaNota(binder.bean)
        this.dataProvider.refreshItem(binder.bean)
      })

      addColumnButton(VaadinIcon.FILE_TABLE, "Arquivos", "Arquivos") { nota ->
        DlgEditFile(viewModel).editFile(nota)
      }.setClassNameGenerator {
        if (it.listFiles().isNotEmpty()) "marcaDiferenca" else ""
      }

      notaLoja()
      notaNI()
      notaNota()
      notaDataEmissao()
      notaDataEntrada()
      notaVencimento()
      //notaObservacao()
      if (listNotas.mapNotNull { it.lido }.isNotEmpty()) notaLido()
      if (listNotas.mapNotNull { it.consumo }.isNotEmpty()) notaConsumo()
      if (listNotas.mapNotNull { it.demanda }.isNotEmpty()) notaDemanda()
      if (listNotas.mapNotNull { it.periodo }.isNotEmpty()) notaPeriodo()
      if (listNotas.mapNotNull { it.ref }.isNotEmpty()) notaRef()
      if (listNotas.mapNotNull { it.cod }.isNotEmpty()) notaCod()
      notaObsEditor().textFieldEditor()
      notaValor().apply {
        val totalPedido = listNotas.sumOf { it.valor }.format()
        setFooter(Html("<b><font size=4>${totalPedido}</font></b>"))
      }
      sort(listOf(GridSortOrder(getColumnBy(NotaEntrada::dataEntrada), SortDirection.DESCENDING)))
    }
  }
}
