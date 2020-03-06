package java_reflection;

import java_reflection.annotations.IgnoreReflection;

import java.util.List;

public class Client {

    @IgnoreReflection
    private List<Phone> phones;

    private String name;
    private String lastName;

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
