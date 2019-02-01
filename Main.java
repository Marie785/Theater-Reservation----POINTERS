//
//Marrie-Claire Salha
//mjs170530
//
package Tickets;

import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

//prototypes: public static void printReport()
//prototypes: public static void reserveSeats()
//prototypes: public static void Auditorium1()

public class Main 
{
    public static void main(String[] args) throws IOException
    {
        //declare the hashmap
        //open up the file with the usernames and passwords
        HashMap<String, Customer> Account = new HashMap();
        
        //these are just my variables that will deal with usernames/passwords/menus
        File file = new File("userdb.dat");
        Scanner input = new Scanner(file);
        String user = "", pass = "";
        int cusMenu = 0, adminMenu = 0;
        boolean valid = true, repeatMenu = true;
        
        //these are the variables that will be interacting with the auditorium class
        Auditorium A1 = new Auditorium();
        Auditorium A2 = new Auditorium();
        Auditorium A3 = new Auditorium();
        int rowNum1 = 0,       rowNum2 = 0,       rowNum3 = 0;
        int adult1 = 0,        adult2 = 0,        adult3 = 0;
        int child1 = 0,        child2 = 0,        child3 = 0;
        int senior1 = 0,       senior2 = 0,       senior3 = 0;
        int totalTickets1 = 0, totalTickets2 = 0, totalTickets3 = 0;
        int colNums1 = 0,      colNums2 = 0,      colNums3 = 0;
        char startingSeat = ' ';
        String seatLetter = "";
        
        
        //* * * * * * * * * * * * * * * * * * * * * * * *
        //if the file exists, then fill in the hashmap  *
        //* * * * * * * * * * * * * * * * * * * * * * * *
        if(file.exists())
        {
            //while there is still stuff to read from the file, continue filling
            //in the hashmap
            while(input.hasNext())
            {
                //gets username and password from file, creates new object w/that info
                user = input.next();
                pass = input.next();
                
                //puts in new customer object into the hashmap
                Account.put(user, new Customer(user, pass));
            }
            
            user = "";
            pass = "";
            input.close();
        }
        //if it doesn't exist, then tell the user that the file doesn't exist
        else
        {
            System.out.println("The file containing the usernames and passwords doesn't exist.");
            input.close();
        }
        
        
        
        //* * * * * * * * * * * * * * * * * * * * * *
        //make the three separate auditoriums here  *
        //* * * * * * * * * * * * * * * * * * * * * *
        file = new File("A1.txt");
        input = new Scanner(file);
        
        if(file.exists())
        {
            A1 = new Auditorium(input);
            colNums1 = A1.getNumCols() + 64;
        }
        input.close();
        
        
        file = new File("A2.txt");
        input = new Scanner(file);
        
        if(file.exists())
        {
            A2 = new Auditorium(input);
            colNums2 = A2.getNumCols() + 64;
        }
        input.close();
        
        
        file = new File("A3.txt");
        input = new Scanner(file);
        
        if(file.exists())
        {
            A3 = new Auditorium(input);
            colNums3 = A3.getNumCols() + 64;
        }
        input.close();
        
        //changes the scanner so that it now deals with user input
        input = new Scanner(System.in);
        
        
        
        //* * * * * * * * * * * * * * * * * * * * * *
        //now the user will log in to reserve seats *
        //* * * * * * * * * * * * * * * * * * * * * *
        do
        {
            //sets the menu numbers back to zero just for safety
            cusMenu = 0;
            adminMenu = 0;
            
            //loop until they enter in a valid login
            do
            {
                //ask the user to enter in their username
                System.out.println("Please enter your username:");
                user = input.next();

                //loop at most three times if their password is incorrect
                for(int i = 0; i < 3; i++)
                {
                    //as the user the enter in their password
                    System.out.println("Please enter your password:");
                    pass = input.next();

                    //for just in case the username returns a null pointer
                    try
                    {
                        //check if they entered in the password correctly
                        if(Account.get(user).getPassword().equals(pass))
                        {
                            //valid = true means the login was a success
                            valid = true;
                            break;
                        }
                        else
                        {
                            //valid = false means the login wasn't a success
                            valid = false;
                            System.out.println("That password was incorrect.");
                        }
                    }
                    catch(NullPointerException N)   //if the username isn't valid
                    {
                        //valid = false means that the login wasn't a success
                        valid = false;
                        break;
                    }
                }
                
                //if the login was a success, then valid will be true
                //if the login was a failure at any point, then it will be false
                if(valid == true)
                    break;
                
            }while(valid == false);
            
            
            //check to see if it was the admin who logged in, or the user
            if(user.equals("admin"))
            {
                repeatMenu = true;
                //repeat the menu until they either exit or logout
                do
                {
                    //show the admin the menu
                    System.out.println("1. Print report");
                    System.out.println("2. Logout");
                    System.out.println("3. Exit");
                    System.out.println("Please enter a menu number:");
                    
                    //user input 
                    try
                    {
                        adminMenu = input.nextInt();
                    }
                    catch(InputMismatchException E)
                    {
                        System.out.println("\nThat was not a valid menu option.");
                        input.nextLine();
                        
                    }
                    
                    //if we want the menu to be shown again, then this boolean
                    //must be true
                    //if the admin entered in "1", then it will call a function
                    //to print out the report
                    //it will only be false once the admin enters in "2" or "3"
                    if(adminMenu == 1)
                        printReport(A1, A2, A3, adult1, adult2, adult3, child1, child2, child3, senior1, senior2, senior3);
                    else if(adminMenu == 2)
                        repeatMenu = false;
                    else if(adminMenu == 3)
                        repeatMenu = false;
                    
                    
                }while(repeatMenu == true);
            }
            else
            {
                Customer C = Account.get(user);
             
                int updateOrder = 0; //THIS IS FOR WHICH ORDER THEY WANT TO UPDATE
                int updateMenu = 0;  //THIS IS FOR WHAT THEY WANT TO DO WITH THAT ORDER
                int auditoriumNum = 0;
                String cancel = "";
                
                
                //repeat the menu until they logout
                do
                {
                    System.out.println("1. Reserve Seats");
                    System.out.println("2. View Orders");
                    System.out.println("3. Update Orders");
                    System.out.println("4. Display Receipt");
                    System.out.println("5. Log Out");
                    
                    //user input
                    try
                    {
                        cusMenu = input.nextInt();
                        
                        //if they try to enter in an option that doesn't exist
                        if(cusMenu < 1 || cusMenu > 5)
                            throw new InputMismatchException();
                        
                        //we will call different functions based on what they want to do
                        if(cusMenu == 1)    //if they want to reserve seats
                        {
                            reserveSeats(valid, input, A1, A2, A3, startingSeat, seatLetter, rowNum1, rowNum2, rowNum3,
                                         colNums1, colNums2, colNums3, adult1, adult2, adult3, child1, child2, child3, 
                                         senior1, senior2, senior3, C, 0, 0);
                        }
                        else if(cusMenu == 2)    //if they want to see their orders so far
                        {
                            C.displayOrder();
                        }
                        else if(cusMenu == 3 && !C.orders.isEmpty())    //if they want to update an order and the order arralist isn't empty
                        {
                            int numOrders = C.orders.size();  //this will be used to do the update orders menu
                                                   //it is the total number of orders made by that user
                            boolean optionSelected = false; //whether or not the user has chosen and update action or not
                            
                            
                            //display their options, and ask them to pick one
                            C.displayOrder();
                            System.out.println("Please select an order to update:");
                            
                            //validate the user input for which order they wanna update
                            repeatMenu = true;
                            updateOrder = 0;
                            updateMenu = 0;
                            do
                            {
                                try
                                {
                                    updateOrder = input.nextInt();
                                    
                                    //if they enter in a menu number that's not valid, then
                                    //throw an exception
                                    if(updateOrder < 1 || updateOrder > numOrders)
                                        throw new InputMismatchException();
                                }
                                catch(InputMismatchException E)
                                {
                                    System.out.println("That was not a valid menu number.");
                                    input.nextLine();
                                }
                                
                                //if they entered in a valid menu number, then the input
                                //validation loop will exit
                                if(updateOrder >= 1 && updateOrder <= numOrders)
                                    repeatMenu = false;
                                
                            }while(repeatMenu == true);
                            
                            //loop until the user performs an "update action" from below
                            do
                            {
                                //after they choose an order to update, show the user
                                //ANOTHER menu to choose what they want to do with that order
                                System.out.println("1. Add tickets to order");
                                System.out.println("2. Delete tickets from order");
                                System.out.println("3. Cancel order");
                                System.out.println("Please select what you would like to do with your order:");

                                //validate menu input from user for what they want to do with their order
                                repeatMenu = true;
                                do
                                {
                                    try
                                    {
                                        updateMenu = input.nextInt();

                                        if(updateMenu < 1 || updateMenu > 3)
                                            throw new InputMismatchException();
                                    }
                                    catch(InputMismatchException E)
                                    {
                                        System.out.println("That was not a valid menu number.");
                                        input.nextLine();
                                    }

                                    //if the user wants to add to their already existing order
                                    if(updateMenu == 1)
                                    {
                                        optionSelected = reserveSeats(valid, input, A1, A2, A3, startingSeat, seatLetter, rowNum1, rowNum2, rowNum3,
                                                            colNums1, colNums2, colNums3, adult1, adult2, adult3, child1, child2, child3, 
                                                            senior1, senior2, senior3, C, updateOrder, updateMenu);
                                    }
                                    //if the user wants to remove from their already existing order
                                    else if(updateMenu == 2)
                                    {
                                        optionSelected = deleteSeats(updateOrder, C, input, A1, A2, A3);
                                    }
                                    //if the user wants to delete their order
                                    else if(updateMenu == 3)
                                    {
                                        //get which auditorium to calcel the order from
                                        auditoriumNum = C.getAuditorium(updateOrder - 1);
                                        
                                        //get the order array into a string so you can parse it when you cancel the order
                                        cancel = C.orders.get(updateOrder - 1).toString();
                                        Order T = C.getOrder(updateOrder - 1);
                                        
                                        //cancel the order for whatever auditorium you need to from
                                        if(auditoriumNum == 1)
                                            A1.cancelOrder(T, C, cancel);
                                        else if(auditoriumNum == 2)
                                            A2.cancelOrder(T, C, cancel);
                                        else if(auditoriumNum == 3)
                                            A3.cancelOrder(T, C, cancel);
                                        
                                        //removes the order
                                        C.orders.remove(updateOrder - 1);
                                        optionSelected = true;
                                    }

                                    if(updateMenu >=1 && updateMenu <=3)
                                        repeatMenu = false;

                                }while(repeatMenu == true);
                               
                                
                            }while(optionSelected == false);
                            
                        }
                        //if they user wants to display what they have
                        else if(cusMenu == 4)
                        {
                            double total = 0;
                            total = C.displayMoney();
                            System.out.println("The total amount due for all orders is $" + total);
                        }
                    }
                    catch(InputMismatchException E)
                    {
                        System.out.println("\nThat was not a valid menu option.");
                        input.nextLine();
                    }
                    
                }while(cusMenu != 5);
            }
                
            
        }while(adminMenu != 3);
        
        //prints all three auditoriums to their respsective files
        //prints Auditorium 1 to file A1.txt
        PrintWriter output = new PrintWriter(new File("A1.txt"));
                
        //prints the auditorium to the file
        output.print(A1.toFile());
        output.close();

        
        //prints Auditorium 2 to file A2.txt
        output = new PrintWriter(new File("A2.txt"));

        //prints the auditorium to the file
        output.print(A2.toFile());
        output.close();
        
        
        //prints Auditorium 3 to file A3.txt
        output = new PrintWriter(new File("A3.txt"));

        //prints the auditorium to the file
        output.print(A3.toFile());
        output.close();
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This function will remove seats from an order, and possibly cancel the  *
    //order if all seats from the order are removed                           *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public static boolean deleteSeats(int updateOrder, Customer C, Scanner input, Auditorium A1, Auditorium A2, Auditorium A3)
    {
        int rowNum = 0;
        int auditoriumNum = 0;
        char startingSeat = ' ';
        char removeThis = ' ';
        String seat = "";
        boolean repeatMenu = true;
        
        System.out.println("What seat do you want to remove?");
        System.out.println("Please enter in the row:");
        
        //validate the input for the row number
        do
        {
            try
            {
                rowNum = input.nextInt();
                repeatMenu = false;
            }
            catch(InputMismatchException E)
            {
                System.out.println("That was not a number.");
                input.nextLine();
            }
           
        }while(repeatMenu == true);
        
        //validate the input for the seat
        System.out.println("Please enter in the seat letter:");
        repeatMenu = true;
        do
        {
            try
            {
                startingSeat = input.next().charAt(0);
                repeatMenu = false;
            }
            catch(IllegalArgumentException E)
            {
                System.out.println("That was not valid");
                input.nextLine();
            }
                
        }while(repeatMenu == true);
        
        //concatinate the row and seat letter into one string to compare to what's
        //in the order array
        seat += Integer.toString(rowNum);
        seat += startingSeat;
        Order N = C.getOrder(updateOrder - 1);
        //if the seat was valid, then delete it
        //C.orders.get(updateOrder).compareSeats(seat) == true
        if(N.compareSeats(seat) == true)
        {
            //gets which auditorium to delete the seat from
            auditoriumNum = C.getAuditorium(updateOrder - 1);
            
            //depending on which auditorium it is, that auditorium's function 
            //will be called
            if(auditoriumNum == 1)
            {
                removeThis = A1.deleteTheaterSeats(startingSeat, rowNum);
                C.orders.get(updateOrder - 1).decrementTicketTypes(removeThis);
                C.orders.get(updateOrder - 1).removeSeats(seat, updateOrder, C);
            }
            else if(auditoriumNum == 2)
            {
                removeThis = A2.deleteTheaterSeats(startingSeat, rowNum);
                C.orders.get(updateOrder - 1).decrementTicketTypes(removeThis);
                C.orders.get(updateOrder - 1).removeSeats(seat, updateOrder, C);
            }
            else if(auditoriumNum == 3)
            {
                removeThis = A3.deleteTheaterSeats(startingSeat, rowNum);
                C.orders.get(updateOrder - 1).decrementTicketTypes(removeThis);
                C.orders.get(updateOrder - 1).removeSeats(seat, updateOrder, C);
            }
            
            return true;
        }
        else
        {
            System.out.println("Your entered seat is not valid, so it could not be removed");
            return false;
        }
        
    }
    
    //* * * * * * * * * * * * * * * * * * * *
    //This will let the user reserve seats  *
    //* * * * * * * * * * * * * * * * * * * *
    public static boolean reserveSeats(boolean valid, Scanner input, Auditorium A1, Auditorium A2, Auditorium A3, 
                                    char startingSeat, String seatLetter, int rowNum1, int rowNum2, int rowNum3,
                                    int colNums1, int colNums2, int colNums3, int adult1, int adult2, int adult3,
                                    int child1, int child2, int child3, int senior1, int senior2, int senior3, Customer C,
                                    int updateOrder, int updateMenu)
    {
        int menuNum = 0;
        valid = false;
        boolean optionSelected = false;
        
        //if they user is not updating an order, then go through the regular
        //process
        if(updateMenu != 1)
        {
            //loop through until they choose a valid auditorium
            do
            {
                System.out.println("1. Auditorium 1");
                System.out.println("2. Auditorium 2");
                System.out.println("3. Auditorium 3");
                System.out.println("Please choose an auditorium");

                //check for invalid input from the user
                try
                {
                    menuNum = input.nextInt();

                }
                catch(InputMismatchException E)
                {
                    System.out.println("\nThat was not a valid menu option.");
                    input.nextLine();
                    valid = false;
                }

                //if they entered in a valid menu number, then the loop will end, 
                //and the seat reservation process will start
                if(menuNum == 1 || menuNum == 2 || menuNum == 3)
                    valid = true;

            }while(valid == false);
        }
        //if they were updating their order, then go straight to the reservation process
        else if(updateMenu == 1)
        {
            menuNum = C.getAuditorium(updateOrder - 1);
        }
        
        //* * * * * * * * * * * * * * * * * * * *
        //If they wanted auditoriums 1, 2, or 3 *
        //* * * * * * * * * * * * * * * * * * * *
        if(menuNum == 1)
        {
            optionSelected = Auditorium1(A1, rowNum1, colNums1, valid, startingSeat, seatLetter, adult1, child1, senior1, input, C, 1, updateMenu, updateOrder);
              
        }
        else if(menuNum == 2)
        {
            optionSelected = Auditorium1(A2, rowNum2, colNums2, valid, startingSeat, seatLetter, adult2, child2, senior2, input, C, 2, updateMenu, updateOrder);
        }
        else if(menuNum == 3)
        {
            optionSelected = Auditorium1(A3, rowNum3, colNums3, valid, startingSeat, seatLetter, adult3, child3, senior3, input, C, 3, updateMenu, updateOrder);
        }
        
        //this way, we will know if the user who wanted to update their order, if that was what they wanted, 
        //was able to actually update their order or not, thus telling the program whether to repeat the
        //update action menu or not
        return optionSelected;
        
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * *
    //This will deal with reservations for auditorium 1 *
    //* * * * * * * * * * * * * * * * * * * * * * * * * *
    public static boolean Auditorium1(Auditorium A, int rowNum, int colNums, boolean valid, char startingSeat, String seatLetter, int adult,
                                   int child, int senior, Scanner input, Customer C, int auditNum, int updateMenu, int updateOrder)
    {
        int totalTickets = 0;
        boolean available = false;
        Order O = new Order(auditNum, adult, child, senior);
        
        //prints the auditorium to the screen
        System.out.println(A.toString());

        //asks the user what row they would like to be in
        //loops until they enter in a valid row
        valid = false;
        do
        {
            try
            {
               System.out.println("What row would you like to be in?");
               rowNum = input.nextInt();

               if(rowNum >= 1 && rowNum <= A.getNumRows())
               {
                   valid = true;
               }
               else
               {
                   System.out.println("That was not a valid row number.");
                   valid = false;
               }

            }
            catch(InputMismatchException E)
            {
                System.out.println("That was not a valid row number.");
                input.nextLine();
                valid = false;
            }
        }while(valid  == false);

        //asks the user what starting seat they want
        //loops until they've entered in a valid starting seat
        valid = false;
        do
        {
            try
            {
                System.out.println("Please enter in your starting seat:");
                seatLetter = input.next();

                if(seatLetter.length() == 1)
                {
                    startingSeat = seatLetter.charAt(0);

                    if(startingSeat >= 65 && startingSeat <= colNums)
                        valid = true;
                }
                else
                {
                    System.out.println("This was not a valid starting seat.");
                    valid = false;
                }

            }
            catch(InputMismatchException E)
            {
                System.out.println("This was not a valid starting seat.");
                input.nextLine();
                valid = false;
            }
        }while(valid == false);

        //asks the user the number of adult tickets the user wants
        //loops until the user enters a valid value
        valid = false;
        do
        {
            try
            {
                System.out.println("Please enter in the number of adult tickets you want:");
                adult = input.nextInt();

                if(adult >= 0)
                    valid = true;
                else
                {
                    System.out.println("You can't have a negative number of tickets.");
                    valid = false;
                }
            }
            catch(InputMismatchException E)
            {
                System.out.println("This is an invalid number of adult tickets.");
                input.nextLine();
                valid = false;
            } 
        }while(valid == false);

        //asks the user the number of child tickets the user wants
        //loops until valid data is inputted
        valid = false;
        do
        {
            try
            {
                System.out.println("Please enter in the number of child tickets you want:");
                child = input.nextInt();

                if(child >= 0)
                    valid = true;
                else
                {
                    System.out.println("You can't have a negative number of tickets.");
                    valid = false;
                }
            }
            catch(InputMismatchException E)
            {
                System.out.println("This is an invalid number of child tickets.");
                input.nextLine();
                valid = false;
            }
        }while(valid == false);

        //asks the user the number of senior tickets the user wants
        //loops until valid data is inputted
        valid = false;
        do
        {
            try
            {
                System.out.println("Please enter in the number of senior tickets you want:");
                senior = input.nextInt();

                if(senior >= 0)
                    valid = true;
                else
                {
                    System.out.println("You can't have a negative number of tickets.");
                    valid = false;
                }
            }
            catch(InputMismatchException E)
            {
                System.out.println("This is an invalid number of senior tickets.");
                input.nextLine();
                valid = false;
            }
        }while(valid == false);
        
        totalTickets = adult + child + senior;

        //returns a boolean value to see if what the user wanted is 
        //available for them in the theater
        available = A.isAvailable(startingSeat, rowNum, totalTickets);

        //if what they want is available, then update your theater
        if(available == true)
        {
            A.updateTheater(startingSeat, rowNum, adult, child, senior);
            System.out.println("Your seat has been reserved.");
            
            //if they're not trying to update an order, then add this in as a
            //new order
            if(updateMenu != 1)
            {
                //this updates the user's order
                //this adds an order to the order arrayList in the Customer class
                //within this class, we will update the actual order itself
                C.addOrder(O, rowNum, startingSeat, totalTickets, adult, child, senior);
            }
            //if they ARE updating an order, then just add the seats to the
            //already existing order
            else if(updateMenu == 1)
            {
                Order N = C.getOrder(updateOrder - 1);
                N.addSeats(rowNum, startingSeat, totalTickets, adult, child, senior);
                return true;
            }
            
        }
        else if(available == false)
        {
            //if they're not trying to update an order, find the best available
            if(updateMenu != 1)
            {
                A.bestAvailable(totalTickets, adult, child, senior, C, O);
            }
            //otherwise, tell the user that their order could not be completed
            else
            {
                System.out.println("Your order could not be updated.");
                return false;
            }
        }

        return true;
    }
    
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **
    //This function will print out the report for the admin                    *
    //It shows the number of open seats, total reserved seats, total adult     *
    //seats reserved, total child seats reserved, total senior seats reserved, *
    //and the ticket sales for the whole auditorium for each auditorium, and   *
    //for all three together                                                   *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * **
    public static void printReport(Auditorium A1, Auditorium A2, Auditorium A3, int a1, int a2, int a3, int c1, int c2, int c3, int s1, int s2, int s3)
    {
        int totalAdult = 0, totalChild = 0, totalSenior = 0;
        int totalOpen1 = 0, totalOpen2 = 0, totalOpen3 = 0;
        double ticketSales1 = 0, ticketSales2 = 0, ticketSales3 = 0;
        
        //this will get the total amount of adult, child, and senior tickets sold per auditorium
        a1 = A1.totalAdultSold();
        a2 = A2.totalAdultSold();
        a3 = A3.totalAdultSold();
        
        c1 = A1.totalChildSold();
        c2 = A2.totalChildSold();
        c3 = A3.totalChildSold();
        
        s1 = A1.totalSeniorSold();
        s2 = A2.totalSeniorSold();
        s3 = A3.totalSeniorSold();
        
        totalAdult = a1 + a2 + a3;
        totalChild = c1 + c2 + c3;
        totalSenior = s1 + s2 + s3;
        
        ticketSales1 = (a1 * 10) + (c1 * 5) + (s1 * 7.5);
        ticketSales2 = (a2 * 10) + (c2 * 5) + (s2 * 7.5);
        ticketSales3 = (a3 * 10) + (c3 * 5) + (s3 * 7.5);
        
        totalOpen1 = (A1.totalSeats() - A1.totalTicketsSold());
        totalOpen2 = (A2.totalSeats() - A2.totalTicketsSold());
        totalOpen3 = (A3.totalSeats() - A3.totalTicketsSold());
        
        //prints out the top line
        System.out.println("             Open Seats  Tot. Res. Seats  Tot. Adult Seats  Tot. Child Seats  Tot. Senior Seats  Ticket Sales");
        
        //this will print out everything needed for auditorium 1
        System.out.printf("%-13s", "Auditorium 1 ");
        System.out.printf("%-12d", totalOpen1);
        System.out.printf("%-17d", A1.totalTicketsSold());
        System.out.printf("%-18d", a1);
        System.out.printf("%-18d", c1);
        System.out.printf("%-19d", s1);
        System.out.printf("%-12.2f", ticketSales1);
        System.out.println();
        
        //this will print out everything needed for auditorium 2
        System.out.printf("%-13s", "Auditorium 2 ");
        System.out.printf("%-12d", totalOpen2);
        System.out.printf("%-17d", A2.totalTicketsSold());
        System.out.printf("%-18d", a2);
        System.out.printf("%-18d", c2);
        System.out.printf("%-19d", s2);
        System.out.printf("%-12.2f", ticketSales2);
        System.out.println();
        
        //this will print out everything needed for auditorium 3
        System.out.printf("%-13s", "Auditorium 3 ");
        System.out.printf("%-12d", totalOpen3);
        System.out.printf("%-17d", A3.totalTicketsSold());
        System.out.printf("%-18d", a3);
        System.out.printf("%-18d", c3);
        System.out.printf("%-19d", s3);
        System.out.printf("%-12.2f", ticketSales3);
        System.out.println();
        
        //this will print out everything needed for the total
        System.out.printf("%-13s", "Total");
        System.out.printf("%-12d", (totalOpen1 + totalOpen2 + totalOpen3));
        System.out.printf("%-17d", (A1.totalTicketsSold() + A2.totalTicketsSold() + A3.totalTicketsSold()));
        System.out.printf("%-18d", totalAdult);
        System.out.printf("%-18d", totalChild);
        System.out.printf("%-19d", totalSenior);
        System.out.printf("%-12.2f", (ticketSales1 + ticketSales2 + ticketSales3));
        System.out.println();
        
        
    }
    
}
