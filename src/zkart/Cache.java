package zkart;

import java.util.*;

public enum Cache {
    INSTANCE;

    private static  long invoiceNo=310000;
    private HashMap<String,Customer> customerLogin=new HashMap<>();
    private Admin adminLogin=new Admin();
    private LinkedHashMap<String,HashMap<String,ArrayList<Products>>> products=new LinkedHashMap<>();
    private ArrayList<Products> cart=new ArrayList<>();
    private HashMap<String,ArrayList<Cart>> invoiceList=new HashMap<>();


    public Customer getCustomer(String mail) {
        return customerLogin.get(mail);
    }

    public void setCustomer(Customer cus){
        customerLogin.put(cus.getMail(),cus);
    }

    public HashMap<String,Customer> customerMap(){
        return customerLogin;
    }


    public LinkedHashMap<String,HashMap<String,ArrayList<Products>>> productsMap(){
        return products;
    }

    public void setAdminLogin(String mail,String password){
        adminLogin.setPassword(password);
        adminLogin.setMail(mail);
    }

    public Admin getAdmin() {
        return adminLogin;
    }

    public void addToCart(Products products,String mail) {
        cart.add(products);
    }

    public Cart checkout(String mail) {
       ArrayList<Cart> invoice=invoiceList.getOrDefault(mail,new ArrayList<>());
       Cart cus=new Cart();
       cus.setInvoiceNumber(invoiceNo++);
       ArrayList<Products> list=new ArrayList<>();
       double totalAmount=0;
       for(Products pro:cart) {
           list.add(pro);
           totalAmount+=pro.getPrice();
       }
        cus.setTotalAmount(totalAmount);
       cus.setInvoiceList(list);
       invoice.add(cus);
       invoiceList.put(mail,invoice);
       cart.clear();
        return cus;
    }

    public ArrayList<Cart> getInvoiceList(String mail) {
        return invoiceList.get(mail);
    }

    public void setProductsMap(Products cus) {
        HashMap<String,ArrayList<Products>> map=products.getOrDefault(cus.getCategory(),new HashMap<>());
        ArrayList<Products> models=map.getOrDefault(cus.getBrand(),new ArrayList<>());
        models.add(cus);
        map.put(cus.getBrand(),models);
        products.put(cus.getCategory(),map);
    }

    public static void main(String[] args) {
        Logical user=new Logical();
        user.fileReader();
        user.itemsReader();
        HashMap<String,Customer> customerLogin= INSTANCE.customerMap();
        for(Map.Entry<String,Customer> map:customerLogin.entrySet()){
            System.out.println(map.getKey()+map.getValue());
        }
    }

}
