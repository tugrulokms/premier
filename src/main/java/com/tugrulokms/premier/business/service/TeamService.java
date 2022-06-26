package com.tugrulokms.premier.business.service;

import com.tugrulokms.premier.business.dto.Fixture;
import com.tugrulokms.premier.business.dto.TeamDto;

import java.util.List;

public interface TeamService {

    void update(TeamDto team);

    int advanceWeek(Fixture fixture, int weekCount);

    Fixture manageFixture(List<TeamDto> teams);

    List<TeamDto> findAll();
}
