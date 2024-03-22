package com.example.JunitMockito.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.JunitMockito.Model.Vendor;
import com.example.JunitMockito.Services.vendorService;

@RestController
public class Controller {

	static final Logger logger=LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	vendorService vendorService;
	
	@GetMapping("/getAllVendor")
	public List<Vendor> getAllVendor(){
		logger.info("Getting All Vendors...");
		return this.vendorService.getAllVendor();
	}
	
	@PostMapping("/addVendor")
	public Vendor addVendor(@RequestBody Vendor vendor) {
		logger.info("Adding Vendor...");
		return this.vendorService.addVendor(vendor);
	}
	
	@GetMapping("/getVendor/{id}")
	public Vendor getVendorById(@PathVariable int id) {
		logger.info("Getting vendor of id " + id);
		return this.vendorService.getVendorById(id);
	}
	
	@PutMapping("/updateVendor/{id}")
	public Vendor updateVendor(@PathVariable int id,@RequestBody Vendor vendor) {
		logger.info("Updating Vendor id "+id+"vendor :"+ vendor.toString());
		return this.vendorService.updateVendor(vendor, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteVendor(@PathVariable int id) {
		logger.info("deleting the Vendor id"+id);
		return this.vendorService.deleteVendor(id);
	}
	
	@GetMapping("/getVendorByName/{name}")
	public List<Vendor> getVendorByName(@PathVariable String name){
		logger.info("Getting vendor List by Name...");
		return this.vendorService.getByvendorName(name);
	}
	
}
