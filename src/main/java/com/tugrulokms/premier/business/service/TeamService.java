package com.tugrulokms.premier.business.service;

import com.tugrulokms.premier.business.dto.TeamDto;

public interface TeamService {

    void update(TeamDto team);

    int advanceWeek(int weekCount);

    Iterable<TeamDto> findAll();
}
