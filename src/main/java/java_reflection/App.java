package java_reflection;


public class App 
{
    public static void main( String[] args )
    {
        PhoneRepository repo = new PhoneRepository();

        Phone phone = new Phone();
        phone.setIdentifier("999999");

        Client client = new Client();
        client.setName("Daniel");

        PhoneTech tech = new PhoneTech();
        tech.setName("4G");

        PhoneType type = new PhoneType();
        type.setName("SAMSUNG");

        phone.setOwner(client);
        phone.setTech(tech);
        phone.setType(type);

        repo.save(phone);
    }
}
