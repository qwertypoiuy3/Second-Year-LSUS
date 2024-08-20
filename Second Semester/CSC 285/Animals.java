interface IMotion {
  void execute();
}

class Walk implements IMotion {
  @Override
  public void execute() {
    System.out.println("Walking");
  }
}

class Fly implements IMotion {
  @Override
  public void execute() {
    System.out.println("Flying");
  }
}

class Attack implements IMotion {
    @Override
    public void execute() {
        System.out.println("Attacking");
    }
}

class Flee implements IMotion {
    @Override
    public void execute() {
        System.out.println("Fleeing");
    }
    
}

abstract class Creature {
  private IMotion motion;
  // other generic attributes

  public void setMotion(IMotion motion) {
    this.motion = motion;
  }
  
  public void move() {
    motion.execute();
  }
  // other generic methods
}

class Fox extends Creature {
  public Fox(Walk walk) {
    setMotion(walk);
  }
}

class Goose extends Creature {
  public Goose(IMotion motion) {
    setMotion(motion);
  }
}

public class Animals {
  public static void main(String[] args) {
    Walk walk = new Walk();
    Fly fly = new Fly();
    Attack attack = new Attack();
    Flee flee = new Flee();

    // create a fox and a goose and let them walk around
    Fox fox = new Fox(walk);
    fox.move();

    Goose goose = new Goose(walk);
    goose.move();

    /// let the goose fly away
    goose.setMotion(fly);
    goose.move();
    
    // Attack and Flee implementation
    fox.setMotion(attack);
    fox.move();
    goose.setMotion(flee);
    goose.move();
  }
}
