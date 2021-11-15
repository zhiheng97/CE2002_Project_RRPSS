# CE/CZ2002 Object-Oriented Design & Programming Assignment


### OBJECTIVE

The main objective of this assignment is
- to apply the Object-Oriented (OO) concepts you have learnt in the course,
- to model, design and develop an OO application.
- to gain familiarity with using Java as an object oriented programming language.
- to work collaboratively as a group to achieve a common goal.

### THE ASSIGNMENT

The assignment for your group will be to design and develop a : **Restaurant Reservation and Point of Sale System (RRPSS).**

RRPSS is an application to computerize the processes of making **reservation, recording of orders and displaying of sale records. It will be solely used by the restaurant staff.**

The following are information about the application:
1. Menu items should be categorized according to its type, eg, Main course, drinks, dessert, etc.
2. Menu items can be added with details like name, description, price.
3. Promotional set package comes in a single package price with descriptions of the items to be served.
4. A customer may order a set package and ala carte menu items as well.
5. An order should indicate the staff who created the order.
6. Staff information can be in the form of name, gender, employee ID and job title.
7. Reservation is made by providing details like date, time, #pax, name, contact, etc. The system should check availability and allocate a suitable table.
8. When a reservation is made, a table is reserved till the reservation booking is removed.
9. Table comes in different seating capacity, in even sizes, with minimum of 2 and maximum of 10 pax ("Persons At Table").
10. Order invoice can be printed to list the order details (eg, table number, timestamp) and a complete breakdown of order items details with taxes details.
11. Discounts can be given to customers who hold membership of the restaurant or other entities.
l2. Sale revenue report will detail the period, individual sale items (either ala carte or promotional items) and total revenue.

#### Functional Requirements:
1. Create/Update/Remove menu item
2. Create/Update/Remove promotion
3. Create order
4. View order
5. Add/Remove order item/s to/from order
6. Create reservation booking
7. Check/Remove reservation booking
8. Check table availability
9. Print order invoice
10. Print sale revenue report by period (eg day or month)

_(Note : you may re-order or re-phrase the above functionalities when displaying your application menu)_

_The application is to be developed as a Console-based application (non-Graphical UI)._

_Data should be stored in flat file format, either in text or binary. No database application (eg MySQL, MS Access, etc) is to be used. You may populate your menu items with data collected from the internet._

_You will create your own test cases and data to test your application thoroughly. However, you should also create test cases to test for cases* of full reservation, releasing of table/s upon payment and removing of reservation/s upon ‘period expiry’._

#### Assumptions :
1. Reservation can only be __made in advance__. Reservation will be __automatically removed XX minutes after the actual booking time*__.
2. The currency will be in __Singapore Dollar (SGD) and Good and Services Tax (GST) and service charge must be included in the order invoice__.
3. Once an __order invoice is printed*__, it is __assumed that payment has been made and the table is vacated*__.
4. Customer with __membership card will be entitled to discount__.
5. There is __no requirement for access control__ and there is __no need for authentication (login/logout) in order to use the application__.
6. There is __no need to interface with external system__, eg Payment, printer, etc.

### THE REPORT
Your report will include the following :
1. A detailed UML Class Diagram for the application
    - show clearly the class relationship, notation
    - notes to explain, if necessary    
2. A write-up on your design considerations and use of OO concepts.
3. A detailed UML Sequence Diagram showing the flow of the “Check/Remove reservation booking” function.
    - Your flow should include the case of removing of reservation/s upon ‘period expiry’.
    - The diagram should show clearly all participating objects (minimum 3) involved with relevant interaction fragments and flow details to understand your design fully.
4. Screen captures of the testings done (those essential test cases not covered in your demo).
