package com.example.JunitMockito.Controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.JunitMockito.Model.Vendor;
import com.example.JunitMockito.Services.vendorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@WebMvcTest(controllers = Controller.class)
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private vendorService vendorservice;

	private List<Vendor> vendorList;
	Vendor VendorOne =new Vendor(1, "Amazon", "USA", 123455);
	Vendor VendorTwo =new Vendor(2, "Flipkart", "UK",23456);
	
	private ListAppender<ILoggingEvent> listAppender;
	
	@BeforeEach
	void setUp() {
		
		listAppender=new ListAppender<ILoggingEvent>();
		listAppender.start();
		ch.qos.logback.classic.Logger logger= (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Controller.class);
		logger.addAppender(listAppender);
		
		this.vendorList = new ArrayList<>();
		this.vendorList.add(VendorOne);
		this.vendorList.add(VendorTwo);
		this.vendorList.add(new Vendor(3, "Ebay", "USA", 34567));

	}
	
	@Test
	void getAllVendorTest() throws Exception{
		when(vendorservice.getAllVendor()).thenReturn(vendorList);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/getAllVendor").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
		
		 List<ILoggingEvent> logsList = listAppender.list;
		assertEquals("Getting All Vendors...",logsList.get(0).getMessage());

	}
	
	@Test
	void addVendorTest() throws Exception{
		when(vendorservice.addVendor(VendorOne)).thenReturn(VendorOne);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/addVendor").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(VendorOne))).andExpect(status().isOk());
		
		
		List<ILoggingEvent> list=listAppender.list;
		assertEquals("Adding Vendor...", list.get(0).getMessage());
	}
	
	@Test
	void getVendorById() throws Exception{
		when(vendorservice.getVendorById(1)).thenReturn(VendorOne);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/getVendor/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.vendorName", is("Amazon")));
		
		List<ILoggingEvent> list=listAppender.list;
		assertEquals("Getting vendor of id 1",list.get(0).getMessage());
	}
	
	@Test
	void updateVendorByIdTest() throws Exception{
		Vendor updatevendor=new Vendor(2,"Flipkart","London",897765884);
		when(vendorservice.getVendorById(2)).thenReturn(VendorTwo);
		VendorTwo.setVendorName(updatevendor.getVendorName());
		VendorTwo.setVendorAddress(updatevendor.getVendorAddress());
		VendorTwo.setPhoneNumber(updatevendor.getPhoneNumber());
		when(vendorservice.updateVendor(VendorTwo, 2)).thenReturn(VendorTwo);
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/updateVendor/2").contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(VendorTwo)))
		.andExpect(status().isOk());
		
		List<ILoggingEvent> list=listAppender.list;
		assertEquals("Updating Vendor id 2vendor :"+updatevendor.toString(),list.get(0).getMessage());
	}

	@Test
	void  DeleteVendorTest() throws Exception{
		when(vendorservice.deleteVendor(1)).thenReturn("Deleted");
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/delete/1")).andExpect(status().isOk());
		
		List<ILoggingEvent> list=listAppender.list;
		assertEquals("deleting the Vendor id"+1,list.get(0).getMessage());
	}
	
	@Test
	void GetVendorByNameTest() throws Exception{
		List<Vendor> vendorlist1=new ArrayList<>();
		vendorlist1.add(VendorOne);
		when(vendorservice.getByvendorName("Amazon")).thenReturn(vendorlist1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/getVendorByName/Amazon").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
		
		List<ILoggingEvent> list=listAppender.list;
		assertEquals("Getting vendor List by Name...",list.get(0).getMessage());
	}
 }
