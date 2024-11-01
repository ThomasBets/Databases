# Databases

Phase A:

Hotel Booking Management System
I. Overview
This project involves the development of a database for a hotel booking management application supporting hotels in multiple countries. The system enables both hotel employees and clients to make reservations online. Key features include:

Hotel & Room Management: Supports information management for hotels and their rooms, including room types, rates, and availability.
Facilities Management: Tracks facilities provided by hotels (HotelFacilities) and individual rooms (RoomFacilities). Facilities can be categorized at multiple levels.
Client Bookings: Allows clients to book one or multiple rooms, assigning a responsible individual to each room. Each booking stores room charges and reservation dates.
Payment Processing: Supports cash, credit card, and bank transfer payments, storing essential information for credit card transactions. Cancellations and updates to paid bookings trigger partial refunds through the original payment method.
Transaction Logging: Records all booking-related transactions with details on action type, date, and amount.
Employee Roles and Access: Allows hotel receptionists and managers to manage reservations with hierarchical access rights.
Activity Management: Supports scheduling weekly activities that clients can reserve through designated employees.

II. Implementation of Relational Schema & Required Functionality
Database Setup
Database Initialization: Create a PostgreSQL database, restore data from the provided backup file, and thoroughly review the existing data structure.
Functionality Implementation
1. Relational Schema
1.1. Convert the ER model’s red-highlighted portion to a relational schema. Create new tables based on this schema within the PostgreSQL database.

2. Data Management (PostgreSQL Functions)
2.1. Client & Credit Card Management: Functions for insert/update/delete actions with parameters: action, documentclient, fname, lname, sex, dateofbirth, address, city, country, cardtype, number, holder, expiration.

2.2. Bulk Bookings Creation: Allows bulk booking of rooms for a specified date range and hotel (idHotel), ensuring no overlapping reservations.

3. Data Retrieval (PostgreSQL Functions)
3.1. Retrieve countries/cities offering room discounts over 30%.

3.2. Find hotels with a specific star rating, name prefix, and Studio-type rooms priced below €80, offering breakfast and a restaurant.

3.3. Display hotels and their room types with the highest discounts, sorted by room type.

3.4. Show all bookings for a hotel with booking details and the booking origin (employee or client).

3.5. Retrieve hotel activities with no client reservations yet.

3.6. Display subtypes of hotel facilities for a first-level facility (non-subtype).

3.7. Search hotels with specific facilities and available rooms with specific facilities, considering facility hierarchies.

3.8. Search hotels with at least one available room per room type offered.

4. Calculations (PostgreSQL Functions)
4.1. Count activities associated with each client.

4.2. Calculate the average age of clients with bookings for a specific room type.

4.3. Calculate the lowest room rate per room type by city in specified countries.

4.4. Identify hotels with above-average revenue within their city.

4.5. Calculate monthly occupancy for a hotel for a specific year.

5. Triggers
5.1. Transaction Update on Payment: Update the transaction log when a booking payment is made.

5.2. Cancellation Deadline Modification: Only managers can modify the cancellation deadline. Restrictions apply to changes that reduce stay duration or rooms.

5.3. Final Amount Adjustment: Update total payment amount upon booking insert/update/delete based on room rate and discounts.

6. Views
6.1. Available Rooms View: Displays current date room availability, type, and the last available date.

6.2. Weekly Reservation Plan View: Provides a weekly room booking overview for the upcoming week, organized by room number.

III. PostgreSQL & pgAdmin Installation
To set up PostgreSQL and pgAdmin, download version 11.11 from EnterpriseDB, then follow these steps:

Install PostgreSQL and pgAdmin.
Use your set password to connect to the server in pgAdmin.
Create a new database by right-clicking "Databases" > "Create Database."
Right-click the new database and select "Restore…" to restore from the provided backup file.

Phase B:

Hotel Reservation Management System

I. Overview
This project represents the second phase of a lab assignment focused on creating a Java application that interacts with a PostgreSQL database through JDBC. The application, developed in Eclipse, uses the PostgreSQL JDBC driver to connect to the database and perform hotel reservation management tasks.

The application provides a user-friendly menu to:
1. Set up database connection details.
2. Search and select hotels by name.
3. Manage customer information and hotel reservations.

II. Features and Functionalities

1. Database Connection
The application prompts the user to enter credentials for connecting to a PostgreSQL database. Required details include:
- IP Address of the database server
- Database Name
- Username
- Password

Upon entering these details, the application establishes a connection using JDBC. If successful, it proceeds to the main menu; otherwise, an error message is displayed, and the user is prompted to re-enter credentials.

2. Main Menu Options

2.1 Hotel Search by Name Prefix
The application allows users to enter a hotel name prefix (e.g., entering “Hil” to search for hotels like "Hilton" or "Hillside"). The system then displays an alphabetized list of matching hotels, each with an index number for easy selection.

Example:
Hilton
Hillside 

The user selects a hotel by entering its index number, unlocking further actions, such as searching for customers, viewing reservation details, and checking room availability.

2.2 Hotel-Specific Actions

i. Customer Search by Last Name Prefix  
After selecting a hotel, the user can search for customers by entering a last name prefix. The application then displays an alphabetized list of matching customers, including:
- Customer ID
- First and Last Name
- Contact Information

ii. Viewing and Updating Customer Reservations  
Upon entering a customer ID, the system displays the customer's reservation details for the selected hotel. This includes:
- Index Number
- Room Code
- Check-in and Check-out Dates
- Room Charge

The user can select a reservation by its index number to update check-in/check-out dates or room charges. Entering "0" returns the user to the main menu.

iii. Viewing Available Rooms for a Specified Date Range  
Users can specify a date range to view available rooms at the selected hotel. The application displays a list of rooms with:
- Room Identifier
- Room Number
- Room Type

The user can select a room by entering its index number to make a reservation for a specific customer, requiring entry of the customer’s ID for the specified date range.

III. Implementation Instructions

1. Setting Up JDBC  
   - Install the PostgreSQL JDBC driver in Eclipse and add it to the Java project.
   - Implement a method to prompt the user for connection details and establish a connection to the database.

2. Menu and Navigation Logic  
   - Design a main menu and submenus that accept user input and invoke the necessary SQL queries.

3. SQL Query Development  
   - Write SQL queries to:
     - Search for hotels
     - Retrieve customer data
     - Display available rooms
     - Update reservation details
   - Ensure queries are structured securely and effectively.

4. Reservation Updates  
   - Implement a method for updating reservation details, such as check-in/check-out dates and room charges.

5. Error Handling  
   - Include error handling for:
     - Database connection issues
     - SQL query errors
     - Invalid user inputs

IV. Example User Interaction
Welcome to the Hotel Reservation Management System

Connect to Database

Search Hotels

Exit

Option: 2

Enter hotel name prefix: Hil Found the following hotels:

Hilton Athens

Hillside Resort

Selection: 1 -- Hilton Athens Submenu --

Search Customers

View Customer Reservations

View Available Rooms

Return to Main Menu
