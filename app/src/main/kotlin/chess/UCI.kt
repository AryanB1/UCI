package chess
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.Socket
class UCI(private val socket: Socket, hash: Double, nalimovPath: String, nalimovCache: Double,
          ponder: Boolean, ownBook: Boolean, multiPV: Double, UCI_Elo: Double,
          UCI_AnalyseMode: Boolean, UCI_Opponent: String, UCI_ShowCurrLine: Boolean = false,
          UCI_ShowRefutations: Boolean = false, UCI_LimitStrength: Boolean = false,
          depth: Int, seldepth: Array<Int>, time: Double, nodes: Int, pv: String,
          multipv: Array<String>, score: Array<Double>, currmove: String,
          currmovenumber: Int, hashfull: Double, nps: Int, tbhits: Double,
          cpuload: Double, string: String, refutation: String, currline: Int, bestmove: String,
          gameHasEnded: Boolean){
    var hash: Double = hash
        set(value) {
            field = value
        }
    var nalimovPath: String = nalimovPath
        set(value) {
            field = value
        }
    var nalimovCache: Double = nalimovCache
        set(value) {
            field = value
        }
    var ponder: Boolean = ponder
        set(value) {
            field = value
        }
    var ownBook: Boolean = ownBook
        set(value) {
            field = value
        }
    var multiPV: Double = multiPV
        set(value) {
            field = value
        }
    var UCI_Elo: Double = UCI_Elo
        set(value) {
            field = value
        }
    var UCI_AnalyseMode: Boolean = UCI_AnalyseMode
        set(value) {
            field = value
        }
    var UCI_Opponent: String = UCI_Opponent
        set(value) {
            field = value
        }
    var UCI_ShowCurrLine: Boolean = UCI_ShowCurrLine
        set(value) {
            field = value
        }
    var UCI_ShowRefutations: Boolean = UCI_ShowRefutations
        set(value) {
            field = value
        }
    var UCI_LimitStrength: Boolean = UCI_LimitStrength
        set(value) {
            field = value
        }
    var depth: Int = depth
        set(value) {
            field = value
        }
    var seldepth: Array<Int> = seldepth
        set(value) {
            field = value
        }
    var time: Double = time
        set(value) {
            field = value
        }
    var nodes: Int = nodes
        set(value) {
            field = value
        }
    var pv: String = pv
        set(value) {
            field = value
        }
    var multipv: Array<String> = multipv
        set(value) {
            field = value
        }
    var score: Array<Double> = score
        set(value) {
            field = value
        }
    var currmove: String = currmove
        set(value) {
            field = value
        }
    var currmovenumber: Int = currmovenumber
        set(value) {
            field = value
        }
    var hashfull: Double = hashfull
        set(value) {
            field = value
        }
    var nps: Int = nps
        set(value) {
            field = value
        }
    var tbhits: Double = tbhits
        set(value) {
            field = value
        }
    var cpuload: Double = cpuload
        set(value) {
            field = value
        }
    var string: String = string
        set(value) {
            field = value
        }
    var refutation: String = refutation
        set(value) {
            field = value
        }
    var currline: Int = currline
        set(value) {
            field = value
        }
    var bestmove: String = bestmove
        set(value) {
            field = value
        }
    var gameHasEnded: Boolean = gameHasEnded
        set(value) {
            field = value
        }
    private val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
    private val writer = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
    private fun sendId() {
        writer.write("id name Chess Engine\n")
        writer.write("id author AryanB1\n")
        writer.flush()
    }
    private fun sendUciOk() {
        writer.write("uciok\n")
        writer.flush()
    }
    private fun sendReadyOk() {
        writer.write("readyok\n")
        writer.flush()
    }
    fun sendBestMove() {
        writer.write("bestmove $bestmove\n")
        writer.flush()
    }
    fun sendCopyProtection() {
        writer.write("copyprotection chess\n")
        writer.flush()
    }
    fun sendRegistration() {
        writer.write("registration error\n")
        writer.flush()
    }
    fun sendMessage(message: String) {
        writer.write(message + "\n")
        writer.flush()
    }
    fun sendOptions() {
        writer.write("$hash\n")
        writer.write(nalimovPath + "\n")
        writer.write("$nalimovCache\n")
        writer.write("$ponder\n")
        writer.write("$ownBook\n")
        writer.write("$multiPV\n")
        writer.write("$UCI_ShowCurrLine\n")
        writer.write("$UCI_ShowRefutations\n")
        writer.write("$UCI_LimitStrength\n")
        writer.write("$UCI_Elo\n")
        writer.write("$UCI_AnalyseMode\n")
        writer.write(UCI_Opponent + "\n")
        writer.flush()
    }
    fun endGame() {
        gameHasEnded = true;
    }
    fun sendInfo() {
        writer.write("$depth\n")
        writer.write("$seldepth\n")
        writer.write("$time\n")
        writer.write("$nodes\n")
        writer.write("$pv\n")
        writer.write("$multipv\n")
        writer.write("$score\n")
        writer.write("$currmove\n")
        writer.write("$currmovenumber\n")
        writer.write("$hashfull\n")
        writer.write("$nps\n")
        writer.write("$tbhits\n")
        writer.write("$cpuload\n")
        writer.write("$string\n")
        writer.write("$refutation\n")
        writer.write("$currline\n")
        writer.flush()
    }
    fun retrieveInfo(): String {
        return reader.readLine()
    }
    fun ponder(): String {
        return bestmove;
    }
    fun processInput() {
        while (true) {
            val input = reader.readLine() ?: break
            val parts = input.split(" ")
            when (parts[0]) {
                "uci" -> {
                    sendId()
                    sendOptions()
                    sendUciOk()
                }
                "isready" -> sendReadyOk()
                "ucinewgame" -> {
                    endGame()
                    sendId()
                    sendOptions()
                    sendUciOk()
                }
                "position" -> sendInfo()
                "go" -> retrieveInfo()
                "stop" -> sendBestMove()
                "ponderhit" -> ponder();
                "quit" -> break
                else -> sendMessage("Unknown command")
            }
        }
    }
}