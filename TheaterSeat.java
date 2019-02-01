//
//Marie-Claire Salha
//mjs170530
//PROJECT 4 VERSION
//
package Tickets;
import Tickets.BaseNode;

public class TheaterSeat extends BaseNode
{
    protected TheaterSeat up = null;
    protected TheaterSeat down = null;
    protected TheaterSeat left = null;
    protected TheaterSeat right = null;
    
    //default constructor
    TheaterSeat()
    {
        super(0, ' ', false, ' ');
        up = null;
        down = null;
        left = null;
        right = null;
    }

    //overloaded constructor
    TheaterSeat(TheaterSeat u, TheaterSeat d, TheaterSeat l, TheaterSeat ri, int r, char s, boolean res, char t)
    {
        super(r, s, res, t);
        up = u;
        down = d;
        left = l;
        right = ri;
    }
    
    //overloaded constructor that only takes in the latter half of the information
    TheaterSeat(int r, char s, boolean res, char t)
    {
        super(r, s, res, t);
    }
}
