package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.Console;

public class Toddler extends Sim {

	public Toddler(Console console, String name) {
		super(name);
		
		this.age = 0;
		this.aspiration = "Become a child";
		this.traitSystem.addTrait(console);
	}

	@Override
	public Sim ageUp(Console console) {
		console.writeNotification("Happy birthday! %s is now a Child!", this.toString());
		return new Child(console, this);
	}
}
