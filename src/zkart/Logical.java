package zkart;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logical {

    public void setAdmin() {
        String encrypt = encryption("xyzzy");
        Cache.INSTANCE.setAdminLogin("admin@zoho.com", encrypt);
    }

    public String customerLoginChecker(String mail, String password) {
        if (mail != null && password != null) {
            Customer cus = Cache.INSTANCE.getCustomer(mail);
            if (cus != null) {
                String password1 = cus.getPassword();
                String decrypt = decryption(password1);
                if (decrypt.equals(password)) {
                    return "true";
                } else
                    return "Enter valid password";
            } else
                return "Enter valid mailId";
        } else
            return "Enter valid email and password";
    }

    public String adminChecker(String mail, String password) {
        if (mail != null && password != null) {
            Admin admin = Cache.INSTANCE.getAdmin();
            if (admin != null) {
                String password1 = admin.getPassword();
                String decrypt = decryption(password1);
                if (decrypt.equals(password)) {
                    return "true";
                } else
                    return "Enter valid password";
            } else
                return "Enter valid mailId";
        } else
            return "Enter valid email and password";
    }


    public String encryption(String password) {
        String encrypt = "";
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) == 'Z') {
                encrypt += 'A';
                continue;
            }
            if (password.charAt(i) == 'z') {
                encrypt += 'a';
                continue;
            }
            if (password.charAt(i) == '9') {
                encrypt += '0';
                continue;
            }
            encrypt += (char) ((int) password.charAt(i) + 1);
        }
        return encrypt;
    }

    public String decryption(String password) {
        String decrypt = "";
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) == 'A') {
                decrypt += 'Z';
                continue;
            }
            if (password.charAt(i) == 'a') {
                decrypt += 'z';
                continue;
            }
            if (password.charAt(i) == '0') {
                decrypt += '9';
                continue;
            }
            decrypt += (char) ((int) password.charAt(i) - 1);
        }
        return decrypt;
    }

    public void fileReader() {
        FileReader fr = null;
        try {
            File file = new File("/home/inc4/zusers_db.txt");
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String lines;
            while ((lines = br.readLine()) != null) {
                String[] array = lines.split("\\s");
                Customer cus = new Customer();
                cus.setMail(array[0]);
                cus.setPassword(array[1]);
                cus.setName(array[2]);
                cus.setMobile(Long.parseLong(array[3]));
                Cache.INSTANCE.setCustomer(cus);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    public void fileWriter(Customer cus) {
        FileWriter fr = null;
        try {
            File file = new File("/home/inc4/zusers_db.txt");
            fr = new FileWriter(file, true);
            String lines = cus.getMail() + " " + cus.getPassword() + " " + cus.getName() + " " + cus.getMobile();
            System.out.println("weded");
            fr.write(lines);
            fr.write("\n");
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    public boolean passwordChecker(String password) {
        if (password != null) {
            String regex = "^(?=.*[A-Z]){2,}" + "(?=.*[a-z]{2,})" + "(?=.*[0-9]{2,})" + "([@#$%^!_]*).{6,}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(password);
            return m.matches();
        }
        return false;
    }

    public void adminPasswordChanger(String password) {
        String encrypt = encryption(password);
        Admin admin = Cache.INSTANCE.getAdmin();
        admin.setPassword(encrypt);
        List<String> password1 = admin.getPasswords();
        if (password1 == null)
            password1 = new ArrayList<>();
        password1.add(encrypt);
    }

    public LinkedHashMap<String, HashMap<String, ArrayList<Products>>> getProductmap() {
        return Cache.INSTANCE.productsMap();
    }

    public String maildIDChecker(String mailId) {
        if (mailId != null) {
            HashMap<String, Customer> map = Cache.INSTANCE.customerMap();
            if (map.containsKey(mailId)) {
                return "Mail id already available";
            } else
                return "false";
        }
        return "Enter valid mailId";
    }

    public String setCustomer(Customer cus) {
        if (cus != null) {
            String encrypt = encryption(cus.getPassword());
            cus.setPassword(encrypt);
            fileWriter(cus);
            Cache.INSTANCE.setCustomer(cus);
            return "Customer Added successfully";
        } else
            return "Customer not added";
    }

    public void addToCart(Products products, String mail) {
        Cache.INSTANCE.addToCart(products, mail);
    }

    public Cart checkout(String mail) {
        Cart list = Cache.INSTANCE.checkout(mail);
        return list;
    }

    public ArrayList<Cart> getInvoiceList(String mail) {
        ArrayList<Cart> list = Cache.INSTANCE.getInvoiceList(mail);
        return list;
    }

    public String CustomerPasswordChanger(String newPassword, String mail) {
        if (newPassword != null) {
            String encrypt = encryption(newPassword);
            HashMap<String, Customer> cus = Cache.INSTANCE.customerMap();
            Customer person = cus.get(mail);
            ArrayList<String> list=person.getPasswords();
            if(list==null){
                list=new ArrayList<>();
            }else {
               if( list.contains(encrypt))
                   return "You can't use the last three old passwords";
            }
            list.add(person.getPassword());
            person.setPassword(encrypt);
            return "Password changed Successfully";
        }
        return "Password was not changed try again";
    }

    public void itemsReader() {
        FileReader fr = null;
        try {
            File file = new File("/home/inc4/z_kart_db.txt");
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String lines;
            while ((lines = br.readLine()) != null) {
                String[] array = lines.split(" ");
                Products cus = new Products();
                cus.setCategory(array[0]);
                cus.setBrand(array[1]);
                cus.setModel(array[2]);
                cus.setPrice(Double.parseDouble(array[3]));
                cus.setStock(Integer.parseInt(array[4]));
                Cache.INSTANCE.setProductsMap(cus);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    public String getProduct(String category, String brand, String model,String mail) {
        int count = 0;
        LinkedHashMap<String, HashMap<String, ArrayList<Products>>> products = Cache.INSTANCE.productsMap();
        HashMap<String, ArrayList<Products>> map = products.get(category);
        if (map.containsKey(brand)) {
            ArrayList<Products> product = map.get(brand);
            for (Products models : product) {
                if (models.getModel().equals(model)) {
                    if (models.getStock() != 0) {
                        addToCart(models, mail);
                        models.setStock(models.getStock() - 1);
                        return "product added to the cart successfully";
                    } else
                        return "Product not available";
                }
                else
                    count++;
            }
            if (count == product.size())
                return "Model not available";
        } else
            return "Brand name is wrong";

      return "null";
    }

    public String setStockCount(String model, int stock) {
        LinkedHashMap<String, HashMap<String, ArrayList<Products>>> map = getProductmap();
        for (Map.Entry<String, HashMap<String, ArrayList<Products>>> map1 : map.entrySet()) {
            HashMap<String, ArrayList<Products>> brands = map1.getValue();
            for (Map.Entry<String, ArrayList<Products>> brand : brands.entrySet()) {
                ArrayList<Products> models = brand.getValue();
                for (Products item : models) {
                    if (item.getModel().equals(model)) {
                        item.setStock(item.getStock() + stock);
                        return "Stock updated";
                    }
                }
            }
        }
        return "Stock Not updated";
    }
}
