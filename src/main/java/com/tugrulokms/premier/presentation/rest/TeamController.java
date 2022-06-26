package com.tugrulokms.premier.presentation.rest;

import com.tugrulokms.premier.business.dto.TeamDto;
import com.tugrulokms.premier.business.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PutMapping("/api/team")
    public void updateTeam(@RequestBody TeamDto team) {
        teamService.update(team);
    }

    @GetMapping("/api/teams")
    public Iterable<TeamDto> findAll() {

        return teamService.findAll();
    }

    @PostMapping("/api/advance/{week}")
    public int advanceWeek(@PathVariable("week") int weekCount) {

        return teamService.advanceWeek(weekCount);
    }
}