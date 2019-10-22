package thrift.logic.parser;

import static thrift.commons.core.Messages.MESSAGE_INVALID_MONTH_FORMAT;

import java.time.Month;
import java.util.NoSuchElementException;
import java.util.Optional;
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
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MONTH);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_MONTH)
                && !args.isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE));
        }

        return getCommand(argMultimap);
    }

    private ListCommand getCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, CliSyntax.PREFIX_MONTH)) {
            try {
                Month month = ParserUtil.parseMonth(argMultimap.getValue(CliSyntax.PREFIX_MONTH).get());
                System.out.println(month);
            } catch (Exception e) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_MONTH_FORMAT,
                        ListCommand.MESSAGE_MONTH));            }
            return new ListCommand(); //filter by month
        } else {
            return new ListCommand();
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
