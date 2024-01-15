package components;

// 1.1.1 Creation of the client class
public class Clients {
    private String name;
    private String firstName;
    private int clientNumber;
    private static int c = 1;

    public Clients(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
        this.clientNumber = c++;
    }

    public Clients(String name, String firstName, int clientNumber) {
        this.name = name;
        this.firstName = firstName;
        this.clientNumber = clientNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }
    @Override
    public String toString() {
        return "Client number " + clientNumber + " : Name = " + name + ", FirstName = " + firstName;
    }
}
