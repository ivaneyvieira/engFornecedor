package br.com.astrosoft.fornecedor.viewmodel.fornecedor

import br.com.astrosoft.fornecedor.model.beans.*
import br.com.astrosoft.fornecedor.model.reports.NotaEntradaReport
import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.framework.viewmodel.fail

class TabFornecedorPendenciaViewModel(val viewModel: FornecedorViewModel) : ITabFornecedorViewModel {
  fun updateView() {
    val filtro = subView.filtro()
    val list = Fornecedor.findFornecedorLoja(filtro)
    subView.updateFiltro(list)
  }

  override fun deleteFile(file: NFFile?) = viewModel.exec {
    file?.apply {
      this.delete()
    }
  }

  override fun insertFile(file: NFFile?) = viewModel.exec {
    file?.apply {
      this.insert()
    }
  }

  fun remove(fornecedor: Fornecedor?) = viewModel.exec {
    fornecedor ?: fail("O fonecedor não foi selecionado")
    fornecedor.status = EStatusFornecedor.Normal
    fornecedor.update()
    subView.updateComponent()
  }

  fun marcaConcluir(fornecedor: Fornecedor?) = viewModel.exec {
    fornecedor ?: fail("O fonecedor não foi selecionado")
    fornecedor.status = EStatusFornecedor.Concluido
    fornecedor.update()
    subView.updateComponent()
  }

  override fun salvaNota(nota: NotaEntrada?) = viewModel.exec {
    nota ?: fail("Nota não selecionada")
    nota.saveNotas()
  }

  override fun imprimirRelatorio(fornecedor: Fornecedor?, notas: List<NotaEntrada>) = viewModel.exec {
    notas.ifEmpty { fail("Não há nota selecionado") }
    val report = NotaEntradaReport.processaRelatorio(notas, fornecedor?.labelTitle() ?: "")
    viewModel.showReport("Fornecedor", report)
  }

  fun updateFornecedor(fornecedor: Fornecedor?) {
    fornecedor ?: fail("O fonecedor não foi selecionado")
    fornecedor.update()
    subView.updateComponent()
  }

  private val subView
    get() = viewModel.view.tabFornecedorPendencia
}

interface ITabFornecedorPendencia : ITabView {
  fun filtro(): FiltroFornecedor
  fun updateFiltro(list: List<Fornecedor>)
}