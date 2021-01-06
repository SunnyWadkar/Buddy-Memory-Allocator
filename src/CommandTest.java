import student.TestCase;

/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */
public class CommandTest extends TestCase {

    /**
     * Runs tests on all methods found in the Command class
     */
    public void testCommandClass() {
        Command c = new Command("add", "Death Note", null, null);
        assertEquals(c.getCmd(), "add");
        assertEquals(c.getArgument(), "Death Note");
        assertNull(c.getUpdateCmd());
        assertNull(c.getFields());
        c.setCmd("print");
        assertEquals(c.getCmd(), "print");
        c.setArgument("hashtable");
        assertEquals(c.getArgument(), "hashtable");
        c.setCmd("update");
        c.setUpdateCmd("add");
        assertEquals(c.getUpdateCmd(), "add");
        assertNotNull(c.getUpdateCmd());
        assertEquals(c.getCmd(), "update");
        c.setFields("Death Note<SEP>Genre<SEP>Anime");
        assertEquals(c.getFields(), "Death Note<SEP>Genre<SEP>Anime");
        assertNotNull(c.getFields());
    }
}
