package chess

class Commands {
    fun connectToGUI(pathToEngine: String, nameOfEngine: String, authorOfEngine: String) {
        try {
            Connection().sendOutput(nameOfEngine + "\n", false)
            Connection().sendOutput(authorOfEngine + "\n", false)
            Connection().sendOutput("uciok\n", false)
            Connection().sendOutput("readyok\n", false)
        }
        catch(error: Error) {
            print("connection failed")
            throw(error)
        }
    }
    fun move(move: String = "0000", sendViaCloud: Boolean = false) {
        if(sendViaCloud) {
            Cloud().sendData(move)
        }
        else {

        }

    }
}