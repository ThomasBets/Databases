
public class Main { 

	public static void main(String[] args) {
		
		Functions fun = new Functions();
		
		switch (fun.userInput("1) Choose 1 to connect to a DataBase \n2) Choose 2 to search a hotel with specific prefix ")) {
			
		case "1":
			fun.dbConnect();
			break;
		case "2":
			System.out.println("You have to connect to a DataBase first!!");
			fun.dbConnect();
			fun.dbHotelSearch();
			fun.dbUpdateBooking();
			fun.dbAvailRooms();
			fun.dbAvailRooms();
			}
	}
} 
