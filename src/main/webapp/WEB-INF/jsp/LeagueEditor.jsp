<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="/main.css">
<title>Insert title here</title>
</head>
<body>

<div class="row">
    <div class="column">
        <table border="1">
            <tr id="captions">
                <td>League Table</td>
                <td>Match Results</td>
            </tr>
            <tr>
                <td>
                    <table>
                        <tr class="titles">
                            <td class="name">Teams</td>
                            <td>PTS</td>
                            <td>P</td>
                            <td>W</td>
                            <td>D</td>
                            <td>L</td>
                            <td>GD</td>
                        </tr>
                        <c:forEach var="team" items="${teams}">
                        <tr>
                            <td class="name">${team.teamName}</td>
                            <td>${team.points}</td>
                            <td>${team.played}</td>
                            <td>${team.wins}</td>
                            <td>${team.draws}</td>
                            <td>${team.losses}</td>
                            <td>${team.goalDifference}</td>
                        </tr>
                        </c:forEach>
                    </table>
                </td>
                <td>
                    <table>
                        <tr class="subtitle">
                            <td style="text-align:center">Week Match Result</td>
                        </tr>
                        <tr>
                            <td>team1 2 - 3 team2</td>
                        </tr>
                        <tr>
                            <td>team3 1 - 0 team4</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td><button type="submit" id="all">Play All</button></td>
                <form action="/" method="POST">
                    <td><button type="submit" id="next">Next Week</button></td>
                </form>
            </tr>
        </table>
    </div>
    <div class="column" id="predictions">
        <table border="1">
            <caption class="subtitle">${week}${suffix} Week Predictions of Championship</caption>
            <c:forEach var="team" items="${teams}">
                <tr>
                    <td class="name">${team.teamName}</td>
                    <td>%55</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<div class="row">
    <table>
        <tr>

        </tr>
    </table>
</div>

</body>
</html>