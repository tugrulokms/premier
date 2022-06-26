package com.tugrulokms.premier.presentation.rest;

import com.tugrulokms.premier.PremierApplication;
import com.tugrulokms.premier.business.dto.Fixture;
import com.tugrulokms.premier.business.dto.Match;
import com.tugrulokms.premier.business.dto.TeamDto;
import com.tugrulokms.premier.business.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    private Fixture fixture;

    @PutMapping("/api/team")
    public void updateTeam(@RequestBody TeamDto team) {
        teamService.update(team);
    }

    @GetMapping("/api/teams")
    public List<TeamDto> findAll() {

        return teamService.findAll();
    }

    @PostMapping("/api/advance/{week}")
    public int advanceWeek(@PathVariable("week") int weekCount) {

        fixture = teamService.manageFixture(findAll());

        return teamService.advanceWeek(fixture, weekCount);
    }

}