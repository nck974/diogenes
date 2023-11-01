package dev.nichoko.diogenes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import dev.nichoko.diogenes.mock.UserAuthenticationMock;
import dev.nichoko.diogenes.model.UserAuthentication;
import dev.nichoko.diogenes.utils.JsonProcessor;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@TestPropertySource(properties = "security.disable-security=false")
@TestPropertySource(properties = "diogenes.security.enabled=true")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Flyway flyway;

    @MockBean
    private AuthenticationManager authenticationManager;

    // @MockBean
    // private JwtUtils jwtUtils;

    /*
     * Clean up the database before each test
     */
    @BeforeEach
    public void cleanUp() {
        flyway.clean();
        flyway.migrate();
    }

    /**
     * Verify that invalid credentials yield to a 401
     *
     * @throws Exception
     */
    @Test
    void testAuthenticateFailure() throws Exception {
        UserAuthentication userAuthentication = UserAuthenticationMock.getMockUserAuthentication();
        this.mockMvc.perform(
                post("/authenticate")
                        .content(JsonProcessor.stringifyClass(userAuthentication))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Verify that is possible to authenticate with valid credentials
     *
     * @throws Exception
     */
    @Test
    void testAuthenticateSuccess() throws Exception {
        // Mock the behavior of authenticationManager
        UserDetails userDetails = mock(UserDetails.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, "password"));

        UserAuthentication userAuthentication = UserAuthenticationMock.getMockUserAuthentication();
        this.mockMvc.perform(
                post("/authenticate")
                        .content(JsonProcessor.stringifyClass(userAuthentication))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    /**
     * Verify it is not possible to access an authenticated endpoint
     *
     * @throws Exception
     */
    @Test
    void testRestrictedAccessToResourceFailure() throws Exception {
        this.mockMvc.perform(
                get("/api/v1/item/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Verify it is possible to access an authenticated endpoint with valid token
     *
     * @throws Exception
     */
    @Test
    void testRestrictedAccessToResourceSuccess() throws Exception {

        // Mock the behavior of authenticationManager to return the userDetails with
        // authorities
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User("test", "test", authorities);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, "test", userDetails.getAuthorities()));

        // Send authentication
        UserAuthentication userAuthentication = UserAuthenticationMock.getMockUserAuthentication();
        String token = this.mockMvc.perform(
                post("/authenticate")
                        .content(JsonProcessor.stringifyClass(userAuthentication))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse().getContentAsString();
        this.mockMvc.perform(
                get("/api/v1/item/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Verify it is not possible to access an authenticated endpoint without valid
     * Bearer prefix
     *
     * @throws Exception
     */
    @Test
    void testRestrictedAccessToResourceInvalidTokenPrefix() throws Exception {

        // Mock the behavior of authenticationManager to return the userDetails with
        // authorities
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User("test", "test", authorities);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, "test", userDetails.getAuthorities()));

        // Send authentication
        UserAuthentication userAuthentication = UserAuthenticationMock.getMockUserAuthentication();
        String token = this.mockMvc.perform(
                post("/authenticate")
                        .content(JsonProcessor.stringifyClass(userAuthentication))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse().getContentAsString();
        this.mockMvc.perform(
                get("/api/v1/item/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "XXXX " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}
