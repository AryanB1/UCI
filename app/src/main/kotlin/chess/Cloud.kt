package chess
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL
class Cloud {
    fun sendData(dataToSend: String, urlToCloudInstance: String = ""): Boolean {
        val url = URL(urlToCloudInstance)
        val connection = url.openConnection()
        connection.doOutput = true
        try {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            connection.setRequestProperty("Content-Header", "curl -H \"API_PRIVATE_KEY: API_KEY\" CLOUD_URL")
            connection.setRequestProperty("Content-Length", dataToSend.length.toString())
            DataOutputStream(connection.getOutputStream()).use { it.writeBytes(dataToSend) }
            BufferedReader(InputStreamReader(connection.getInputStream())).use { bf ->
                var line: String
                while (bf.readLine().also { line = it } != null) {
                    println(line)
                }
            }
            return true
        } catch (error: Error) {
            return false
        }
    }
    fun returnData( dataToSend: String, urlToLocalConnection: String = ""): Boolean {
        val url = URL(urlToLocalConnection)
        val connection = url.openConnection()
        connection.doOutput = true
        try {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            connection.setRequestProperty("Content-Header", "curl -H \"LOCAL_PRIVATE_KEY: LOCAL_KEY\" LOCAL_PATH")
            connection.setRequestProperty("Content-Length", dataToSend.length.toString())
            DataOutputStream(connection.getOutputStream()).use { it.writeBytes(dataToSend) }
            BufferedReader(InputStreamReader(connection.getInputStream())).use { bf ->
                var line: String
                while (bf.readLine().also { line = it } != null) {
                    println(line)
                }
            }
            return true
        } catch (error: Error) {
            return false
        }
    }
}