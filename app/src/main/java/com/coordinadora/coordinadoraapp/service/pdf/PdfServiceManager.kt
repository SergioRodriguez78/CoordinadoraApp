package com.coordinadora.coordinadoraapp.service.pdf

import android.graphics.Bitmap

interface PdfServiceManager {
    fun savePdf(pdfEncode: String, fileName: String)
    suspend fun pdfToBitmap(pdfEncode: String): List<Bitmap>
}
