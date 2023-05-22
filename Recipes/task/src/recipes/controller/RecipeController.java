package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.dto.RecipeDto;
import recipes.exception.ForbiddenException;
import recipes.service.RecipeService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/recipe")
@Validated
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PostMapping("/new")
    public ResponseEntity<Map<String, Integer>> createRecipe(@Valid @RequestBody RecipeDto recipe, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        RecipeDto newRecipe = recipeService.createRecipe(recipe, principal.getName());
        return new ResponseEntity<>(Map.of("id", newRecipe.getId()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable("id") int id) {
        return new ResponseEntity<>(recipeService.getRecipe(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable("id") int id, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        recipeService.deleteRecipe(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable("id") int id, @Valid @RequestBody RecipeDto recipe, Principal principal) {
        recipeService.updateRecipe(recipe, id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search/")
    public ResponseEntity<List<RecipeDto>> searchRecipes(
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false, defaultValue = "") String name) {
        if (!category.equals("") && name.equals("")) {
            return new ResponseEntity<>(recipeService.searchRecipesByCategory(category), HttpStatus.OK);
        } else if (!name.equals("") && category.equals("")) {
            return new ResponseEntity<>(recipeService.searchRecipesByName(name), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException() {
        return new ResponseEntity<>("Element not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintValidationException() {
        return new ResponseEntity<>("Error in data", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

}
