package com.fse.taskmanagement.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.taskmanagement.TaskManagementApplication;
import com.fse.taskmanagement.model.UserDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TaskManagementApplication.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
	
	private MockMvc mockMvc;
	private UserDto userToSave;
	private ObjectMapper mapper;
	private String userFirstNameToCreate = "testFirstName";
	private String userFirstNameToUpdate = "updatedTestFirstName";
	static String userIdToVerify ;
		
		@Autowired
		private WebApplicationContext wac;
		
		@Before
		public void setup() throws ParseException {
			this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
			this.mapper = new ObjectMapper();
			
			//Create data for save
			userToSave = new UserDto();
			userToSave.setFirstName(userFirstNameToCreate);
		}
		
		@Test
		public void verifyCreateUser() throws Exception{
			String uri = "/user";
			
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
					.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(userToSave))).andReturn();
			
			JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString());
			List<String> returnedValue = jsonNode.get("result").findValuesAsText("firstName");
			
			Map<String, Object> map = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
			assertNotNull(map);
			assertTrue(returnedValue.size()>0);
			assertEquals(returnedValue.get(0), userToSave.getFirstName());
			
			userIdToVerify = jsonNode.get("result").findValuesAsText("userId").get(0);
		}
		
		@Test
		public void verifyUpdateUser() throws Exception{
			String uri = "/user/";
			userToSave.setUserId(Integer.valueOf(userIdToVerify));
			userToSave.setFirstName(userFirstNameToUpdate);
			
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri+userIdToVerify)
					.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(userToSave))).andReturn();
			
			JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString());
			List<String> returnedValue = jsonNode.get("result").findValuesAsText("firstName");
			
			Map<String, Object> map = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
			assertNotNull(map);
			assertTrue(returnedValue.size()==1);
			assertEquals(jsonNode.get("result").findValuesAsText("userId").get(0), userIdToVerify);
			assertEquals(returnedValue.get(0), userFirstNameToUpdate);
		}
		
		@Test
		public void verifyUserrGetAllUserss() throws Exception{
			String uri = "/user";
			
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
			.andReturn();
			
			JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString());
			List<String> returnedValue = jsonNode.get("result").findValuesAsText("firstName");
			
			//Map<String, Object> map = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
			//assertNotNull(map);
			assertTrue(returnedValue.size()>0);
			assertTrue(returnedValue.contains(userFirstNameToUpdate));
		}
		
		@After
		public void tearDown() throws Exception {
			String uri = "/user/";
			mockMvc.perform(MockMvcRequestBuilders.delete(uri+userIdToVerify).accept(MediaType.APPLICATION_JSON_VALUE))
					.andReturn();		
			
		}

}
