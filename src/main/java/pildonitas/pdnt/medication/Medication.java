package pildonitas.pdnt.medication;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pildonitas.pdnt.medication.status.Status;
import pildonitas.pdnt.user.User;

import java.time.LocalTime;

@Entity
@Table(name = "medications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 100, unique = true, name = "medication_name")
    private String name;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "dosage")
    private String dosage;

    @Column
    private String allergies;

    @Column(nullable = false, name = "frequency")
    private String frequency;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

}
