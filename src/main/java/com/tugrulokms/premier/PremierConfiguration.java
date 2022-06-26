package com.tugrulokms.premier;

import com.tugrulokms.premier.data.entity.Team;
import com.tugrulokms.premier.data.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class PremierConfiguration {

    @Autowired
    private TeamRepository teamRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void resetDatabase() {
        Iterable<Team> teams = teamRepository.findAll();

        for(Team team: teams) {
            team.setPoints(0);
            team.setPlayed(0);
            team.setWins(0);
            team.setDraws(0);
            team.setLosses(0);
            team.setGoalDifference(0);
        }
    }
}
