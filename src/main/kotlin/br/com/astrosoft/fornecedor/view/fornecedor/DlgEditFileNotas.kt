package br.com.astrosoft.fornecedor.view.fornecedor

import br.com.astrosoft.fornecedor.model.beans.NFFile
import br.com.astrosoft.fornecedor.model.beans.NotaEntrada
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NFFileViewColumns.nfFileData
import br.com.astrosoft.fornecedor.view.fornecedor.columns.NFFileViewColumns.nfFileDescricao
import br.com.astrosoft.fornecedor.viewmodel.fornecedor.ITabFornecedorViewModel
import br.com.astrosoft.framework.view.SubWindowForm
import br.com.astrosoft.framework.view.addColumnButton
import br.com.astrosoft.framework.view.showOutput
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.upload.FileRejectedEvent
import com.vaadin.flow.component.upload.Upload
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer

class DlgEditFileNotas(val viewModel: ITabFornecedorViewModel) {
  fun editFile(nota: NotaEntrada) {
    val grid = createFormEditFile(nota)
    val form = SubWindowForm("NF: ${nota.nota}", toolBar = { _ ->
      val (buffer, upload) = uploadFile()
      upload.addSucceededListener {
        val fileName = it.fileName
        val bytes = buffer.getInputStream(fileName).readBytes()
        val nfFile = NFFile.new(nota, fileName, bytes)
        viewModel.insertFile(nfFile)
        grid.setItems(nota.listFiles())
      }
    }) {
      grid
    }
    form.open()
  }

  private fun createFormEditFile(nota: NotaEntrada): Grid<NFFile> {
    val gridDetail = Grid(NFFile::class.java, false)
    return gridDetail.apply {
      addThemeVariants(GridVariant.LUMO_COMPACT)
      isMultiSort = false
      setItems(nota.listFiles()) //
      addColumnButton(VaadinIcon.EYE, "Visualizar", "Ver") { file ->
        val form = SubWindowForm(file.nome, toolBar = { }) {
          val div = Div()
          div.showOutput(file.nome, file.file)
          div
        }
        form.open()
      }
      addColumnButton(VaadinIcon.TRASH, "Remover arquivo", "Rem") { file ->
        viewModel.deleteFile(file)
        setItems(nota.listFiles())
      }
      nfFileDescricao()
      nfFileData()
    }
  }

  private fun HasComponents.uploadFile(): Pair<MultiFileMemoryBuffer, Upload> {
    val buffer = MultiFileMemoryBuffer()
    val upload = Upload(buffer)
    upload.setAcceptedFileTypes("image/jpeg", "image/png", "application/pdf", "text/plain")
    val uploadButton = Button("Arquivo Nota")
    upload.uploadButton = uploadButton
    upload.isAutoUpload = true
    upload.maxFileSize = 1024 * 1024 * 1024
    upload.addFileRejectedListener { event: FileRejectedEvent ->
      println(event.errorMessage)
    }
    upload.addFailedListener { event ->
      println(event.reason.message)
    }
    add(upload)
    return Pair(buffer, upload)
  }
}