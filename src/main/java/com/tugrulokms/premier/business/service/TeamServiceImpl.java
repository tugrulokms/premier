package com.tugrulokms.premier.business.service;

import com.tugrulokms.premier.business.dto.*;
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
    public int advanceWeek(Fixture fixture, int weekCount) {

        Random random = new Random();

        List<TeamDto> teams = findAll();

        Map<TeamDto, Integer> game = new HashMap<>();

        for(int i = 0; i < 2; i++) {

            Match match = fixture.getWeeks().get(weekCount).getMatches().get(i);

            TeamDto homeTeam = match.getHomeTeam();
            TeamDto awayTeam = match.getAwayTeam();

            game.put(homeTeam, random.nextInt(8));
            game.put(awayTeam, random.nextInt(8));

            int homeScore = game.get(homeTeam);
            int awayScore = game.get(awayTeam);

            homeTeam.setPlayed(homeTeam.getPlayed() + 1);
            awayTeam.setPlayed(awayTeam.getPlayed() + 1);

            if(homeScore > awayScore) {

                homeTeam.setWins(homeTeam.getWins() + 1);
                homeTeam.setPoints(homeTeam.getPoints() + 3);

                awayTeam.setLosses(awayTeam.getLosses() + 1);

            } else if(homeScore < awayScore) {

                awayTeam.setWins(awayTeam.getWins() + 1);
                awayTeam.setPoints(awayTeam.getPoints() + 3);

                homeTeam.setLosses(homeTeam.getLosses() + 1);

            } else if(game.get(homeTeam).equals(game.get(awayTeam))) {

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

    public Fixture manageFixture(List<TeamDto> teams) {

        Fixture fixture = new Fixture();
        List<TeamDto> tempList = teams;

        List<Week> matchesFirstHalf = arrangeMatches(tempList);
        List<Week> matchesSecondHalf = matchesFirstHalf;

        List<Week> allWeeks = new ArrayList<>();
        allWeeks.addAll(matchesFirstHalf);
        allWeeks.addAll(matchesSecondHalf);

        fixture.setWeeks(allWeeks);

        return fixture;
    }

    private List<Week> arrangeMatches(List<TeamDto> teams) {

        Random random = new Random();

        List<Week> weeks = new ArrayList<>();
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Week week = new Week();
            if(weeks.isEmpty()) {
                while(teams.size() > 0) {
                    Match match = new Match();
                    int rand = random.nextInt(teams.size() - 1);

                    TeamDto homeTeam = teams.get(rand);
                    match.setHomeTeam(homeTeam);
                    teams.remove(homeTeam);

                    int rand2;

                    if(teams.size() - 1 == 0)
                        rand2 = 0;
                    else
                        rand2 = random.nextInt(teams.size() - 1);

                    TeamDto awayTeam = teams.get(rand2);
                    match.setAwayTeam(awayTeam);
                    teams.remove(awayTeam);

                    matches.add(match);
                    System.out.println(match.getHomeTeam().getTeamName() + " vs " + match.getAwayTeam().getTeamName());
                }

            } else {
                Match match = new Match();

                TeamDto prevMatch1Team1 = matches.get(i-1).getHomeTeam();
                TeamDto prevMatch2Team2 = matches.get(i).getAwayTeam();
                match.setHomeTeam(prevMatch2Team2);
                match.setAwayTeam(prevMatch1Team1);

                matches.add(match);

            }
            week.setMatches(matches);
            weeks.add(week);
        }

        return weeks;
    }
}