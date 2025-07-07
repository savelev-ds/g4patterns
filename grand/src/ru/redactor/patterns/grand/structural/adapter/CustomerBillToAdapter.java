package ru.redactor.patterns.grand.structural.adapter;

public class CustomerBillToAdapter implements AddressIF {

    public CustomerBillToAdapter(Customer myCustomer) {
        this.myCustomer = myCustomer;
    }

    private final Customer myCustomer;

    @Override
    public String getAddress() {
        return myCustomer.getBillToAddress();
    }

    @Override
    public void setAddress(String address) {
        myCustomer.setBillToAddress(address);
    }

}
