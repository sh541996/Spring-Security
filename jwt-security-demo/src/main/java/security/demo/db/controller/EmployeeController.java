package security.demo.db.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	
    @RequestMapping({ "/greeting" })
    public String welcomePage() {
        return "Welcome!";
    }
}