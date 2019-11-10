package org.lucas.example.framework.eclipse.collections.companykata;

/**
 * @create: 2018-07-06
 * @description:
 */
public class Person {

    private Address address;

    public Person(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}