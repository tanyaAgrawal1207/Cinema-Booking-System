/**
* Author : Tanya Agrawal
* zID : z5118236
* Last Modified : 7/04/2018
* A System that could serve as the "back-end" for a cinema booking system. 
* Customers can make, change and delete cinema bookings. 
* Each booking has an ID number and is made for a given number of adjacent seats in a particular cinema at a particular time 
* A booking request is either granted in full or is completely rejected by the system; there are no bookings partially filled.
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The main CinemaBookingSystem class 
 * It performs the scanner function 
 * @author tanya
 *
 */
public class CinemaBookingSystem{
	ArrayList<Cinema> cinemaList;
	ArrayList<Session> sessionList;
	public static void main(String[] args) throws CloneNotSupportedException {
		Scanner sc = null;
		try {
			sc = new Scanner (new File(args[0]));
			CinemaBookingSystem system = new CinemaBookingSystem();
			system.processor(sc);
		}
		catch (FileNotFoundException e)
	    {
	          System.out.println(e.getMessage());
	    }
		finally
		{
	          if (sc != null) sc.close();
	    }
	}
	/**
	 * The processor function takes in the scanner and using the first word of the line 
	 * calls the class needed
	 * @param sc
	 * @throws CloneNotSupportedException
	 */
	public void processor(Scanner sc) throws CloneNotSupportedException {
		cinemaList = new ArrayList<Cinema>();
		sessionList = new ArrayList<Session>();
	
		while(sc.hasNextLine()) {
			String text = sc.nextLine();
			if(!text.startsWith("#")) {
				String[] splitText = text.split("#");
				String request = splitText[0];
				if( request.startsWith("Cinema")) {
					List<String> myList = new ArrayList<String>(Arrays.asList(request.split(" ")));
					/**
					 * Used to pass in the different from the input file into the variables
					 */
					int cinema_number = Integer.parseInt(myList.get(1));
					String row = myList.get(2);
					int seat = Integer.parseInt(myList.get(3));
					Seats S =  new Seats(row , seat);
					Cinema C;
					/**
					 * If cinema exists no need to make another cinema with the same number
					 * If cinema doesn't exist then make a new cinema and add it to the cinemaList
					 * In both cases append the cinema to contain the new row of seats that are created
					 */
					 if(checkCinemaExists(cinema_number) == false) {
						 C = new Cinema(cinema_number, S);
						 cinemaList.add(C);
						 C.appendList(S);
						 
					 }else {
						 C = getCinemaFromNumber(cinemaList, cinema_number);
						 C.appendList(S);
					 }
				}
				
				if(request.startsWith("Session")) {
					List<String> myList1 = new ArrayList<String>(Arrays.asList(request.split(" ")));
					int cinema = Integer.parseInt(myList1.get(1));
					String time = myList1.get(2);
					String movie = myList1.get(3);
					/**
					 * Get the uniquely made cinema with the right rows and seats using the getCinemaFromNumber function
					 * After the cinema is found, clone it to another temporary ArrayList 
					 * so that the cinema itself doesnt get booked and the different sessions can be made
					 */
					Cinema Cin = getCinemaFromNumber(cinemaList, cinema);
					ArrayList<Seats> temp = Cin.getSeatNumber();
					ArrayList<Seats> S = new ArrayList<Seats>();
					for(Seats t: temp) {
						Seats a = new Seats(t.getRow(), t.getNumOfSeat());
						S.add(a);
					}
					Cin.setSeatNumber(S);
					/**
					 * Adding the newly cloned cinema to the sessionist
					 */
					Session Sess = new Session(cinema, time, movie, Cin);
					sessionList.add(Sess);
				}
				
				if(request.startsWith("Request")) {
					List<String> myList2 = new ArrayList<String>(Arrays.asList(request.split(" ")));
					int id = Integer.parseInt(myList2.get(1));
					int cinema_number = Integer.parseInt(myList2.get(2));
					String time = myList2.get(3);
					int tickets = Integer.parseInt(myList2.get(4));

					/**
					 * Check that a session is available
					 */
					Session Sesh = checkSessionFromRequest(cinema_number, time);
					if (Sesh != null) {				
						Cinema C = Sesh.getCineS();
						if(makeBooking(C, tickets, id) == true) {
							Seats S = C.getSeatsC();
							System.out.printf("Booking: %d ", id );
							S.printingSeats(tickets, id);
						}else {
							System.out.println("Booking rejected");
						}
					}
				}
				
				if(request.startsWith("Change")) {
					List<String> myList3 = new ArrayList<String>(Arrays.asList(request.split(" ")));
					int id = Integer.parseInt(myList3.get(1));
					int cinema_number = Integer.parseInt(myList3.get(2));
					String time = myList3.get(3);
					int tickets = Integer.parseInt(myList3.get(4));
					
					if(cancelBooking(id) == true) {
						Session Ss = checkSessionFromRequest(cinema_number, time);
						if(Ss != null) {
							Cinema C = Ss.getCineS();
							if(makeBooking(C,tickets,id) == true) {
								System.out.printf("Change: %d ", id);
								Seats S = C.getSeatsC();
								S.printingSeats(tickets, id);
							}else {
								System.out.println("Change rejected");
							}
						}
					}
				}
				
				if(request.startsWith("Cancel")) {
					List<String> myList4 = new ArrayList<String>(Arrays.asList(request.split(" ")));
					int id = Integer.parseInt(myList4.get(1));
					if(cancelBooking(id) == true) {
						System.out.printf("Cancel %d\n", id);
					}else {
						System.out.println("No such booking");
					}
				}
				
				if(request.startsWith("Print")) {
					//List<String> myList5 = new ArrayList<String>(Arrays.asList(request.split(" ")));
					//int cinema_number = Integer.parseInt(myList5.get(1));
					//String time = myList5.get(2);
				}
			}
		}
	}
	/**
	 * Cancels the booking and returns true if the booking is cancelled successfully
	 * @param id
	 * @return true or false based on whether the booking is cancelled successfully or not
	 */
	private boolean cancelBooking(int id) {
		for(int i = 0; i < sessionList.size(); i++) {
			Session S = sessionList.get(i);
			Cinema C = S.getCineS();
			if(C.cancelBookingC(id) == true) {
				return true;
			}
		}
		return false;
	}
	/**
	 * makes the booking in the Cinema passed in as c
	 * returns true if successfully able to allocate the seats
	 * returns false if unsuccessful and the seats cant be allocated
	 * the seats can't be allocated because not enough seats in the same row
	 * @param c
	 * @param tickets
	 * @param id
	 * @return true or false based on whether successful or not
	 */
	private boolean makeBooking(Cinema c, int tickets, int id) {
		if(c.makeBookingC(tickets, id) == true) {
			//System.out.println("Making the booking");
			return true;
		} else {
			//System.out.println("Failed at Making the booking");
			return false;
		}
	}
	
	/**
	 * checks if the sessionList isn't empty
	 * If sessionList isn't null finds the Session matching the cinema_number and time
	 * @param cinema_number
	 * @param time
	 * @return the Session object 
	 */
	private Session checkSessionFromRequest(int cinema_number, String time) {
		if(sessionList.size() == 0)
			return null; 
		
		for(Session S: sessionList){
			if (S.getCinemaNumberS() == cinema_number && S.getTimeS().equals(time)) {
				//System.out.println("Session found");
				return S;
			}
		}
		//System.out.println("Session not found!");
		return null;
	}
	/**
	 * gets the cinema from the cinemaList using the unique cinema_number
	 * @param cinemaList
	 * @param cinema_number
	 * @return the Cinema 
	 * @throws CloneNotSupportedException
	 */
	private Cinema getCinemaFromNumber(ArrayList<Cinema> cinemaList, int cinema_number) throws CloneNotSupportedException {
		for (int i = 0; i < cinemaList.size(); i++){
			Cinema C = cinemaList.get(i);
			if (C.getCinemaNum() == cinema_number){
				return C.clone();
			}
		}
		return null;
	}
	/**
	 * checks if the cinema already exists using the cinema_number
	 * @param cinema_number
	 * @return boolean value of true or false according to whether the unique cinema exists or not
	 */
	private Boolean checkCinemaExists(int cinema_number){
		for(Cinema C: cinemaList){
			if (C.getCinemaNum() == cinema_number) {
				return true;
			}
			if(cinemaList == null) {
				return false;
			}
		}
		return false;
	}
}
