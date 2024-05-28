import java.util.*;
import java.io.*;

public class DataReader {
    Map<String, Customer> customers = new HashMap<>(); //customerId-t hasznalom egyedi kulcskent
    List<Payment> payments = new ArrayList<>();

    public void loadCustomer(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))){ //fájl megnyitása
            String line;
            while ((line = br.readLine()) != null){  //soronként beolvassuk
                String[] data = line.split(",");  //elspliteljuk
                String key = data[1];
                //teszt eset nem teljesen ertem a feladat leirast, hogy nekem kell e a teljes exception-t kulon esetekkel lekezelni, vagy eleg lenne egy try catch, igy csinalta  egy pelda esetet, amiben magamnak ellenorzok egy lehetseges hibat
                if(data.length == 4){  //ha 4 adattagbol all a sor, akkor nem hibas, kulonben logerrorba megy
                    Customer customer = new Customer(data[0], data[1], data[2], data[3]);  //customer peladfny letrehozasa az adott sorbol
                    if(!customers.containsKey(key)){ //ne lehessen a key-t duplikalni, customerId egyedi legyen
                        customers.put(data[1], customer); //customerId, mint key, az adott customerhez, a hashmaphez hozza adva
                    }
                    else {
                        logError("This key is already in use " + line);
                    }
                }
                else {
                    logError("Customer data is incorrect " + line);
                }
            }
        } catch (IOException e){ //input output exception
            e.getMessage(); //hiba kiirasa
        }
    }

    public void loadPayment(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))){ //fájl megnyitása
            String line;
            while ((line = br.readLine()) != null){  //soronként beolvassuk
                String[] data = line.split(",");  //elspliteljuk

                //teszt eset nem teljesen ertem a feladat leirast, hogy nekem kell e a teljes exception-t kulon esetekkel lekezelni, vagy eleg lenne egy try catch, igy csinalta  egy pelda esetet, amiben magamnak ellenorzok egy lehetseges hibat
                if(data.length == 5){  //ha 4 adattagbol all a sor, akkor nem hibas, kulonben logerrorba megy
                    Payment payment = new Payment(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4]);
                    payments.add(payment);

                }
                else {
                    logError("Customer data is incorrect " + line);
                }
            }
        } catch (IOException e){ //input output exception
            e.getMessage(); //hiba kiirasa
        }
    }

    public void logError(String error){
        try (FileWriter fw = new FileWriter("application.log", true)){  //hozzafuzi az elozo sorhoz
            fw.write(error + "\n");  //soronkent beleirjuk az error messaget es a hozza tartozo sot
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
