package chess;
import kotlin.test.Test;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.*;
import java.net.Socket;

class UCITest {
    @Mock
    private Socket mockSocket;
    @Mock
    private BufferedReader mockReader;
    @Mock
    private BufferedWriter mockWriter;

    @InjectMocks
    private UCI uci;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(mockSocket.getInputStream()).thenReturn(mock(InputStream.class));
        when(mockSocket.getOutputStream()).thenReturn(mock(OutputStream.class));

        uci = new UCI(mockSocket, 1024, "user/test/path", 2048, true, false, 1, 2000, true,
        "opponent", false, false, false, 20, new Integer[]{20}, 300, 1000,
        "pv", new String[]{"pv1"}, new Double[]{1.0}, "e2e4", 10, 500.0,
        1000000, 10.0, 0.5, "info", "ref", 1, "e2e4", false);

        Field readerField = UCI.class.getDeclaredField("reader");
        readerField.setAccessible(true);
        readerField.set(uci, mockReader);

        Field writerField = UCI.class.getDeclaredField("writer");
        writerField.setAccessible(true);
        writerField.set(uci, mockWriter);
    }

    @Test
    void testSendId() throws IOException {
        uci.sendId();
        verify(mockWriter).write("id name Chess Engine\n");
        verify(mockWriter).write("id author AryanB1\n");
        verify(mockWriter).flush();
    }

    @Test
    void testSendUciOk() throws IOException {
        uci.sendUciOk();
        verify(mockWriter).write("uciok\n");
        verify(mockWriter).flush();
    }

    @Test
    void testSendReadyOk() throws IOException {
        uci.sendReadyOk();
        verify(mockWriter).write("readyok\n");
        verify(mockWriter).flush();
    }

    @Test
    void testSendBestMove() throws IOException {
        uci.sendBestMove();
        verify(mockWriter).write("bestmove e2e4\n");
        verify(mockWriter).flush();
    }

    @Test
    void testSendCopyProtection() throws IOException {
        uci.sendCopyProtection();
        verify(mockWriter).write("copyprotection chess\n");
        verify(mockWriter).flush();
    }

    @Test
    void testSendRegistration() throws IOException {
        uci.sendRegistration();
        verify(mockWriter).write("registration error\n");
        verify(mockWriter).flush();
    }

    @Test
    void testSendMessage() throws IOException {
        String message = "custom message";
        uci.sendMessage(message);
        verify(mockWriter).write(message + "\n");
        verify(mockWriter).flush();
    }

    @Test
    void testSendOptions() throws IOException {
        uci.sendOptions();
        verify(mockWriter).write("1024.0\n");
        verify(mockWriter).write("path/to/nalimov\n");
        verify(mockWriter).write("2048.0\n");
        verify(mockWriter).write("true\n");
        verify(mockWriter).write("false\n");
        verify(mockWriter).write("1.0\n");
        verify(mockWriter).write("false\n");
        verify(mockWriter).write("false\n");
        verify(mockWriter).write("false\n");
        verify(mockWriter).write("2000.0\n");
        verify(mockWriter).write("true\n");
        verify(mockWriter).write("opponent\n");
        verify(mockWriter).flush();
    }

    @Test
    void testEndGame() {
        uci.endGame();
        assertTrue(uci.gameHasEnded);
    }

    @Test
    void testSendInfo() throws IOException {
        uci.sendInfo();
        verify(mockWriter).write("20\n");
        verify(mockWriter).write("20\n");
        verify(mockWriter).write("300.0\n");
        verify(mockWriter).write("1000\n");
        verify(mockWriter).write("pv\n");
        verify(mockWriter).write("pv1\n");
        verify(mockWriter).write("1.0\n");
        verify(mockWriter).write("e2e4\n");
        verify(mockWriter).write("10\n");
        verify(mockWriter).write("500.0\n");
        verify(mockWriter).write("1000000\n");
        verify(mockWriter).write("10.0\n");
        verify(mockWriter).write("0.5\n");
        verify(mockWriter).write("info\n");
        verify(mockWriter).write("ref\n");
        verify(mockWriter).write("1\n");
        verify(mockWriter).flush();
    }

    @Test
    void testRetrieveInfo() throws IOException {
        when(mockReader.readLine()).thenReturn("info line");
        String info = uci.retrieveInfo();
        assertEquals("info line", info);
    }

    @Test
    void testPonder() {
        String ponderMove = uci.ponder();
        assertEquals("e2e4", ponderMove);
    }

    @Test
    void testProcessInput() throws IOException {
        when(mockReader.readLine())
            .thenReturn("uci")
            .thenReturn("isready")
            .thenReturn("ucinewgame")
            .thenReturn("position")
            .thenReturn("go")
            .thenReturn("stop")
            .thenReturn("ponderhit")
            .thenReturn("quit");

        uci.processInput();

        verify(mockWriter, times(3)).write("id name Chess Engine\n");
        verify(mockWriter, times(3)).write("id author AryanB1\n");
        verify(mockWriter, times(3)).flush();
        verify(mockWriter).write("uciok\n");
        verify(mockWriter).write("readyok\n");
        verify(mockWriter).write("bestmove e2e4\n");
    }
}
