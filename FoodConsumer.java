package ProducerConsumer;

public class FoodConsumer extends Thread {
    private FoodBank bank;
    public FoodConsumer(FoodBank bankInit){
        bank = bankInit;
    }
    @Override
    public void run(){
        while(true){
            try {
                int foodAmt = (int)(1+Math.random() *100);
                bank.takeFood(foodAmt);
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
