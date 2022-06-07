package nl.hu.bep.setup;

import nl.hu.bep.shopping.model.MyUser;
import nl.hu.bep.shopping.model.Product;
import nl.hu.bep.shopping.model.Shopper;
import nl.hu.bep.shopping.model.ShoppingList;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("initializing application");
        Shopper p = new Shopper("Dum-Dum");
        Shopper c = new Shopper("Jack-Sparrow");
        ShoppingList il = new ShoppingList("initialList", p);
        ShoppingList al = new ShoppingList("anotherList", p);
        ShoppingList al2 = new ShoppingList("Jack's-list", c);
        p.addList(il);
        p.addList(al);
        c.addList(al2);
        il.addItem(new Product("Cola Zero"), 4);
        il.addItem(new Product("Cola Zero"), 4);
        il.addItem(new Product("Toiletpapier 6stk"), 1);
        al.addItem(new Product("Paracetamol 30stk"), 3);
        al2.addItem(new Product("Jack Daniels Whiskey"), 4);
        al2.addItem(new Product("the Rum that is gone"),6);

        MyUser a = new MyUser("Thomas", "thomas", "user", "wachtwoord");
        MyUser b = new MyUser("Thom", "thom", "admin", "wachtwoord");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("terminating application");
    }

}
