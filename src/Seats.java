import java.util.ArrayList;
/**
 * Seats Class
 * Attributes- 
 * unique row name - row
 * number of seats in the row - num_seats
 * @author tanya
 *
 */
public class Seats{
	private String row;
//	private int number_of_seats;
	private ArrayList<Integer> seats;
	public Seats(String row , int num_seats) {
		this.row = row;
		seats  = new ArrayList<Integer>(num_seats);
		initialiseSeats(num_seats);
	}
	
	/**
	 * This will set the initial values of the seats to zero, 
	 * when a booking is made, replace the 0 with the booking_id
	 * @param num_seats
	 */
	private void initialiseSeats(int num_seats) {
		for(int index = 0; index < num_seats; index++) {
			this.seats.add(0);
		}
	}

	public String getRow() {
		return row;
	}
	public int getNumOfSeat() {
		return seats.size();
	} 
	/**
	 * Used for debugging and accessing what the Seats are set to
	 * @param number_of_seats
	 * @param row
	 * @return
	 */
	public ArrayList<Integer> getSeats(int number_of_seats, String row){
		for(int i = 0; i < number_of_seats; i++) {
			seats.set(i, 0);
		}
		return seats;
		
	}
	
	public void setRow(String row) {
		this.row = row;
	}
	
	/**
	 * this method will check if there are seats available for the requested booking
	 * @param num_of_tickets
	 * @return
	 */
	public boolean requiredSeats(int num_of_tickets) {
		int count = 0;
		//System.out.printf("%d number of seats\n", seats.size());
		for(int j = 0; j < seats.size(); j++) {
			if(seats.get(j) == 0)
				count++;
				//System.out.printf("I got accessed in requiredSeats %s\n", count);
		}
		if (count >= num_of_tickets) {
			//System.out.printf("I got accessed in requiredSeats %s", count);
			return true;
		}
		return false;
	}
	
	/**
	 * Check if there are seats available in the requested row
	 * if there are seats available then start allocating book_id for consecutive seats
	 * also decrease the number of seats as we allocate a seat
	 * 
	 * @param num_of_tickets
	 * @param booking_id
	 * @return
	 */
	public boolean bookSeats(int num_of_tickets, int booking_id) {
		if (this.requiredSeats(num_of_tickets) == false){
			return false;
		}
		int count = num_of_tickets;
		for(int i = 0; i < seats.size(); i++) {
			if((seats.get(i) == 0) && (num_of_tickets > 0)){
				if(count == num_of_tickets) {
					//System.out.printf("%s%d-", this.row,i+1);
				}
				else if( num_of_tickets == 1) {
					//System.out.printf("%s%d\n", this.row,i+1);
				}
				seats.set(i, booking_id);
				num_of_tickets--;
			}
		}
		return true;
	}
	/**
	 * Used for printing the row and tickets that were booked
	 * @param num_of_tickets
	 * @param booking_id
	 */
	public void printingSeats(int num_of_tickets, int booking_id) {
		if (this.requiredSeats(num_of_tickets) == false){
			return;
		}
		int count = num_of_tickets;
		for(int i = 0; i < seats.size(); i++) {
			if((seats.get(i) == 0) && (num_of_tickets > 0)){
				if(count == num_of_tickets) {
					System.out.printf("%s%d-", this.row,i+1);
				}
				else if( num_of_tickets == 1) {
					System.out.printf("%s%d\n", this.row,i+1);
				}
				num_of_tickets--;
			}
		}
	}
	/**
	 * Checks if the unique booking id exists so that it can cancel or not
	 * @param booking_id
	 * @return true or false according to whether booking id found or not
	 */
	public boolean checkIfIdExist(int booking_id) {
		for(int i = 0; i < seats.size(); i++) {
			if(seats.get(i) == booking_id) {
				return true;
			}
		}
		return false;
	}
	/**
	 * return true if the seats with the booking id are cancelled
	 * @param booking_id
	 * @return true or false whether the seats with the booking id are cancelled or not
	 */
	public boolean cancelSeats(int booking_id) {
		if(checkIfIdExist(booking_id) == false) {
			return false;
		}
		for(int i = 0; i < seats.size(); i++) {
			if(seats.get(i) == booking_id) {
				seats.set(i, 0);
			}
		}
		return true;
	}
	
}
