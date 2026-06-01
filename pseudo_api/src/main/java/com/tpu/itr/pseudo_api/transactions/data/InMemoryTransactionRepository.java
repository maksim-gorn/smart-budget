package com.tpu.itr.pseudo_api.transactions.data;


import com.tpu.itr.pseudo_api.transactions.dto.Transaction;
import com.tpu.itr.pseudo_api.transactions.utils.Currencies;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final Map<String, List<Transaction>> storage = new HashMap<>();

    private static final List<Transaction> FIXED_KEY_1 = List.of(
            new Transaction(1, "Територия Кофе", 5814, -449.99f, Currencies.RUB, LocalDate.now().minusDays(1)),
            new Transaction(2, "Ozon", 5399, -1200.99f, Currencies.RUB, LocalDate.now().minusDays(2)),
            new Transaction(3, "Yandex Go", 4121, -350.00f, Currencies.RUB, LocalDate.now().minusDays(3)),
            new Transaction(4, "KFC", 5814, -250.00f, Currencies.RUB, LocalDate.now().minusDays(4)),
            new Transaction(5, "Лента", 5411, -73.69f, Currencies.RUB, LocalDate.now().minusDays(4))
    );

    private static final List<Transaction> FIXED_KEY_2 = List.of(
            new Transaction(6, "DNS", 5732, -89990.10f, Currencies.RUB, LocalDate.now().minusDays(1)),
            new Transaction(7, "Кинопоиск", 4899, -799.40f, Currencies.RUB, LocalDate.now().minusDays(7)),
            new Transaction(8, "Yandex Go", 4121, -420.00f, Currencies.RUB, LocalDate.now().minusDays(2)),
            new Transaction(9, "H&M", 5651, -4990.00f, Currencies.RUB, LocalDate.now().minusDays(5)),
            new Transaction(10, "Operetta", 5812, -1200.00f, Currencies.RUB, LocalDate.now().minusDays(5))
    );

    @Override
    public List<Transaction> findAllByApiKey(String apiKey) {
        return storage.computeIfAbsent(apiKey, this::resolveDataForKey);
    }

    @Override
    public List<Transaction> findAll() {
        return storage.values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public List<Transaction> findByApiKeyAndDateRange(
            String apiKey,
            LocalDate from,
            LocalDate to
    ) {
        return findAllByApiKey(apiKey).stream()
                .filter(t -> !t.date().isBefore(from) && !t.date().isAfter(to))
                .toList();
    }

    @Override
    public void save(Transaction transaction) {
        // не знаю зачем добавил, потом переделаю все равно все
        storage.computeIfAbsent("test-key-1", k -> new ArrayList<>())
                .add(transaction);
    }


    //выбор источника данных в зависимости от ключа
    private List<Transaction> resolveDataForKey(String apiKey) {

        return switch (apiKey) {
            case "test-key-1" -> new ArrayList<>(FIXED_KEY_1);
            case "test-key-2" -> new ArrayList<>(FIXED_KEY_2);
            default -> generateRandomTransactions(apiKey, 30);
        };
    }


    //генерация фейковых данных
    private List<Transaction> generateRandomTransactions(String apiKey, int count) {

        Random random = new Random(apiKey.hashCode());

        List<String> merchants = List.of(
                "Starbucks", "Ozon", "Yandex Go", "KFC", "Лента",
                "Пятерочка", "DNS", "Apple Store", "H&M", "Uber",
                "Netflix", "Безумно шаурма", "Operetta", "McDonald's",
                "СберМаркет", "FixPrice"
        );

        List<Integer> mccs = List.of(
                5814, 5399, 4121, 5732, 5411, 5651, 4899, 5812
        );

        List<Transaction> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            String merchant = merchants.get(random.nextInt(merchants.size()));
            int mcc = mccs.get(random.nextInt(mccs.size()));

            float amount = -(10 + random.nextFloat() * 8000);

            LocalDate date = LocalDate.now()
                    .minusDays(random.nextInt(60));

            result.add(new Transaction(
                    i + 1,
                    merchant,
                    mcc,
                    amount,
                    Currencies.RUB,
                    date
            ));
        }

        return result;
    }
}