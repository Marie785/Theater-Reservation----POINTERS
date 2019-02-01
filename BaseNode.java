//
//Marie-Claire Salha
//mjs170530
//PROJECT 4 VERSION
//
package Tickets;


public abstract class BaseNode 
{
    protected int row;
    protected char seat;
    protected boolean reserved;
    protected char ticketType;
    
    //default constructor
    BaseNode() {};
    
    //overloaded constructor
    BaseNode(int r, char s, boolean res, char t)
    {
        row = r;
        seat = s;
        reserved = res;
        ticketType = t;
    }
    
    //accessors
    public int getRow() {return row;}
    public char getSeat() {return seat;}
    public boolean getReserved() {return reserved;}
    public char getTicketType() {return ticketType;}
    
    //mutators
    public void setRow(int r) {row = r;}
    public void setSeat(char s) {seat = s;}
    public void setReserved(boolean res) {reserved = res;}
    public void setTicketType(char t) {ticketType = t;}
}
