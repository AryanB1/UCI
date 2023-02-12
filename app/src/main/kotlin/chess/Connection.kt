package chess
import java.lang.ProcessBuilder
import java.io.*
class Connection {
    fun establishConnection(pathToEngine: String="default/path/to/engine"): Process {
        try {
            return ProcessBuilder(pathToEngine).start()
        } catch (error: Error) {
            throw (error)
        }
    }
    fun sendOutput(command: String, sendViaCloud: Boolean, pathToEngine: String=""): Boolean {
        return try {
            val connection = establishConnection(pathToEngine)
            if(sendViaCloud) {

            }
            val output = OutputStreamWriter(connection.outputStream)
            output.write(command + "\n")
            true
        } catch (error: Error) {
            false
        }
    }
    fun getInput(linkToCloud: String = ""): String {
        try {
            val connection = establishConnection()
            val input = InputStreamReader(connection.inputStream)
            if(linkToCloud.isEmpty()) {
                return input.toString()
            }
            Cloud().sendData(linkToCloud, input.toString())
            return "Data sent to cloud!"
        }
        catch (error: Error) {
            return error.toString()
        }
    }
}