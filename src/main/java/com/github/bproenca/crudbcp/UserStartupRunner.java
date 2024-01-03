package com.github.bproenca.crudbcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("xdev")
public class UserStartupRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;
    private Logger log = LoggerFactory.getLogger(UserStartupRunner.class);

    @Override
    public void run(String... args) throws Exception {
        //String url = "https://jsonplaceholder.typicode.com/users";
        String baseURL = "https://dummyjson.com/users";
        userService.truncateUser();
		RestTemplate restTemplate = new RestTemplate();
        for (int i = 1; i < 101; i++) {
            String url = baseURL + "/" + i;
            User user = restTemplate.getForObject(url, User.class);
            if (user != null) {
                user.setId(null);
                log.info(">> loading user {} ", user);
                userService.createUser(user);
            } else {
                log.error(">> placeholder didn't work {}", url);
            }
        }
    }
 	   
}
