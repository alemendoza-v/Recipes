package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.model.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    public List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
    public List<Recipe> findByNameIgnoreCaseContainingOrderByDateDesc(String name);
}
