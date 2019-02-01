//
//Marie-Claire Salha
//mjs170530
//
package Tickets;
import java.util.ArrayList;

public class Order 
{
    //auditorium will tell us which auditorium they're in 
    //seat will contain a singular seat, including the row and the letter
    int auditorium = 0;
    String seat = "";
    
    //contains how many tickets there are 
    int adult = 0;
    int child = 0;
    int senior = 0;
    
    //will contain all of the seats from that order (whether updated or not)
    ArrayList<String> seating = new ArrayList<>();
    
    //overloaded constructor
    //put in the information for the order, not including the seats
    Order (int a, int ad, int c, int s)
    {
        auditorium = a;
        adult = ad;
        child = c;
        senior = s;
    }
    
    //this will update the orders array that contains all of the seats for the
    //customer
    public void addSeats(int rowNum, char startingSeat, int totalTickets, int a, int c, int s)
    {
        //this will loop through to make sure that you're adding the correct
        //amount of seats to the array
        for(int i = 0; i < totalTickets; i++)
        {
            seat += Integer.toString(rowNum);
            seat += (char)(startingSeat + i);
            seating.add(seat);
            
            //clears the seat so that it doesn't keep the previous info
            seat = "";
        }
        
        //updates the quantity for each ticket type as well
        adult  += a;
        child  += c;
        senior += s;
    }
    
    //this will remove one singular seat from the order array
    public void removeSeats(String seatInput, int updateOrder, Customer C)
    {
        //this function will only be called if we are deleting a singular seat
        //from the order
        //we will loop through the arrayList until we find the seat we are
        //deleting
        int i = 0;
        for(i = 0; i < seating.size(); i++)
        {
            //if you find a match, then set the boolean to true, and break out
            //of the loop
            if(seating.get(i).equals(seatInput))
            {
                break;
            }
        }
        //after we find the seat we're removing, we then remove it from the list
        seating.remove(i);
        
        //if the array is empty after removing this seat, then delete the order
        if(seating.isEmpty() == true)
        {
            C.deleteOrder(updateOrder);
        }
    }
    
    //this will decrement the correct ticket type after removing the seat, but
    //before removing the order completely from the list
    public void decrementTicketTypes(char removeThis)
    {
        if(removeThis == 'A')
            adult--;
        else if(removeThis == 'C')
            child--;
        else if(removeThis == 'S')
            senior--;
    }
    
    
    //this will compare seats to see if what the user entered was a valid seat
    //to delete
    public boolean compareSeats(String seatInput)
    {
        boolean exists = false;
        
        //loop through the array to see there are any seats that match what
        //the user inputted
        for(int i = 0; i < seating.size(); i++)
        {
            //if you find a match, then set the boolean to true, and break out
            //of the loop
            if(seating.get(i).equals(seatInput))
            {
                exists = true;
                break;
            }
            else
                exists = false;
        }
        
        return exists;
    }
    
    
    //this will print the array of seats the customer has reserved
    public void displaySeats(int orderNum)
    {
        //this variable will ensure that the seats are printed in groups of 5,
        //so that the seats are not all printed and cluttered onto one line
        int group = 0;
        
        System.out.println("Order " + orderNum + ":");
        System.out.println("Auditorium " + auditorium);
        System.out.println("Seats: ");
        
        for(int i = 0; i < seating.size(); i++)
        {
            //this will print out each seat in the order
            System.out.print(seating.get(i) + " ");
            group++;
            
            //if the group number has reached 5, it will start the next group
            //on the next line
            //it will also start over the counter for group
            if(group == 5)
            {
                System.out.println();
                group = 0;
            }
        }
        
        System.out.println();
        System.out.println("Tickets:");
        System.out.println("adult tickets:  " + adult);
        System.out.println("child tickets:  " + child);
        System.out.println("senior tickets: " + senior);
        System.out.println();
    }
    
    //gets the amount the customer owes for that order
    public double amountDue()
    {
        double total = 0;
        
        total += (adult * 10);
        total += (child * 5);
        total += (senior * 7.5);
        
        return total;
    }
    
    @Override
    public String toString()
    {
        String line = "";
        
        for(int i = 0; i < seating.size(); i++)
        {
            line += seating.get(i);
        }
        
        return line;
    }
    
}