# Bremersee Comparator

This project contains a builder for comparing and sorting objects.
  
The builder creates a comparator that can compare any kind of objects which have the same attributes
or the same 'getters'.
It uses reflection to get the values of these attributes or 'getters'. 
The values may be a simple type like java.lang.String or a complex type which implements 
java.lang.Comparable.
  
A comparator can be obtained by the ObjectComparatorFactory:
  
```java
ObjectComparatorFactory factory = ObjectComparatorFactory.newInstance();
ObjectComarator comparator = factory.newObjectComparator(new ComparatorItem("attributeName", true));
```

A ComparatorItem defines how the objects should be compared.
It contains the name of the attribute, the ordering (ascending or descending sort order)
and optionally the next comparator item, so that a chain can be build:
  
```java
ComparatorItem item = new ComparatorItem("attributeName1", true);
item
    .next("attributeName2", false)
    .next("attributeName3", true);
```
  
The attribute name can be a path to the attribute, too:
  
```java
ComparatorItem item = new ComparatorItem("address.streetName", true);
```
  
The classes may look like this:
  
```java
public class Person {
    private Address address;
    // getter and setter
}

public class Address {
    private String streetName;
    // getter and setter
}
```

If the comparator item is empty:

```java
ObjectComparatorFactory factory = ObjectComparatorFactory.newInstance();
ObjectComarator comparator = factory.newObjectComparator(new ComparatorItem());
```

The objects must implement java.lang.Comparable.


### XML Schema

The XML schema of the ComparatorItem is available 
[here](http://bremersee.github.io/xmlschemas/bremersee-comparator-v1a.xsd).


### Spring Framework Support

The Spring Common Data project contains a class for sorting, too.
The class ComparatorSpringUtils contains methods to transform the 
comparator item of this library into the objects of the spring framework. 

```java
ComparatorItem comparatorItem = new ComparatorItem("lastName", true, true);
comparatorItem.next("firstName", true, true);
Sort springSort = ComparatorSpringUtils.toSort(comparatorItem);

// or vice versa:

comparatorItem = ComparatorSpringUtils.fromSort(springSort);
```

To use the Spring Framework Support you have to add the following 
dependency to your project:

```xml
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-commons</artifactId>
    <version>2.1.5.RELEASE</version>
</dependency>
```
