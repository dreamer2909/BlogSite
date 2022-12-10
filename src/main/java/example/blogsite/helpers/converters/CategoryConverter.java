package example.blogsite.helpers.converters;

import example.blogsite.models.Category;
import example.blogsite.repositories.CategoryRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<Long, Category> {
    private final CategoryRepository categoryRepository;

    public CategoryConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category convert(Long id) {
        return categoryRepository.getReferenceById(id);
    }
}
