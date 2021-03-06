package ProgramLauncher;

import BL.*;
import DAL.*;
import PL.*;
import SharedClasses.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Shahar on 06/04/17.
 */
public class ProgramLauncher
{
    public static List<OrderItem> alreadyWarned = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    public static Thread checkPeriodicOrders;
    public static boolean continuePeriodCheck = false;
    public static boolean roleSet = false;
    public static void main(String [] args) throws InterruptedException {
        Connection conn = getConnectionAndInitDatabase("Database.db");

        // DAL INIT
        Items ITEMS = new Items(conn);
        Quantities QUANTITIES = new Quantities(conn);
        Categories CATEGORIES = new Categories(conn);
        Prices PRICES = new Prices(conn, CATEGORIES);

        Contacts CONTACTS = new Contacts(conn);
        Discounts DISCOUNTS = new Discounts(conn);
        Orders ORDERS = new Orders(conn);
        OrdersItems ORDERS_ITEMS = new OrdersItems(conn);
        SupplierItems SUPPLIER_ITEMS = new SupplierItems(conn);
        Suppliers SUPPLIERS = new Suppliers(conn, CONTACTS);

        DAL dal =  new DAL(conn,ITEMS, SUPPLIERS);


        BL bl =  new BL(dal);
        // BL INIT
        SupplierBL SBL = new SupplierBL(CONTACTS, DISCOUNTS, ITEMS, SUPPLIER_ITEMS, SUPPLIERS, ORDERS, ORDERS_ITEMS,QUANTITIES,bl);
        ProductManagement PRODUCT_MANAGEMENT = new ProductManagement(ITEMS, PRICES, QUANTITIES, SBL);
        CategoryManagement CATEGORY_MANAGEMENT = new CategoryManagement(CATEGORIES, ITEMS, PRICES, QUANTITIES);
        PriceManagement PRICE_MANAGEMENT = new PriceManagement(PRICES);



        // PL INIT
        PL_Stock PL_STOCK = new PL_Stock(PRODUCT_MANAGEMENT, PRICE_MANAGEMENT, CATEGORY_MANAGEMENT,SBL);
        PL_Supplier pl_sup= new PL_Supplier (SBL);
        PL_Orders pl_ord= new PL_Orders(SBL);
        Menu MENU = new Menu(PL_STOCK, pl_sup, pl_ord,bl);

        /*
            Database init
         */

        if(CATEGORIES.addCategory(new Category(103,"Drinks"))) {

            String sql;
            Statement stmt = null;
            try {
            stmt = conn.createStatement();

            sql="INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ('12121212', 'Volvo', 1, 500, 1000)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ('13131313', 'Ford', 2, 750, 4000)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ('14141414', 'Tetra', 3, 1000, 7000)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ('15151515', 'Mazda', 4, 1500, 10000)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ('16161616', 'Subaru', 5, 2250, 15000)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (0,'supersal','rishon','Shimi','0501212121');";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (414,'supersal','rishon','Shimon','0501212121');";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (424,'Mega','jafa','Meni','0548889999');";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (434,'Osher Ad','jerusalem','Avram','0587777777');";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (5,'Osher Ad','Tel Aviv','Avrami','0587778777');";
            stmt.executeUpdate(sql);
                sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (-1,'Osher Ad','Tel Aviv','Avrami','0587778777');";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (1,'Rift Herald','Look4MeAtRishon','Agasi','0511111111');";
                stmt.executeUpdate(sql);
            sql="INSERT INTO Shops (code , region) VALUES (0,'A')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Shops (code , region) VALUES (414,'B')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Shops (code , region) VALUES (424,'A')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Shops (code , region) VALUES (434,'A')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Shops (code , region) VALUES (5,'B')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Banks (BankNumber, BankName) VALUES (1, 'Poalim')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Banks (BankNumber, BankName) VALUES (2, 'Leoomi')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Banks (BankNumber, BankName) VALUES (3, 'Yahav')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Banks (BankNumber, BankName) VALUES (4, 'Phoenix')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Banks (BankNumber, BankName) VALUES (5, 'Discount')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Roles (Role) VALUES ('Director of Personal Transport Center')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Roles (Role) VALUES ('Director of Personal Shops')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Roles (Role) VALUES ('Director of Logistics')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Roles (Role) VALUES ('Storekeeper')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Roles (Role) VALUES ('Shop Manager')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Roles (Role) VALUES ('Driver')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Roles (Role) VALUES ('Cleaner')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Roles (Role) VALUES ('Security')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Roles (Role) VALUES ('Cashier')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Roles (Role) VALUES ('Janitor')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (444444444, 'Yaakobi', 'Shimi', '18/02/2016', '...', 5000, 'Shop Manager', 0, 2, 45678)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (555555555, 'Trump', 'Shimon', '12/03/2012', '...', 5500, 'Shop Manager', 414, 3, 456789)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (666666666, 'Levi', 'Meni', '05/02/2011', '...', 5700, 'Shop Manager', 424, 4, 567890)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (777777777, 'Levi', 'Avram', '08/03/2001', '...', 5700, 'Shop Manager', 434, 4, 567891)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (202020202, 'Hassan', 'Hassani', '08/03/2001', '...', 5700, 'Shop Manager', 5, 4, 567892)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (888888888, 'Levi', 'Avrami', '05/02/2012', '...', 5200, 'Storekeeper', 5, 4, 11111)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (171717171, 'Scholes', 'Paul', '29/03/2016', '...', 5100, 'Storekeeper', 0, 1, 55555)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (181818181, 'Giggs', 'Rayn', '30/04/2017', '...', 5200, 'Cashier', 0, 2, 66666)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (191919191, 'Nevil', 'Gary', '19/05/2007', '...', 4900, 'Storekeeper', 0, 3, 77777)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (232323232, 'Keane', 'Roy', '16/06/2008', '...', 4800, 'Janitor', 0, 1, 88888)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (242424242, 'Best', 'Georgy', '27/07/2009', '...', 6000, 'Security', 0, 2, 99999)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (252525252, 'Ferguson', 'Alex', '21/08/2010', '...', 3900, 'Security', 414, 3, 12121)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (262626262, 'VanSaar', 'Edwin', '22/09/2011', '...', 4500, 'Director of Logistics', 0, 1, 13131)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (343434343, 'VanPersi', 'Robin', '22/09/2004', '...', 4500, 'Director of Personal Shops', 0, 1, 13141)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (353535353, 'Hernandez', 'Chicirito', '22/09/2003', '...', 4800, 'Director of Personal Shops', 424, 1, 13151)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (363636363, 'Carrick', 'Michel', '28/01/2006', '...', 4000, 'Director of Personal Shops', 414, 1, 13579)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (696969696, 'Peek', 'Zvika', '27/02/2005', '...', 4000, 'Director of Personal Shops', 5, 2, 24680)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (959595959, 'Hamacabi', 'Yehooda', '21/03/2006', '...', 4000, 'Cleaner', 414, 3, 13531)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (000000001, 'Gump', 'Forest', '12/05/2007', '...', 4000, 'Director of Personal Shops', 434, 4, 24642)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (000000000, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Director of Personal Transport Center', 0, 5, 46864)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (112211221, 'Yoni', 'Halevi', '14/08/2007', '...', 4000, 'Driver', 0, 5, 46864)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Drivers (ID, licence) VALUES (112211221 , 1)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (113311331, 'Grimm', 'Riper', '22/09/2003', '...', 4800, 'Driver', 0, 1, 13151)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Drivers (ID, licence) VALUES (113311331 , 2)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (223322332, 'Jony', 'Bravo', '21/08/2010', '...', 3900, 'Driver', 0, 3, 12121)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Drivers (ID, licence) VALUES (223322332 , 3)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber) VALUES (224422442, 'Jamal', 'Abdelnazzer', '12/03/2012', '...', 5500, 'Driver', 0, 3, 456789)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Drivers (ID, licence) VALUES (224422442 , 4)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Shifts(Code, Date, Day, Time, WorkPlace, ShiftManager) VALUES (1, '01/05/2017', 'Monday', 'morning', 0, 171717171)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Shifts(Code, Date, Day, Time, WorkPlace, ShiftManager) VALUES (2, '01/05/2017', 'Monday', 'evening', 0, 171717171)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Shifts(Code, Date, Day, Time, WorkPlace, ShiftManager) VALUES (3, '01/05/2017', 'Monday', 'morning', 414, 191919191)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Shifts(Code, Date, Day, Time, WorkPlace, ShiftManager) VALUES (4, '01/05/2017', 'Monday', 'evening', 414, 191919191)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO RolesInShifts(Role, Code) VALUES ('Storekeeper', 1)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO RolesInShifts(Role, Code) VALUES ('Cashier', 1)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO RolesInShifts(Role, Code) VALUES ('Storekeeper', 2)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO RolesInShifts(Role, Code) VALUES ('Janitor', 2)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO RolesInShifts(Role, Code) VALUES ('Storekeeper', 3)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO RolesInShifts(Role, Code) VALUES ('Security', 3)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO RolesInShifts(Role, Code) VALUES ('Storekeeper', 4)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO RolesInShifts(Role, Code) VALUES ('Cleaner', 4)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (171717171, 1, 'Available')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (171717171, 1, 'Working')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (181818181, 1, 'Available')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (181818181, 1, 'Working')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (171717171, 2, 'Available')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (171717171, 2, 'Working')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (232323232, 2, 'Available')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (232323232, 2, 'Working')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (191919191, 3, 'Available')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (191919191, 3, 'Working')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (252525252, 3, 'Available')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (252525252, 3, 'Working')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (191919191, 4, 'Available')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (191919191, 4, 'Working')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (959595959, 4, 'Available')";
            stmt.executeUpdate(sql);
            sql="INSERT INTO WorkersInShifts(ID, Code, Status) VALUES (959595959, 4, 'Working')";
            stmt.executeUpdate(sql);
            conn.commit();

            CATEGORIES.addCategory(new Category(102, "KARTON", 103));
            CATEGORIES.addCategory(new Category(100, "Milk", 102));
            CATEGORIES.addCategory(new Category(101, "Meat"));
            CATEGORIES.addCategory(new Category(104, "Bread"));
            CATEGORIES.addCategory(new Category(105, "35%", 101));


            SUPPLIERS.addSupplier(new Supplier(100000, "TNUVA", 1, 1, 15, "check",
                    "with delivery", "Sunday", "netivot"));
            SUPPLIERS.addSupplier(new Supplier(200000, "TARA", 2, 2, 16, "check",
                    "without delivery", "Sunday", "shfaram"));
            SUPPLIERS.addSupplier(new Supplier(300000, "MOTHER-EARTH", 3, 3, 17, "check",
                    "with delivery", "Sunday", "plat-earth"));



                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000001, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Director of Personal Transport Center', 100000, 5, 55001)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000002, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Storekeeper', 100000, 5, 55002)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000003, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Director of Personal Shops', 100000, 5, 55003)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000004, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Shop Manager', 100000, 5, 55004)";
                stmt.executeUpdate(sql);


                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000005, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Director of Personal Transport Center', 200000, 5, 55005)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000006, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Storekeeper', 200000, 5, 55006)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000007, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Director of Personal Shops', 200000, 5, 55007)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000008, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Shop Manager', 200000, 5, 55008)";
                stmt.executeUpdate(sql);


                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000009, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Director of Personal Transport Center', 300000, 5, 55009)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000010, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Storekeeper', 300000, 5, 55010)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000011, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Director of Personal Shops', 300000, 5, 55011)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                        " VALUES (550000012, 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Shop Manager', 300000, 5, 55012)";
                stmt.executeUpdate(sql);
                conn.commit();




                int j=21;
                for(int i =0; i<=1; i++) {
                    sql = "INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                            " VALUES (5500000"+j+", 'gvanya', 'Mr', '14/08/2007', '...', 4000, 'Director of Personal Transport Center', "+((i==0)?0:5)+", 5, 5500"+(j++)+")";
                    stmt.executeUpdate(sql);
                    sql = "INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                            " VALUES (5500000"+j+", 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Storekeeper', "+((i==0)?0:5)+", 5, 5500"+(j++)+")";
                    stmt.executeUpdate(sql);
                    sql = "INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                            " VALUES (5500000"+j+", 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Director of Personal Shops', "+((i==0)?0:5)+", 5, 5500"+(j++)+")";
                    stmt.executeUpdate(sql);
                    sql = "INSERT INTO Workers (ID, Lname, Fname, startDate, TermsOfEmployment, Salary, Role, WorkPlace, BankNumber, BankAccountNumber)" +
                            " VALUES (5500000"+j+", 'Agvanya', 'Mr', '14/08/2007', '...', 4000, 'Shop Manager', "+((i==0)?0:5)+", 5, 5500"+(j++)+")";
                    stmt.executeUpdate(sql);

                }
                conn.commit();

            CONTACTS.addContact(new Contact("101010101", 100000, "Geula Stone",
                    "0523614498", "a@a.a"));

            CONTACTS.addContact(new Contact("202020202", 200000, "Shlomi Saturday",
                    "0598321175", "a@a.a"));

            CONTACTS.addContact(new Contact("303030303", 300000, "Dudu Flying",
                    "0526489301", "c@c.c"));

            ITEMS.addItem(new Item(111111, "KORNFLEKS", 102, "SHKEL-INC",0.5,""));
            ITEMS.addItem(new Item(222222, "Steak", 101, "COWS-KILLERS",1,""));
            ITEMS.addItem(new Item(333333, "Cheese", 100, "TARA",0.2,""));
            ITEMS.addItem(new Item(444444, "White-Bread", 101, "Bereshit",0.4,""));
            ITEMS.addItem(new Item(555555, "Soda", 103, "Shweps",6,""));
            ITEMS.addItem(new Item(666666, "Cola", 103, "Coca-Cola",8,""));
            ITEMS.addItem(new Item(777777, "Arak", 103, "Tzuani-Nehmad",2,""));
            ITEMS.addItem(new Item(888888, "Potatoes", 104, "Mother-Earth",1,""));
            ITEMS.addItem(new Item(999999, "Tomato", 104, "Mother-Earth",0.7,""));
            ITEMS.addItem(new Item(101010, "Rice", 105, "Mother-Earth",0.4,""));
            ITEMS.addItem(new Item(202020, "Eggs", 105, "Mother-Chicken",0.3,""));


            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(100000, 111111, 100000, 12.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 111111, 100001, 12.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 333333, 100001, 12.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 222222, 200000, 22.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(300000, 333333, 300000, 32.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(100000, 444444, 400000, 42.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 555555, 500000, 52.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(300000, 666666, 600000, 52.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(100000, 777777, 700000, 62.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 888888, 800000, 72.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(300000, 999999, 900000, 82.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(100000, 101010, 110000, 92.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 202020, 120000, 112.5));

            DISCOUNTS.addDiscount(new Discount(100000, 111111, 20, 10));
            DISCOUNTS.addDiscount(new Discount(200000, 111111, 20, 20));
            DISCOUNTS.addDiscount(new Discount(200000, 222222, 20, 20));
            DISCOUNTS.addDiscount(new Discount(300000, 333333, 20, 30));
            DISCOUNTS.addDiscount(new Discount(200000, 333333, 20, 40));

            int SHOPID = 0;

                ORDERS.addOrder(new Order(1, SHOPID, 200000, new Date(new java.util.Date()),  "202020202", 0));
                ORDERS.addOrder(new Order(2, SHOPID, 300000, new Date(new java.util.Date()),  "303030303", 0));
                ORDERS.addOrder(new Order(3, SHOPID, 100000, new Date(new java.util.Date()),  "101010101", 0));


                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 111111, 20, 50.0));
                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 222222, 30, 10));
                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 333333, 80, 60.0));
                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 444444, 60, 10));
                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 555555, 60, 22.5));
                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 666666, 70, 10));
                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 777777, 80, 32.5));
                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 888888, 110, 10));
                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 999999, 150, 42.5));
                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 101010, 130, 10));
                ORDERS_ITEMS.addOrderItem(new OrderItem(3, 202020, 120, 12.5));

                for(int i=1; i<=9; i++)
                {
                    ORDERS_ITEMS.addOrderItem(new OrderItem(1, 111111*i, 10*i, 6*i));
                    ORDERS_ITEMS.addOrderItem(new OrderItem(2, 111111*i, 13*i, 11*i));
                }

                QUANTITIES.addItemQuantity(new Quantity(111111, SHOPID,"SHELF 2-A", 10,
                        10, 10, 0, 30));
                QUANTITIES.addItemQuantity(new Quantity(222222, SHOPID,"SHELF 2-B", 10,
                        20, 10, 0, 12));
                QUANTITIES.addItemQuantity(new Quantity(333333, SHOPID,"SHELF 2-C", 20,
                        30, 10, 30, 63));
                QUANTITIES.addItemQuantity(new Quantity(444444, SHOPID,"SHELF 2-D", 0,
                        40, 20, 20, 80));
                QUANTITIES.addItemQuantity(new Quantity(555555, SHOPID,"SHELF 2-E", 0,
                        50, 20, 10, 10));
                QUANTITIES.addItemQuantity(new Quantity(666666, SHOPID, "SHELF 2-F", 0,
                        60, 30, 10, 13));
                QUANTITIES.addItemQuantity(new Quantity(777777, SHOPID,"SHELF 2-G", 0,
                        70, 30, 10, 50));
                QUANTITIES.addItemQuantity(new Quantity(888888, SHOPID,"SHELF 2-H", 0,
                        80, 40, 30, 90));
                QUANTITIES.addItemQuantity(new Quantity(999999, SHOPID,"SHELF 2-I", 0,
                        90, 40, 60, 60));
                QUANTITIES.addItemQuantity(new Quantity(101010, SHOPID,"SHELF 2-J", 0,
                        100, 50, 30, 30));
                QUANTITIES.addItemQuantity(new Quantity(202020, SHOPID,"SHELF 2-K", 0,
                        110, 50, 10, 50));

                PRICES.addItemPrice(new Price(111111, 20.5, 0, null, null));
                PRICES.addItemPrice(new Price(222222, 30.5, 0, null, null));
                PRICES.addItemPrice(new Price(333333, 50.5, 0, null, null));
                PRICES.addItemPrice(new Price(444444, 60.5, 0, null, null));
                PRICES.addItemPrice(new Price(555555, 70.5, 30,
                        new Date(2017, 12, 10), new Date(2017, 12, 30)));
                PRICES.addItemPrice(new Price(666666, 80.5, 20,
                        new Date(2017, 4, 10), new Date(2017, 4, 16)));
                PRICES.addItemPrice(new Price(777777, 90.5, 10,
                        new Date(2017, 3, 10), new Date(2017, 3, 14)));
                PRICES.addItemPrice(new Price(888888, 100.5, 0, null, null));
                PRICES.addItemPrice(new Price(999999, 200.5, 0, null, null));
                PRICES.addItemPrice(new Price(101010, 56.5, 0, null, null));
                PRICES.addItemPrice(new Price(202020, 23.5, 0, null, null));

                ORDERS.setArrivalDate(3, new Date(new java.util.Date()));


                SHOPID = 5;


                ORDERS.addOrder(new Order(4, SHOPID, 200000, new Date(new java.util.Date()),  "202020202", 0));
                ORDERS.addOrder(new Order(5, SHOPID, 300000, new Date(new java.util.Date()),  "303030303", 0));
                ORDERS.addOrder(new Order(6, SHOPID, 100000, new Date(new java.util.Date()),  "101010101", 0));


                ORDERS_ITEMS.addOrderItem(new OrderItem(4, 111111, 20, 50.0));
                ORDERS_ITEMS.addOrderItem(new OrderItem(5, 222222, 30, 10));
                ORDERS_ITEMS.addOrderItem(new OrderItem(6, 333333, 80, 60.0));
                ORDERS_ITEMS.addOrderItem(new OrderItem(4, 444444, 60, 10));
                ORDERS_ITEMS.addOrderItem(new OrderItem(5, 555555, 60, 22.5));
                ORDERS_ITEMS.addOrderItem(new OrderItem(6, 666666, 70, 10));
                ORDERS_ITEMS.addOrderItem(new OrderItem(4, 777777, 80, 32.5));
                ORDERS_ITEMS.addOrderItem(new OrderItem(5, 888888, 110, 10));
                ORDERS_ITEMS.addOrderItem(new OrderItem(6, 999999, 150, 42.5));
                ORDERS_ITEMS.addOrderItem(new OrderItem(4, 101010, 130, 10));
                ORDERS_ITEMS.addOrderItem(new OrderItem(5, 202020, 120, 12.5));

                for(int i=1; i<=9; i++)
                {
                    ORDERS_ITEMS.addOrderItem(new OrderItem(1, 111111*i, 9*i, 21*i));
                    ORDERS_ITEMS.addOrderItem(new OrderItem(3, 111111*i, 2*i, 5*i));
                }

                QUANTITIES.addItemQuantity(new Quantity(111111, SHOPID,"SHELF 2-A", 10,
                        10, 10, 0, 30));
                QUANTITIES.addItemQuantity(new Quantity(222222, SHOPID,"SHELF 2-B", 10,
                        20, 10, 0, 12));
                QUANTITIES.addItemQuantity(new Quantity(333333, SHOPID,"SHELF 2-C", 20,
                        30, 10, 30, 63));
                QUANTITIES.addItemQuantity(new Quantity(444444, SHOPID,"SHELF 2-D", 30,
                        10, 20, 20, 80));
                QUANTITIES.addItemQuantity(new Quantity(555555, SHOPID,"SHELF 2-E", 0,
                        50, 20, 10, 10));
                QUANTITIES.addItemQuantity(new Quantity(666666, SHOPID, "SHELF 2-F", 20,
                        40, 30, 10, 13));
                QUANTITIES.addItemQuantity(new Quantity(777777, SHOPID,"SHELF 2-G", 0,
                        70, 30, 10, 50));
                QUANTITIES.addItemQuantity(new Quantity(888888, SHOPID,"SHELF 2-H", 15,
                        65, 40, 30, 90));
                QUANTITIES.addItemQuantity(new Quantity(999999, SHOPID,"SHELF 2-I", 0,
                        90, 40, 60, 60));
                QUANTITIES.addItemQuantity(new Quantity(101010, SHOPID,"SHELF 2-J", 0,
                        100, 50, 30, 30));
                QUANTITIES.addItemQuantity(new Quantity(202020, SHOPID,"SHELF 2-K", 0,
                        110, 50, 10, 50));

                PRICES.addItemPrice(new Price(111111, 10.5, 0, null, null));
                PRICES.addItemPrice(new Price(222222, 10.5, 0, null, null));
                PRICES.addItemPrice(new Price(333333, 10.5, 0, null, null));
                PRICES.addItemPrice(new Price(444444, 10.5, 0, null, null));
                PRICES.addItemPrice(new Price(555555, 10.5, 30,
                        new Date(2017, 12, 10), new Date(2017, 12, 30)));
                PRICES.addItemPrice(new Price(666666, 10.5, 20,
                        new Date(2017, 4, 10), new Date(2017, 4, 16)));
                PRICES.addItemPrice(new Price(777777, 10.5, 10,
                        new Date(2017, 3, 10), new Date(2017, 3, 14)));
                PRICES.addItemPrice(new Price(888888, 10.5, 0, null, null));
                PRICES.addItemPrice(new Price(999999, 10.5, 0, null, null));
                PRICES.addItemPrice(new Price(101010, 10.5, 0, null, null));
                PRICES.addItemPrice(new Price(202020, 10.5, 0, null, null));

                ORDERS.setArrivalDate(2, new Date(new java.util.Date()));

                bl.addTransport(new Date(new java.util.Date()).toStringWithBackslash(),"08:00",""+1);

                conn.commit();

            } catch (SQLException e) {
            e.printStackTrace();
            }
            catch (NituzException ignored){}
        }

        SBL.initOrderID();

        checkPeriodicOrders = new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }
            while(!roleSet)
            {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}
                if(!continuePeriodCheck)
                {
                    break;
                }
            }
            while(continuePeriodCheck){

                Order [] orders = ORDERS.getPeriodicOrders(BL.shopID);
                List<Order> warnings = new ArrayList<>();
                int count = 0;
                for(Order order:orders)
                {
                    // check if tomorrow the order comes
                    java.sql.Date lastDate = order.getDate().toSQLdate();
                    java.sql.Date todayDate = new Date(new java.util.Date()).toSQLdate();
                    long diff = todayDate.getTime() - lastDate.getTime();
                    long days = TimeUnit.MILLISECONDS.toDays(diff);
                    int frequency = ORDERS.getFrequency(order.getOrderID());

                    if(days - frequency == 0)
                    {
                        ORDERS.setDate(order.getOrderID(),new Date(new java.util.Date()));
                        ORDERS.setArrivalDate(order.getOrderID(),null);
                    }
                    if(frequency - days == 1)
                    {
                        count++;
                        warnings.add(order);
                    }
                }

                orders = new Order[count];
                orders = warnings.toArray(orders);

                if(orders.length > 0)
                {
                    synchronized (System.in)
                    {
                        for (Order order : orders) {
                            //System.out.println(order.toStringWithoutOrderItems());
                            OrderItem[] OI = ORDERS_ITEMS.getOrderItems(order.getOrderID());
                            for (OrderItem aOI : OI) {
                                Iterator iterator = alreadyWarned.iterator();
                                boolean continue_ = false;
                                while (iterator.hasNext()) {
                                    OrderItem oi = (OrderItem) iterator.next();
                                    if (oi.getOrderID() == aOI.getOrderID() && aOI.getItemID() == oi.getItemID()) {
                                        continue_ = true;
                                    }
                                }
                                if (continue_) {
                                    continue;
                                }
                                System.out.println("Periodic Order for tomorrow found, OrderID: "+aOI.getOrderID());
                                System.out.println(aOI.toString());
                                Quantity q = QUANTITIES.getQuantity(aOI.getItemID(),BL.shopID);
                                System.out.println("The current Amount is: " + (q == null ? "0" : q.getCurrent()));
                                System.out.println("Would you like to change the amount to order? Enter 'yes' to change");
                                String choice = sc.nextLine();
                                if (choice.equals("yes")) {
                                    System.out.println("New Amount: ");
                                    int amount = Integer.parseInt(sc.nextLine());
                                    boolean result = ORDERS_ITEMS.setQuantity(order.getOrderID(), aOI.getItemID(), amount);
                                    System.out.println("Update status: " + result);
                                    alreadyWarned.add(aOI);
                                }
                                alreadyWarned.add(aOI);
                            }
                        }
                    }
                }


                try {
                    Thread.sleep(24*60*60);
                } catch (InterruptedException e)
                {
                }
            }
        });
        checkPeriodicOrders.start();

        // start
        try{
            MENU.start();
        }
        catch (NituzException ignored){}
        continuePeriodCheck = false;
        checkPeriodicOrders.interrupt();
        checkPeriodicOrders.join();


        try
        {
            conn.commit();
            conn.close();
            Thread.sleep(500);

        } catch (Exception ignored){}
    }

    private static Connection getConnectionAndInitDatabase(String dataBaseName) {
        Connection c = null;
        Statement stmt = null;
        try {
            /*Opening Connection*/
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dataBaseName);

            c.createStatement().execute("PRAGMA FOREIGN_KEYS = ON;");
            c.setAutoCommit(false);


            String sql;

            stmt = c.createStatement();



            //********************************************************************************************************

            sql = "CREATE TABLE IF NOT EXISTS Drivers "+
                    "(ID INT PRIMARY KEY NOT NULL REFERENCES Workers(ID) ,"+
                    " Licence INT NOT NULL);";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE IF NOT EXISTS Trucks"+
                    " (Plate VARCHAR(10) PRIMARY KEY NOT NULL,"+
                    " Model VARCHAR(50) NOT NULL, licenseType INT NOT NULL ,"+
                    " Wight INT NOT NULL , MaxWight INT NOT NULL);";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE IF NOT EXISTS Transport"+
                    " (TransportNumber INT PRIMARY KEY NOT NULL "+
                    " ,date DATE NOT NULL );";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE IF NOT EXISTS Sites"+
                    " (code INT PRIMARY KEY NOT NULL,"+
                    " Name VARCHAR(50) NOT NULL ,"+
                    "Address VARCHAR(50) NOT NULL,"+
                    " Contact VARCHAR(50) NOT NULL,"+
                    " Phone VARCHAR(10) NOT NULL);";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE IF NOT EXISTS Shops"+
                    " (code INT PRIMARY KEY REFERENCES Sites(code),"+
                    " region VARCHAR(50) NOT NULL );";
            stmt.executeUpdate(sql);
            sql="CREATE TABLE IF NOT EXISTS Missions"+
                    " (shop INT REFERENCES Shops(code),"+
                    " Supplier INT REFERENCES Supliers(code) ,"+
                    "Transport INT REFERENCES Transport(TransportNumber),"+
                    " item INT REFERENCES Itemss(code),"+
                    "plandQ INT ,actualQ INT ,"+
                    "PRIMARY KEY (shop,Supplier,Transport,item));";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE IF NOT EXISTS TrucksTrnsportSigning "+
                    "(truck VARCHAR(10) REFERENCES Trucks(Plate),"+
                    "transport INT REFERENCES Transport(TransportNumber),"+
                    "PRIMARY KEY (truck,transport));";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE IF NOT EXISTS driverAsiignmetns "+
                    "(truck VARCHAR(10) REFERENCES Trucks(Plate),"+
                    "transport INT PRIMARY KEY REFERENCES Transport(TransportNumber) ,"+
                    "driver INT REFERENCES Drivers(ID));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Workers "+
                    "(ID INT PRIMARY KEY NOT NULL, "+
                    "Lname VARCHAR(50) NOT NULL, "+
                    "Fname VARCHAR(50) NOT NULL, "+
                    "startDate VARCHAR(50) DEFAULT  NULL,"+
                    "TermsOfEmployment VARCHAR(250),"+
                    " Salary INT,"+
                    "Role VARCHAR(250) NOT NULL,"+
                    "WorkPlace INT NOT NULL," +
                    "BankNumber INT NOT NULL,"+
                    " BankAccountNumber INT NOT NULL,"+
                    "FOREIGN KEY(BankNumber) REFERENCES Banks(BankNumber),"+
                    " FOREIGN KEY(Role) REFERENCES Roles(Role),"+
                    " FOREIGN KEY(WorkPlace) REFERENCES Sites(code));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Banks"+
                    " (BankNumber INT PRIMARY KEY NOT NULL,"+
                    " BankName VARCHAR(50) NOT NULL );";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Shifts"+
                    " (Code INT PRIMARY KEY NOT NULL,"+
                    " Date VARCHAR(50) NOT NULL,"+
                    " Day VARCHAR(50) NOT NULL,"+
                    " Time VARCHAR(50) NOT NULL,"+
                    " WorkPlace INT NOT NULL,"+
                    " ShiftManager INT,"+
                    " FOREIGN KEY(ShiftManager) REFERENCES Workers(ID) ON DELETE CASCADE,"+
                    " FOREIGN KEY(WorkPlace) REFERENCES Sites(Code));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Roles "+
                    "(Role VARCHAR(50) PRIMARY KEY NOT NULL );";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS RolesInShifts "+
                    "(Role VARCHAR(50) NOT NULL, "+
                    "Code INT NOT NULL, "+
                    "FOREIGN KEY(Role) REFERENCES Roles(Role), "+
                    "FOREIGN KEY(Code) REFERENCES Shifts(Code) ON DELETE CASCADE );";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS WorkersInShifts "+
                    "(ID INT NOT NULL, Code INT NOT NULL,"+
                    " Status VARCHAR(50) NOT NULL,"+
                    " FOREIGN KEY(ID) REFERENCES Workers(ID),"+
                    " FOREIGN KEY(Code) REFERENCES Shifts(Code) ON DELETE CASCADE );";
            stmt.executeUpdate(sql);

            //********************************************************************************************************

            /*Creating Tables if they are NOT existed */

            /*
                Suppliers Table : ID, Name, BankNum, BranchBum, AccountNum, Payment, DeliveryMethod, SupplyTime, Address.
             */
            sql = "CREATE TABLE IF NOT EXISTS Suppliers " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    "Name TEXT NOT NULL," +
                    " BankNum          INT    NOT NULL, " +
                    " BranchNum        INT    NOT NULL, " +
                    " AccountNum	   INT    NOT NULL, " +
                    " Payment         TEXT	NOT NULL," +
                    " DeliveryMethod TEXT NOT NULL," + //SOMEONE BRING ME *OR* I BRING FROM SOMEONE
                    " SupplyTime TEXT," + // DAYS
                    " FOREIGN KEY(BankNum) REFERENCES Banks(BankNumber)ON UPDATE CASCADE, "+
                    " FOREIGN KEY(ID) REFERENCES Sites(code) ON UPDATE CASCADE ON DELETE CASCADE);";
            stmt.execute(sql);
            stmt.close();

            /*
                Contacts Table : ID, Full name, Phone Number, Email.
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Contacts " +
                    "(ID   TEXT NOT NULL," +
                    " SiteID INT  NOT NULL," +
                    " FullName   TEXT  NOT NULL, " +
                    " PhoneNumber TEXT NOT NULL, " +
                    " Email	TEXT," +
                    "PRIMARY KEY(SiteID, ID),"+
                    "FOREIGN KEY(SiteID) REFERENCES Sites(code) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE);";
            stmt.execute(sql);
            stmt.close();

            /*
                Category : ID, Name, ID_Father. When ID_father is -1, that category has no father.
             */
            stmt = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS CATEGORY " +
                    "(ID INT PRIMARY KEY     NOT NULL ," +
                    " NAME           CHAR(50) NOT NULL, " +
                    " ID_FATHER  INT DEFAULT NULL REFERENCES CATEGORY(ID) " +
                    " ON UPDATE CASCADE ON DELETE SET NULL);";
            stmt.execute(sql);
            stmt.close();

            /*
                Items : ID, Name, CategoryNumber, Manufacture.
             */
            stmt = c.createStatement();
            sql = "  CREATE TABLE IF NOT EXISTS Items " +
                    " (ID   INT PRIMARY KEY  NOT NULL," +
                    " NAME   TEXT NOT NULL, " +
                    " CategoryNumber       INT    REFERENCES CATEGORY(ID) ON DELETE SET NULL ON UPDATE CASCADE, " +
                    " Manufacture         TEXT    NOT NULL," +
                    " Weight REAL NOT NULL," +
                    " Description TEXT);";
            stmt.execute(sql);
            stmt.close();


            /*
                SuppliersItems : SupplierID, ItemID, CatalogNumber, Cost, SupplierID(FR), ItemID(FR)
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS SupplierItems " +
                    "(SupplierID INT   NOT NULL," +
                    " ItemID INT  NOT NULL," +
                    " CatalogNumber INT NOT NULL,"+
                    " Cost REAL  NOT NULL, " +
                    " PRIMARY KEY(SupplierID,ItemID)," +
                    " FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(ItemID) REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE); " ;
            stmt.execute(sql);
            stmt.close();


            /*
                Discounts : SupplierID, ItemID, Quantity, DiscountPercentage, SupplierID(FR), ItemID(FR)
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Discounts " +
                    "(SupplierID INT  NOT NULL," +
                    " ItemID INT   NOT NULL," +
                    " Quantity INT NOT NULL,"+
                    " DiscountPercentage INT  NOT NULL, " +
                    " PRIMARY KEY (SupplierID, ItemID, Quantity),"+
                    " FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(ItemID) REFERENCES Items(ID) ON DELETE CASCADE ON UPDATE CASCADE); " ;
            stmt.execute(sql);
            stmt.close();


            stmt = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS Orders " +
                    "(OrderID INT PRIMARY KEY  NOT NULL," +
                    " ShopID INT NOT NULL," +
                    " SupplierID INT   NOT NULL," +
                    " Date  DATE  NOT NULL, " +
                    " ContactID TEXT  NOT NULL, " +
                    " ArrivalDate Date DEFAULT NULL," +
                    " OrderFrequency INT NOT NULL DEFAULT 0," +
                    " FOREIGN KEY(SupplierID , ContactID) REFERENCES Contacts(SiteID, ID) ON UPDATE CASCADE ON DELETE CASCADE," +
                    " FOREIGN KEY(ShopID) REFERENCES Shops(code));";
            stmt.execute(sql);
            stmt.close();


            stmt = c.createStatement();
            sql =    "CREATE TABLE IF NOT EXISTS OrdersItems " +
                    "(OrderID INT  NOT NULL," +
                    " ItemID INT NOT NULL,"+
                    " Quantity INT  NOT NULL," +
                    " FinalCost REAL  NOT NULL, " +
                    "PRIMARY KEY(OrderID,ItemID), "+
                    " FOREIGN KEY(OrderID) REFERENCES Orders(OrderID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(ItemID) REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE);";
            stmt.execute(sql);
            stmt.close();


            /*
                Quantities : ItemID, Location, Defects, Warehouse, Minimum, Store, Order. (Current = Store+Warehouse+Defects)
             */
            stmt = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS QUANTITIES " +
                    "(ItemID INT NOT NULL," +
                    "ShopID INT NOT NULL ," +
                    "LOCATION TEXT NOT NULL," +
                    "MINIMUM INT NOT NULL," +
                    "ORDER_AMOUNT INT DEFAULT 0," +
                    "WAREHOUSE INT NOT NULL," +
                    "STORE INT NOT NULL," +
                    "DEFECTS INT NOT NULL, " +
                    "PRIMARY KEY(ItemID,ShopID), " +
                    "FOREIGN KEY(ItemID) REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE," +
                    "FOREIGN KEY(ShopID) REFERENCES Shops(code) ON UPDATE CASCADE ON DELETE CASCADE);";
            stmt.execute(sql);
            stmt.close();

            /*
                Prices : ItemID, SellPrice,, Percentage, DateStart, DateEnd.
            */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS PRICES" +
                    "(ItemID INT REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE ," +
                    "SellPrice REAL NOT NULL," +
                    "Percentage INT,"+
                    "DateStart DATE," +
                    "DateEnd DATE);";
            stmt.execute(sql);
            stmt.close();



            c.commit();
            stmt.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return c;
    }


}