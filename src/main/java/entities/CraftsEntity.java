package entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="crafts")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CraftsEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="craft_name")
    private String name;

  @OneToMany
    @JoinColumn(name="craft_id")
    List<AstronautEntity> astronauts;



}

