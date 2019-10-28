package thrift.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.logic.parser.CommandParserTestUtil.assertParseFailure;
import static thrift.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.CommandTestUtil;
import thrift.logic.commands.ListCommand;

public class ListCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE);

    private ListCommandParser parser = new ListCommandParser();

//    @Test
//    public void parse_validArgs_returnsListCommand() {
//
//        ListCommand expectedListCommand =
//                new ListCommand(ParserUtil.parseDate("01/2019"));
//
//        assertParseSuccess(parser, parser.parse(CommandTestUtil.MONTH_JANUARY_19));
//    }

//    @Test
//    public void parse_whitespaceOnlyPreamble_success() {
//        // invalid non empty preamble before valid prefix
//        assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
//                + CommandTestUtil.VALID_MONTH_JAN_19, MESSAGE_INVALID_FORMAT);
//    }

    @Test //this one works.
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, " r/invalidprefix", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, " invalidpreamble" + "m/10/2019", MESSAGE_INVALID_FORMAT);

    }

}
