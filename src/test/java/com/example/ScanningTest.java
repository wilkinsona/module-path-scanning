package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Condition;
import org.junit.Test;

public class ScanningTest {

    @Test
    public void scanningTest() throws Exception {
        // Check that com.example.One can be loaded and is loaded from target/classes
        One one = new One();
        URL codeSourceLocation = one.getClass().getProtectionDomain().getCodeSource().getLocation();
        assertThat(codeSourceLocation.toString()).contains("/target/classes");
        // Check the resource locations from com/example. Locations in both
        // target/classes and target/test-classes should be returned
        List<String> resourceLocations = Collections.list(getClass().getClassLoader().getResources("com/example")).stream().map(URL::toString).collect(Collectors.toList());
        assertThat(resourceLocations).areExactly(1, new Condition<>((location) -> location.contains("/target/test-classes"), "Location contains /target/test-classes"));
        assertThat(resourceLocations).areExactly(1, new Condition<>((location) -> location.contains("/target/classes"), "Location contains /target/classes"));
    }

}