package guild_manager.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "missions")
public class Mission {

	/* Attributes */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "status", nullable = false)
	private String status;

	@ManyToMany(mappedBy = "missions", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Adventurer> participants = new HashSet<>();

	/* Constructors */

	public Mission(){}

	public Mission(String title, String description, String status){
		this.title = title;
		this.description = description;
		this.status = status;
	}

	/* Getters and Setters */
	
	public int getId(){
		return this.id;
	}

	public String getTitle(){
		return this.title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getDescription(){
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getStatus(){
		return this.status;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public Set<Adventurer> getParticipants(){
		return this.participants;
	}

	/* Methods */
	public void addParticipant(Adventurer adventurer){
		if(participants.add(adventurer)){
			adventurer.getMissions().add(this);
		}
	}

	public boolean isAdvIn(Adventurer adventurer){
		return participants.contains(adventurer);
	}

}
