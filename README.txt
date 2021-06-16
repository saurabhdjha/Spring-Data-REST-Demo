
Spring Data and REST API crud APP

Ceating a Spring Boot Application using Spring Data with REST API support.
--------------------------------------------------------------------------
Aim: to learn about Spring Data and REST API.
There are 2 entities in this project, Customer and Admin.


Following functionalities have been provided for Customer:
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
   by providing correct credentials [currently, spring security hasn't been used. Credentials verfied at backend in ServiceImpl class],  customer can logged in and can view his details.
   URI: /getMyDetails/{emailId}/{password}
   HTTP Method: GET
   REST Controller Method: getMyDetails(String emailId, String password)

3) Update Details:
   ---------------
   by providing correct credentials [currently, spring security hasn't been used. Credentials verfied at backend in ServiceImpl class], customer can update his/ her details like phoneNumber,
   city and password
   URI: updateDetails/{emailId}/{password}
   HTTP Method: POST
   REST Controller Method: updateDetails(Customer customer, String emailId, String password)

4) Delete Account:
   --------------- 
   by providing correct credentials [currently, spring security hasn't been used. Credentials verfied at backend in ServiceImpl class], customer can delete his/her account.
   URI: /deleteMyAccount/{emailId}/{password}
   HTTP Method: DELETE
   REST Controller Method: deleteMyAccount(String emailId, String password)



Following functionalities have been provided for Admin:
---------------------------------------------------------

1) View Cutomer Details
   ---------------------
   by providing correct credentials [currently, spring security hasn't been used. Credentials verfied at backend in ServiceImpl class], admin can view customer details.
   URI: /customerById/{customerId}
   HTTP Method: GET
   REST Controller Method: getCustomerById(Admin admin, Integer customerId)

2) View ALL Customers
   ------------------
   by providing correct credentials [currently, spring security hasn't been used. Credentials verfied at backend in ServiceImpl class], admin can view all customers details at once.
   URI: /customers
   HTTP Method: GET
   REST Controller Method: getAllCustomers()

3) View All Customers present in particular city:
   ----------------------------------------------
   by providing correct credentials [currently, spring security hasn't been used. Credentials verfied at backend in ServiceImpl class], admin can view all customers present in a city.
   URI: /customers/{city}
   HTTP Method: GET
   REST Controller Method: getAllCustomersByCity(String city)


>> Exception cases are handled
>> Default Logging ( Logback ) is used for logging. Check: logs/Error.log

   