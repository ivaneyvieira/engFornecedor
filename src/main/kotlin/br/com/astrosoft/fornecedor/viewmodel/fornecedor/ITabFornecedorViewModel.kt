package br.com.astrosoft.fornecedor.viewmodel.fornecedor

import br.com.astrosoft.fornecedor.model.beans.Fornecedor
import br.com.astrosoft.fornecedor.model.beans.NFFile
import br.com.astrosoft.fornecedor.model.beans.NotaEntrada

interface ITabFornecedorViewModel {
  fun deleteFile(file: NFFile?)

  fun insertFile(file: NFFile?)
  fun imprimirRelatorio(fornecedor: Fornecedor?, notas: List<NotaEntrada>)
  fun salvaNota(nota: NotaEntrada?)
}