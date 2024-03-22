package com.example.JunitMockito.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JunitMockito.Model.Vendor;
@Repository
public interface VendorRepo extends JpaRepository<Vendor, Integer>{

	List<Vendor> findByVendorName(String name);

}
