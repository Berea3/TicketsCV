package com.tickets.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.email.Email;
import com.tickets.entities.generator.Generator;
import com.tickets.security.entities.User;
import com.tickets.security.entities.UserRepository;
import com.tickets.security.entities.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SecurityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Email email;

    @GetMapping("/security")
    public String security() throws JsonProcessingException
    {
        Map<String, Object> responseBody=new HashMap<>();
        responseBody.put("loggedin","yes");

        ObjectMapper mapper=new ObjectMapper();
        String json=mapper.writeValueAsString(responseBody);
        return json;
    }

    @GetMapping("/security/loggedin")
    public HashMap<String,Boolean> loggedin()
    {
        HashMap<String,Boolean> map=new HashMap<>();
        map.put("loggedin",true);
        return map;
    }

    @GetMapping("/security/all")
    public String securityAll()
    {
        return "loggedin normal account";
    }

    @GetMapping("/security/admin")
    public String securityAdmin()
    {
        return "loggedin admin";
    }

    @GetMapping("/security/unauthenticated")
    public String securityUnauthenticated()
    {
        System.out.println("proba");
        return "not logged in";
    }

    @PostMapping("/security/sign-up")
    public String securitySignUp(@RequestBody User user)
    {
        if (!UserService.userExists(user.getEmail(),userRepository))
        {
            user.setId(Generator.generateId());
            String salt=BCrypt.gensalt();
            user.setPassword(BCrypt.hashpw(user.getPassword(),salt));
            userRepository.save(user);
            email.send(user.getEmail(),"Tickets","Your account was created");
            return "user added";
        }
        else
        {
            return "user already exists";
        }
    }

    @GetMapping("/security/getUser")
    public User getUser() throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        User user=objectMapper.readValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(),User.class);
        user=userRepository.findById(user.getId()).get();
        return user;
    }
}
