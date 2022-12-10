package example.blogsite.services;

import example.blogsite.models.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAll();
    Category getById(Long id);
}
