package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String[] ingredients;
    private String[] directions;
    private String category;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
