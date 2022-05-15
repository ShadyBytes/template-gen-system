package com.timur.templategensystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Controller
public class Controllers {
    @GetMapping("/healthcheck")
    String healthcheck(Model model) {
        model.addAttribute("directory", System.getProperty("user.dir"));
        return "healthcheck";
    }

    @GetMapping("/logs")
    String getFile(@RequestParam String filename, Model model) {
        Path filePath = Path.of(System.getProperty("user.dir") + "\\" + filename);
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream
                     = Files.lines(Paths.get(String.valueOf(filePath)), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileContent = contentBuilder.toString();

        model.addAttribute("log", fileContent);
        return "logs";
    }

    @PostMapping("/json")
    String json(@RequestBody ShopItem shopItem, Model model) {
        model.addAttribute("id", shopItem.getId());
        model.addAttribute("name", shopItem.getName());
        model.addAttribute("price", shopItem.getPrice());
        return "ShopItem";
    }
}
