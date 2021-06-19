
Spring Data and REST API crud APP

Creating a Spring Boot Application using Spring Data with REST API support.
--------------------------------------------------------------------------
Aim: to learn about Spring Data and REST API.

There are 2 entities in this project, Customer and Admin.

__________________________________________________________________________________________________________________________________

Update**: created three more entities. Address, Loan and Card in order to understand Relationships in JPA

Relationship b/w:
----------------- 

>Customer & Address
 ------------------
 (1:1),  address_id as foreign key in Customer Table.

>Loan & Customer
 ---------------
 (Many:1), customer_id as foreign key in Loan Table.

>Customer & Card
 ----------------
 (1:M), created a List<Card> in Customer Entity Class. Also, customer_id as foreign Key in Card Table 
__________________________________________________________________________________________________________________________________


Project Configurations:
-----------------------
>project generated using "spring initializr"
>Dependencies: Spring Web, Spring Data JPA and MySQL Driver

>in application.properties add following:-

#MySQL settings
spring.datasource.url=jdbc:mysql://localhost:3306/springdatacrud
spring.datasource.username=root
spring.datasource.password=root
#JPA settings
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

>> Currently, server is configured at port 3456. If required, can change the same by modifing "server.port" in application.properties

>> Currently, spring security hasn't been used. Credentials verfied at backend in ServiceImpl class



**Following functionalities have been provided for Customer:
---------------------------------------------------------

1) add new customer ie. Sign up
   -----------------------------
   unregistered customers can register by providing details such as name, dob, city, email and phone. 
   emailId along with password entered by customer will be used for Logging.
   URI: /signUp
   HTTP Method: POST
   REST Controller Method: signUp(Customer customer)
   Customer Details Provided as JSON Data

2)  view Details
    ------------
   by providing correct credentials ,  customer can logged in and can view his details.
   URI: /getMyDetails/{emailId}/{password}
   HTTP Method: GET
   REST Controller Method: getMyDetails(String emailId, String password)

3) Update Details:
   ---------------
   by providing correct credentials, customer can update his/ her details like phoneNumber,password, city, state and pincode
   URI: updateDetails/{emailId}/{password}
   HTTP Method: POST
   REST Controller Method: updateDetails(Customer customer, String emailId, String password)

4) Delete Account:
   --------------- 
   by providing correct credentials, customer can delete his/her account.
   URI: /deleteMyAccount/{emailId}/{password}
   HTTP Method: DELETE
   REST Controller Method: deleteMyAccount(String emailId, String password)

5) Apply for loan:
   ---------------
   by providing correct credentials, customer can login into his account and then can apply for loan by providing details like: Loan Amount and Purpose
   URI: /applyForLoan/{emailId}/{password}/{amount}/{loanType}
   HTTP Method: POST
   REST Controller Method: getLoan(String emailId, String password, Double amount, String loanType)

6) View Loan History:
   ------------------
   by providing correct credentials, customer can login into his account and then can view his Loan history(applied, approved and rejected ones)
   URI: /myLoans/{emailId}/{password}
   HTTP Method: GET
   REST Controller Method: myLoans(String emailId, String password)

7) Add credit/debit Card:
   ----------------------
   by providing correct credentials, customer can login into his account, then can add credit/debit cards.
   URI: /addCard/{emailId}/{password}
   HTTP Method: POST
   REST Controller Method: myLoans(CardTO cardDTO, String emailId, String password)

8) View Saved Cards: 
   -----------------
   by providing correct details, customer can view all his saved cards.
   URI: /getMyCards/{emailId}/{password}
   HTTP Method: GET
   REST Controller Method: myCards(String emailId, String password)


**Following functionalities have been provided for Admin:
---------------------------------------------------------


By logging with correct credentials, Admin can:

1) View Cutomer Details
   ---------------------
   URI: /customerById/{customerId}
   HTTP Method: GET
   REST Controller Method: getCustomerById(Admin admin, Integer customerId)

2) View ALL Customers
   ------------------
   URI: /customers
   HTTP Method: GET
   REST Controller Method: getAllCustomers()

3) View All Customers present in particular city:
   ----------------------------------------------
   URI: /customers/{city}
   HTTP Method: GET
   REST Controller Method: getAllCustomersByCity(String city)

4) Customer'S Loan Information
   -------------------------------
   URI: /loansByCustomerId/{customerId}
   HTTP Method: GET
   REST Controller Method: getLoansByCustomerId(Integer customerId)

5) Approve Customer's Loan Request
   --------------------------------
   URI: /approveLoan/{customerId}/{loanId}
   HTTP Method: PUT
   REST Controller Method: approveLoan(Integer customerId, Integer loanId)

6) Reject Customer's Loan Request
   ------------------------------
   URI: /rejectLoan/{customerId}/{loanId}
   HTTP Method: PUT
   REST Controller Method: rejectLoan(Integer customerId, Integer loanId)
    


>> Exception cases are handled
>> Default Logging ( Logback ) is used for logging. Check: logs/Error.log

