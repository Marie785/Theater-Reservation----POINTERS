//
//Marie-Claire Salha
//mjs170530
//PROJECT 4 VERSION
//
package Tickets;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Auditorium 
{
    //first = first seat in the whole auditorium, row 1, seat A
    //
    //ptr = the pointer that stays one seat behind the new node, to connect the
    //seats left and right
    //
    //prevRow = the pointer that is in one row above the new node, but in the
    //same column, so that we can connect up and down
    TheaterSeat first = null;
    TheaterSeat ptr = null;
    TheaterSeat prevRow = null;
    private int numRows = 0;
    private int numCols = 0;
    
    //default constructor
    Auditorium() {};
    
    //* * * * * * * * * * * * *
    //overloaded constructor  *
    //* * * * * * * * * * * * *
    Auditorium(Scanner input)
    {
        String line = "";
        
        //takes in the first line for the auditorium
        line = input.nextLine();
        for(int i = 0; i < line.length(); i++)
        {
            TheaterSeat node = new TheaterSeat();
            
            //this takes in just the very first seat
            if(i == 0)
            {
                //the row will not change past this because we will always
                //be in row one for this for loop
                //we will use node to enter in the information, and then set
                //first equal to that, and then set the other pointer equal to
                //first
                node.seat = 'A';
                node.row = 1;
                numRows = 1;
                
                if(line.charAt(i) == '.')
                    node.reserved = false;
                else
                    node.reserved = true;

                node.ticketType = line.charAt(i);
                first = node;
                ptr = first;
                prevRow = first;
            }
            else
            {
                //gets information into the new new
                node.seat = (char)(65 + i);
                node.row = 1;
                
                if(line.charAt(i) == '.')
                    node.reserved = false;
                else
                    node.reserved = true;
                
                //connects the new node to the rest of the list
                node.ticketType = line.charAt(i);
                node.left = ptr;
                ptr.right = node;
                ptr = node;
            }
            
            numCols++;
        }
        
        int j = 2;
        //takes in the rest of the lines for the auditorium
        while(input.hasNext())
        {   
            //fRow keeps track of the first seat of the row you are 
            //currently looping through, so that at the end of this entire 
            //for loop, before you go through the next iteration of the
            //while loop, you set prevRow to fRow, so that prevRow will now
            //be above where the new row will take place. And then after 
            //you set prevRow to fRow, you then do the new node for the new
            //row, and then you would set fRow to the new node so it's then
            //the first seat of that new row, etc, etc...
            TheaterSeat fRow = new TheaterSeat();
            
            line = input.nextLine();
            for(int i = 0; i < line.length(); i++)
            {
                TheaterSeat node = new TheaterSeat();
                
                //this takes in the first seat of every row
                //the if statement takes in the first seat of the row
                if(i == 0)
                {
                    //gets information into the new node
                    //sets the seat letter for the seat we're in, in that particular
                    //row (so if we're in row 1, seat A, then this will be set
                    //to 'A'
                    node.seat = (char)(65 + i);
                    node.row = j;
                    numRows = j;

                    //checks if the seat is currently reserved
                    if(line.charAt(i) == '.')
                        node.reserved = false;
                    else
                        node.reserved = true;

                    //gets the type of ticket that is already reserved in that
                    //seat
                    //connects the new node to the rest of the list
                    node.ticketType = line.charAt(i);
                    node.up = prevRow;
                    prevRow.down = node;
                    
                    //this now sets ptr to the current node before
                    //we remake a new node
                    //and then we set prevRow to the next node in the row above 
                    //so that we can connect the seat above to the seat below
                    ptr = node;
                    fRow = node;
                    prevRow = prevRow.right;
                }
                //the else statement takes in the rest of the seats of the row
                else
                {
                    //gets information into the new node
                    node.seat = (char)(65 + i);
                    node.row = j;
                    
                    if(line.charAt(i) == '.')
                        node.reserved = false;
                    else
                        node.reserved = true;
                    
                    //connects the new node to the rest of the list
                    node.ticketType = line.charAt(i);
                    node.up = prevRow;
                    prevRow.down = node;
                    
                    node.left = ptr;
                    ptr.right = node;
                    
                    ptr = node;
                    prevRow = prevRow.right;
                    
                }
            }
            prevRow = fRow;
            j++;
        }
    }
    
    //* * * * * *
    //accessors *
    //* * * * * *
    public int getNumRows() {return numRows;}
    public int getNumCols() {return numCols;}
    
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This function checks to see if what the user originally wanted was available  *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public boolean isAvailable(char startingSeat, int rowNum, int totalTickets)
    {
        boolean available = false;
        TheaterSeat ptr3 = first;
        
        //gets to the correct row via pointer movement
        while(ptr3.row != rowNum)
            ptr3 = ptr3.down;
        
        //gets to the correct starting seat
        while(ptr3.seat != startingSeat)
            ptr3 = ptr3.right;
        
        //checks to see if all of the seats the user wants are available
        for(int i = 0; i < totalTickets; i++)
        {
            //if we can check these seats without going out of bounds of the 
            //auditorium and reaching null
            if(((int)(startingSeat - 65) + totalTickets) <= numCols)
            {
                //if it's not reserved, then move to the right to check the 
                //next seat, and set available to true
                if(ptr3.reserved == false)
                {
                    ptr3 = ptr3.right;
                    available = true;
                }
                //if it is reserved, set available equal to false and break out of 
                //the loop
                else if(ptr3.reserved == true)
                {
                    available = false;
                    break;
                }
            }
        }
        
        return available;
    }
    
    //* * * * * * * * * * * * * * * * * * * * *
    //This function updates the theater seats *
    //* * * * * * * * * * * * * * * * * * * * *
    public void updateTheater(char startingSeat, int rowNum, int adult, int child, int senior)
    {
        TheaterSeat ptr3 = first;
        
        //gets the pointer to the correct row
        while(ptr3.row != rowNum)
            ptr3 = ptr3.down;
        
        //gets the pointer to the correct starting seat
        while(ptr3.seat != startingSeat)
            ptr3 = ptr3.right;
        
        //updates the adults' seat reservations
        for(int i = 0; i < adult; i++)
        {
            ptr3.reserved = true;
            ptr3.ticketType = 'A';
            ptr3 = ptr3.right;
        }
        
        //updates the childs' seat reservations
        for(int i = 0; i < child; i++)
        {
            ptr3.reserved = true;
            ptr3.ticketType = 'C';
            ptr3 = ptr3.right;
        }
        
        //updates the seniors' seat reservations
        for(int i = 0; i < senior; i++)
        {
            ptr3.reserved = true;
            ptr3.ticketType = 'S';
            ptr3 = ptr3.right;
        }
    }
    
    //* * * * * * * * * * * * * * * * * * * * *
    //This function deletes theater seats     *
    //* * * * * * * * * * * * * * * * * * * * *
    public char deleteTheaterSeats(char startingSeat, int rowNum)
    {
        TheaterSeat ptr3 = first;
        char removeThis = ' ';
        
        //gets the pointer to the correct row
        while(ptr3.row != rowNum)
            ptr3 = ptr3.down;
        
        //gets the pointer to the correct starting seat
        while(ptr3.seat != startingSeat)
            ptr3 = ptr3.right;
        
        //use this to decrement the certain ticket type for this seat in the 
        //orders class
        if(ptr3.ticketType == 'A')
            removeThis = 'A';
        else if(ptr3.ticketType == 'C')
            removeThis = 'C';
        else if(ptr3.ticketType == 'S')
            removeThis = 'S';
        
        //un-reserve the seat
        ptr3.reserved = false;
        ptr3.ticketType = '.';
        
        return removeThis;
    }
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This function will remove all of the seats from a given order, because  *
    //the order was cancelled                                                 *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public void cancelOrder(Order T, Customer C, String cancel)
    {
        TheaterSeat ptr3 = first;
        String seat = "";
        char startingSeat = ' ';
        int rowNum = 0;
        
        //loops throught 
        for(int i = 0; i < T.seating.size(); i++)
        {
            seat = T.seating.get(i);
            rowNum = Integer.parseInt(seat.substring(0,1));
            startingSeat = seat.charAt(1);
            deleteTheaterSeats(startingSeat, rowNum);
        }
        
        /*//we will loop through the string that was passed in to get to all of
        //the seats and cancel them
        //this will be done through parsing and moving around where we are in 
        //the auditorium quite a bit
        do
        {
            seat = "";  //set it back to empty strings whenever you start the loop over
            
            //get the first two characters in the string, which will contain
            //the row (first), and the starting seat (second)
            seat = cancel.substring(0,2);
            cancel = cancel.substring(0,2); //cut the string so those first two characters are no longer there
            
            //convert the first character into the row number
            rowNum = Integer.parseInt(seat.substring(0,1));
            startingSeat = seat.charAt(1);  //convert the second character in the string to the starting seat
            
            
            //now start a while loop that will determine where to move the pointer
            //to, so that it goes to the correct row
            //if we're already at the row we want, then don't move it
            
            //if we're at a row number lower than what we need, move the pointer down
            if(ptr3.row < rowNum)
            {
                //move the pointer down the auditorium rows, thus increasing the row number
                while(ptr3.row != rowNum)
                {
                    ptr3 = ptr3.down;
                }
            }
            //if we're at a row number higher than what we need, move the pointer up
            else if(ptr3.row > rowNum)
            {
                //move the pointer up the rows, thus decreasing the row number
                while(ptr3.row != rowNum)
                {
                    ptr3 = ptr3.up;
                }
            }
            
            
            //now we check for the seat itself, just by comparing the characters
            
            //if we're at a seat that's too far to the right, then we'll move the pointer left
            if(ptr3.seat < startingSeat)
            {
                //move the pointer to the right, so that it eventually matches the character in the seat order
                while(ptr3.seat != startingSeat)
                {
                    ptr3 = ptr3.right;
                }
            }
            //if we're at a seat that's too far to the left, then we'll move the pointer right
            else if(ptr3.seat > startingSeat)
            {
                //move the pointer to the left, so that it eventually matches the character in the seat order
                while(ptr3.seat != startingSeat)
                {
                    ptr3 = ptr3.left;
                }
            }
            
            //once we've reached the desired location, we'll make the seat available again
            ptr3.reserved = false;
            ptr3.ticketType = '.';
            
        }while(cancel != "");*/
        
    }
    
    
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    //This function will find the best available seat for the user in the *
    //auditorium                                                          *
    //All of the questions about reservations and stuff will be handled   *
    //in here.                                                            *
    //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public void bestAvailable(int totalTickets, int adult, int child, int senior, Customer C, Order O)
    {
        Scanner input3 = new Scanner(System.in);
        int curRow = 1,         //y2 (THE ROW YOU ARE CURRENTLY ON)
            curCol = 1,         //x2 (THE COLUMN YOU ARE CURRENTLY ON))
            bestRow = 0,        //this will keep track of the row with the best seating so far
            startingSeat = -1;  //keeps track of the starting seat for the best row, but if it
                                //stays as a -1, then that means that there was never a better seat
        double  centerRow = 0,  //y1 (THIS IS THE CENTER ROW OF THE ENTIRE AUDITORIUM)
                centerCol = 0,  //x1 (THIS IS THE CENTETER COLUMN OF THE ENTIRE AUDITORIUM)
                smallestDistance = 0,
                distance = 0,
                number = 0;     //used for doing the square root for the distance
        char answer = ' ',
             seat = ' ';
        
        TheaterSeat ptr3 = first;
        TheaterSeat rowPtr = first;     //this pointer's job is to keep track of the beginning
                                        //of the row so we can go back to this one and then go
                                        //down to the next row from this pointer
        boolean available = false;
                        
        //find the center of the entire auditorium
        //calculates biggest distance possible to be the initial smallest distance
        centerRow = (numRows + 1) / 2.0;
        centerCol = (numCols + 1) / 2.0;
        number = Math.pow(((totalTickets + curCol) - centerCol), 2) + Math.pow((curRow - centerRow), 2);
        smallestDistance = Math.sqrt(number);
                
        
        //goes through each row
        while(ptr3 != null)
        {
            //this will go through each seat in the row
            while(ptr3 != null)
            {
                //sets the current row and column numbers that we're on
                curRow = ptr3.row;
                curCol = (int)(ptr3.seat - 64);
                
                //checks to see if we can reserve those seats
                available = isAvailable(ptr3.seat, curRow, totalTickets);
                
                if(available == true)
                {
                    double x1 = 0;
                    double y1 = 0;
                    double x2 = 0;
                    double y2 = 0;
                    
                    //get coordinates to calculate distance
                    x1 = ((totalTickets + 1) / 2.0) + curCol;
                    y1 = curRow;
                    x2 = centerCol;
                    y2 = centerRow;
                    
                    //now, we calculate the distance with the distance formula
                    number = Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2);
                    distance = Math.sqrt(number);
                    
                    //if we have found a distance that is shorter than the smallest
                    if(distance < smallestDistance || startingSeat == -1)
                    {
                        smallestDistance = distance;
                        bestRow = curRow;
                        startingSeat = curCol;
                    }
                    //if we have a tie
                    else if(distance == smallestDistance)
                    {
                        //tie for distance
                        if(Math.abs((curRow - centerRow)) < Math.abs((bestRow - centerRow)))
                        {
                            bestRow = curRow;
                            startingSeat = curCol;
                        }
                        //tie for rows
                        if(curRow < bestRow)
                        {
                            bestRow = curRow;
                            startingSeat = curCol;
                        }
                    }
                }
                
                //moves ptr3 a seat over to the right
                ptr3 = ptr3.right;
            }
            
            //moves ptr3 down a row
            ptr3 = rowPtr;
            ptr3 = ptr3.down;
            rowPtr = ptr3;
        }
        
        if(startingSeat == -1)
        {
            System.out.println("There were no seats available.");
        }
        else
        {
            //ask the user if they want these new best available seats
            seat = (char)(startingSeat + 64);
            System.out.println("Do you want " + totalTickets + " seats, on row " + bestRow + ", starting at seat " + seat + "?");
            boolean valid = false;
            do
            {
                try
                {
                    answer = input3.next().charAt(0);
                    valid = true;
                }
                catch(InputMismatchException E)
                {
                    System.out.println("Please enter in Y or N.");
                    valid = false;
                }
                
                //makes sure that the user entered in a "Y" or a "N"
                if(answer != 'y' && answer != 'Y' && answer != 'n' && answer != 'N')
                    valid = false;
                
            }while(valid == false);

            if(answer == 'y' || answer == 'Y')
            {
                updateTheater(seat, bestRow, adult, child, senior);
                System.out.println("Your seats have been reserved.");
                
                //update the Customer object, which will in turn update the seats
                C.addOrder(O, bestRow, seat, totalTickets, adult, child, senior);
                
            }
            else if(answer == 'n' || answer == 'N')
            {
                System.out.println("Your reservation has been canceled.");
            }
        }
        
        
    }
    
    //Prints to the console
    @Override
    public String toString()
    {
        String auditorium = " ";
        TheaterSeat travPtr = first;
        TheaterSeat ptr2 = first;
        int i = 0;
        
        //this will get us the letters on the top of the auditorium
        while(travPtr != null)
        {
            auditorium += (char)(65 + i);
            i++;
            travPtr = travPtr.right;
        }
        
        
        //afterwards, we want to set travPtr back to the beginning of the linked
        //list so that we can print out the actual auditorium, not just the 
        //letters of the columns
        travPtr = first;
        auditorium += '\n';
        i = 1;
        while(ptr2.down != null)
        {
            auditorium += i;
            while(travPtr != null)
            {
                if(travPtr.ticketType == '.')
                    auditorium += ".";
                else
                    auditorium += "#";
                
                travPtr = travPtr.right;
            }
            
            //this will happen so that when we want to move travPtr down a row,
            //then ptr2 will always be a row above where travPtr wants to move to
            if(i > 1)
            {
                //this pointer will keep track of the row above (unless we are
                //on row 1)
                ptr2 = ptr2.down;
            }
            travPtr = ptr2.down;
            i++;
            auditorium += '\n';
        }
        
        return auditorium;
    }
    
    
    //Prints to the file
    public String toFile()
    {
        String auditorium = "";
        TheaterSeat travPtr = first;
        TheaterSeat ptr2 = first;
        int i = 0;
        
        //afterwards, we want to set travPtr back to the beginning of the linked
        //list so that we can print out the actual auditorium, not just the 
        //letters of the columns
        while(ptr2 != null)
        {
            while(travPtr != null)
            {
                if(travPtr.ticketType == '.')
                    auditorium += ".";
                else
                    auditorium += travPtr.ticketType;
                
                travPtr = travPtr.right;
            }
            ptr2 = ptr2.down;
            travPtr = ptr2;
            //this will happen so that when we want to move travPtr down a row,
            //then ptr2 will always be a row above where travPtr wants to move to
            
            /*if(i > 1)
            {
                //this pointer will keep track of the row above (unless we are
                //on row 1)
                ptr2 = ptr2.down;
            }*/
            //travPtr = ptr2.down;
            i++;
            auditorium += '\n';
        }
        
        return auditorium;
    }
    
    //This function will calculate the total number of seats in the auditorium
    public int totalSeats()
    {
        int total = 0;
        
        total = numRows * numCols;
        return total;
    }
    
    //This function will count how many tickets were sold in total
    public int totalTicketsSold()
    {
        int total = 0;
        TheaterSeat ptr3 = first;
        TheaterSeat begPtr = first; //will be at the beginning of every row
        
        //goes through the whole auditorium, and will count up how many total
        //tickets were sold in the auditorium
        while(begPtr != null)
        {
            while(ptr3 != null)
            {
                if(ptr3.reserved == true)
                    total++;
                
                ptr3 = ptr3.right;
            }
            
            //moves the pointers down a row
            begPtr = begPtr.down;
            ptr3 = begPtr;
        }
        
        return total;
    }
    
    //This function will count how many adult tickets were sold in total
    public int totalAdultSold()
    {
        int total = 0;
        TheaterSeat ptr3 = first;
        TheaterSeat begPtr = first; //will be at the beginning of every row
        
        //goes through the whole auditorium, and will count up how many total
        //adult tickets were sold in the auditorium
        while(begPtr != null)
        {
            while(ptr3 != null)
            {
                if(ptr3.ticketType == 'A')
                    total++;
                
                ptr3 = ptr3.right;
            }
            
            //moves the pointers down a row
            begPtr = begPtr.down;
            ptr3 = begPtr;
        }
        
        return total;
    }
    
    //This function will count how many child tickets were sold in total
    public int totalChildSold()
    {
        int total = 0;
        TheaterSeat ptr3 = first;
        TheaterSeat begPtr = first; //will be at the beginning of every row
        
        //goes through the whole auditorium, and will count up how many total
        //child tickets were sold in the auditorium
        while(begPtr != null)
        {
            while(ptr3 != null)
            {
                if(ptr3.ticketType == 'C')
                    total++;
                
                ptr3 = ptr3.right;
            }
            
            //moves the pointers down a row
            begPtr = begPtr.down;
            ptr3 = begPtr;
        }
        
        return total;
    }
    
    //This function will count how many senior tickets were sold in total
    public int totalSeniorSold()
    {
        int total = 0;
        TheaterSeat ptr3 = first;
        TheaterSeat begPtr = first; //will be at the beginning of every row
        
        //goes through the whole auditorium, and will count up how many total
        //senior tickets were sold in the auditorium
        while(begPtr != null)
        {
            while(ptr3 != null)
            {
                if(ptr3.ticketType == 'S')
                    total++;
                
                ptr3 = ptr3.right;
            }
            
            //moves the pointers down a row
            begPtr = begPtr.down;
            ptr3 = begPtr;
        }
        
        return total;
    }
    
    //This function will add up how much was spent in total by everyone in the
    //auditorium
    public double totalSpent()
    {
        double total = 0.0;
        TheaterSeat ptr3 = first;
        TheaterSeat begPtr = first; //will be at the beginning of every row
        
        //counts up the total amount of money spent in the auditorium
        while(begPtr != null)
        {
            while(ptr3 != null)
            {
                if(ptr3.seat == 'A')
                    total += 10;
                if(ptr3.seat == 'C')
                    total += 5;
                if(ptr3.seat == 'S')
                    total += 7.5;
                
                ptr3 = ptr3.right;
            }
            
            //moves the pointers down a row
            begPtr = begPtr.down;
            ptr3 = begPtr;
        }
        
        
        return total;
    }
}
