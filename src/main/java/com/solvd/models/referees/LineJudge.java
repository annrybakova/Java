package com.solvd.models.referees;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solvd.App;
import com.solvd.models.game.Game;

public class LineJudge extends Referee {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public LineJudge(int experience, String name) {
        super(name, experience);
    }

    @Override
    public void officiateGame(Game game) {
        train();
        logger.info(
                "Line Referee " + getName() + " is overseeing the sidelines for the game " + game.gameBetween());
    }

    @Override
    public String toString() {
        return "Line Referee{name='" + name + "', experience=" + experience + "}";
    }
}
