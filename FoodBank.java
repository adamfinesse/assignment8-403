package ProducerConsumer;

public class FoodBank {
    private int food;
     public FoodBank(){
        food = 0;
    }
     synchronized public void giveFood(int newAmt){
             food = food + newAmt;
             System.out.println("The balance is now "+ food +" items.");
             notify();
     }
     synchronized public void takeFood(int subAmt){
         if(food <= 0){
             try{
                 System.out.println("Waiting to get food");
                 wait();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
         while(subAmt > food){ // probably not the best way to do this, revisit later.
             try {
                 wait();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
         System.out.println("Taking "+subAmt+" items of food, the balance is now " + (food -subAmt) +" items.");
         food = food - subAmt;
     }

}
