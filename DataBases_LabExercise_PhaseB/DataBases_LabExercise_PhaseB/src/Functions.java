import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Functions {
	
	Connection conn;

	// Constructor for Driver Search//
	public Functions() {
		
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver Found!!");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Not Found!!");
		}
	}
	
	// Functions for connection with postgres database with 2 options. You can insert connection data from userInput or put them as attributes in function's call//
	public void dbConnect() {
			
			String ip = userInput("Give ip");
			String dbName = userInput("Give a DataBase's name");
			String username = userInput("Give username");
			String password = userInput("Give password");
			
			try {
				conn = DriverManager.getConnection("jdbc:postgresql://"+ip+":5432/"+dbName, username, password);
				System.out.println("Connection is Successfull! conn:" + conn);
			} catch (SQLException e) {
				System.out.println("Connection is not Successfull! conn:" + conn);
			}
		}
	
	public void dbConnect(String ip,String dbName, String username, String password) {
		
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://"+ip+":5432/"+dbName, username, password);
			System.out.println("Connection is Successfull! conn:" + conn);
		} catch (SQLException e) {
			System.out.println("Connection is not Successfull! conn:" + conn);
		}
	}
	
	// Function for 2i //
	public void dbHotelSearch() {
		
		try {
			Statement st =conn.createStatement();
			
			ResultSet res1 = st.executeQuery("select * from hotel where left(hotel.name,2)=" +userInput("\nGive 2 characters for hotel name prefix in ''")+ "order by hotel.\"idHotel\",hotel.name ASC");
			
			while(res1.next()) {
				System.out.println(" hotelId: "+res1.getInt(1)+" hotelName: "+res1.getString(2)+" hotelStars: "+res1.getInt(3)+" hotelAddress: "+res1.getString(4)+
						" hotelCity: "+res1.getString(5)+" hotelCountry: "+res1.getString(6)+" hotelPhone: "+res1.getString(7)+" hotelFax: "+res1.getString(8));
			}
			
			ResultSet res2 = st.executeQuery("select * from person where left(person.lname,2)="+userInput("\nGive 2 characters for person lastname prefix in ''")+"\r\n" + 
					"intersect\r\n" + 
					"select * from person where person.\"idPerson\" in \r\n" + 
					"	(select roombooking.\"bookedforpersonID\" from roombooking where roombooking.\"roomID\" in \r\n" + 
					"		(select room.\"idRoom\" from room where room.\"idHotel\" in\r\n" + 
					"			(select hotel.\"idHotel\" from hotel where hotel.\"idHotel\"="+userInput("Give hotel id") +"))) order by lname asc");
			
			while(res2.next()) {
				System.out.println(" personId: "+res2.getInt(1)+" firstName: "+res2.getString(2)+" lastName: "+res2.getString(3)+" sex: "+res2.getString(4)+
						" dateOfBirth: "+res2.getString(5)+" personAddress: "+res2.getString(6)+" personCity: "+res2.getString(7)+" personCountry: "+res2.getString(8));
			}
			
			res1.close();
			res2.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	// Function for 2ii//
	public void dbUpdateBooking() {
		try {
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			String personId = userInput("\nGive person id to search bookings");
			
			ResultSet res1 = st.executeQuery("select roombooking.\"hotelbookingID\", roombooking.\"roomID\", roombooking.checkin, roombooking.checkout, roombooking.rate from roombooking "
					+ "where roombooking.\"bookedforpersonID\" ="+personId+" order by roombooking.\"hotelbookingID\" asc");
			
			while(res1.next()) {
				System.out.println("hotelBookingId: "+res1.getInt(1)+" roomId: "+res1.getInt(2)+" checkin: "+res1.getDate(3)+" checkout: "+res1.getDate(4)+" rate: "+res1.getFloat(5));
			}
			
			ResultSet res2 = st.executeQuery("select roombooking.\"hotelbookingID\", roombooking.\"roomID\", roombooking.checkin, roombooking.checkout, roombooking.rate from roombooking "
					+ "where roombooking.\"hotelbookingID\"="+userInput("\nGive hotelBooking id for update")+" and roombooking.\"bookedforpersonID\"="+personId +"limit 1");
			
			res2.next();
			
			System.out.println("!!!!! hotelBookingId: "+res2.getInt(1)+" roomId: "+res2.getInt(2)+" checkin: "+res2.getDate(3)+" checkout: "+res2.getDate(4)+
					" rate: "+res2.getFloat(5));
			
			String checkinDate = userInput("Give new checkin date in ''");
			String checkoutDate = userInput("Give new checkout date in ''");
			float roomRate = Float.parseFloat(userInput("Give new value for rate"));
			
			st.executeUpdate("update roombooking set checkin="+checkinDate+", checkout="+checkoutDate+" , rate="+roomRate+
							 "where  roombooking.\"hotelbookingID\"="+ res2.getInt(1) +" and roombooking.\"roomID\"="+ res2.getInt(2));
						
			
			res1.close();
			res2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Function for iii//
	public void dbAvailRooms() {
		
		try {
			Statement st = conn.createStatement();
			
			ResultSet res = st.executeQuery("select room.\"idRoom\", room.\"number\", room.roomtype\r\n" + 
					"from room\r\n" + 
					"where room.\"idRoom\" in (select roombooking.\"roomID\"\r\n" + 
					"					   	from roombooking\r\n" + 
					"					   	where roombooking.checkout  between"+ userInput("\nGive the first date in '' to see available rooms") +" and "
											+userInput("\nGive the second date in '' to see available rooms ") + ") order by room.\"idRoom\"  asc");
			
			while(res.next()) {
				System.out.println("idRoom: "+res.getInt(1)+" roomNumber: "+res.getInt(2)+" roomType: "+res.getString(3));
			}
			
			PreparedStatement pst = conn.prepareStatement("insert into roombooking values("+userInput("Give hotelbooking id")+",(select roombooking.\"roomID\" from roombooking "
					+ "where roombooking.\"roomID\"="+ userInput("\nGive a room id for booking") +"limit 1) ," + userInput("\nGive person id for booking")+"," +userInput("\nGive checkin date in ''")+ ","+userInput("\nGive checkout date in ''")+" , ?)");			
			
			
			float roomRate = Float.parseFloat(userInput("\nGive value for rate"));
			pst.setFloat(1, roomRate);
			
			pst.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
		
	
	 public void dbClose() {
		 try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 // User input function//
	 public String userInput (String input) {
		 
		 Scanner scn = new Scanner(System.in);
		 System.out.println(input);
		 return scn.nextLine();		 
	 }
}
