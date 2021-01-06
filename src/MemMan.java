import java.io.FileNotFoundException;

/**
 * {Project Description Here}
 */

/**
 * The class containing the main method.
 *
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class MemMan {
    /**
     * @param args
     *            Command line parameters
     * @throws FileNotFoundException
     *             exception if file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        // This is the main file for the program.
        if (args.length != 3) {
            System.out.println("Usage: java MemMan <initial-memory-size>"
                + " <initial-hash-size> <command-file>");
            return;
        }
        int poolsize = Integer.parseInt(args[0]);
        int hashTableSize = Integer.parseInt(args[1]);
        double hashExtensionLoad = 0.5;
        Hash hashtable = new Hash(poolsize, hashTableSize, hashExtensionLoad);
        CommandParser p1 = new CommandParser();
        Command[] comm = p1.getCommands(args[2]);
        for (int i = 0; i < comm.length; i++) {
            Command c = comm[i];
            switch (c.getCmd()) {
                case "print":
                    if (c.getArgument().equals("hashtable")) {
                        hashtable.printHashTable();
                    }
                    else if (c.getArgument().equals("blocks")) {
                        hashtable.printFreeMemory();
                    }
                    break;
                case "add":
                    hashtable.insert(c.getArgument());
                    break;
                case "delete":
                    hashtable.delete(c.getArgument());
                    break;
                case "update":
                    if ((c.getUpdateCmd() != null) && (c.getArgument() != null)
                        && (c.getFields() != null)) {
                        if (c.getUpdateCmd().equals("add")) {
                            hashtable.addRecordFields(c.getArgument(), c
                                .getFields());
                        }
                        else if (c.getUpdateCmd().equals("delete")) {
                            hashtable.deleteRecordFields(c.getArgument(), c
                                .getFields());
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
