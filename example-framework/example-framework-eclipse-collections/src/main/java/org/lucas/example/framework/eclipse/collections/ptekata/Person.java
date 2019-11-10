package org.lucas.example.framework.eclipse.collections.ptekata;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.list.mutable.FastList;

public class Person
{
    private final String firstName;
    private final String lastName;
    private final MutableList<Pet> pets = FastList.newList();

    public Person(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public boolean named(String name)
    {
        return name.equals(this.firstName + ' ' + this.lastName);
    }

    public boolean hasPet(PetType petType)
    {
        return this.pets.anySatisfyWith(Predicates2.attributeEqual(Pet::getType), petType);
    }

    public MutableList<Pet> getPets()
    {
        return this.pets;
    }

    public MutableBag<PetType> getPetTypes()
    {
        return this.pets.collect(Pet::getType, HashBag.newBag());
    }

    public Person addPet(PetType petType, String name, int age)
    {
        this.pets.add(new Pet(petType, name, age));
        return this;
    }

    public boolean isPetPerson()
    {
        return this.getNumberOfPets() >= 1;
    }

    public int getNumberOfPets()
    {
        return this.pets.size();
    }
}
