public class Payment {
    String webshopId;
    String customerId;
    int amount;
    String bankAccount;
    String cardNumber;
    String paymentDate;

    String cardOrTransferHelper;

    public Payment(String webshopId, String customerId, int amount, String paymentDate, String cardOrTransferHelper) {
        this.webshopId = webshopId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.cardOrTransferHelper = cardOrTransferHelper;
        if(cardOrTransferHelper.equals("card"))
        {
            this.cardNumber = cardOrTransferHelper;
        }
        if(cardOrTransferHelper.equals("transfer"))
        {
            this.bankAccount = cardOrTransferHelper;
        }
    }
}
