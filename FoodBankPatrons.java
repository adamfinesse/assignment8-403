package ProducerConsumer;

public class FoodBankPatrons {
    public static void main(String[] args){
        FoodBank foodbank = new FoodBank();
        FoodProducer foodproducer = new FoodProducer(foodbank);
        FoodConsumer foodconsumer = new FoodConsumer(foodbank);

        foodproducer.start();
        foodconsumer.start();
    }
}
