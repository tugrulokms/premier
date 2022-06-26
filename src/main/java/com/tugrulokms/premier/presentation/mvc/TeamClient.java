package com.tugrulokms.premier.presentation.mvc;

import com.tugrulokms.premier.business.dto.TeamDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class TeamClient {

    @GetMapping("/")
    public String findAllTeams(Model model) {

        String url = "http://localhost:8080/api/teams";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<TeamDto>> response =
                restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<TeamDto>>() {});

        List<TeamDto> teams = response.getBody();

        model.addAttribute("teams", teams);

        return "LeagueEditor";
    }

    @PostMapping("/")
    public String advanceWeek(Model model, Integer weekCount) {

        int week;

        if(weekCount == null)
            week = 0;
        else
            week = weekCount;

        System.out.println(week);

        model.addAttribute("week", week);

        String url = "http://localhost:8080/api/advance/" + week;

        RestTemplate restTemplate = new RestTemplate();

        String weekTime = "" + restTemplate.postForObject(url, week, Integer.class);

        String lastString = String.valueOf(weekTime.charAt(weekTime.length() - 1));

        String suffix = "";

        if(lastString == "1") {
            suffix = "st";
        } else if (lastString == "2") {
            suffix = "nd";
        } else if (lastString == "3") {
            suffix += "rd";
        } else {
            suffix += "th";
        }

        model.addAttribute("suffix", suffix);

        return "LeagueEditor";
    }
}
