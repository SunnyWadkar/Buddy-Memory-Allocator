import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import student.TestCase;

/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */
public class MemManTest extends TestCase {

    private final ByteArrayOutputStream progOut = new ByteArrayOutputStream();

    /**
     * to retrieve the print stream from the program
     */
    public void setUp() {
        System.setOut(new PrintStream(progOut));
    }


    /**
     * Get code coverage of the class declaration.
     * 
     * @throws FileNotFoundException
     */
    public void testRInit() throws FileNotFoundException {
        MemMan manager = new MemMan();
        assertNotNull(manager);
        String[] args = { "32", "10", "Project1_sampleInput.txt" };
        MemMan.main(args);
    }


    /**
     * Get code coverage of the class declaration
     * 
     * @throws FileNotFoundException
     */
    public void testRInit2() throws FileNotFoundException {
        MemMan manager = new MemMan();
        assertNotNull(manager);
        String[] args = { "256", "101", "Project1_sampleInput.txt" };
        MemMan.main(args);
    }


    /**
     * Get code coverage of the class declaration
     * 
     * @throws FileNotFoundException
     */
    public void testRInit3() throws FileNotFoundException {
        MemMan manager = new MemMan();
        assertNotNull(manager);
        String[] args = {};
        MemMan.main(args);
        assertEquals(progOut.toString().trim(),
            "Usage: java MemMan <initial-memory-size>"
                + " <initial-hash-size> <command-file>");
    }
}
