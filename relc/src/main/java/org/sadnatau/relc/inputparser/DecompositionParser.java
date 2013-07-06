package org.sadnatau.relc.inputparser;

import java.util.ArrayList;
import java.util.List;

public class DecompositionParser {

	private String decompositionFilePath;
	
	/**
	 * Constructor.
	 * 
	 * @param decompFilePath the path to decomposition specification file 
	 */
	public DecompositionParser(String decompFilePath) {
		
		this.decompositionFilePath = decompFilePath;

        Dog dog = new Dog();
        Animal animal = new Animal();
        List<Dog> animalList = new ArrayList<>();
        animalList.add((Dog)animal);

	}

    class Animal  {

    }

    class Dog extends Animal {

    }

    class Cat extends Animal {

    }
}
