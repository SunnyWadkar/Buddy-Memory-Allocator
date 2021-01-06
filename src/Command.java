/**
 * @author Pranav Chimote pchimote
 * @author Sunny Wadkar sunnywadkar
 * @version 2020-9-18
 */
public class Command {
    private String cmd;
    private String argument;
    private String updateCmd;
    private String fields;

    /**
     * Constructer for the command class
     * 
     * @param cmd
     *            command parsed from the input file
     * @param argument
     *            record name parsed from the input file
     * @param updatecmd
     *            command that follows the update command parsed from the input
     *            file
     * @param fields
     *            key value pair of fields to be updated for a record
     */
    public Command(
        String cmd,
        String argument,
        String updatecmd,
        String fields) {
        this.cmd = cmd;
        this.argument = argument;
        this.updateCmd = updatecmd;
        this.fields = fields;
    }


    /**
     * @return String retrieve parsed command
     */
    public String getCmd() {
        return cmd;
    }


    /**
     * @param cmd
     *            setter for cmd object
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }


    /**
     * @return String retreive parsed record name
     */
    public String getArgument() {
        return argument;
    }


    /**
     * @param argument
     *            setter for record name
     */
    public void setArgument(String argument) {
        this.argument = argument;
    }


    /**
     * @return String retrieves the command that follows the update command
     */
    public String getUpdateCmd() {
        return updateCmd;
    }


    /**
     * @param updatecmd
     *            setter for the update command
     */
    public void setUpdateCmd(String updatecmd) {
        this.updateCmd = updatecmd;
    }


    /**
     * @return String retrives the key value pair of record fields to be added
     *         separated by <SEP>
     */
    public String getFields() {
        return fields;
    }


    /**
     * @param fields
     *            setter for key-value pair of record fields
     */
    public void setFields(String fields) {
        this.fields = fields;
    }
}
