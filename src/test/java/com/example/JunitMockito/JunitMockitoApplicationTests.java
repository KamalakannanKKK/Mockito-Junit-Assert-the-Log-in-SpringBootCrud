package com.example.JunitMockito;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.JunitMockito.Controller.Controller;
import com.example.JunitMockito.Model.Vendor;
import com.example.JunitMockito.Repository.VendorRepo;
import com.example.JunitMockito.Services.vendorService;

@SpringBootTest
class JunitMockitoApplicationTests {

	private MockMvc mockmvc;
	
	@Mock
	vendorService vendorService;
	
	@Mock
	VendorRepo repo;

	
	Controller controller;
	
	Vendor vendor1=new Vendor(1,"Amazon","USA",987654433);
	Vendor vendor2=new Vendor(2,"Flipkart","UK",98766478);
	Vendor vendor3=new Vendor(3,"EBay","km",987656788);
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockmvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	void getAllVendor() throws Exception {

		List<Vendor> vendor = new ArrayList<Vendor>(Arrays.asList(vendor1, vendor2, vendor3));

		when(repo.findAll()).thenReturn(vendor);

		mockmvc.perform(MockMvcRequestBuilders.get("/getAllVendor").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));

	}

}
