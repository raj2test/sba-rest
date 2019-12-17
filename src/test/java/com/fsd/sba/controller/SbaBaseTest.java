package com.fsd.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fsd.sba.AbstractTest;
import com.fsd.sba.SbaRestApplication;


@SpringBootTest(classes = { SbaRestApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class SbaBaseTest extends AbstractTest{
	
	@Autowired
	public MockMvc mockMvc;
	
}
