package com.xworkz.swiggyreact.restcontroller;

import com.xworkz.swiggyreact.dto.SaveFormDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:1234")
public class SwiggyFormRestController {

    public SwiggyFormRestController()
    {
        System.out.println("SwiggyFormRestController constructor");
    }

    @PostMapping("/save")
    public void save(@RequestBody SaveFormDTO dto)
    {
        System.out.println("save method in RestController");
        System.out.println(dto);
    }
}
