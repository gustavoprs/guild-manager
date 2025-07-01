package guild_manager.models;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {

	/* Attributes */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "type", nullable = false)
	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private Adventurer owner;

	/* Constructors */

	public Item(){}

	public Item(String name, String type){
		this.name = name;
		this.type = type;
	}

	public Item(String name, String type, Adventurer owner){
		this.name = name;
		this.type = type;
		this.owner = owner;
	}
	
	/* Getters and Setters */

	public int getId(){
		return this.id;
	}

	public String getName(){
		return this.name;
	}

	public String getType(){
		return this.type;
	}

	public Adventurer getOwner(){
		return this.owner;
	}

	public void setOwner(Adventurer owner){
		this.owner = owner;
	}

	public String describe(){
		String description = "The item " + this.getName() + " of type " + this.getType(); 
		
		Adventurer owner = getOwner();
		if(owner != null){
			description += " belongs to the adventurer " + this.getOwner().getName();
		} else {
			description += " belongs to no one";
		}

		return description;  
	}

		@Override
	public String toString(){
		String string = "ID: " + getId() + " | Name: " + getName() + " | Type: " + getType();
		
		Adventurer owner = getOwner();
		if(owner != null){
			string += " | Owner: " + getOwner().getName();
		} else {
			string += " | Owner: No one";
		}
		
		return string;
	}

} 
