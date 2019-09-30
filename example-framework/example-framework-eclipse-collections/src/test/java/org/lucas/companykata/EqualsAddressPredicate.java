package org.lucas.companykata;

import org.eclipse.collections.api.block.predicate.Predicate;

/**
 * @create: 2018-07-06
 * @description:
 */
public class EqualsAddressPredicate implements Predicate<Address> {

    private final Address address;

    private int counter = 1;

    public EqualsAddressPredicate(Address address) {
        this.address = address;
    }

    @Override
    public boolean accept(Address address) {
        System.out.println("Predicate: " + this.counter);
        this.counter++;
        return address == this.address;
    }
}