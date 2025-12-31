package main.java.ru.practicum.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Size(max=254)
    @Column(nullable = false)
    String name;

    @NotBlank
    @Email
    @Size(max=254)
    @Column(unique = true, nullable = false)
    String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
