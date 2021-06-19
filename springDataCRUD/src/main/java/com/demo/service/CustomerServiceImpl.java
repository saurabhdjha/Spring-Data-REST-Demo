package com.demo.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dto.AddressDTO;
import com.demo.dto.CardDTO;
import com.demo.dto.CustomerDTO;
import com.demo.dto.LoanDTO;
import com.demo.entity.Address;
import com.demo.entity.Card;
import com.demo.entity.Customer;
import com.demo.entity.Loan;
import com.demo.exception.CustomerException;
import com.demo.repository.CustomerRepository;
import com.demo.repository.LoanRepository;



@Service(value="CustomerService")
@Transactional
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	
	@Override
	public Integer addCustomer(CustomerDTO customerDTO) throws CustomerException{
		
		Customer customerdb=customerRepository.findByEmailId(customerDTO.getEmailId());
		
		if(customerdb!=null)
			throw new CustomerException("Service.CUSTOMER_ALREADY_EXIST");
		
		if(customerDTO.getEmailId()==null || customerDTO.getPassword()==null || customerDTO.getName()==null || customerDTO.getBirthDate()==null)
			throw new CustomerException("Service.CANNOT_BE_NULL");
		Customer customer=new Customer();
		customer.setName(customerDTO.getName());
		customer.setBirthDate(customerDTO.getBirthDate());
		customer.setEmailId(customerDTO.getEmailId());
		customer.setPhoneNo(customerDTO.getPhoneNo());
		customer.setPassword(customerDTO.getPassword());
		Address address=new Address();
		AddressDTO addressDTO=customerDTO.getAddress();
		if(addressDTO!=null)
		{
			address.setAddress_id(addressDTO.getAddress_id());
			address.setCity(addressDTO.getCity());
			address.setState(addressDTO.getState());
			address.setPincode(addressDTO.getPincode());
			customer.setAddress(address);
		}
		
		Integer custId=customerRepository.save(customer).getCustomerId();

		return custId;
	}

	@Override
	public String updateCustomer(CustomerDTO customerDTO, String emailId, String password) throws CustomerException{
		
		Customer customerdb=customerRepository.findByEmailId(emailId);
		if(customerdb==null)
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");

		if(!password.equalsIgnoreCase(customerdb.getPassword()))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
		String message="";
		
		// can change only phoneNumber,password, city, state and pincode
		
		String newPhoneNumber=customerDTO.getPhoneNo();
		if(newPhoneNumber!=null)
		{
			customerdb.setPhoneNo(newPhoneNumber);
			message+="phoneNumber ";
		}
	
		
		String newPassword=customerDTO.getPassword();
		if(newPassword!=null)
		{
			customerdb.setPassword(newPassword);
			message+="password ";
		}
		
		AddressDTO addressDTO=customerDTO.getAddress();
		Address address=customerdb.getAddress();
		if(addressDTO!=null)
		{
			String city=customerDTO.getAddress().getCity();
			if(city!=null)
			{
				address.setCity(city);
				message+="city ";
			}
			String state=customerDTO.getAddress().getState();
			if(state!=null)
			{
				address.setState(state);
				message+="state ";
			}
			
			Integer pincode=customerDTO.getAddress().getPincode();
			if(pincode!=null)
			{
				address.setPincode(pincode);
				message+="pincode ";
			}
		}
		
		
		if(message==null || message.isEmpty())
			return "nothing updated!!";
		
		return message+" updated succesfully!!";
	}

	@Override
	public CustomerDTO getMyDetails(String emailId, String password) throws CustomerException{

		Customer customer=customerRepository.findByEmailId(emailId);
		
		if(customer==null)
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");
		if(!customer.getPassword().equalsIgnoreCase(password))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
		CustomerDTO customerDTO=new CustomerDTO();
		
		customerDTO.setCustomerId(customer.getCustomerId());
		customerDTO.setName(customer.getName());
		customerDTO.setBirthDate(customer.getBirthDate());
		customerDTO.setEmailId(customer.getEmailId());
		customerDTO.setPhoneNo(customer.getPhoneNo());
		customerDTO.setPassword(customer.getPassword());
		// Setting Address info
		AddressDTO addressDTO=new AddressDTO();
		addressDTO.setAddress_id(customer.getAddress().getAddress_id());
		addressDTO.setCity(customer.getAddress().getCity());
		addressDTO.setState(customer.getAddress().getState());
		addressDTO.setPincode(customer.getAddress().getPincode());
		customerDTO.setAddress(addressDTO);
		return customerDTO;
	}

	@Override
	public Integer deleteMyAccount(String emailId, String password) throws CustomerException{

		Customer customer=customerRepository.findByEmailId(emailId);
		
		if(customer==null)
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");
		if(!customer.getPassword().equalsIgnoreCase(password))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
		int customerId=customer.getCustomerId();
		customerRepository.delete(customer);
		return customerId;
	}

	@Override
	public Integer applyForLoan(String emailId, String password, Double amount, String loanType) throws CustomerException {
		
		Customer customer=customerRepository.findByEmailId(emailId);
		if(customer==null)
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");
		if(!customer.getPassword().equalsIgnoreCase(password))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
		Loan loan=new Loan();
		loan.setAmount(amount);
		loan.setLoanType(loanType);
		loan.setCustomer(customer);
		loan.setStatus("Applied");
		
		return loanRepository.save(loan).getLoanId();
	}

	@Override
	public List<LoanDTO> myLoans(String emailId, String password) throws CustomerException {
		
		Customer customer=customerRepository.findByEmailId(emailId);
		if(customer==null)
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");
		if(!customer.getPassword().equalsIgnoreCase(password))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
		List<Loan> loans=loanRepository.getAllLoansOfCustomer(customer.getCustomerId());
		
		List<LoanDTO> loanDTOs=new ArrayList<>();
		
		for(Loan loan:loans)
		{
			LoanDTO loanDTO=new LoanDTO();
			loanDTO.setAmount(loan.getAmount());
			loanDTO.setLoanId(loan.getLoanId());
			loanDTO.setLoanType(loan.getLoanType());
			if(loan.getStatus().equalsIgnoreCase("issued"))
			{
				loanDTO.setStatus(loan.getStatus());
				loanDTO.setIssueDate(loan.getIssueDate());
			}
			else
			{
				loanDTO.setStatus(loan.getStatus());
			}
			
			loanDTOs.add(loanDTO);
		}
		
		return loanDTOs;
	}

	@Override
	public Integer addCard(String emailId, String password, CardDTO cardDTO) throws CustomerException {
		
		Customer customer=customerRepository.findByEmailId(emailId);
		if(customer==null)
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");

		if(!password.equalsIgnoreCase(customer.getPassword()))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
		Card card=new Card();
		card.setCardNo(cardDTO.getCardNo());
		card.setExpDate(cardDTO.getExpDate());
		
		List<Card> cards=customer.getCards();
		cards.add(card);
		customerRepository.save(customer);
		return customer.getCustomerId();
	}

	@Override
	public List<CardDTO> myCards(String emailId, String password) throws CustomerException {
		
		Customer customer=customerRepository.findByEmailId(emailId);
		if(customer==null)
			throw new CustomerException("Service.CUSTOMER_DOESNT_EXIST");

		if(!password.equalsIgnoreCase(customer.getPassword()))
			throw new CustomerException("Service.INCORRECT_PASSWORD");
		
		List<Card> cards=customer.getCards();
		List<CardDTO> cardDTOs=new ArrayList<>();
		for(Card card:cards)
		{
			CardDTO cardDTO=new CardDTO();
			cardDTO.setCardId(card.getCardId());
			cardDTO.setCardNo(card.getCardNo());
			cardDTO.setExpDate(card.getExpDate());
			cardDTOs.add(cardDTO);
		}
		
		return cardDTOs;
	}


}
