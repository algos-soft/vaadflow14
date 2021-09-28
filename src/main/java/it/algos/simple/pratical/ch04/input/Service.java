package it.algos.simple.pratical.ch04.input;

import java.util.*;

public class Service {


    public List<Department> getDepartments() {
        return Arrays.asList(
                new Department(1, "R&D"),
                new Department(2, "Marketing"),
                new Department(3, "Sales"));
    }

}
