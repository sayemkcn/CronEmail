package net.toracode.app.controller;

import net.toracode.app.domain.User;
import net.toracode.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class HelloController {

    private static Long ID = 0l;

    @Autowired
    private UserRepository userRepo;

    @Scheduled(fixedDelay = 2000)
    private void cronService() {
        try {
            URL url = new URL("http://localhost:8080/email/send?id=" + ID);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            if (connection.getResponseCode() == 200) {
                ID++;
            }
            System.out.println(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String sayHello() {
        return "Hello World.";
    }

    @RequestMapping(value = "/email/send", method = RequestMethod.POST)
    private ResponseEntity sendEmail(@RequestParam("id") Long id) {
        this.sendEmailAlreadyNotSent(id);
        return null;
    }

    private void sendEmailAlreadyNotSent(Long id) {
        User user = this.userRepo.findOne(id);
        if (user != null && !user.isAlreadySent()) {
            user.setAlreadySent(true);
            userRepo.save(user);
            System.out.print("ID: " + ID + "\t" + "Email Sent to:\t" + user.getEmail() + "\tCODE: ");
        } else {
            sendEmailAlreadyNotSent(++ID);
        }
    }

}
