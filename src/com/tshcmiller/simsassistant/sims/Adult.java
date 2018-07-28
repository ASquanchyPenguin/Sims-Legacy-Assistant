package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.Console;

public class Adult extends Sim {

	public Adult(Console console, String name, int age, TraitSystem traitSystem, String aspiration) {
		super(name);
		
		this.age = age;
		this.traitSystem = traitSystem;
		this.aspiration = aspiration;
		
		if (!this.traitSystem.isFull()) {
			traitSystem.fillTraits(console, 4);
		}
	}
	
	public Adult(Console console, String name) {
		this(console, name, 3, new TraitSystem(), Aspirations.getRandomAdultAspiration());
		console.writeNotification("The aspiration \"%s\" has been acquired!", aspiration);
	}

	
	public Adult(Console console, Teen teen) {
		this(console, teen.name, 3, teen.traitSystem, teen.aspiration);
		this.id = teen.id;
	}

	@Override
	public Sim ageUp(Console console) {
		switch (++age) {
		case 4:
			console.writeNotification("Well, another year, another age. %s is now an Adult", this.toString());
			break;
		case 5:
			console.writeNotification("%s has reached the golden years.", this.toString());
			break;
		default:
			console.writeNotification("%s is already max age!", this.toString());
			age = 5;
		}
		
		return this;
	}
}
