/*
 * Copyright (c) 2018 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.lucas.companykata;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.collection.primitive.MutableDoubleCollection;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.ParallelListIterable;
import org.eclipse.collections.api.list.primitive.MutableDoubleList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.ObjectDoubleMap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.SortedBags;
import org.eclipse.collections.impl.list.mutable.MultiReaderFastList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;
import org.lucas.task.ThreadPoolTaskExecutor;

import java.util.Collections;
import java.util.Iterator;

/**
 * Below are links to APIs that may be helpful during these exercises.
 * <p>
 * {@link RichIterable#aggregateBy(Function, Function0, Function2)}
 * {@link RichIterable#sumByDouble(Function, DoubleFunction)}
 * {@link RichIterable#flatCollect(Function)}
 * {@link MutableList#select(Predicate)}
 * {@link MutableList#anySatisfy(Predicate)}
 * {@link MutableList#toMap(Function, Function)}
 * {@link RichIterable#asLazy()}
 * {@link MutableList#groupBy(Function)}
 * {@link MutableList#collectDouble(DoubleFunction, MutableDoubleCollection)}
 * {@link MutableDoubleList#max()}
 *
 * @see <a href="http://eclipse.github.io/eclipse-collections-kata/company-kata/#/23">Exercise 8 Slides</a>
 */
public class Exercise8Test extends CompanyDomainForKata {
    /**
     * Extra credit. Aggregate the total order values by city.
     *
     * @see RichIterable#aggregateBy(Function, Function0, Function2)
     */
    @Test
    public void totalOrderValuesByCity() {
        Function0<Double> zeroValueFactory = () -> 0.0;
        Function2<Double, Customer, Double> aggregator = (result, customer) -> result + customer.getTotalOrderValue();

        MutableMap<String, Double> map = this.company.getCustomers().aggregateBy(
                Customer::getCity,
                zeroValueFactory,
                aggregator);

        Verify.assertSize(2, map);
        Assert.assertEquals(446.25, map.get("London"), 0.0);
        Assert.assertEquals(857.0, map.get("Liphook"), 0.0);
    }

    /**
     * Solve the same problem as above using a more specialized API.
     *
     * @see RichIterable#sumByDouble(Function, DoubleFunction)
     */
    @Test
    public void totalOrderValuesByCityUsingPrimitiveValues() {
        Function<Customer, String> cityFunction = Customer::getCity;
        DoubleFunction<Customer> totalOrderValueFunction = Customer::getTotalOrderValue;
        ObjectDoubleMap<String> map = this.company.getCustomers().sumByDouble(
                cityFunction,
                totalOrderValueFunction);

        Verify.assertSize(2, map);
        Assert.assertEquals(446.25, map.get("London"), 0.0);
        Assert.assertEquals(857.0, map.get("Liphook"), 0.0);
    }

    /**
     * Extra credit. Aggregate the total order values by item.
     * Hint: Look at {@link RichIterable#aggregateBy(Function, Function0, Function2)} and remember
     * how to use {@link RichIterable#flatCollect(Function)} to get an iterable of all items.
     */
    @Test
    public void totalOrderValuesByItem() {
        Function0<Double> zeroValueFactory = () -> 0.0;
        Function2<Double, LineItem, Double> aggregator = (result, lineItem) -> result + lineItem.getValue();

        MutableMap<String, Double> map = this.company.getOrders()
                .flatCollect(Order::getLineItems)
                .aggregateBy(
                        LineItem::getName,
                        zeroValueFactory,
                        aggregator);

        Verify.assertSize(12, map);
        Assert.assertEquals(100.0, map.get("shed"), 0.0);
        Assert.assertEquals(10.5, map.get("cup"), 0.0);
    }

    /**
     * Solve the same problem as above using a more specialized API.
     *
     * @see RichIterable#sumByDouble(Function, DoubleFunction)
     */
    @Test
    public void totalOrderValuesByItemUsingPrimitiveValues() {
        Function<LineItem, String> nameFunction = LineItem::getName;
        DoubleFunction<LineItem> valueFunction = LineItem::getValue;
        ObjectDoubleMap<String> map = this.company.getOrders()
                .flatCollect(Order::getLineItems)
                .sumByDouble(
                        nameFunction,
                        valueFunction);

        Verify.assertSize(12, map);
        Assert.assertEquals(100.0, map.get("shed"), 0.0);
        Assert.assertEquals(10.5, map.get("cup"), 0.0);
    }

    /**
     * Extra credit. Find all customers' line item values greater than 7.5 and sort them by highest to lowest price.
     */
    @Test
    public void sortedOrders() {
        MutableSortedBag<Double> orderedPrices = this.company.getOrders()
                .asLazy()
                .flatCollect(Order::getLineItems)
                .collectDouble(LineItem::getValue)
                .select(value -> value > 7.5)
                .collect(Double::valueOf)
                .into(TreeBag.newBag(Comparators.reverseNaturalOrder()));

        MutableSortedBag<Double> expectedPrices = SortedBags.mutable.with(
                Collections.reverseOrder(), 500.0, 150.0, 120.0, 75.0, 50.0, 50.0, 12.5);
        Verify.assertSortedBagsEqual(expectedPrices, orderedPrices);
    }

    /**
     * Extra credit. Figure out which customers ordered saucers (in any of their orders).
     */
    @Test
    public void whoOrderedSaucers() {
        MutableList<Customer> customersWithSaucers = this.company.getCustomers()
                .select(customer -> customer.getOrders()
                        .anySatisfy(order -> order.getLineItems()
                                .anySatisfy(lineItem -> "saucer".equals(lineItem.getName()))));

        Verify.assertSize("customers with saucers", 2, customersWithSaucers);
    }

    /**
     * Extra credit. Look into the {@link MutableList#toMap(Function, Function)} method.
     */
    @Test
    public void ordersByCustomerUsingAsMap() {
        MutableMap<String, MutableList<Order>> customerNameToOrders =
                this.company.getCustomers().toMap(Customer::getName, Customer::getOrders);

        Assert.assertNotNull("customer name to orders", customerNameToOrders);
        Verify.assertSize("customer names", 3, customerNameToOrders);
        MutableList<Order> ordersForBill = customerNameToOrders.get("Bill");
        Verify.assertSize("Bill orders", 3, ordersForBill);
    }

    /**
     * Extra credit. Create a multimap where the values are customers and the key is the price of
     * the most expensive item that the customer ordered.
     */
    @Test
    public void mostExpensiveItem() {
        MutableListMultimap<Double, Customer> multimap = this.company.getCustomers()
                .groupBy(customer -> customer.getOrders()
                        .asLazy()
                        .flatCollect(Order::getLineItems)
                        .collectDouble(LineItem::getValue)
                        .max());

        Verify.assertSize(3, multimap);
        Verify.assertSize(2, multimap.keysView());
        Assert.assertEquals(
                Lists.mutable.with(
                        this.company.getCustomerNamed("Fred"),
                        this.company.getCustomerNamed("Bill")),
                multimap.get(50.0));
    }

    @Test
    public void lazyEvaluationTest() {
        Address address1 = new Address("上海");
        Address address2 = new Address("广州");
        Address address3 = new Address("深圳");
        Person person1 = new Person(address1);
        Person person2 = new Person(address2);
        Person person3 = new Person(address3);
        MutableList<Person> people = Lists.mutable.with(person1, person2, person3);
        MutableList<Address> addresses = people.collect(new AddressFunction());
        addresses.anySatisfy(new EqualsAddressPredicate(address2));
        System.out.println("=============");
        LazyIterable<Person> peopleLazy = people.asLazy();
        LazyIterable<Address> addressesLazy = peopleLazy.collect(new AddressFunction());
        addressesLazy.anySatisfy(new EqualsAddressPredicate(address2));
    }

    @Test
    public void testParallelLazyEvaluation() {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        Address address1 = new Address("上海");
        Address address2 = new Address("广州");
        Address address3 = new Address("深圳");
        Person person1 = new Person(address1);
        Person person2 = new Person(address2);
        Person person3 = new Person(address3);
        MutableList<Person> people = Lists.mutable.with(person1, person2, person3);
        ParallelListIterable<Person> peopleLazy = people.asParallel(threadPoolExecutor, 2);
        ParallelListIterable<Address> addressesLazy =
                peopleLazy.collect(Person::getAddress);
        Assert.assertTrue(addressesLazy.anySatisfy(address2::equals));
    }

    @Test
    public void testReaderList() {
        MultiReaderFastList<String> list =
                MultiReaderFastList.newListWith("1", "2", "3");
        list.withWriteLockAndDelegate(backingList -> {
            Iterator<String> iterator = backingList.iterator();
            System.out.println(iterator.next());
            iterator.remove();
        });
        Assert.assertEquals(Lists.mutable.with("2", "3"), list);
    }
}
