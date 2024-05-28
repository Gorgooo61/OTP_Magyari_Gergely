import javax.swing.text.html.parser.Entity;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReportCreater {
    Map<String, Customer> customers;
    List <Payment> payments;

    public ReportCreater(Map<String, Customer> customers, List<Payment> payments) {
        this.customers = customers;
        this.payments = payments;
    }

    //customerenkent az osszesitett koltes
    public void generateCustomerReport(String file) {
        try(PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Customer customer : customers.values()) { //vegigmegyunk az osszes customeren
                int sum = 0;
                List<Payment> customerPayments = payments.stream().filter(payment -> payment.customerId.equals(customer.customerId)).collect(Collectors.toList());  //ahol a customerId megegyezik kigyujtjuk egy listaba
                for (Payment payment : customerPayments) {  //Ki vesszuk az amountokat
                    sum += payment.amount;
                }
                writer.println(customer.name + "," + customer.address + "," + sum);
            }
            writer.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }


    //Ket legtobbet koltott customer
    public void generateTopCustomerReport(String inputFile, String outputFile) {
        List<String[]> topCustomers = new ArrayList<>(); //ebbe tesszuk bele az elozo csvt
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
            String line;
            while ((line = br.readLine()) != null){  //soronként beolvassuk
                String[] data = line.split(",");  //elspliteljuk
                topCustomers.add(data);  //soronkent hozzadjuk a csv-t
            }
        } catch (IOException e){
            e.getMessage();
        }
        topCustomers.sort((a, b) -> Integer.compare(Integer.parseInt(b[2]), Integer.parseInt(a[2])));  //rendezes az osszeg alapjan
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))){
            for(int i = 0; i < 2; i++){  //elso 2 sor kiirasa
                String[] line = topCustomers.get(i);
                writer.println(line[0] + "," + line[1] + "," + line[2]);
            }
            writer.close();
        }catch (IOException e){
            e.getMessage();
        }
    }


    public void generateWebshopReport(String file) {
        Map<String, Integer> cardPayments = new HashMap<>();  //kiszedjuk kulon mapokba a card es transfereket
        Map<String, Integer> transferPayments = new HashMap<>();
        for(Payment payment : payments) {
            if(payment.cardOrTransferHelper.equals("card")){
                cardPayments.put(payment.webshopId, cardPayments.getOrDefault(payment.webshopId, 0) + payment.amount);
            } else if (payment.cardOrTransferHelper.equals("transfer")){
                transferPayments.put(payment.webshopId, transferPayments.getOrDefault(payment.webshopId, 0) + payment.amount);
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))){
            Set<String> shops = new HashSet<>(cardPayments.keySet());  //letrehozunk egy set-et az osszes shopId-val
            shops.addAll(transferPayments.keySet());  //All-> keySet miatt
            for (String shop : shops) {
                writer.println(shop + "," + cardPayments.getOrDefault(shop, 0) + "," + transferPayments.getOrDefault(shop, 0));  //visszaadjuk shoponkent a korabban osszeszamolt valuet

            }
            writer.close();
        }catch (IOException e){
            e.getMessage();
        }
    }
}

