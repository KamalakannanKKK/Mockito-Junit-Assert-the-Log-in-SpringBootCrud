package com.example.JunitMockito.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.JunitMockito.Model.Vendor;
import com.example.JunitMockito.Repository.VendorRepo;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;


public class vendorServiceTest {

	private ListAppender<ILoggingEvent> listAppender;
	
	@Mock
	VendorRepo vendorRepo;
	
	@InjectMocks
	vendorService vendorService;

	Vendor vendorOne,vendorTwo;
	List<Vendor> vendorList=new ArrayList<>();
	List<Vendor> vendorList1=new ArrayList<>();
	
	@BeforeEach
	void SetUp() {
        listAppender = new ListAppender<>();
        listAppender.start();
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(vendorService.class);
        logger.addAppender(listAppender);
		
		MockitoAnnotations.openMocks(this);
		 vendorOne = new Vendor(1,"Amazon","USA",900000000);
		 vendorTwo = new Vendor(2,"Flipkart","London",789378839);
		 vendorList.add(vendorOne);
		 vendorList.add(vendorTwo);
		 
	}
	
	@Test
	void addVendorTest() {
		 Vendor vendor = new Vendor(1,"Amazon","USA",900000000);
		when(vendorRepo.save(vendor)).thenReturn(vendor);
		assertThat(vendorService.addVendor(vendor)).isEqualTo(vendor);
		List<ILoggingEvent> logList=listAppender.list;
		assertEquals("vendor Added..",logList.get(0).getMessage());
	}
	
	@Test
	void getVendorByIdTest() {
	
		 when(vendorRepo.findById(1)).thenReturn(Optional.ofNullable(vendorOne));
		 assertThat(vendorService.getVendorById(1).getVendorName()).isEqualTo(vendorOne.getVendorName());
		 assertThat(vendorService.getVendorById(1).getVendorAddress()).isEqualTo(vendorOne.getVendorAddress());
		 List<ILoggingEvent> logsList = listAppender.list;
	        assertEquals("Get By Id Method Started..", logsList.get(0)
	                                      .getMessage());
	}
	
	@Test
	void getVendorByNameTest() {
		 when(vendorRepo.findByVendorName("Amazon")).thenReturn((vendorList));
		 assertThat(vendorService.getByvendorName("Amazon").get(0).getVendorName()).isEqualTo(vendorList.get(0).getVendorName());
	}
	
	@Test
	void getAllVendorList() {
		when(vendorRepo.findAll()).thenReturn(vendorList);
		assertThat(vendorService.getAllVendor().size()).isEqualTo(vendorList.size());
	}
	
	@Test
	void updateVendorTest() {
		Vendor newVendor = new Vendor(2,"Flipkart","UK",789378839);
		when(vendorRepo.findById(2)).thenReturn(Optional.ofNullable(vendorOne));
		vendorOne.setVendorName(newVendor.getVendorName());
		vendorOne.setPhoneNumber(newVendor.getPhoneNumber());
		when(vendorRepo.save(vendorOne)).thenReturn(vendorOne);
		assertThat(vendorService.updateVendor(newVendor, 2).getVendorAddress()).isEqualTo(vendorOne.getVendorAddress());
	}
	
	@Test
	void deleteVendorTest() {
		doAnswer(Answers.CALLS_REAL_METHODS).when(vendorRepo).deleteById(1);
		assertThat(vendorService.deleteVendor(1)).isEqualTo("Deleted");
	}
	
	//NegativeTestCases
	@Test
	void getAllVendorListEmptyTest() {
		when(vendorRepo.findAll()).thenReturn(vendorList1);
		assertThat(vendorService.getAllVendor()).isEmpty();
	}
	

    @AfterEach
    public void stopList() {

        listAppender.stop();
        
    }
}
