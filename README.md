# Polytope's Checkstyle Extensions

This repository is a collection of Checkstyle Checks which are used in Polytope's codebases.

## Checks

Below is the list of checks in this repository.

### Parameter Alignment

Ensures that parameters are aligned either on the same line or such that there is a line separating the parameters from left parenthesis and from the right parenthesis. See examples below

#### OK
```java
methodCall(a, b, c);
methodCall(
    a,
    b,
    c
);
```

#### NOT OK
```java
methodCall(a, 
           b, 
           c);
methodCall(a,
           b,
           c
);
methodCall(
    a,
    b,
    c);
```