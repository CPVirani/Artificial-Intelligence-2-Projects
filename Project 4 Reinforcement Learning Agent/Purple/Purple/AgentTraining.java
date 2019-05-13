import java.util.Random;

class AgentTraining {

    private Environment environment;
    private int curr_x = 0;
    private int curr_y = 0;
    private Agent trainAgent;
    private double[][][] Q = qTable.GetNewInstance();

    public AgentTraining(Environment wumpusEnvironment){
        this.environment = wumpusEnvironment;
        TransferPercept transferPercept = new TransferPercept(wumpusEnvironment);
        this.trainAgent = new Agent(wumpusEnvironment,transferPercept,false);
        environment.placeAgent(trainAgent);
    }

    public void train(){
        curr_x = environment.getAgentLocation()[0];
        curr_y = environment.getAgentLocation()[1];
        int old_x = curr_x;
        int old_y = curr_y;

        // Select an action
        int action = selectAction();
//        System.out.println("Action: " +  action);
        // Execute the selected action
        executeAction(action);

        // Take the action and get the reward
        int newPosx = trainAgent.getLocation()[0];
        int newPosy = trainAgent.getLocation()[1];

        curr_x = newPosx;
        curr_y = newPosy;
        ;

        if((old_x == curr_x) && (old_y == curr_y)){
            if( Q[old_x][old_y][action] == 0){
                Q[old_x][old_y][action] = qTableConfig.BUMPREWARD;
            }
        }

        double reward = Q[old_x][old_y][0];


        // Get the max Q-value of the new position.
        double maxQValueOfNextState = qTable.getMaxQTableValue(newPosx,newPosy);

        // Update the Q-table, specifically the old State-action value.
        Q[old_x][old_y][action] = Q[old_x][old_y][action] +
                                      qTableConfig.LEARNINGRATE *
                                      (reward + (qTableConfig.DISCOUNTFACTOR * maxQValueOfNextState) - Q[old_x][old_y][action]);
//        for(int i = 1; i < 5; i++){
//            System.out.println(Q[old_x][old_y][i]);
//        }
//        System.out.println(String.valueOf(old_x)+ String.valueOf(old_y) + ": " + "Action:" + String.valueOf(action)+ " : " + Q[old_x][old_y][action]);
    }


    public int selectAction() {
        Random rand = new Random();
        if (Math.random() < qTableConfig.PROBABILITY) {
//            System.out.println("Random");
            return rand.nextInt(4) + 1;
        } else {
//            System.out.println("QValue");
            return qTable.getBestActionMaxQValueAction(curr_x, curr_y);
        }
    }

    public void executeAction(int action) {

        // Check for pits, gold, wumpus
        checkState();

        // Right
        if (action == 1) {

            switch (trainAgent.getDirection()) {
                case 'E':
                    break;
                case 'S':
                    trainAgent.turnLeft();
                    break;
                case 'W':
                    trainAgent.turnLeft();
                    trainAgent.turnLeft();
                    break;
                case 'N':
                    trainAgent.turnRight();
                    break;
            }
            trainAgent.goForward();
            environment.placeAgent(trainAgent);

        }
        // Down
        if (action == 2) {

            switch (trainAgent.getDirection()) {
                case 'E':
                    trainAgent.turnRight();
                    break;
                case 'S':
                    break;
                case 'W':
                    trainAgent.turnLeft();
                    break;
                case 'N':
                    trainAgent.turnRight();
                    trainAgent.turnRight();
                    break;
            }
            trainAgent.goForward();
            environment.placeAgent(trainAgent);
        }
        // Left
        if (action == 3) {
            switch (trainAgent.getDirection()) {
                case 'E':
                    trainAgent.turnRight();
                    trainAgent.turnRight();
                    break;
                case 'S':
                    trainAgent.turnRight();
                    break;
                case 'W':
                    break;
                case 'N':
                    trainAgent.turnLeft();
                    break;
            }
            trainAgent.goForward();
            environment.placeAgent(trainAgent);

        }
        // Up
        if (action == 4) {

            switch (trainAgent.getDirection()) {
                case 'E':
                    trainAgent.turnLeft();
                    break;
                case 'S':
                    trainAgent.turnRight();
                    trainAgent.turnRight();
                    break;
                case 'W':
                    trainAgent.turnRight();
                    break;
                case 'N':
                    break;
            }
            trainAgent.goForward();
            environment.placeAgent(trainAgent);
        }

    }

    public void checkState() {
        if (environment.checkDeathforTrain()) {
            if (environment.getWumpus()) {
//                System.out.println("Agent Death");
                environment.shootArrow();
                Q[curr_x][curr_y][0] = qTableConfig.WUMPUSREWARD;
            }
        }
        else if (environment.getGlitter()) {
//            System.out.println("Grabbing glitter");

            Q[curr_x][curr_y][0] = qTableConfig.GOLDREWARD;
            environment.grabGold();
        }
        else if (environment.getPit()) {
//            System.out.println("Inside Pit");

            Q[curr_x][curr_y][0] = qTableConfig.PITREWARD;
            environment.climbPit();
        }
    }
}