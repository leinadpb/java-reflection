package java_reflection;

import java_reflection.annotations.IgnoreReflection;

import java.util.Map;
import java.util.Set;

public class PhoneTechType {
    private String name;

    @IgnoreReflection
    private Set<String> companies;

    private Map<String, String> compKeys;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
