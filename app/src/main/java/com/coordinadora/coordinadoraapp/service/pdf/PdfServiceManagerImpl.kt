package com.coordinadora.coordinadoraapp.service.pdf

import android.os.Build
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.util.Base64

class PdfServiceManagerImpl : PdfServiceManager {

    override fun savePdf(pdfEncode: String, fileName: String) {
        val pdfBytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(pdfEncode)
        } else {
            android.util.Base64.decode(pdfEncode, android.util.Base64.DEFAULT)
        }

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
}