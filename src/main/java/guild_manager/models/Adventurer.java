package guild_manager.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "adventurers")
public class Adventurer {

	/* Attributes */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "level", nullable = false)
	private int level;

	@Column(name = "role", nullable = false)
	private String role;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Item> items = new HashSet<>();

	@ManyToMany
	@JoinTable(
		name = "adventurers_missions",
		joinColumns = @JoinColumn(name = "adventurer_id"),
		inverseJoinColumns = @JoinColumn(name = "mission_id")
	)
	private Set<Mission> missions = new HashSet<>();

	/* Constructors */

	public Adventurer(){}

	public Adventurer(String name, int level, String role){
		this.name = name;
		this.level = level;
		this.role = role;
	}

	/* Getters and Setters */

	public int getId(){
		return this.id;
	}

	public String getName(){
		return this.name;
	}

	public int getLevel(){
		return this.level;
	}

	public String getRole(){
		return this.role;
	}

	public void setRole(String role){
		this.role = role;
	}

	public Set<Item> getItems(){
		return this.items;
	}

	public Set<Mission> getMissions(){
		return this.missions;
	}

	/* Methods */
	public void levelUp(){
		this.level++;
	}

	public void addItem(Item item){
		item.setOwner(this);
		items.add(item);
	}

}
