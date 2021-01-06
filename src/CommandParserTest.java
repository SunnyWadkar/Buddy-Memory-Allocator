import java.io.FileNotFoundException;

import student.TestCase;

/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */
public class CommandParserTest extends TestCase {

    /**
     * Test for all the methods in the CommandParser class
     * 
     * @throws FileNotFoundException
     *             exception if file is not found
     */
    public void testCommandParser() throws FileNotFoundException {

        CommandParser comm = new CommandParser();
        Command[] parse1 = comm.getCommands("testCommandParser.txt");
        assertEquals(parse1[0].getCmd(), "print");
        assertEquals(parse1[0].getArgument(), "hashtable");
        assertEquals(parse1[2].getCmd(), "delete");
        assertEquals(parse1[2].getArgument(), "Death Note");
        assertEquals(parse1[3].getCmd(), "update");
        assertEquals(parse1[3].getArgument(), "Another Note");
        assertEquals(parse1[3].getUpdateCmd(), "delete");
        assertEquals(parse1[3].getFields(), "Genre<SEP>Anime");

    }

}
