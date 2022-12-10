package example.blogsite.helpers.converters;

import example.blogsite.models.Tag;
import example.blogsite.repositories.TagRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TagConverter implements Converter<String, Set<Tag>> {
    @Override
    public Set<Tag> convert(String tag) {
        Set<Tag> tagList = new HashSet<>();
        if (tag != null) {
            String[] tags = tag.split(",");
            for (String t : tags) {
                t = t.trim().replaceAll("\\s+", " ").toUpperCase();
                tagList.add(new Tag(t));
            }
        }
        return tagList;
    }
}
