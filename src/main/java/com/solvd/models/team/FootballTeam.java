package com.solvd.models.team;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solvd.annotations.Info;
import com.solvd.exceptions.IncompleteTeamException;
import com.solvd.interfaces.Trackable;
import com.solvd.interfaces.Trainable;
import com.solvd.interfaces.functional_interface.EarningsCalculator;

@Info(author = "Anna Rybakova")
public class FootballTeam<T extends FootballPlayer> implements Trainable, Trackable, Comparable<FootballTeam> {
    private static final Logger logger = LoggerFactory.getLogger(FootballTeam.class);
    private String name;
    private Manager manager;
    private ArrayList<T> team = new ArrayList<T>();
    private int teamSkillBonus = 0;

    public FootballTeam(String name, Manager manager) {
        this.name = name;
        this.manager = manager;
    }

    public void addFootballPlayer(T player) {
        team.add(player);
    }

    public double getTeamSkill() {
        // int teamSkill = 0;
        return team.stream().mapToDouble(player -> player.getPlayerSkill()).sum() + teamSkillBonus;
        // for (T player : team) {
        // teamSkill += player.getPlayerSkill();
        // }
        // return teamSkill + teamSkillBonus;
    }

    @Override
    public void train(double score) {
        this.teamSkillBonus += score;
        logger.info(name + " won and increased team skill by " + score);
    }

    public void updatePlayersSkill(int score) {
        logger.info("Skills of all members of team " + name + " are increased by " + team.size() / score);
        // for (T member : team) {
        // member.updatePlayerSkill(team.size() / score);
        // }
        team.forEach(member -> member.updatePlayerSkill(team.size() / score));
    }

    public void updateManagerMoney(int score) {
        double baseScore = (double) team.size() / score;
        EarningsCalculator calculator = (s, m) -> s * 1.2;
        manager.earnMoney(baseScore, calculator);
    }

    public String getFootballTeamName() {
        return name;
    }

    public ArrayList<T> getTeamMembers() {
        return team;
    }

    public void setFootballTeamName(String name) {
        this.name = name;
    }

    public void setTeamMembers(ArrayList<T> team) {
        this.team = team;
    }

    public void validateTeam() {
        // boolean hasGoalkeeper = false;
        // boolean hasDefender = false;
        // boolean hasForward = false;
        // for (T player : team) {
        // if (player instanceof Goalkeeper) {
        // hasGoalkeeper = true;
        // } else if (player instanceof Defender) {
        // hasDefender = true;
        // } else if (player instanceof Forward) {
        // hasForward = true;
        // }
        // }
        boolean hasGoalkeeper = team.stream().anyMatch(player -> player instanceof Goalkeeper);
        boolean hasDefender = team.stream().anyMatch(player -> player instanceof Defender);
        boolean hasForward = team.stream().anyMatch(player -> player instanceof Forward);

        if (!hasGoalkeeper || !hasDefender || !hasForward) {
            throw new IncompleteTeamException(
                    "Team " + name + " must include at least 1 Goalkeeper, 1 Defender, and 1 Forward.");
        }
    }

    @Override
    public void displayStats() {
        logger.info("Team: " + name);
        logger.info("Manager: " + manager.getName());
        logger.info("Total Skill Level: " + getTeamSkill());
        logger.info("Players:");
        team.forEach(
                player -> logger.info("  " + player.getPlayerName() + " (Skill: " + player.getPlayerSkill() + ")"));
        // for (T player : team) {
        // logger.info(" " + player.getPlayerName() + " (Skill: " +
        // player.getPlayerSkill() + ")");
        // }
    }

    @Override
    public int compareTo(FootballTeam other) {
        return this.name.compareTo(other.name);
    }
}
