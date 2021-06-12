package code.hackathon.unisubscribe.controllers;

import code.hackathon.unisubscribe.enums.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("api")
@RequiredArgsConstructor
public class CommonController {


    @GetMapping("/getCategories")
    public ResponseEntity<List<String>> allCategories(){
        List<String> categories = Stream.of(Category.values())
                .map(Category::name)
                .collect(Collectors.toList());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}
