package Canjas;

import java.util.ArrayList;
import java.util.List;

public class Teleport {

    private final List<Pair<Integer, Integer>> spawnList;
    private final List<Pair<Integer, Integer>> fightList;

    public Teleport() {
        this.spawnList = new ArrayList<>();
        this.fightList = new ArrayList<>();
    }

    private void addingPos(int x, int y) {
        Pair<Integer, Integer> coos = new Pair<>(x, y);
        this.spawnList.add(coos);
    }

    private void addingPos2(int x, int y) {
        Pair<Integer, Integer> coos = new Pair<>(x, y);
        this.fightList.add(coos);
    }

    private void fillFightList() {
        addingPos2(200, 200);
        addingPos2(-200, 200);
        addingPos2(200, -200);
        addingPos2(-200, -200);

        addingPos2(100, 100);
        addingPos2(-100, 100);
        addingPos2(100, -100);
        addingPos2(-100, -100);

        addingPos2(50, 50);
        addingPos2(-50, 50);
        addingPos2(50, -50);
        addingPos2(-50, -50);

        addingPos2(200, -100);
        addingPos2(-200, 100);
        addingPos2(-200, 100);
        addingPos2(-200, -100);

        addingPos2(100, 200);
        addingPos2(-100, 200);
        addingPos2(100, -200);
        addingPos2(-100, -200);

        addingPos2(0, 200);
        addingPos2(0, -200);
        addingPos2(200, 0);
        addingPos2(-200, -0);

        addingPos2(0, 100);
        addingPos2(0, -100);
        addingPos2(100, 0);
        addingPos2(-100, -0);

        addingPos2(200, 50);
        addingPos2(-200, 50);
        addingPos2(200, -50);
        addingPos2(-200, -50);

        addingPos2(50, 200);
        addingPos2(-50, 200);
        addingPos2(50, -200);
        addingPos2(-50, -200);

        addingPos2(200, 150);
        addingPos2(-200, 150);
        addingPos2(200, -150);
        addingPos2(-200, -150);

        addingPos2(150, 200);
        addingPos2(-150, 200);
        addingPos2(150, -200);
        addingPos2(-150, -200);
    }


    private void fillSpawnList() {
        addingPos(700, 700);
        addingPos(500, 700);
        addingPos(300, 700);
        addingPos(100, 700);
        addingPos(0, 700);

        addingPos(700, -700);
        addingPos(500, -700);
        addingPos(300, -700);
        addingPos(100, -700);
        addingPos(0, -700);

        addingPos(-700, -700);
        addingPos(-500, -700);
        addingPos(-300, -700);
        addingPos(-100, -700);

        addingPos(-700, 700);
        addingPos(-500, 700);
        addingPos(-300, 700);
        addingPos(-100, 700);

        addingPos(700, 600);
        addingPos(700, 400);
        addingPos(700, 200);
        addingPos(700, 0);

        addingPos(-700, 600);
        addingPos(-700, 400);
        addingPos(-700, 200);
        addingPos(-700, 0);

        addingPos(700, -600);
        addingPos(700, -400);
        addingPos(700, -200);

        addingPos(-700, -600);
        addingPos(-700, -400);
        addingPos(-700, -200);

        addingPos(700, 500);
        addingPos(500, 500);
        addingPos(300, 500);
        addingPos(100, 500);
        addingPos(0, 500);

        addingPos(700, -500);
        addingPos(500, -500);
        addingPos(300, -500);
        addingPos(100, -500);
        addingPos(0, -500);

        addingPos(-700, -500);
        addingPos(-500, -500);
        addingPos(-300, -500);
        addingPos(-100, -500);

        addingPos(-700, 500);
        addingPos(-500, 500);
        addingPos(-300, 500);
        addingPos(-100, 500);

        addingPos(500, 600);
        addingPos(500, 400);
        addingPos(500, 200);
        addingPos(500, 0);

        addingPos(-500, 600);
        addingPos(-500, 400);
        addingPos(-500, 200);
        addingPos(-500, 0);

        addingPos(500, -600);
        addingPos(500, -400);
        addingPos(500, -200);

        addingPos(-500, -600);
        addingPos(-500, -400);
        addingPos(-500, -200);

        addingPos(200, 600);
        addingPos(200, 400);
        addingPos(200, 200);
        addingPos(200, 0);

        addingPos(-200, 600);
        addingPos(-200, 400);
        addingPos(-200, 200);
        addingPos(-200, 0);

        addingPos(200, -600);
        addingPos(200, -400);
        addingPos(200, -200);

        addingPos(-200, -600);
        addingPos(-200, -400);
        addingPos(-200, -200);

        addingPos(600, 200);
        addingPos(300, 200);
        addingPos(100, 200);
        addingPos(0, 200);

        addingPos(600, -200);
        addingPos(300, -200);
        addingPos(100, -200);
        addingPos(0, -200);

        addingPos(-600, -200);
        addingPos(-300, -200);
        addingPos(-100, -200);

        addingPos(-600, 200);
        addingPos(-300, 200);
        addingPos(-100, 200);
    }

    public List<Pair<Integer, Integer>> getSpawnList() {
        fillSpawnList();
        return (this.spawnList);
    }

    public List<Pair<Integer, Integer>> getFightList() {
        fillFightList();
        return (this.fightList);
    }
}
