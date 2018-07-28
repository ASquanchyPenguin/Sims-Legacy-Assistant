package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.Console;

public class Child extends Sim {

	public Child(Console console, TraitSystem traitSystem, String name) {
		super(name);
		
		this.age = 1;
		this.traitSystem = traitSystem;
		this.aspiration = Aspirations.getRandomChildAspiration();
		this.traitSystem.fillTraits(console, 2);
		
		console.writeNotification("The aspiration \"%s\" has been acquired!", aspiration);
	}
	
	public Child(Console console, Toddler toddler) {
		this(console, toddler.traitSystem, toddler.name);
		this.id = toddler.id;
		
	}

	@Override
	public Sim ageUp(Console console) {
		console.writeNotification("Can you believe it? %s is now a Teen!", this.toString());
		return new Teen(console, this);
	}
}
