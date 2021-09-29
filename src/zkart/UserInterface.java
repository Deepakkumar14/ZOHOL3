package zkart;


import java.util.*;

public class UserInterface {
    private Scanner input=new Scanner(System.in);
    private Logical logic=new Logical();
    public static void main(String[] args) {
        UserInterface user=new UserInterface();
            user.logic.setAdmin();
            user.logic.fileReader();
            user.logic.itemsReader();
            user.login();
    }

    public void login() {
        int choice;
        int temp = 0;
        System.out.println("1.Sign in\n2.Sign up\n3.Exit");
        choice = input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1: {
                System.out.println("1.Customer\n2.Admin\n3.Exit");
                choice = input.nextInt();
                input.nextLine();
                if (choice == 1) {
                   String output ="";
                   String mail=null ;
                   while (!(output.equals("true"))) {
                       System.out.println("Enter mailId");
                       mail = input.nextLine();
                       System.out.println("Enter password");
                       String password = input.nextLine();
                       output = logic.customerLoginChecker(mail, password);
                       if(output.equals("true"))
                           System.out.println("Login successfully");
                       else
                          System.out.println(output);
                    }
                    customerUserInterface(mail);
                } else if (choice == 2) {
                    System.out.println("Enter mailId");
                    String mail = input.nextLine();
                    System.out.println("Enter password");
                    String password = input.nextLine();
                    String output = logic.adminChecker(mail, password);
                    if (output.equals("true")) {
                        if (temp++ == 0) {
                            System.out.println("You need to change the password\nEnter new password");
                            String newPassword = input.nextLine();
                            while (!(logic.passwordChecker(newPassword))) {
                                System.out.println("Password must contain 2 lower cases and 2 upper cases and 2 numbers");
                                newPassword = input.nextLine();
                            }
                            logic.adminPasswordChanger(newPassword);
                        }
                        adminUserInterface(mail);
                    } else
                        System.out.println(output);
                } else
                    System.out.println("Enter valid input");
                login();
                break;
            }
            case 2: {
                Customer cus = new Customer();
                System.out.println("Enter mailId");
                String mailId = input.nextLine();
                String output = logic.maildIDChecker(mailId);
                if (!output.equals("false")) {
                    System.out.println(output);
                } else {
                    System.out.println("Enter name");
                    String name = input.nextLine();
                    System.out.println("Enter mobile");
                    long mobile = input.nextLong();
                    input.nextLine();
                    System.out.println("Enter password");
                    String password = input.nextLine();
                    while (!(logic.passwordChecker(password))) {
                        System.out.println("Password must contain 2 lower cases and 2 upper cases and 2 numbers");
                        password = input.nextLine();
                    }
                        System.out.println("Enter password again to Confirm");
                        String confirm=input.nextLine();
                        while(confirm!=password) {
                            System.out.println("Enter password again");
                            confirm = input.nextLine();
                        }
                    cus.setMail(mailId);
                    cus.setPassword(password);
                    cus.setName(name);
                    cus.setMobile(mobile);
                    String output2 = logic.setCustomer(cus);
                    System.out.println(output2);
                    login();
                    break;
                }
            }
            case 4:
                break;

            default: {
                System.out.println("Enter valid input");
                login();
                break;
            }
        }
    }

    private void adminUserInterface(String mail) {
        System.out.println("1.Display items\n2.Exit");
        int choice = input.nextInt();
        switch (choice) {
            case 1: {
                LinkedHashMap<String,HashMap<String,ArrayList<Products>>> map=logic.getProductmap();
                for (Map.Entry<String, HashMap<String, ArrayList<Products>>> map1 : map.entrySet()) {
                    HashMap<String, ArrayList<Products>> brands = map1.getValue();
                    for (Map.Entry<String, ArrayList<Products>> brand : brands.entrySet()) {
                        ArrayList<Products> models = brand.getValue();
                        for (Products model : models){
                            if ( model.getStock() <= 10) {
                                System.out.println(model);
                                System.out.println("Do you want to reorder\n1,yes\n2.No");
                                choice = input.nextInt();
                                input.nextLine();
                                if (choice == 1) {
                                    System.out.println("Enter the stock quantity to reorder");
                                    int stock = input.nextInt();
                                    input.nextLine();
                                    String output = logic.setStockCount(model.getModel(), stock);
                                    System.out.println(output);
                                }
                            }
                        }
                    }
                }
                adminUserInterface(mail);
                break;
            }
            case 2:
                break;
            default:{
                System.out.println("Invalid choice");
                adminUserInterface(mail);
                break;
            }

        }
    }

    private void customerUserInterface(String mail) {
        int choice=0;
        System.out.println("1.Add list to cart\n2.Print invoice\n3.change password\n4.Exit");
        choice=input.nextInt();
        input.nextLine();
        switch (choice){
            case 1:{
                LinkedHashMap<String,HashMap<String,ArrayList<Products>>> map=logic.getProductmap();
                int i=1;
               for(Map.Entry<String,HashMap<String,ArrayList<Products>>> map1:map.entrySet())
                   System.out.println(i++ +" "+map1.getKey());
                System.out.println("Choose a category ");
                String category=input.nextLine();
                HashMap<String,ArrayList<Products>> brands=map.get(category);
                for(Map.Entry<String,ArrayList<Products>> brand:brands.entrySet()) {
                    String temp=brand.getKey();
                    System.out.println(temp);
                    for (Products model:brand.getValue()) {
                        System.out.println(model.getModel()+" "+model.getPrice());
                    }
                }
                System.out.println("Choose a brand and model to add to cart");
                String brand=input.nextLine();
                System.out.println("Choose the model");
                String model=input.nextLine();
                String out=logic.getProduct(category,brand,model,mail);
                System.out.println(out);
                System.out.println("Do you want to add any other product......Enter 1");
                System.out.println("Do you want to checkout the cart......Enter 2");
                choice=input.nextInt();
                input.nextLine();
                if(choice==1)
                    customerUserInterface(mail);
                else if(choice==2) {
                    Cart list= logic.checkout(mail);
                    System.out.println("Your invoice Number is  "+list.getInvoiceNumber());
                        for(Products items:list.getInvoiceList())
                            System.out.println(items);
                    System.out.println("Total Amount is "+list.getTotalAmount());
                        }
                customerUserInterface(mail);
                break;
            }
            case 2:{
                System.out.println("Your invoice bill is");
                ArrayList<Cart> cart=logic.getInvoiceList(mail);
                    for(Cart list1:cart) {
                        System.out.println("Invoice Id "+list1.getInvoiceNumber());
                        for (Products product : list1.getInvoiceList()) {
                            System.out.println(product);
                        }
                    }
                customerUserInterface(mail);
                break;
            }
            case 3:{
                System.out.println("Enter the new password ");
                String newPassword = input.nextLine();
//                HashMap<String,Customer> customerLogic= Cache.INSTANCE.customerMap();
//                for(Map.Entry<String,Customer> map:customerLogic.entrySet()){
//                    System.out.println(map.getKey()+map.getValue());
//                }
                while (!(logic.passwordChecker(newPassword))){
                    System.out.println("Password must contain 2 lower case 2 upper cases and 2 numbers ");
                    newPassword=input.nextLine();
                }
                System.out.println("Enter password again to Confirm");
                String confirm=input.nextLine();
                while(confirm!=newPassword) {
                    System.out.println("Enter password again");
                    confirm = input.nextLine();
                }
               String output= logic.CustomerPasswordChanger(newPassword,mail);
//                HashMap<String,Customer> customerLogin= Cache.INSTANCE.customerMap();
//                for(Map.Entry<String,Customer> map:customerLogin.entrySet()){
//                    System.out.println(map.getKey()+map.getValue());
//                }
                System.out.println(output);
                customerUserInterface(mail);
                break;
            }
            case 4:
                break;
            default: {
                System.out.println("Invalid choice");
                customerUserInterface(mail);
                break;
            }
        }

    }
}
