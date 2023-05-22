package recipes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import recipes.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Arrays;

public class RecipeDto {
    @JsonIgnore
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @Size(min = 1)
    private String[] ingredients;
    @NotNull
    @Size(min = 1)
    private String[] directions;
    @NotNull
    @NotBlank
    private String category;
    private LocalDateTime date;
    @JsonIgnore
    private User user;
    public RecipeDto() {
    }

    public RecipeDto(int id, String name, String description, @NotNull @Size(min = 1) String[] ingredients, @NotNull @Size(min = 1) String[] directions, String category, LocalDateTime createdUpdatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.category = category;
        this.date = createdUpdatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getDirections() {
        return directions;
    }

    public void setDirections(String[] directions) {
        this.directions = directions;
    }

    @Override
    public String toString() {
        return "RecipeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", directions=" + Arrays.toString(directions) +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime createdUpdatedAt) {
        this.date = createdUpdatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
