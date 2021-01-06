import java.io.*;
import java.util.*;

/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */
public class CommandParser {

    /**
     * This function parses the input command file and returns an array of
     * commands to be executed.
     * The returned array is of type Command Class which separates the commands
     * and arguments.
     * 
     * @param inFile
     *            input command file
     * @return Command[] array of commands
     * @throws FileNotFoundException
     *             exception if file is not found
     */
    public Command[] getCommands(String inFile) throws FileNotFoundException {
        LinkedList<Command> commList = new LinkedList<Command>();
        Command[] retArray;
        int commandCount = 0;
        String cmd = null;
        String argument = null;
        String updatecmd = null;
        String fields = null;
        try {
            Scanner scanner = new Scanner(new File(inFile));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                cmd = null;
                argument = null;
                updatecmd = null;
                fields = null;
                line = line.trim();
                line = line.replaceAll("\\s+", " ");
                if ((!line.isEmpty()) && line.contains(" ")) {
                    cmd = line.substring(0, line.indexOf(" "));
                    argument = line.substring(line.indexOf(" "));
                    argument = argument.trim();
                    if (cmd.equals("update") && argument.contains(" ")) {
                        updatecmd = argument.substring(0, argument.indexOf(
                            " "));
                        argument = argument.substring(argument.indexOf(" "));
                        String[] values = argument.split("<SEP>");
                        argument = values[0].trim();
                        fields = " ";
                        for (int i = 1; i < values.length - 1; i++) {
                            fields = fields + values[i].trim() + "<SEP>";
                        }
                        fields = fields.trim() + values[values.length - 1]
                            .trim();
                    }
                    Command c = new Command(cmd, argument, updatecmd, fields);
                    commList.insert(c);
                    commandCount++;
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        retArray = new Command[commandCount];
        Node<Command> tmp = commList.getHead().getNext();
        int j = 0;
        while (tmp != null) {
            retArray[j] = tmp.getValue();
            j++;
            tmp = tmp.getNext();
        }
        return retArray;
    }
}
