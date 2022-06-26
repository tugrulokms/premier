package com.tugrulokms.premier.business.service;

import com.tugrulokms.premier.business.dto.LeagueDto;
import com.tugrulokms.premier.business.dto.TeamDto;
import com.tugrulokms.premier.data.entity.League;
import com.tugrulokms.premier.data.entity.Team;
import com.tugrulokms.premier.data.repository.LeagueRepository;
import com.tugrulokms.premier.data.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Override
    public void update(TeamDto teamDto) {
        Team team = toEntity(teamDto);
        teamRepository.save(team);
    }

    @Override
    public int advanceWeek(int weekCount) {
        Random random = new Random();

        List<TeamDto> teams = findAll();

        while(teams.size() > 1) {

            List<TeamDto> opposingTeams = new ArrayList<>();

            for(int i = 0; i < 2; i++) {
                int order = random.nextInt(teams.size()-1);
                opposingTeams.add(teams.get(order));
                System.out.println(teams.size() + ": " + teams.get(order).getTeamName());
                teams.remove(order);
            }

            Map<TeamDto, Integer> match = new HashMap<>();
            TeamDto homeTeam = opposingTeams.get(0);
            TeamDto awayTeam = opposingTeams.get(1);

            match.put(homeTeam, random.nextInt(8));
            match.put(awayTeam, random.nextInt(8));

            int homeScore = match.get(homeTeam);
            int awayScore = match.get(awayTeam);

            for(TeamDto team: opposingTeams)
                team.setPlayed(team.getPlayed() + 1);

            if(homeScore > awayScore) {

                homeTeam.setWins(homeTeam.getWins() + 1);
                homeTeam.setPoints(homeTeam.getPoints() + 3);

                awayTeam.setLosses(awayTeam.getLosses() + 1);

            } else if(homeScore < awayScore) {

                awayTeam.setWins(awayTeam.getWins() + 1);
                awayTeam.setPoints(awayTeam.getPoints() + 3);

                homeTeam.setLosses(homeTeam.getLosses() + 1);

            } else if(match.get(homeTeam) == match.get(awayTeam)) {
                homeTeam.setDraws(homeTeam.getDraws() + 1);
                awayTeam.setDraws(awayTeam.getDraws() + 1);

                homeTeam.setPoints(homeTeam.getPoints() + 1);
                awayTeam.setPoints(awayTeam.getPoints() + 1);
            }

            homeTeam.setGoalDifference(homeTeam.getGoalDifference() + (homeScore - awayScore));
            awayTeam.setGoalDifference(awayTeam.getGoalDifference() + (awayScore - homeScore));

            update(homeTeam);
            update(awayTeam);
        }

        return weekCount + 1;
    }

    @Override
    public List<TeamDto> findAll() {
        return toDtos(teamRepository.findAll());
    }

    private TeamDto toDto(Team team) {

        TeamDto teamDto = new TeamDto();

        teamDto.setTeamId(team.getTeamId());
        teamDto.setTeamName(team.getTeamName());
        teamDto.setPoints(team.getPoints());
        teamDto.setPlayed(team.getPlayed());
        teamDto.setWins(team.getWins());
        teamDto.setDraws(team.getDraws());
        teamDto.setLosses(team.getLosses());
        teamDto.setGoalDifference(team.getGoalDifference());

        LeagueDto league = new LeagueDto();
        league.setLeagueId(team.getLeague().getLeagueId());
        league.setLeagueName(team.getLeague().getLeagueName());

        teamDto.setLeague(league);

        return teamDto;
    }

    private Team toEntity(TeamDto teamDto) {

        Team team = new Team();

        team.setTeamId(teamDto.getTeamId());
        team.setTeamName(teamDto.getTeamName());
        team.setPoints(teamDto.getPoints());
        team.setPlayed(teamDto.getPlayed());
        team.setWins(teamDto.getWins());
        team.setDraws(teamDto.getDraws());
        team.setLosses(teamDto.getLosses());
        team.setGoalDifference(teamDto.getGoalDifference());

        Optional<League> optional = leagueRepository.findById(teamDto.getLeague().getLeagueId());

        if(optional.isPresent()) {
            League league = optional.get();
            team.setLeague(league);
        }

        return team;
    }

    private List<TeamDto> toDtos(Iterable<Team> teams) {

        List<TeamDto> teamDtos = new ArrayList<>();

        for (Team team: teams) {
            teamDtos.add(toDto(team));
        }

        return teamDtos;
    }

}