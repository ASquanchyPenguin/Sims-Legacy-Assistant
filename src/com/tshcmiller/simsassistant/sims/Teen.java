package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.Console;

public class Teen extends Sim {

	public Teen(Console console, TraitSystem traitSystem, String name) {
		super(name);
		
		this.age = 2;
		this.traitSystem = traitSystem;
		this.aspiration = Aspirations.getRandomAdultAspiration();
		this.traitSystem.fillTraits(console, 3);
		
		console.writeNotification("The aspiration \"%s\" has been acquired!", aspiration);
	}
	
	public Teen(Console console, Child child) {
		this(console, child.traitSystem, child.name);
		this.id = child.id;
	}

	@Override
	public Sim ageUp(Console console) {
		console.writeNotification("They grow up so fast. %s is now a Young-Adult!", this.toString());
		return new Adult(console, this);
	}

}
