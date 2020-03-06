package java_reflection;

import java_reflection.annotations.Column;

public class Phone {

    private Client owner;

    @Column(name="tech")
    private PhoneTech tech;

    @Column(name="type")
    private PhoneType type;

    @Column(name="id")
    private String identifier;

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public PhoneTech getTech() {
        return tech;
    }

    public void setTech(PhoneTech tech) {
        this.tech = tech;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
