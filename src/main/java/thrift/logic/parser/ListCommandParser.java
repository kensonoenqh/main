package thrift.logic.parser;

import java.util.Arrays;
import java.util.stream.Stream;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MONTH ,CliSyntax.PREFIX_TAG);
        if (args.isEmpty()) {
            return new ListCommand(); //list all transactions
        } else if (!argMultimap.getValue(CliSyntax.PREFIX_MONTH).isEmpty() &&
                !argMultimap.getValue(CliSyntax.PREFIX_TAG).isEmpty()) {
            String month = argMultimap.getValue(CliSyntax.PREFIX_MONTH).get();
            String tag = argMultimap.getValue(CliSyntax.PREFIX_TAG).get();
            return new ListCommand(); //list all transactions filtered by both month and tag, coming in v1.3
        } else if (!argMultimap.getValue(CliSyntax.PREFIX_MONTH).isEmpty() &&
                argMultimap.getValue(CliSyntax.PREFIX_TAG).isEmpty()) {
            String month = argMultimap.getValue(CliSyntax.PREFIX_MONTH).get();
            return new ListCommand(); //list all transactions filtered by month, coming in v1.3
        } else if (argMultimap.getValue(CliSyntax.PREFIX_MONTH).isEmpty() &&
                !argMultimap.getValue(CliSyntax.PREFIX_TAG).isEmpty()) {
            String month = argMultimap.getValue(CliSyntax.PREFIX_MONTH).get();
            return new ListCommand(); //list all transactions filtered by month, coming in v1.3
        } else { //bad args input or wrong prefixes used will throw the parseexception
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    /**
     * This methods checks if the required prefixes are present in the {@code ArgumentMultimap}.
     *
     * @param argumentMultimap the object to check for the existence of prefixes.
     * @param prefixes variable amount of {@code Prefix} to confirm the existence of.
     * @return true if specified prefixes are present in the argumentMultimap.
     */
    protected static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
