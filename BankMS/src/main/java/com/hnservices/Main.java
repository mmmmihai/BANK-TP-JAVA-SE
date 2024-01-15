package com.hnservices;

import com.hnservices.components.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.json.JSONObject;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

// 1.1.2 Creation of main class for tests
public class Main {
    public static void main(String[] args) throws IOException {

        displayClients();

        ArrayList<Accounts> accountsList = loadAccounts(clientList);
        displayAccounts(Main.accountsList);

        Hashtable<Integer, Accounts> accountHashtable = createAccountHashtable(accountsList);
        ArrayList<Flow> flowsList = loadFlows(Main.accountsList);

        displayFlows(flowsList);

        loadJson(flowsList);

        updateBalances(flowsList, accountHashtable);

        displayAccountHashtable(accountHashtable);

// Does not work... :(
        try {
            loadXML();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }

    static ArrayList<Clients> clientList = new ArrayList<>();

    public static Collection generateClients(int number) {

        for (int i = 1; i <= number; i++) {
            clientList.add(new Clients("name" + i, "firstname" + i));
        }
        return clientList;
    }

    public static void displayClients() {

        Scanner scanner = new Scanner(System.in);

        int num;
        do {
            System.out.println("Please write the number of clients you wish to generate (n>= 1) ");
            num = scanner.nextInt();

        } while (num < 1);

        generateClients(num);

        clientList.stream().forEach((cl) -> {
            System.out.println(cl.toString());
        });

    }

    // 1.2.3 Creation of the table account
    static ArrayList<Accounts> accountsList = new ArrayList<>();

    public static ArrayList<Accounts> loadAccounts(ArrayList<Clients> clientList) {

        for (Clients client : clientList) {
            accountsList.add(new SavingsAccount(client));
            accountsList.add(new CurrentAccount(client));

        }

        return accountsList;
    }

    public static void displayAccounts(ArrayList<Accounts> accountsCollection) {
        accountsCollection.stream().forEach((acc) -> {
            System.out.println(acc.toString());
        });
    }

    //1.3.1 Adaptation of the table of accounts
    public static Hashtable<Integer, Accounts> createAccountHashtable(ArrayList<Accounts> accountsCollection) {
        Hashtable<Integer, Accounts> accountHashtable = new Hashtable<>();

        for (Accounts account : accountsCollection) {
            accountHashtable.put(account.getAccountNumber(), account);
        }

        return accountHashtable;
    }

    public static void displayAccountHashtable(Hashtable<Integer, Accounts> accHashtable) {
        accHashtable.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(Accounts::getBalance)))
                .forEach(entry -> System.out.println(entry.getValue()));
    }


//1.3.4 Creation of the flow array

    public static ArrayList<Flow> loadFlows(ArrayList<Accounts> accountsList) {
        ArrayList<Flow> flowList = new ArrayList<>();
        Date operationDate = Date.valueOf(LocalDate.now().plusDays(2));

        Debit debit = new Debit("Debit", 50.0, 1, true, operationDate);
        flowList.add(debit);

        for (Accounts account : accountsList) {
            if (account instanceof CurrentAccount) {
                Credit firstCredit = new Credit("Credit", 100.50, account.getAccountNumber(), true, operationDate);
                flowList.add(firstCredit);
            } else if (account instanceof SavingsAccount) {
                Credit secondCredit = new Credit("Credit", 1500.0, account.getAccountNumber(), true, operationDate);
                flowList.add(secondCredit);
            }
        }

        Transfer transfer = new Transfer("Transfer", 50.0, 1, 2, true, operationDate);
        flowList.add(transfer);
//        Transfer transfer2 = new Transfer("Transfer", 1470.0, 3, 4, true, operationDate);
//        flowList.add(transfer2);

        int h = flowList.size();
        return flowList;

    }

    public static void displayFlows(ArrayList<Flow> flowsList) {
        flowsList.stream()
                .map(Flow::toString)
                .forEach(System.out::println);
    }

    // 1.3.5 Updating accounts
    public static void updateBalances(ArrayList<Flow> flowsList, Map<Integer, Accounts> accountHashtable) {
        for (Flow flow : flowsList) {
            int targetAccountNumber = flow.getTargetAccountNumber();

            if (accountHashtable.containsKey(targetAccountNumber)) {
                Accounts account = accountHashtable.get(targetAccountNumber);
                account.setBalance(flow);
            }

            if (flow instanceof Transfer) {
                Transfer transfer = (Transfer) flow;
                int issuingAccountNumber = transfer.getAccountNumber();

                if (accountHashtable.containsKey(issuingAccountNumber)) {
                    Accounts issuingAccount = accountHashtable.get(issuingAccountNumber);
                    issuingAccount.setBalance(flow);
                }
            }
        }

//        ArrayList<Double> balances = new ArrayList<Double>();
//        accountsCollection.stream().forEach((p)-> {balances.add(p.getBalance());});
//
//        Predicate<Double> LowerThan0 = x -> x < 0;
//
//        List<Double> collect = balances.stream()
//                .filter(LowerThan0)
//                .collect(Collectors.toList());
//
//        if (collect.size() > 0) {
//            System.out.println("Number of accounts with negative balance: " + collect.size());
//        }

        Optional<Accounts> accountWithNegativeBalance = accountHashtable.values().stream()
                .filter(account -> account.getBalance() < 0)
                .findFirst();

        accountWithNegativeBalance.ifPresent(account ->
                System.out.println("Account " + account.getAccountNumber() + " has a negative balance: " + account.getBalance()));

    }

    // 2.1 JSON file of flows
    public static void loadJson(ArrayList<Flow> flows) throws IOException {

        JSONObject json = new JSONObject();

        FileWriter file = new FileWriter("C:\\Formation\\IntelliJ\\MMBANK\\output.json");

        for (int n = 0; n < flows.size(); n++) {

            json.put("Comment", flows.get(n).getComment());
            json.put("Identifier", flows.get(n).getIdentifier());
            json.put("Amount", flows.get(n).getTargetAccountNumber());
            json.put("Effect", flows.get(n).isEffect());
            json.put("Date", flows.get(n).getDateOfFlow());

            file.write(json.toString(4));

        }

        file.close();

    }

    // 2.2 XML file of account
    public static void loadXML() throws ParserConfigurationException, JAXBException {


        JAXBContext jaxbContext = JAXBContext.newInstance(Accounts[].class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File file = new File("C:\\Formation\\IntelliJ\\MMBANK\\output.xml");
        JAXBElement<Accounts[]> root = new JAXBElement<Accounts[]>(new QName("accounts"),
                Accounts[].class, accountsList.toArray(new Accounts[accountsList.size()]));
            marshaller.marshal(root, file);



    }

}