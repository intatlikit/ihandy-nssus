package com.nssus.ihandy.data.util

import android.os.Environment
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

object FileUtil {

    fun writeOrAppendTextToFile(
        fileName: String,
        text: String,
        onWriteFileSuccess: () -> Unit,
        onWriteFileError: (String) -> Unit
    ) {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(downloadsDir, fileName)

        try {
            // Ensure directories exist if a path is included
            if (file.parentFile != null && file.parentFile?.exists() == false)
                file.parentFile?.mkdirs() // Create directories if they don't exist

            BufferedWriter(FileWriter(file, file.exists())).use { writer -> // file.exists() is the checked flag to append text
                writer.write(text)
                writer.newLine() // Add a newline after writing the text if appending
            }

            onWriteFileSuccess()
        } catch (e: Exception) {
            e.printStackTrace()

            onWriteFileError(e.message ?: "Failed to write to file")
        }
    }

}