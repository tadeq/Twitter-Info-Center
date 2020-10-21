package com.twitterinfocenter.service;

import com.twitterinfocenter.exception.TwitterInfoCenterException;
import com.twitterinfocenter.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriesService {

    private final UsersService usersService;

    private List<Category> categories = new ArrayList<>();

    @Autowired
    public CategoriesService(UsersService usersService) {
        this.usersService = usersService;
    }

    public Optional<Category> getCategory(String categoryName) {
        return categories.stream()
                .filter(category -> categoryName.equals(category.getName()))
                .findAny();
    }

    public List<Category> getCategories(List<String> categoryNames) {
        return categories.stream()
                .filter(category -> categoryNames.contains(category.getName()))
                .collect(Collectors.toList());
    }

    public Category addCategory(Category category) {
        if (getCategoryNames(categories).contains(category.getName())) {
            throw new TwitterInfoCenterException(HttpStatus.BAD_REQUEST.value(), "Category " + category.getName() + " already exists");
        }
        usersService.addUsers(category.getUsernames());
        categories.add(category);
        return category;
    }

    public Category updateCategory(Category updatedCategory) {
        Optional<Category> foundCategory = getCategory(updatedCategory.getName());
        if (foundCategory.isEmpty()) {
            throw new TwitterInfoCenterException(HttpStatus.NOT_FOUND.value(), "Category " + updatedCategory.getName() + " does not exist");
        }
        Category category = foundCategory.get();
        categories.remove(category);
        List<String> currentUsernames = category.getUsernames();
        List<String> usernamesToAdd = new ArrayList<>(updatedCategory.getUsernames());
        List<String> usernamesToDelete = new ArrayList<>(currentUsernames);
        usernamesToDelete.removeAll(usernamesToAdd);
        usernamesToAdd.removeAll(currentUsernames);
        currentUsernames.removeAll(usernamesToDelete);
        currentUsernames.addAll(usernamesToAdd);
        categories.add(category);
        return category;
    }

    private List<String> getCategoryNames(List<Category> categories) {
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }
}
