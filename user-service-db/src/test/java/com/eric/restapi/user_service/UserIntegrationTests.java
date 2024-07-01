package com.eric.restapi.user_service;

import com.eric.restapi.user_service.model.User;
import com.eric.restapi.user_service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserIntegrationTests {

	private static MockHttpServletRequest request;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${sql.script.create.user}")
	private String sqlCreateUser;

	@Value("${sql.script.delete.user}")
	private String sqlDeleteUser;

	@Value("${sql.script.get.user}")
	private String sqlGetUser;

	@Value("${sql.script.count.users}")
	private String sqlCountUsers;

	private Integer currentNbOfUsers;

	private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;
	private static final String USER_URL = "/api/v1/users";
	private static final Long BAD_USER_ID = 999999999L;

	@BeforeEach
	void setupBeforeEach() {
		currentNbOfUsers = jdbc.queryForObject(sqlCountUsers, Integer.class);
		System.out.println("currentNbOfUsers: " + currentNbOfUsers);
		//jdbc.execute(sqlCreateUser);
	}

	@AfterEach
	void cleanupAfterEach() {
		System.out.println(jdbc.queryForObject(sqlCountUsers, Integer.class));;
	}

	@DisplayName("Get users")
	@Test
	void getUsersTest() throws Exception {
		mockmvc.perform(get(USER_URL))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(currentNbOfUsers)));
	}

	@DisplayName("Create user")
	@Test
	void createUserTest() throws Exception {
		String userEmail = "alain.prost@gmail.com";
		String userFirstName = "Alain";
		String userLastName = "Prost";
		User verifyUser = createAndGetUserByEmail(new User(userFirstName, userLastName, userEmail));
		System.out.println("verifyUser: " + verifyUser.toString());
		assertNotNull(verifyUser, "We should find the user");
		assertEquals(userEmail, verifyUser.getEmail());
		assertEquals(userFirstName, verifyUser.getFirstName());
		assertEquals(userLastName, verifyUser.getLastName());
	}

	@DisplayName("Get user by Id")
	@Test
	void getUserByIdTest() throws Exception {
		String firstName = "Joseph";
		String lastName = "Corato";
		String email = "joseph.corato@gmail.com";
		User user = createAndGetUserByEmail(
				new User(firstName, lastName, email));
		System.out.println("user: " + user.toString());
		mockmvc.perform(get(USER_URL + "/{id}", user.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(user.getId()))
				.andExpect(jsonPath("$.first_name").value(firstName))
				.andExpect(jsonPath("$.last_name").value(lastName))
				.andExpect(jsonPath("$.email").value(email));
	}

	@DisplayName("Get user does not exist")
	@Test
	void getUserDoesNotExistTest() throws Exception {
		mockmvc.perform(get(USER_URL + "/{id}", BAD_USER_ID))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status_code", is(404)))
				.andExpect(jsonPath("$.message", is("User not found for id: " + BAD_USER_ID + ". Cannot get")));
	}

	@DisplayName("Delete user")
	@Test
	void deleteUserByIdTest() throws Exception {
		User user = createAndGetUserByEmail(
				new User("Jackson", "Brown", "jackson.brown@gmail.com"));
		System.out.println("user: " + user.toString());
		mockmvc.perform(delete(USER_URL + "/{id}", user.getId())).andExpect(status().isOk());
		assertFalse(userRepository.findUserById(user.getId()).isPresent());
	}

	@DisplayName("Delete user does not exist")
	@Test
	void deleteUserDoesNotExistTest() throws Exception {
		mockmvc.perform(delete(USER_URL + "/{id}", BAD_USER_ID))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status_code", is(404)))
				.andExpect(jsonPath("$.message", is("User not found for id: " + BAD_USER_ID + ". Cannot delete")));
	}

	@DisplayName("Patch user")
	@Test
	void patchUserTest() throws Exception {
		User user = createAndGetUserByEmail(
				new User("JosephToBePatched", "Corato", "josephToBePatched.corato@gmail.com"));
		System.out.println("userToBePatched: " + user.toString());
		String newFirstName = "JosephPatched";
		User newUser = new User(newFirstName, user.getLastName(), "josephPatched@gmail.com");
		mockmvc.perform(patch(USER_URL + "/{id}", user.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.first_name").value(newFirstName));
		User patchedUser = userRepository.findUserById(user.getId()).orElseThrow();
		assertEquals(patchedUser.getFirstName(), newFirstName);
	}

	@DisplayName("Put user that exists")
	@Test
	void putExistingUserTest() throws Exception {
		User user = createAndGetUserByEmail(
				new User("arthur", "Corato", "arthur.corato@gmail.com"));
		System.out.println("user: " + user.toString());
		String newFirstName = "corentin";
		String newEmail = "corentin.corato@gmail.com";
		User newUser = new User(newFirstName, user.getLastName(), newEmail);
		mockmvc.perform(put(USER_URL + "/{id}", user.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id").value(user.getId()))
				.andExpect(jsonPath("$.first_name").value(newFirstName))
				.andExpect(jsonPath("$.email").value(newEmail));
		User updatedUser = userRepository.findUserById(user.getId()).orElseThrow();
		assertEquals(updatedUser.getFirstName(), newFirstName);
		assertEquals(updatedUser.getLastName(), user.getLastName());
		assertEquals(updatedUser.getEmail(), newEmail);
	}

	@DisplayName("Put user that does not exist")
	@Test
	void putNotExistingUserTest() throws Exception {
		String firstName = "martin";
		String lastName = "dufour";
		String email = "martin.dufour@gmail.com";
		User newUser = new User(firstName, lastName, email);
		mockmvc.perform(put(USER_URL + "/{id}", BAD_USER_ID).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id").value(not(BAD_USER_ID)))
				.andExpect(jsonPath("$.first_name").value(firstName))
				.andExpect(jsonPath("$.last_name").value(lastName))
				.andExpect(jsonPath("$.email").value(email));
		User updatedUser = userRepository.findUserByEmail(email).orElseThrow();
		System.out.println("user: " + updatedUser.toString());
		assertNotEquals(updatedUser.getId(), BAD_USER_ID);
		assertEquals(updatedUser.getFirstName(), firstName);
		assertEquals(updatedUser.getLastName(), lastName);
		assertEquals(updatedUser.getEmail(), email);
	}

	private User createAndGetUserByEmail(User user) throws Exception {
		mockmvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.first_name").value(user.getFirstName()))
				.andExpect(jsonPath("$.last_name").value(user.getLastName()))
				.andExpect(jsonPath("$.email").value(user.getEmail()));
		return userRepository.findUserByEmail(user.getEmail()).orElseThrow();
	}

}
