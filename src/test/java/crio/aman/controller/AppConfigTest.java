package crio.aman.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crio.aman.entity.User;
import crio.aman.service.IUserService;
import crio.aman.service.impl.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Array.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ExtendWith(MockitoExtension.class)
class AppConfigTest {

    @Autowired
    private MockMvc mockMvc;                 // MockMvc is used to call hit the api calls

    @MockBean
    private IUserService userService;

    private User user;

    @Autowired
    private ObjectMapper objectMapper;      // convert object into json and its vice versa

    List<User> userList1 = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userList1.add(new User("1", "Aman", 86, null));
        userList1.add(new User("2", "Deepak", 80, null));
        userList1.add(new User("3", "Devesh", 45, null));
        userList1.add(new User("4", "Dipika", 20, null));
        user = User.builder()
                .userName("deepak sharma")
                .id("abc")
                .score(83)
                .build();
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserById() throws Exception {
        // mocking service
        Mockito.when(userService.getUserById("abc")).thenReturn(user);
        // making the request
        MockHttpServletRequestBuilder requestObject = MockMvcRequestBuilders
                .get("/contest/users/{userId}", "abc");
        // hit the request
        ResultActions resultAction = mockMvc.perform(requestObject);
        ResultActions resultActions;
        resultActions = resultAction
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("deepak sharma"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value("83"))
                .andExpect(status().isOk());
        String response = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }

    @Test
    void createUser() throws Exception {


        //  to hit the rest api of post method we need request object?
        // make the post request using the MockMvcRequestBuilders

        // 0. to define the functionality of dependencies
    /*    Mockito.when(userService.createUser(user)).thenReturn(user);


        //1. make the request object
        MockHttpServletRequestBuilder requestObj = MockMvcRequestBuilders.post("/contest/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));


        //2. to perform the request, or we say to hit the request
        ResultActions resultActions = mockMvc.perform(requestObj);


        //3. to check the desired result
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("deepak sharma"))
                // we can add as much addExpect() as we want
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value("83"))
                .andExpect(MockMvcResultMatchers.status().isCreated());


        // 4. finally return the response object

        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);*/


//        -------------------------------------------------------------------------------------------------------


//        // mock the functionality of service layer to create the user
//        Mockito.when(userService.createUser(user)).thenReturn(user);
//
//        // hitting the rest api  http:localhost:8080//contest//users
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/contest/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(user))
//        ).andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("deepak sharma"))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//
//                .andReturn();
//
//        System.out.println(  mvcResult.getResponse().getContentAsString());


//        --------------------------------------------------------------------------------------------------------

        // mock the functionality of service layer to create the user
        Mockito.when(userService.createUser(user)).thenReturn(user);

        // hitting the rest api  http:localhost:8080//contest//users
        String content = mockMvc.perform(post("/contest/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                ).andExpect(jsonPath("$.userName").value("deepak sharma"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        System.out.println(content);


    }

//    @Test
//    void updateUserById() throws Exception {
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
//                        .put("/contest/users/{userId}/{score}", "abc", 56)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // Then
//        String responseBody = result.getResponse().getContentAsString();
//        System.out.println(responseBody);
//    }


    @Test
    void updateUserById() throws Exception {
        // Given
//        User user = new User(); // Initialize the user object
        Mockito.when(userService.updateUserById("abc", 56)).thenReturn(user); // Assuming "abc" is the userId and 56 is the new score

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/contest/users/{userId}/{score}", "abc", 56)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseBody = result.getResponse().getContentAsString();
        System.out.println(responseBody);
    }

    @Test
    void deleteUserById() throws Exception {
        // mocking the service
        Mockito.when(userService.deleteUserById("abc")).thenReturn("user deleted!");
        // making request
        String content = mockMvc.perform(delete("/contest/users/{userId}", "abc"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(content);

    }
}