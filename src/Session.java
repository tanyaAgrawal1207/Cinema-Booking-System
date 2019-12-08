import java.util.ArrayList;
/**
 * Session class
 * Attributes - 
 * unique cinema number - cinema
 * unique time for the session - time
 * unique movie 
 * the Cinema that the movie is playing at
 * @author tanya
 *
 */
public class Session {
	private int cinema;
	private String time;
	private String movie;
	private Cinema Cine;
	private ArrayList<Cinema> session;
	public Session (int cinema, String time, String movie, Cinema Cine) {
		this.cinema = cinema;
		this.time = time;
		this.movie = movie;
		this.Cine = Cine;
		session = new ArrayList<Cinema>();
		this.session.add(Cine);
	}
	public int getCinemaNumberS() {
		return this.cinema;
	}
	public String getTimeS() {
		return this.time;
	}
	public String getMovieS() {
		return this.movie;
	}
	public Cinema getCineS() {
		return Cine;
	}
	public ArrayList<Cinema> getListSesh () {
		return this.session;
	}
}
