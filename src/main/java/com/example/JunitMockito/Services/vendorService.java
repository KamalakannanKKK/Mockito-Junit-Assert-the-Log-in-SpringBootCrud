package com.example.JunitMockito.Services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JunitMockito.Model.Vendor;
import com.example.JunitMockito.Repository.VendorRepo;

@Service
public class vendorService {
	static final Logger LOGGER = LoggerFactory.getLogger(vendorService.class);
	
	@Autowired
	private VendorRepo vendorRepo;
	
	public Vendor getVendorById(int id) {
		LOGGER.info("Get By Id Method Started..");
		return this.vendorRepo.findById(id).get();
	}
	
	public List<Vendor> getByvendorName(String Name) {
		return this.vendorRepo.findByVendorName(Name);
	}
	
	public Vendor addVendor(Vendor vendor) {
		LOGGER.info("vendor Added..");
		return this.vendorRepo.save(vendor);
	}
	
	public List<Vendor> getAllVendor(){
		return this.vendorRepo.findAll();
	}
	
	public Vendor updateVendor(Vendor vendor,int id) {
		Vendor oldVendor=vendorRepo.findById(id).get();
		oldVendor.setVendorName(vendor.getVendorName());
		oldVendor.setVendorAddress(vendor.getVendorAddress());
		oldVendor.setPhoneNumber(vendor.getPhoneNumber());
		return this.vendorRepo.save(oldVendor);
	}
	
	public String deleteVendor(int id) {
		this.vendorRepo.deleteById(id);
		return "Deleted";
	}		
	}
