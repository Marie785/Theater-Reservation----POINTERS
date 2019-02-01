//
//Marie-Claire Salha
//mjs170530
//
package Tickets;
import java.util.ArrayList;

public class Customer 
{
    //declare username and password variables
    //make an array list of order objects
    String username = "";
    String password = "";
    ArrayList<Order> orders = new ArrayList<>();
    
    //overloaded constructor
    //enters in the login informatin from the file once it's read
    Customer(String u, String p)
    {
        username = u;
        password = p;
    }
    
    //accessors
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public int getArraySize() {return orders.size();}
    public Order getOrder(int order) {return orders.get(order);}
    
    //mutators
    public void setUsername(String u) {username = u;}
    public void setPassword(String p) {password = p;}
    
    
    //function to add to array list
    public void addOrder(Order O, int rowNum, char startingSeat, int totalTickets, int adult, int child, int senior)
    {
        //adds one order object onto the array list, essentially adding one 
        //element to the list
        orders.add(O);
        
        //calls the order object's function so that I can update the seats
        //for that order
        O.addSeats(rowNum, startingSeat, totalTickets, adult, child, senior);
        
    }
    
    //function to delete an order from the list
    public void deleteOrder(int updateOrder)
    {
        orders.remove(updateOrder - 1);
    }
    
    //this will display each order object
    public void displayOrder()
    {
        //this will loop through the array list
        for(int i = 0; i < orders.size(); i++)
        {
            orders.get(i).displaySeats((i + 1));
        }
        
    }
    
    //displays orders, but adds in how much they have to pay
    public double displayMoney()
    {
        double total = 0;
        double totalAll = 0;
        //this will loop through the array list
        for(int i = 0; i < orders.size(); i++)
        {
            orders.get(i).displaySeats((i + 1));
            total = orders.get(i).amountDue();
            totalAll += total;
            System.out.println("Your bill for this order is: $" + total);
        }
        
        return totalAll;
    }
    
    //This function will find what auditorium the user is updating when they
    //go through the update order menu
    public int getAuditorium(int updateOrder)
    {
        //thisw ill return the auditorium the user wants to update for their order
        return orders.get(updateOrder).auditorium;
    }
}
