package seedu.potato;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * Parses and formats task dates using the date formats supported by Potato.
 */
final class DateParser {
    static final String DATE_FORMAT_HINT = "[date formats: DD-MM-YYYY or DD/MM/YYYY]";

    private static final DateTimeFormatter DASH_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter SLASH_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DISPLAY_DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM uuuu", Locale.ENGLISH);

    private DateParser() {
    }

    /**
     * Parses a date written with dashes or slashes.
     *
     * @param dateText Date text to parse.
     * @return Parsed calendar date.
     * @throws DateTimeParseException If the text is not a valid supported date.
     */
    static LocalDate parse(String dateText) {
        try {
            return LocalDate.parse(dateText, DASH_DATE_FORMAT);
        } catch (DateTimeParseException exception) {
            return LocalDate.parse(dateText, SLASH_DATE_FORMAT);
        }
    }

    /**
     * Formats a date in Potato's canonical dash-separated form.
     *
     * @param date Date to format.
     * @return Date formatted as {@code DD-MM-YYYY}.
     */
    static String format(LocalDate date) {
        return date.format(DASH_DATE_FORMAT);
    }

    /**
     * Formats a date for display using an abbreviated English month name.
     *
     * @param date Date to format.
     * @return Date formatted as {@code DD MMM YYYY}.
     */
    static String formatForDisplay(LocalDate date) {
        return date.format(DISPLAY_DATE_FORMAT);
    }
}
