package br.com.astrosoft.fornecedor.viewmodel.fornecedor

import br.com.astrosoft.fornecedor.model.beans.FiltroFornecedor
import br.com.astrosoft.fornecedor.model.beans.Fornecedor
import br.com.astrosoft.fornecedor.model.beans.NFFile
import br.com.astrosoft.fornecedor.model.beans.NotaEntrada
import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.framework.viewmodel.fail

class TabFornecedorListViewModel(val viewModel: FornecedorViewModel) {
  fun updateView() {
    val filtro = subView.filtro()
    val list = Fornecedor.findFornecedor(filtro)
    subView.updateFiltro(list)
  }

  fun deleteFile(file: NFFile?) = viewModel.exec {
    file?.apply {
      this.delete()
    }
  }

  fun insertFile(file: NFFile?) = viewModel.exec {
    file?.apply {
      this.insert()
    }
  }

  fun salvaNota(nota: NotaEntrada?) = viewModel.exec {
    nota ?: fail("Nota não selecionada")
    nota.saveNotas()
  }

  private val subView
    get() = viewModel.view.tabFornecedorList
}

interface ITabFornecedorList : ITabView{
  fun filtro() : FiltroFornecedor
  fun updateFiltro(list: List<Fornecedor>, )
}