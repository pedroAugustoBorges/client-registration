package org.example.domain;


import jakarta.persistence.*;
import lombok.Data;
import org.example.enumeration.ClientStatus;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@SQLDelete(sql = "UPDATE Client SET status = 'Inactive' WHERE id = ?")
@Where(clause = "status = Active")
@Entity
@Data

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private ClientStatus status = ClientStatus.ACTIVE;

}
