package br.com.astrosoft.fornecedor.view.fornecedor

import br.com.astrosoft.fornecedor.model.beans.Fornecedor
import br.com.astrosoft.fornecedor.model.beans.NotaEntrada
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaDataNota
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaLoja
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaNI
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaNota
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaObservacao
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NotaEntradaViewColumns.notaValor
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.TabFornecedorListViewModel
import br.com.astrosoft.framework.util.format
import br.com.astrosoft.framework.view.SubWindowForm
import br.com.astrosoft.framework.view.addColumnButton
import com.github.mvysny.kaributools.getColumnBy
import com.vaadin.flow.component.Html
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridSortOrder
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.data.provider.SortDirection

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

      addColumnButton(VaadinIcon.FILE_TABLE, "Arquivos", "Arquivos") { nota ->
        DlgEditFile(viewModel).editFile(nota)
      }

      notaNI()
      notaLoja()
      notaDataNota()
      notaNota()
      notaObservacao()
      notaValor().apply {
        val totalPedido = listNotas.sumOf { it.valor }.format()
        setFooter(Html("<b><font size=4>${totalPedido}</font></b>"))
      }
      sort(listOf(GridSortOrder(getColumnBy(NotaEntrada::dataNF), SortDirection.ASCENDING)))
    }
  }
}
