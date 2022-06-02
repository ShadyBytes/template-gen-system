package com.timur.templategensystem;

import com.spire.doc.*;
import com.spire.doc.documents.Paragraph;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

    @GetMapping("/chart")
    public String index(Model model) {
        model.addAttribute("chartData", getChartData());
        return "chart";
    }

    private List<List<Object>> getChartData() {
        Random random = new Random();
        return List.of(
                List.of("Mushrooms", random.nextInt(5)),
                List.of("Onions", random.nextInt(5)),
                List.of("Olives", random.nextInt(5)),
                List.of("Zucchini", random.nextInt(5)),
                List.of("Pepperoni", random.nextInt(5))
        );
    }
    @PostMapping("/arr")
    String arrayController(@RequestParam(value="array") List<Integer> myArray) {
        return "healthcheck";
    }

    @PostMapping("/docx")
    String docx(@RequestParam String templateName, @RequestBody Cisco1941Template cisco1941Template) {
        Document document = new Document(/*System.getProperty("user.dir") +*/ templateName);

        Section section = document.getSections().get(0);
        Table table = section.getTables().get(0);
        Map<String, String> map = new HashMap<>();

        map.put("id", cisco1941Template.id.toString());
        map.put("buyer", cisco1941Template.buyer.toString());
        map.put("quantity", String.valueOf(cisco1941Template.quantity));
        map.put("orderAddress", cisco1941Template.orderAddress.toString());

        replaceTextinTable(map, table);

        document.saveToFile("CreateByReplacingPlaceholder.docx", FileFormat.Docx_2013);
        return "docxResults";
    }

    static void replaceTextinTable(Map<String, String> map, Table table) {
        for (TableRow row : (Iterable<TableRow>) table.getRows()) {
            for (TableCell cell : (Iterable<TableCell>) row.getCells()) {
                for (Paragraph para : (Iterable<Paragraph>) cell.getParagraphs()) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        para.replace("${" + entry.getKey() + "}", entry.getValue(), false, true);
                    }
                }
            }
        }
    }
}
