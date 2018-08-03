package com.tshcmiller.simsassistant.sims;

import com.tshcmiller.simsassistant.Console;
import com.tshcmiller.simsassistant.SimsAssistant;

public class Adult extends Sim {
	
	private static final long serialVersionUID = 1L;

	public Adult(SimsAssistant assistant, String name, int age, TraitSystem traitSystem, String aspiration) {
		super(name);
		
		this.age = age;
		this.traitSystem = traitSystem;
		this.aspiration = aspiration;
		
		if (!this.traitSystem.isFull()) {
			traitSystem.fillTraits(assistant, 4);
		}
	}
	
	public Adult(SimsAssistant assistant, String name) {
		this(assistant, name, 3, new TraitSystem(), Aspirations.getRandomAdultAspiration(assistant));
		assistant.getConsole().writeNotification("The aspiration \"%s\" has been acquired!", aspiration);
	}

	
	public Adult(SimsAssistant assistant, Teen teen) {
		this(assistant, teen.name, 3, teen.traitSystem, teen.aspiration);
		this.id = teen.id;
	}

	@Override
	public Sim ageUp(SimsAssistant assistant) {
		Console console = assistant.getConsole();
		
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
