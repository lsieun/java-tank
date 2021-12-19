package lsieun.tank.logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeadCriteriaUtils {
    private static final Map<Missile, Set<MissileDeadCriteria>> missileMap = new HashMap<>();
    private static final Map<Tank, Set<TankDeadCriteria>> tankMap = new HashMap<>();
    private static final Map<Block, Set<BlockDeadCriteria>> blockMap = new HashMap<>();

    public void register(Missile missile, MissileDeadCriteria deadCriteria) {
        boolean exists = missileMap.containsKey(missile);
        if (exists) {
            Set<MissileDeadCriteria> set = missileMap.get(missile);
            set.add(deadCriteria);
        }
        else {
            //
            Set<MissileDeadCriteria> set = new HashSet<>();
            set.add(deadCriteria);
            missileMap.put(missile, set);
        }
    }

    public void register(Tank tank, TankDeadCriteria deadCriteria) {
        boolean exists = tankMap.containsKey(tank);
        if (exists) {
            Set<TankDeadCriteria> set = tankMap.get(tank);
            set.add(deadCriteria);
        }
        else {
            //
            Set<TankDeadCriteria> set = new HashSet<>();
            set.add(deadCriteria);
            tankMap.put(tank, set);
        }
    }

    public void register(Block block, BlockDeadCriteria deadCriteria) {
        boolean exists = tankMap.containsKey(block);
        if (exists) {
            Set<BlockDeadCriteria> set = blockMap.get(block);
            set.add(deadCriteria);
        }
        else {
            //
            Set<BlockDeadCriteria> set = new HashSet<>();
            set.add(deadCriteria);
            blockMap.put(block, set);
        }
    }

}
