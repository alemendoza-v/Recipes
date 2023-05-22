package recipes.dto;

import recipes.model.Recipe;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDto {
    private Long id;
    @Pattern(regexp = "^(.+)@(.+)\\.(.+)$")
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
    private List<Recipe> recipes;
    private String role;

    public UserDto() {
    }

    public UserDto(Long id, @Pattern(regexp = "^(.+)@(.+)\\.(.+)$") String email, @NotBlank @Size(min = 8) String password, List<Recipe> recipes, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.recipes = recipes;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
