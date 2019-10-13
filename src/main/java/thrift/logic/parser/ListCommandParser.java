package thrift.logic.parser;

import java.util.Arrays;

import thrift.commons.core.Messages;
import thrift.logic.commands.ListCommand;
import thrift.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MONTH);
            if (argMultimap.getValue(CliSyntax.PREFIX_MONTH).isEmpty()) {
                return new ListCommand();
            } else {
                System.out.println("working with m/months");
                throw new ParseException(
                        String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
    }

}
