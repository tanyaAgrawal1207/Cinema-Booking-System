import java.util.ArrayList;
/**
 * Cinema Class 
 * Attributes - unique cinema_number and number of seats in the row - seat
 * @author tanya
 *
 */
public class Cinema implements Cloneable{
	private int cinema_number;
	private ArrayList<Seats> seat_number ;
	private Seats seat;
	/**
	 * The constructor for the Cinema class
	 * @param cinema_number
	 * @param seat
	 */
	public Cinema (int cinema_number, Seats seat) {
		super();
		this.cinema_number = cinema_number;
		seat_number = new ArrayList<Seats>();
		this.seat_number.add(seat);
		//System.out.printf("the seat_number %s\n", seat_number);
		this.seat = seat;	
	}
	/**
	 * clones the Cinema class so that the various sessions can be stored
	 */
    public Cinema clone() throws CloneNotSupportedException {
        return (Cinema) super.clone();
    }
	/**
	 * 
	 * @return seat_number ArrayList of Seats
	 */
    public ArrayList<Seats> getSeatNumber() {
    	return this.seat_number;
    }
    
    public void setSeatNumber(ArrayList<Seats> s) {
    	this.seat_number = s;
    }
	/**
	 * 
	 * @return cinema_number
	 */
	public int getCinemaNum() {
		return this.cinema_number;
	}
	
	public Seats getSeatsC() {
		return seat;
	}
	/**
	 * returns true if the booking can be made and false otherwise
	 * @param no_of_tickets
	 * @param book_id
	 * @return
	 */
	public boolean makeBookingC(int no_of_tickets, int book_id) {
		for(Seats S : seat_number) {
			//System.out.println("Current Row: " + seat_number);
			if(S.bookSeats(no_of_tickets, book_id) == true) {
				return true;
			}
		}
		return false;	
	}
	/**
	 * 
	 * @param book_id
	 * @return true if the booking can be cancelled, false otherwise
	 */
	public boolean cancelBookingC(int book_id) {
		for(Seats S : seat_number) {
			if(S.cancelSeats(book_id) == true) {
				return true;
			}
		}
		return false;
	}
	
	public void appendList (Seats seat) {
		if(!this.seat_number.contains(seat)) {
			this.seat_number.add(seat);
			//System.out.printf("Seat added: %s\n", seat);
		}
	}
	
}
