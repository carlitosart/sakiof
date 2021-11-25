package com.sakila.sakila.bl;

import com.sakila.sakila.dao.AddressRepository;
import com.sakila.sakila.dao.CustomerRepository;
import com.sakila.sakila.dto.AddressModel;
import com.sakila.sakila.dto.CustomerModel;
import com.sakila.sakila.dto.UserInformation;
import com.sakila.sakila.dto.UserRequest;
import com.vividsolutions.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class UserService implements UserDetailsService {
    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;

    @Autowired
    public UserService(CustomerRepository customerRepository,AddressRepository addressRepository){
        this.customerRepository = customerRepository;
        this.addressRepository=addressRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        CustomerModel user = customerRepository.findByEmail(email);
        if(user != null){
            UserInformation userInformationModel=new UserInformation(user.getEmail(),user.getCustomerId(),user.getPassword(),List.of(new SimpleGrantedAuthority("ROLE_USER")));
            return userInformationModel;
        } else {
            throw new UsernameNotFoundException("User '"+email+"' not found!");
        }
    }
    public void signUp(UserRequest userRequest){
        AddressModel addressModel=new AddressModel();
        addressModel.setAddress(userRequest.getAddress());
        addressModel.setAddress2(userRequest.getAddress2());
        addressModel.setCityId(userRequest.getCityId());
        addressModel.setDistrict(userRequest.getDistrict());
        addressModel.setPhone(userRequest.getPhone());
        addressModel.setPostalCode(userRequest.getPostalCode());
        GeometryFactory geomFactory = new GeometryFactory();
        addressModel.setLocation(geomFactory.createPoint(new Coordinate(1,1)));
        addressRepository.save(addressModel);
        CustomerModel customerModel=new CustomerModel();
        customerModel.setAddressId(addressModel.getAddressId());
        customerModel.setEmail(userRequest.getEmail());
        customerModel.setFirstName(userRequest.getFirstName());
        customerModel.setLastName(userRequest.getLastName());
        customerModel.setStoreId(userRequest.getStoreId());
        String passEncode=new BCryptPasswordEncoder().encode(userRequest.getPassword());
        customerModel.setPassword(passEncode);
        customerRepository.save(customerModel);
    }
}
