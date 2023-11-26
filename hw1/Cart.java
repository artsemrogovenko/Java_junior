package hw1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import seminar1.task2.Food;
import seminar1.task2.UMarket;

/**
 * Корзина
 * @param <T> Еда
 */
public class Cart <T extends Food>{

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    public Cart(Class<T> clazz, UMarket market)
    {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs(){
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> {
            System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                    index.getAndIncrement(), food.getName(),
                    food.getProteins() ? "Да" : "Нет",
                    food.getFats() ? "Да" : "Нет",
                    food.getCarbohydrates() ? "Да" : "Нет");
        });
    }
    
    /**
     * Балансировка корзины
     */
    // TODO Переработать метод балансировки корзины товаров cardBalancing() с использованием Stream API
    public void cardBalancing()
    {
        try {
            boolean proteins = foodstuffs.stream().anyMatch(p -> p.getProteins());
            boolean fats = foodstuffs.stream().anyMatch(p -> p.getFats());
            boolean carbohydrates = foodstuffs.stream().anyMatch(p -> p.getCarbohydrates());

            if (proteins && fats && carbohydrates) {
                System.out.println("Корзина уже сбалансирована по БЖУ.");
                return;
            } else {
                if (!proteins) {
                    proteins = true;
                    foodstuffs.add((T) market.getThings(Food.class).stream().filter(Food::getProteins).findAny().get());
                }
                if (!fats) {
                    fats = true;
                    foodstuffs.add((T) market.getThings(Food.class).stream().filter(Food::getFats).findAny().get());
                }
                if (!carbohydrates) {
                    carbohydrates = true;
                    foodstuffs.add((T) market.getThings(Food.class).stream().filter(Food::getCarbohydrates).findAny().get());
                }
            }

            if (proteins && fats && carbohydrates)
                System.out.println("Корзина сбалансирована по БЖУ.");

        } catch (NoSuchElementException e) {
            System.out.println("\nНевозможно сбалансировать корзину по БЖУ.");
        }

    }

}
