public class qTable {

//    public static final int PITREWARD = -1;
//    public static final int WUMPUSREWARD = -1;
//    public static final int GOLDREWARD = 1;
//    public static final int BUMPREWARD = -1;
//
//    public static final double PROBABILITY = 0.5;
//    public static final double COSTOFACTION = 0;
//
  //  public static final double LEARNINGRATE = 0.5;
 //   public static final double DISCOUNTFACTOR = 0.8;

//    public static final int ITERATIONS = 1000;

//    public static final String[] MOVE = {"LEFT", "RIGHT", "UP", "DOWN"};
//    public static final double MAXACTIONSCORE = -1;

    private static double[][][] qTable = null;

    public static double[][][] GetNewInstance(){

        if(qTable == null){
            qTable = new double[4][4][5];
            for(int x = 0; x < 4; x++){
                for(int y = 0;y < 4; y++){
                    for(int r = 0; r < 5; r++){
                        qTable[x][y][r] = 0;
                    }
                }
            }
        }
        return qTable;
    }




    public static int getBestActionMaxQValueAction(int x, int y){

        int Best_Action = 1;
        for(int i = 1; i < 5; i++){
            if(qTable[x][y][i] > qTable[x][y][Best_Action]){
                Best_Action = i;
            }
        }
        return Best_Action;
    }

    public static double getMaxQTableValue(int x, int y){

        double Best_Value = qTable[x][y][1];
        for(int i = 1; i < 5; i++){
            if(qTable[x][y][i] > Best_Value){
                Best_Value = qTable[x][y][i];
            }
        }
        return Best_Value;
    }

    //   public void updateQTable(int x, int y) {
    //       int index = -1;
//
//        if (x < 4 && y < 4) {
//            switch (y) {
//                case 1:
//                    index = x - y;
//                    break;
//                case 2:
//                    index = x + y + 1;
//                    break;
//                case 3:
//                    index = x + y + 4;
//                    break;
//                case 4:
//                    index = x + y + 7;
//                    break;
//                default:
//                    index = -1;
//                    break;
//            }
//        }
//
//        int currentQTableRowIndex = index;
//        int nextQTableRowIndex = index + getBestActionMaxQValueAction(x,y);
//
    //       if (nextQTableRowIndex > -1) {
//            qTable[currentQTableRowIndex][nextQTableRowIndex] += LEARNINGRATE * (qTable[currentQTableRowIndex][0] + (DISCOUNTFACTOR * this.getMaxQTableValue(qTable[nextQTableRowIndex])) - qTable[currentQTableRowIndex] + COSTOFACTION);
//        } else {
    //          qTable[currentQTableRowIndex][nextQTableRowIndex] += LEARNINGRATE * COSTOFACTION;
    //       }
//    }


    public static void DisplayqTable(){
        System.out.println("Printing qTable Table");
        for(int x = 0; x < 4; x++){
            for(int y = 0;y < 4; y++){

                System.out.println(String.valueOf(x) + String.valueOf(y) +  " \nAction: ");
                for(int r = 0; r < 5; r++){
                    if(r == 0){
                        System.out.println("Reward Is: " + qTable[x][y][r]);
                    }
                    System.out.println(qTable[x][y][r]);
                }
            }
        }
    }

}

