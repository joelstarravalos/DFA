# Project 1: Deterministic Finite Automata

* Author: Joel Starr-Avalos
* Class: CS361
* Semester: Spring 2020

## Overview
A program to simulate a Deterministic Finite Automata and determine if strings from a regular language are accepted
by the DFA.

## Compiling and Using
To compile: 
$ javac fa/*.java fa/dfa/*.java
To run:
$ java fa.dfa.DFADriver <test-file>

## Discussion
This project was, for the most part, a simple task of implementing the given interfaces. However, the trickiest part 
came in choosing data structures to model various sets of the DFA. An actual Set must be return per the interface, but you 
cannot easily access the members of sets by name in constant time. For this a HashMap is useful, but HashMap cannot be interated through esily
for say printing out all the members and moreover is not a Set. You can use the values() or entrySet methods for this, but they do not
preserve the order the elements were put in. For this you can use a LinkedHashMap. Likewise you can use a LinkedHashSet for the alphabet.
A HashMap is a logical choice for transitions, but if it from the DFA itself, it needs to be nested as a Hashmap where a state is a
key to another HashMap where a symbol is a key for another state. To simplify this, I chose to just have each state hold it's own 
HashMap of transitions. Similarily, I could have just made it so every state knew whether or not it was a final state, but I 
ended up keeping the full set of final states.

I also decided to include helper methods for copying states and adding states as states instead of strings to make creating
the complement of a DFA easier.
## Testing

Tested primarily with the given test files.

## Extra Credit

## Sources used
Used various documentation for classes from the java.util package
https://docs.oracle.com/javase/8/docs/api/?java/util/Collections.html
Used this tutorial on iterating through HashMaps
https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
----------