package com.coordinadora.coordinadoraapp.service.pdf

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Base64

class PdfServiceManagerImpl(
    private val context: Context
) : PdfServiceManager {

    override suspend fun savePdf(pdfEncode: String, fileName: String): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val pdfBytes = decodePdfString(pdfEncode)

                val directory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "pruebacoordi")
                } else {
                    File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "pruebacoordi"
                    )
                }

                if (!directory.exists()) {
                    if (!directory.mkdirs()) {
                        return@withContext Result.failure(Exception("Cannot create directory"))
                    }
                }

                val file = File(directory, fileName)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/pruebacoordi")
                    }

                    val uri = context.contentResolver.insert(
                        MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                        contentValues
                    ) ?: return@withContext Result.failure(Exception("Cannot create file"))

                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        outputStream.write(pdfBytes)
                    }
                } else {
                    FileOutputStream(file).use { outputStream ->
                        outputStream.write(pdfBytes)
                    }
                }

                Result.success(file.absolutePath)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }


    override suspend fun pdfToBitmap(pdfEncode: String): List<Bitmap> =
        withContext(Dispatchers.IO) {
            var pdfFile: File? = null
            var fileDescriptor: ParcelFileDescriptor? = null
            var pdfRenderer: PdfRenderer? = null

            try {
                val pdfBytes = decodePdfString(pdfEncode)

                pdfFile = File(context.cacheDir, "temp_${System.currentTimeMillis()}.pdf")
                pdfFile.writeBytes(pdfBytes)

                fileDescriptor = ParcelFileDescriptor.open(
                    pdfFile,
                    ParcelFileDescriptor.MODE_READ_ONLY
                )

                pdfRenderer = PdfRenderer(fileDescriptor)

                val bitmaps = mutableListOf<Bitmap>()

                for (pageIndex in 0 until pdfRenderer.pageCount) {
                    pdfRenderer.openPage(pageIndex).use { page ->
                        val originalWidth = page.width
                        val originalHeight = page.height

                        val scale = calculateOptimalScale(originalWidth, originalHeight)

                        val bitmap = Bitmap.createBitmap(
                            (originalWidth * scale).toInt(),
                            (originalHeight * scale).toInt(),
                            Bitmap.Config.ARGB_8888
                        )

                        page.render(
                            bitmap,
                            null,
                            null,
                            PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                        )

                        bitmaps.add(bitmap)
                    }
                }

                bitmaps

            } catch (e: Exception) {
                throw Exception("Error processing PDF: ${e.message}", e)
            } finally {
                try {
                    pdfRenderer?.close()
                    fileDescriptor?.close()
                    pdfFile?.delete()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    private fun calculateOptimalScale(width: Int, height: Int): Float {
        val maxDimension = 2048 // Max dimension for the bitmap
        val maxCurrentDimension = maxOf(width, height)
        return if (maxCurrentDimension > maxDimension) {
            maxDimension.toFloat() / maxCurrentDimension
        } else {
            1f
        }
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