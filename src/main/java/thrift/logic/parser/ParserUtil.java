package thrift.logic.parser;

import static java.util.Objects.requireNonNull;
import static thrift.commons.core.Messages.MESSAGE_INVALID_MONTH_FORMAT;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import thrift.commons.core.index.Index;
import thrift.commons.util.StringUtil;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Remark;
import thrift.model.transaction.Value;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Description parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String value} into a {@code Value}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code value} is invalid.
     */
    public static Value parseValue(String value) throws ParseException {
        requireNonNull(value);
        String trimmedValue = value.trim();
        if (!Value.isValidValue(trimmedValue)) {
            throw new ParseException(Value.VALUE_CONSTRAINTS);
        }
        return new Value(trimmedValue);
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Remark parseRemark(String remark) {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String month} into a {@code Month}.
     *
     * @throws ParseException if the given {@code month} is invalid.
     */
    public static Month parseMonth(String month) throws ParseException {
        requireNonNull(month);
        try {
            String pattern = "MMMM";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String monthCaps = sdf.format(sdf.parse(month)).toUpperCase();
            return Month.valueOf(monthCaps);
        } catch (java.text.ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_MONTH_FORMAT));
        }
    }
}
