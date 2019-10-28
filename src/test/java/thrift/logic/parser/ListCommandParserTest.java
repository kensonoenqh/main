package thrift.logic.parser;

import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.logic.parser.CommandParserTestUtil.assertParseFailure;
import static thrift.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.ListCommand;
import thrift.model.transaction.TransactionIsInMonthYearPredicate;

public class ListCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE);

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(new TransactionIsInMonthYearPredicate(ParserUtil.parseDate("09/2019")));

        assertParseSuccess(parser, "m/09/2019", expectedListCommand);
    }
    
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
    }

}
