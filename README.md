# Bremersee Comparator
This project contains classes for comparing and sorting objects.

The comparator can compare any kind of objects which have the same attributes or the same 'getters'.
It uses reflection to get the values of these attributes or 'getters'. 
The values may be a simple type like java.lang.String or a complex type which implements java.lang.Comparable.

The generated maven site is committed to the [gh-pages branch](https://github.com/bremersee/comparator/tree/gh-pages) and visible [here](http://bremersee.github.io/comparator/). There you can find some examples, too.

## Release 1.0.2
Release 1.0.2 is build with Java 7.

Fix: Comparing objects with no or emty ComparatorItem

It is available at Maven Central:
```xml
<dependency>
    <groupId>org.bremersee</groupId>
    <artifactId>bremersee-comparator</artifactId>
    <version>1.0.2</version>
</dependency>
```

## Release 1.0.1
Release 1.0.1 is build with Java 7.

It is available at Maven Central:
```xml
<dependency>
    <groupId>org.bremersee</groupId>
    <artifactId>bremersee-comparator</artifactId>
    <version>1.0.1</version>
</dependency>
```
