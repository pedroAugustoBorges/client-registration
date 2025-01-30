package org.example.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enumeration.ClientStatus;
import org.hibernate.annotations.SQLDelete;


import java.time.LocalDate;

@SQLDelete(sql = "UPDATE Client SET status = 'Inactive' WHERE id = ?")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private ClientStatus status = ClientStatus.ACTIVE;

    public Client(Integer id, String name, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }
}
