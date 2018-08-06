package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;

public class Child extends Sim {

	private static final long serialVersionUID = 1L;

	public Child(SimsAssistant assistant, TraitSystem traitSystem, String name) {
		super(name);
		
		this.age = 1;
		this.traitSystem = traitSystem;	
	}
	
	public Child(SimsAssistant assistant, Toddler toddler) {
		this(assistant, toddler.traitSystem, toddler.name);
		this.id = toddler.id;
	}

	@Override
	public Sim ageUp(SimsAssistant assistant) {
		assistant.getConsole().writeNotification("Can you believe it? %s is now a Teen!", this.toString());
		Teen teen = new Teen(assistant, this);
		teen.traitSystem.addTrait(assistant, name);
		teen.rollAspiration(assistant);
		return teen;
	}
	
	@Override
	public void rollAspiration(SimsAssistant assistant) {
		Console console = assistant.getConsole();
		aspiration = Aspirations.getRandomChildAspiration();
		console.writeNotification("%s has acquired the aspiration %s!", name, aspiration);
	}

	@Override
	public void rollTraits(SimsAssistant assistant) {
		this.traitSystem.fillTraits(assistant, name, 2);
	}
}
