package com.example.JunitMockito.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.JunitMockito.Model.Vendor;
@SpringBootTest
public class vendorRepoTest {

	@Autowired
	VendorRepo vendorrepo;
	Vendor vendor;
	
    @BeforeEach
    void setUp() {
    	vendor = new Vendor(1,"Amazon",
                "USA", 345678);
    	vendorrepo.save(vendor);
    }

    @AfterEach
    void tearDown() {
    	vendor = null;
    	vendorrepo.deleteAll();
    }
    
	@Test
	void vendorFindByNameTest() throws Exception{
		List<Vendor> vendorList=vendorrepo.findByVendorName("Amazon");
		assertThat(vendorList.get(0).getVendorId()).isEqualTo(vendor.getVendorId());
		assertThat(vendorList.get(0).getVendorAddress()).isEqualTo(vendor.getVendorAddress());
	}
	
	
}
