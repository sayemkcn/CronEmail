package net.toracode.app.controller;

import net.toracode.app.domain.User;
import net.toracode.app.repository.UserRepository;
import net.toracode.app.service.MailService;
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
    private static Long COUNT = 0l;

    private String subject = "অটো আযান-রমজান ক্যালেন্ডার অ্যাপ সম্পর্কে আমাদের জানান ";
    private String message = "আসসালামু আলাইকুম।\n" +
            "ভালো আছেন আশা করি। আমি রমজান ক্যালেন্ডার মোবাইল অ্যাপের ডেভেলপার, আপনাদের কাছে অ্যাপটি কেমন লেগেছে জানতে চাচ্ছি। সেই সাথে আর কি কি সুবিধা থাকলে অ্যাপটি আপনার আর বেশী উপকারে আসত সে সম্পর্কে আমাদের বলুন। আমরা আপনার সাজেশন ভাল লাগলে সেটা নিয়ে অবশ্যই কাজ করব। \n" +
            "\n" +
            "সেই সাথে আমাদের আর অনেক সুন্দর সুন্দর অ্যাপ ডাউনলোড করতে পারেন। \n" +
            "এই যেমন বাংলাদেশের সকল মোবাইল ফোনের সর্বশেষ দাম জানতে BD Mobile Phone Price অ্যাপটি ডাউনলোড করতে পারেন এখান থেকে \n" +
            "https://play.google.com/store/apps/details?id=net.toracode.mobilepricebd\n" +
            "\n" +
            "কিংবা বাসা খুঁজতে খুঁজতে হয়রান হয়ে গেলে BD To-Let অ্যাপের মাধ্যমেই যোগাযোগ করতে পারেন সকল বাড়িওয়ালার সাথে। কিংবা আপনি বাড়িওয়ালা হলে নিজেই টুলেট অ্যাড পোস্ট করতে পারেন এর মাধ্যমে। অ্যাপটি ডাউনলোড করতে পারেন এখান থেকে https://play.google.com/store/apps/details?id=sayem.picosoft.bdtolet\n" +
            "\n" +
            "আমাদের সাথে থাকার জন্য আপনাকে ধন্যবাদ। আশা করছি আপনার মূল্যবান সাজেশন এবং সমর্থন দিয়ে আমাদের সাথে থাকবেন। \n" +
            "\n" +
            "ধন্যবাদান্তে,\n" +
            "Sayem Hossain\n" +
            "Developer, ToraCode\n" +
            "\n";

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MailService mailService;

    @Scheduled(fixedDelay = 1000)
    private void cronService() {

        try {
            URL url = new URL("http://localhost:8080/email/send?id=" + ID);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            if (connection.getResponseCode() == 200) {
                ID++;
            }
            System.out.println("\t\t" + connection.getResponseCode());
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
            try {
                this.mailService.sendEmail(user.getEmail(), this.subject, this.message);
                user.setAlreadySent(true);
                userRepo.save(user);
                COUNT++;
                System.out.print("ID: " + ID + "\tTOTAL: " + COUNT + "\t" + "Email Sent to:\t" + user.getEmail());
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            sendEmailAlreadyNotSent(++ID);
        }


    }

}
