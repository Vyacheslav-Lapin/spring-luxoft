package ru.vlapin.trainings.springluxoft.service.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.experimental.NonFinal;
import ru.vlapin.trainings.springluxoft.model.Client;

import org.springframework.stereotype.Component;

public interface ClientRepository {

    Client add(Client client);

    Client get(long id);

    Client getBy(String name);

    List<Client> getAll();

    Client update(Client o);

    boolean remove(long id);
}

@Component
class MapClientRepository implements ClientRepository {

    Map<Long, Client> data = new HashMap<>();

    @NonFinal
    long currentId = 0;

    @Override
    public Client add(Client client) {

        client.setId(currentId++);
        data.put(client.getId(), client);

        return client;
    }

    @Override
    public Client get(long id) {

        return data.get(id);
    }

    @Override
    public List<Client> getAll() {

        return new ArrayList<>(data.values());
    }

    @Override
    public Client getBy(String name) {
        for (Client client : getAll()) {
            if (name.equals(client.getName())) {
                return client;
            }
        }

        return null;
    }

    @Override
    public Client update(Client client) {
        return data.put(client.getId(), client);
    }

    @Override
    public boolean remove(long id) {
        return data.remove(id) != null;
    }
}
