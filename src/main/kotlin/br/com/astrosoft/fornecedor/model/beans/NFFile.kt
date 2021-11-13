package br.com.astrosoft.fornecedor.model.beans

import br.com.astrosoft.fornecedor.model.saci
import java.time.LocalDate

class NFFile(val storeno: Int,
             val pdvno: Int,
             val xano: Int,
             val date: LocalDate,
             var nome: String,
             var file: ByteArray) {
  fun insert() = saci.insertFile(this)

  fun update() = saci.updateFile(this)

  fun delete() = saci.deleteFile(this)

  companion object {
    fun new(nota: NotaEntrada, fileName: String, bytes: ByteArray): NFFile {
      return NFFile(storeno = nota.loja,
                    pdvno = 8888,
                    xano = nota.invno,
                    date = LocalDate.now(),
                    nome = fileName,
                    file = bytes)
    }

    fun new(fornecedor: Fornecedor, fileName: String, bytes: ByteArray): NFFile {
      return NFFile(storeno = fornecedor.loja ?: 0,
                    pdvno = 7777,
                    xano = fornecedor.vendno,
                    date = LocalDate.now(),
                    nome = fileName,
                    file = bytes)
    }
  }
}