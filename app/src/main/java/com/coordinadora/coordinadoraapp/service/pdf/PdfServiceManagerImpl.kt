package com.coordinadora.coordinadoraapp.service.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.Base64

class PdfServiceManagerImpl(
    private val context: Context
) : PdfServiceManager {

    override fun savePdf(pdfEncode: String, fileName: String) {
        val pdfBytes = decodePdfString(pdfEncode)

        val directory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "pruebacoordi"
        )

        if (!directory.exists()) {
            val created = directory.mkdirs()
            if (created) {
                println("Directory created: ${directory.absolutePath}")
            } else {
                println("Failed to create directory: ${directory.absolutePath}")
            }
        }

        val file = File(directory, fileName)
        FileOutputStream(file).use { outputStream ->
            outputStream.write(pdfBytes)
        }
        println("PDF saved successfully at ${file.absolutePath}")

    }

    override suspend fun pdfToBitmap(pdfEncode: String): List<Bitmap> =
        withContext(Dispatchers.Default) {

            val pdfBytes = decodePdfString(pdfEncode)

            val pdfFile = File(context.cacheDir, "temp.pdf")
            pdfFile.writeBytes(pdfBytes)

            val fileDescriptor =
                ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(fileDescriptor)

            val bitmaps = (0 until pdfRenderer.pageCount).map { index ->
                async {
                    pdfRenderer.openPage(index).use { page ->
                        val bitmap =
                            Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)

                        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                        bitmap
                    }
                }
            }.awaitAll()

            pdfRenderer.close()
            fileDescriptor.close()
            pdfFile.delete()

            bitmaps
        }

    private fun decodePdfString(pdfEncode: String): ByteArray {
        val pdfBytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(pdfEncode)
        } else {
            android.util.Base64.decode(pdfEncode, android.util.Base64.DEFAULT)
        }
        return pdfBytes
    }


}