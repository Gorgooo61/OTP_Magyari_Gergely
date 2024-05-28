
public class Main {
    public static void main(String[] args) {
        DataReader datareader = new DataReader();
        datareader.loadCustomer("customer.csv");
        datareader.loadPayment("payment.csv");


        ReportCreater reportCreater = new ReportCreater(datareader.customers, datareader.payments);
        reportCreater.generateCustomerReport("report01.csv");
        reportCreater.generateTopCustomerReport("report01.csv", "top.csv");
        reportCreater.generateWebshopReport("report02.csv");
    }
}