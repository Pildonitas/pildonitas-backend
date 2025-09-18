package pildonitas.pdnt.intake;

import jakarta.persistence.*;
import lombok.*;
import pildonitas.pdnt.medication.Medication;
import pildonitas.pdnt.intake.status.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "intakes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Intake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    private LocalDateTime intakeTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime takenAt;
}