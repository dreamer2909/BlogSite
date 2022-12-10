package example.blogsite.helpers.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return LocalDate.parse(date, formatter);
    }
}
