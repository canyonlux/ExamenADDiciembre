package org.example;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;

public class ClienteCRUD {

    public void createClient(Cliente client, MongoCollection<Cliente> collection) {
        executeMongoOperation(() -> collection.insertOne(client));
    }

    public void borrarClient(Cliente client, MongoCollection<Cliente> collection) {
        executeMongoOperation(() -> {
            CuentaCRUD accountCRUD = new CuentaCRUD();
            accountCRUD.deleteAccount(client);
            collection.deleteOne(eq("clientid", client.getClientid()));
        });
    }

    public void modificarClient(Cliente client, MongoCollection<Cliente> collection) {
        executeMongoOperation(() -> collection.replaceOne(eq("clientid", client.getClientid()), client));
    }

    public void mostrarClients(MongoCollection<Cliente> collection) {
        executeMongoOperation(() -> collection.find().forEach(System.out::println));
    }

    private void executeMongoOperation(Runnable operation) {
        try {
            operation.run();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
