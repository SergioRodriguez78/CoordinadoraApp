package com.coordinadora.coordinadoraapp.service.pdf

import android.graphics.Bitmap

interface PdfServiceManager {
    suspend fun savePdf(pdfEncode: String, fileName: String): Result<String>
    suspend fun pdfToBitmap(pdfEncode: String): List<Bitmap>
}
