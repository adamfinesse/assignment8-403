package ProducerConsumer;

public class FoodProducer extends Thread {
    private FoodBank bank;
    public FoodProducer(FoodBank bankInit){
        bank = bankInit;
    }
    @Override
    public void run(){
        while(true){
            try {
                int foodAmt = (int)(1+Math.random() *100);
                bank.giveFood(foodAmt);
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
