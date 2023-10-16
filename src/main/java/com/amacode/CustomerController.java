package com.amacode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import  com.amacode.CustomerRepository;
@Controller
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @GetMapping("/customers")
    public String getCustomers(Model model){
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers",customers);
        return "customers";
    }
    @GetMapping("/addcustomer")
    public String showcustomerform(Model model){

        model.addAttribute("newCustomer",new Customer());
        return "/addcustomer";
    }







    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){}

    @PostMapping("/addcustomer")
    public String addCustomer(@ModelAttribute("newCustomer") Customer customer){
        customerRepository.save(customer);
        return  "redirect:/customers";
    }
    //    public void addCustomer(@RequestBody NewCustomerRequest request){
//        Customer customer = new Customer();
//        customer.setName(request.name);
//        customer.setAge(request.age);
//        customer.setEmail(request.email);
//
//        customerRepository.save(customer);
//    }
    @DeleteMapping("/deletecustomer/{id}")
    public  void deleteCustomer( @PathVariable("id") Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("updatecustomer/{id}")
    public void updatecustomer(@RequestBody NewCustomerRequest request, @PathVariable("id") Integer id){


        Optional<Customer> tobeupdatedCustomer =customerRepository.findById(id);

        if (tobeupdatedCustomer.isPresent()){
            Customer customer = tobeupdatedCustomer.get();

            customer.setName(request.name);
            customer.setAge(request.age);
            customer.setEmail(request.email);
            customerRepository.save(customer);
        }else {
            System.out.println("no customer");
        }





    }
}
