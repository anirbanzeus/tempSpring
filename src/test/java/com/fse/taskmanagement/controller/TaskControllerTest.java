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
import com.fse.taskmanagement.model.TaskDto;
import com.fse.taskmanagement.util.ProjectUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TaskManagementApplication.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TaskControllerTest {

	private MockMvc mockMvc;
	private TaskDto taskToSave;
	private TaskDto taskToSaveAsParent;
	private String taskNameToCreate = "testTask";
	private String mockProjectName = "mockProjectName";
	private ObjectMapper mapper;
	static String taskIdToVerify ;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setup() throws ParseException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		this.mapper = new ObjectMapper();
		
		//Object to save
		ProjectUtils utils = new ProjectUtils();
		Date currentDateInPattern = new SimpleDateFormat("yyyy-mm-dd").parse("2019-10-31");
		taskToSave = new TaskDto();
		taskToSave.setTaskName(taskNameToCreate);
		taskToSave.setEndDate(utils.getNextDay(currentDateInPattern));
		taskToSave.setStartDate(currentDateInPattern);
		taskToSave.setProjectName(mockProjectName);
		
		//Object to save as Parent
		taskToSaveAsParent = new TaskDto();
		taskToSaveAsParent.setTaskName(taskNameToCreate);
		taskToSaveAsParent.setProjectName(mockProjectName);
		taskToSaveAsParent.setParent(true);
	}
	
	@Test
	public void verifyCreateTask() throws Exception{
		String uri = "/tasks";
		
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(taskToSave))).andReturn();
		
		JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString());
		List<String> returnedValue = jsonNode.get("result").findValuesAsText("taskId");
		
		Map<String, Object> map = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
		assertNotNull(map);
		assertNotNull(returnedValue);
		assertTrue(returnedValue.size()==1);
		
		taskIdToVerify =returnedValue.get(0) ;
	}
	
	@Test
	public void verifyCreateParentTask() throws Exception{
		String uri = "/tasks";
		
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(taskToSaveAsParent))).andReturn();
		
		JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString());
		List<String> returnedValue = jsonNode.get("result").findValuesAsText("parent");
		
		Map<String, Object> map = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
		assertNotNull(map);
		assertNotNull(returnedValue);
		assertTrue(returnedValue.size()==1);
		assertTrue(returnedValue.get(0) == "true");
		
		//taskIdToVerify =returnedValue.get(0) ;
	}
	
	@Test
	public void verifyUpdateTask() throws Exception{
		String uriGet = "/tasks/";
		List<String> returnedValuePost = null;
		String resultText = null;
		
		TaskDto taskToUpdate = new TaskDto();
		taskToUpdate.setTaskName("testTaskName");
		taskToUpdate.setTaskId(Integer.valueOf(taskIdToVerify));
		
		
		
		MvcResult resultPost = mockMvc.perform(MockMvcRequestBuilders.put(uriGet+taskIdToVerify)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(taskToUpdate))).andReturn();
		
		JsonNode jsonNodePost =  mapper.readTree(resultPost.getResponse().getContentAsString());
		JsonNode jsonNodeResultText =  mapper.readTree(resultPost.getResponse().getContentAsString()).get("message");
		
		returnedValuePost = jsonNodePost.get("result").findValuesAsText("taskName");		
		resultText = jsonNodeResultText.asText();

		
		assertNotNull(returnedValuePost);
		assertNotNull(resultText);
		assertEquals("Task updated successfully.", resultText);
		assertEquals("testTaskName", returnedValuePost.get(0));
		assertEquals(taskIdToVerify, jsonNodePost.get("result").findValuesAsText("taskId").get(0));
	}
	
	@Test
	public void verifyTaskGetById() throws Exception{
		String uri = "/tasks/";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri+taskIdToVerify).accept(MediaType.APPLICATION_JSON_VALUE))
		.andReturn();
		
		JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString()).get("result");
		List<String> returnedValue = jsonNode.findValuesAsText("taskId");
		
		assertNotNull(jsonNode);
		assertEquals(taskIdToVerify,returnedValue.get(0));
	}
	
	
	@Test
	public void verifyTaskGetByProjectName() throws Exception{
		String uri = "/tasks/fetch/";
		String projectName=mockProjectName;
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri+projectName).accept(MediaType.APPLICATION_JSON_VALUE))
		.andReturn();
		
		JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString());
		List<String> returnedValue = jsonNode.get("result").findValuesAsText("taskName");
		
		Map<String, Object> map = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
		assertNotNull(map);
		assertTrue(returnedValue.size()>0);
	}
	
	@Test
	public void verifyTaskGetAllTasks() throws Exception{
		String uri = "/tasks";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
		.andReturn();
		
		JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString());
		List<String> returnedValue = jsonNode.get("result").findValuesAsText("taskName");
		
		Map<String, Object> map = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
		assertNotNull(map);
		assertTrue(returnedValue.size()>0);
	}
	
	
	@Test
	public void verifyParentTaskFetch() throws Exception{
		String uri = "/tasks/scope";
		String resultFromCall = null;
				
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		
		JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString()).get("message");
		resultFromCall = jsonNode.asText();
		
		assertNotNull(resultFromCall);
		assertEquals("All parent tasks retreived successfully.", resultFromCall);
	}
	
	@Test
	public void verifyUpdateToComplete() throws Exception{
		String uri = "/tasks/complete/";
		String resultFromCall = null;
		
		TaskDto taskToUpdate = new TaskDto();
				
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri+taskIdToVerify).contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(taskToUpdate))).andReturn();
				
		JsonNode jsonNode =  mapper.readTree(result.getResponse().getContentAsString()).get("message");
		List<String> jsonNodeEntity =  mapper.readTree(result.getResponse().getContentAsString()).get("result").findValuesAsText("status");
		resultFromCall = jsonNode.asText();
		
		assertNotNull(resultFromCall);
		assertEquals("Complete", jsonNodeEntity.get(0));
		assertEquals("Task completed successfully.", resultFromCall);
	}
	
	@After
	public void tearDown() throws Exception {
		String uri = "/tasks/";
		mockMvc.perform(MockMvcRequestBuilders.delete(uri+taskIdToVerify).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();		
		
	}
	
	

}
