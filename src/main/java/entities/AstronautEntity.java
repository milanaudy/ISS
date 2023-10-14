package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="astronauts")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

 public class AstronautEntity {

@Id
 @Column(name="id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;

@Column(name="astronaut_name")
 private String name;

//@Column(name="craft_id")
 //private String craftID;

@ManyToOne
 @JoinColumn(name = "craft_id")
private CraftsEntity craft;


}
