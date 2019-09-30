package org.lucas.companykata;

import org.eclipse.collections.api.block.function.Function;

/**
 * @create: 2018-07-06
 * @description:
 */
public class AddressFunction implements Function<Person, Address> {

    private int counter = 1;

    @Override
    public Address valueOf(Person person) {
        System.out.println("Function: " + this.counter);
        this.counter++;
        return person.getAddress();
    }
}