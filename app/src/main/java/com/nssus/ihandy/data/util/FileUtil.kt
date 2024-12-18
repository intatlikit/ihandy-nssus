package com.nssus.ihandy.data.util

import android.os.Environment
import org.apache.commons.net.PrintCommandListener
import org.apache.commons.net.ftp.FTPClient
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.PrintWriter

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

    suspend fun uploadFileUsingFTP(
        filePath: String,
        server: String,
        username: String,
        password: String,
        remoteDirectory: String = "/",
        port: Int = 21
    ): Boolean {
        val ftpClient = FTPClient()
        ftpClient.addProtocolCommandListener(PrintCommandListener(PrintWriter(System.out), true))  // Debugging FTP commands
        var inputStream: FileInputStream? = null

        return try {
            println("Connecting to FTP server: $server:$port")
            ftpClient.connect(server, port)

            // Check if the connection was successful
            if (!ftpClient.isConnected) {
                throw Exception("Failed to connect to FTP server: $server:$port")
            }

            println("Logging in with username: $username")
            if (!ftpClient.login(username, password)) {
                throw Exception("Failed to login. Check username/password.")
            }

            // Set FTP mode (Passive Mode)
            ftpClient.enterLocalPassiveMode()

            // Set timeout values for connection and data transfer
            ftpClient.connectTimeout = 10000  // 10 seconds timeout
//            ftpClient.dataTimeout = 10000     // 10 seconds timeout

            // Prepare the file to upload
            val file = File(filePath)
            if (!file.exists()) {
                throw Exception("File does not exist: $filePath")
            }

            inputStream = FileInputStream(file)
            val remoteFilePath = "$remoteDirectory/${file.name}".replace("//", "/")

            println("Uploading file: $remoteFilePath")
            val success = ftpClient.storeFile(remoteFilePath, inputStream)
            if (success) {
                println("File uploaded successfully to $remoteFilePath")
            } else {
                throw Exception("File upload failed for: $remoteFilePath")
            }
            success
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
            false
        } finally {
            inputStream?.close()
            if (ftpClient.isConnected) {
                try {
                    ftpClient.logout()
                    ftpClient.disconnect()
                } catch (e: Exception) {
                    println("Error closing FTP connection: ${e.message}")
                }
            }
        }
    }

}