package ru.zakharov.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.zakharov.project.domain.OurUrl;
import ru.zakharov.project.repos.OurUrlRepo;

import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    private OurUrlRepo urlRepo;

    public static final String HOST = "http://localhost:8080/";
    private static final String SYMBOLS = "qwertyuiopasdfghjklzxcvbnm1234567890";
    private static final int MAX_LENGTH = 5;

    @GetMapping("{token}")
    public void processShortUrl(@PathVariable String token, HttpServletResponse resp) {
        OurUrl url = urlRepo.findByToken(token);
        if (url != null) {
            url.setVisits(url.getVisits()+1);
            urlRepo.save(url);
            String longUrl = url.getLongUrl();
            resp.setHeader("Location", longUrl);
            resp.setStatus(302);
        }
        else {
            System.out.println("ОШИБОЧКА!");
            resp.setStatus(404);
        }
    }

    @GetMapping("/home")
    public String homePage() throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        System.out.println(InetAddress.getLocalHost());
        return "mainpage";
    }

    @PostMapping("/processData")
    public String processData(@RequestParam(name = "longUrl") String longUrl,
                              Model model) {
        OurUrl url = urlRepo.findByLongUrl(longUrl);
        if (url == null) {
            url = createNewUrl(longUrl);
        }
        model.addAttribute("urlObject", url);
        return "mainpage";
    }

    public OurUrl createNewUrl(String longUrl) {
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder("");
        for (int i = 0; i < MAX_LENGTH; i++) {
            shortUrl.append(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
        }
        if (urlRepo.findByToken(shortUrl.toString())!=null) {
            createNewUrl(longUrl);
        }
        OurUrl ourUrl = new OurUrl(shortUrl.toString(), longUrl);
        urlRepo.save(ourUrl);
        return ourUrl;
    }

}
