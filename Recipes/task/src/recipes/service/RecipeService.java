package recipes.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.dto.RecipeDto;
import recipes.exception.ForbiddenException;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public RecipeDto createRecipe(RecipeDto newRecipe, String user) {
        newRecipe.setDate(LocalDateTime.now());
        newRecipe.setUser(userRepository.findByEmail(user).get());
        Recipe recipe = modelMapper.map(newRecipe, Recipe.class);
        return modelMapper.map(recipeRepository.save(recipe), RecipeDto.class);
    }

    public RecipeDto getRecipe(int id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            return modelMapper.map(recipe.get(), RecipeDto.class);
        }
        throw new NoSuchElementException();
    }

    public void deleteRecipe(int id, String email) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            if (recipe.get().getUser().getEmail().equals(email)) {
                recipeRepository.delete(recipe.get());
                return;
            } else {
                throw new ForbiddenException();
            }
        }
        throw new NoSuchElementException();
    }

    public void updateRecipe(RecipeDto recipeDto, int id, String email) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            if (recipe.get().getUser().getEmail().equals(email)) {
                RecipeDto updatedRecipe = modelMapper.map(recipe.get(), RecipeDto.class);
                updatedRecipe.setName(recipeDto.getName());
                updatedRecipe.setCategory(recipeDto.getCategory());
                updatedRecipe.setDescription(recipeDto.getDescription());
                updatedRecipe.setDirections(recipeDto.getDirections());
                updatedRecipe.setIngredients(recipeDto.getIngredients());
                updatedRecipe.setDate(LocalDateTime.now());
                recipeRepository.save(modelMapper.map(updatedRecipe, Recipe.class));
                return;
            } else {
                throw new ForbiddenException();
            }
        }
        throw new NoSuchElementException();
    }

    public List<RecipeDto> searchRecipesByCategory(String filter) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(filter).stream()
                .map(recipe -> modelMapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }

    public List<RecipeDto> searchRecipesByName(String filter) {
        return recipeRepository.findByNameIgnoreCaseContainingOrderByDateDesc(filter).stream()
                .map(recipe -> modelMapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }
}
