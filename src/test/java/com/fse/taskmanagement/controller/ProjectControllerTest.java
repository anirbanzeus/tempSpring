package com.fse.taskmanagement.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.fse.taskmanagement.model.ProjectDto;
import com.fse.taskmanagement.util.ProjectUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TaskManagementApplication.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectControllerTest {
	
private MockMvc mockMvc;
private ProjectDto projectToSave;
private ProjectUtils utils;
private ObjectMapper mapper;
private String projectNameToCreate = "testProject";
private String projectNameToUpdate = "updatedTestProjectName";
private String projectManagerName = "testProjectmanagerName";
static String projectIdToVerify ;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setup() throws ParseException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		this.utils = new ProjectUtils();
		this.mapper = new ObjectMapper();
		
		//Create data for save
		Date currentDateInPattern = new SimpleDateFormat("yyyy-mm-dd").parse("2019-10-31");		
		projectToSave = new ProjectDto();
		projectToSave.setProjectName(projectNameToCreate);
		projectToSave.setEndDate(utils.getNextDay(currentDateInPattern));
		projectToSave.setStartDate(currentDateInPattern);
		projectToSave.setProjectManager(projectManagerName);
	}
	
	@Test
	public void verifyCreateProject() throws Exception{
		String uri = "/project";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(projectToSave))).andReturn();
		
		JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString());
		List<String> returnedValue = jsonNode.get("result").findValuesAsText("projectName");
		
		Map<String, Object> map = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
		assertNotNull(map);
		assertTrue(returnedValue.size()==1);
		assertEquals(returnedValue.get(0), projectToSave.getProjectName());
		
		projectIdToVerify = jsonNode.get("result").findValuesAsText("projectId").get(0);
	}
	
	@Test
	public void verifyUpdateProject() throws Exception{
		String uri = "/project/";
		projectToSave.setProjectId(Integer.valueOf(projectIdToVerify));
		projectToSave.setProjectName(projectNameToUpdate);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri+projectIdToVerify)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(projectToSave))).andReturn();
		
		JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString());
		List<String> returnedValue = jsonNode.get("result").findValuesAsText("projectName");
		
		Map<String, Object> map = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
		assertNotNull(map);
		assertTrue(returnedValue.size()==1);
		assertEquals(jsonNode.get("result").findValuesAsText("projectId").get(0), projectIdToVerify);
		assertEquals(returnedValue.get(0), projectNameToUpdate);
	}
	
	@Test
	public void verifyUserrGetAllProjects() throws Exception{
		String uri = "/project";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
		.andReturn();
		
		JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString());
		List<String> returnedValue = jsonNode.get("result").findValuesAsText("projectName");
		
		assertTrue(returnedValue.size()>0);
		assertTrue(returnedValue.contains(projectNameToUpdate));
	}
	
	@After
	public void tearDown() throws Exception {
		String uri = "/project/";
		mockMvc.perform(MockMvcRequestBuilders.delete(uri+projectIdToVerify).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();		
		
	}

}
