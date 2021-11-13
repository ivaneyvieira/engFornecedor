package br.com.astrosoft.fornecedor.model

import br.com.astrosoft.devolucao.model.NotaEntradaVO
import br.com.astrosoft.fornecedor.model.beans.*
import br.com.astrosoft.framework.model.Config.appName
import br.com.astrosoft.framework.model.DB
import br.com.astrosoft.framework.model.QueryDB
import br.com.astrosoft.framework.util.toSaciDate
import org.sql2o.Query

class QuerySaci : QueryDB(driver, url, username, password) {
  fun findUser(login: String?): UserSaci? {
    login ?: return null
    val sql = "/sqlSaci/userSenha.sql"
    return query(sql, UserSaci::class) {
      addParameter("login", login)
      addParameter("appName", appName)
    }.firstOrNull()
  }

  fun findAllUser(): List<UserSaci> {
    val sql = "/sqlSaci/userSenha.sql"
    return query(sql, UserSaci::class) {
      addParameter("login", "TODOS")
      addParameter("appName", appName)
    }
  }

  fun allLojas(): List<Loja> {
    val sql = "/sqlSaci/listLojas.sql"
    return query(sql, Loja::class)
  }

  fun findLojaByCnpj(cnpj: String): Loja? {
    val sql = "/sqlSaci/findLojaByCnpj.sql"
    return query(sql, Loja::class) {
      addOptionalParameter("cnpj", cnpj)
    }.firstOrNull()
  }

  fun updateUser(user: UserSaci) {
    val sql = "/sqlSaci/updateUser.sql"
    script(sql) {
      addOptionalParameter("login", user.login)
      addOptionalParameter("bitAcesso", user.bitAcesso)
      addOptionalParameter("loja", user.storeno)
      addOptionalParameter("appName", appName)
    }
  }

  fun representante(vendno: Int): List<Representante> {
    val sql = "/sqlSaci/representantes.sql"
    return query(sql, Representante::class) {
      addOptionalParameter("vendno", vendno)
    }
  }

  fun listFuncionario(codigo: String): Funcionario? {
    val sql = "/sqlSaci/listFuncionario.sql"
    return query(sql, Funcionario::class) {
      addOptionalParameter("codigo", codigo.toIntOrNull() ?: 0)
    }.firstOrNull()
  }

  fun saveNotaNdd(notas: List<NotaEntradaVO>) {
    val sql = "/sqlSaci/saveNotaNdd.sql"
    script(sql, notas.map { nota ->
      { q: Query ->
        q.addOptionalParameter("id", nota.id)
        q.addOptionalParameter("numero", nota.numero)
        q.addOptionalParameter("cancelado", nota.cancelado)
        q.addOptionalParameter("serie", nota.serie)
        q.addOptionalParameter("dataEmissao", nota.dataEmissao?.toSaciDate() ?: 0)
        q.addOptionalParameter("cnpjEmitente", nota.cnpjEmitente)
        q.addOptionalParameter("nomeFornecedor", nota.nomeFornecedor)
        q.addOptionalParameter("cnpjDestinatario", nota.cnpjDestinatario)
        q.addOptionalParameter("ieEmitente", nota.ieEmitente)
        q.addOptionalParameter("ieDestinatario", nota.ieDestinatario)
        q.addOptionalParameter("baseCalculoIcms", nota.valorNota ?: nota.baseCalculoIcms)
        q.addOptionalParameter("baseCalculoSt", nota.baseCalculoSt)
        q.addOptionalParameter("valorTotalProdutos", nota.valorTotalProdutos)
        q.addOptionalParameter("valorTotalIcms", nota.valorTotalIcms)
        q.addOptionalParameter("valorTotalSt", nota.valorTotalSt)
        q.addOptionalParameter("baseCalculoIssqn", nota.baseCalculoIssqn)
        q.addOptionalParameter("chave", nota.chave)
        q.addOptionalParameter("status", nota.status)
        q.addOptionalParameter("xmlAut", nota.xmlAut)
        q.addOptionalParameter("xmlCancelado", nota.xmlCancelado)
        q.addOptionalParameter("xmlNfe", nota.xmlNfe)
        q.addOptionalParameter("xmlDadosAdicionais", nota.xmlDadosAdicionais)
      }
    })
  }

  fun findFornecedores(filtro: FiltroFornecedor): List<Fornecedor> {
    val sql = "/sqlSaci/findFornecedor.sql"
    return query(sql, Fornecedor::class) {
      this.addOptionalParameter("query", filtro.query)
    }
  }

  fun findNotas(filtro: FiltroNotaEntrada): List<NotaEntrada> {
    val sql = "/sqlSaci/findNotaEntrada.sql"
    return query(sql, NotaEntrada::class) {
      this.addOptionalParameter("vendno", filtro.vendno)
      this.addOptionalParameter("loja", filtro.loja ?: 0)
    }
  }

  fun saveNotas(nota: NotaEntrada) {
    val sql = "/sqlSaci/saveNotaEntrada.sql"
    return script(sql) {
      this.addOptionalParameter("ni", nota.invno)
      this.addOptionalParameter("obsEdit", nota.obsEdit)
    }
  }

  //Files Notas
  fun insertFile(file: NFFile) {
    val sql = "/sqlSaci/fileInsert.sql"
    script(sql) {
      addOptionalParameter("storeno", file.storeno)
      addOptionalParameter("pdvno", file.pdvno)
      addOptionalParameter("xano", file.xano)
      addOptionalParameter("date", file.date.toSaciDate())
      addOptionalParameter("nome", file.nome)
      addOptionalParameter("file", file.file)
    }
  }

  fun updateFile(file: NFFile) {
    val sql = "/sqlSaci/fileUpdate.sql"
    script(sql) {
      addOptionalParameter("storeno", file.storeno)
      addOptionalParameter("pdvno", file.pdvno)
      addOptionalParameter("xano", file.xano)
      addOptionalParameter("date", file.date.toSaciDate())
      addOptionalParameter("nome", file.nome)
      addOptionalParameter("file", file.file)
    }
  }

  fun deleteFile(file: NFFile) {
    val sql = "/sqlSaci/fileDelete.sql"
    script(sql) {
      addOptionalParameter("storeno", file.storeno)
      addOptionalParameter("pdvno", file.pdvno)
      addOptionalParameter("xano", file.xano)
      addOptionalParameter("date", file.date.toSaciDate())
      addOptionalParameter("nome", file.nome)
    }
  }

  fun selectFile(nfs: NotaEntrada): List<NFFile> {
    val sql = "/sqlSaci/fileSelect.sql"
    return query(sql, NFFile::class) {
      addOptionalParameter("storeno", nfs.loja)
      addOptionalParameter("pdvno", 8888)
      addOptionalParameter("xano", nfs.invno)
    }
  }

  fun selectFile(fornecedor: Fornecedor): List<NFFile> {
    val sql = "/sqlSaci/fileSelect.sql"
    return query(sql, NFFile::class) {
      addOptionalParameter("storeno", fornecedor.loja ?: 0)
      addOptionalParameter("pdvno", 7777)
      addOptionalParameter("xano", fornecedor.vendno)
    }
  }

  companion object {
    private val db = DB("saci")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
    val ipServer: String? = url.split("/").getOrNull(2)
  }
}

val saci = QuerySaci()
