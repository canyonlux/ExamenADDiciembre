package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Main {
    private static final String MONGO_DB_URI = "mongodb://garbel:qwerty1234@ec2-54-146-188-92.compute-1.amazonaws.com:27017";

    public static void main(String[] args) {
        try (MongoClient dbClient = MongoClients.create(MONGO_DB_URI)) {
            MongoDatabase db = initializeDatabase(dbClient);

            MongoCollection<Cliente> clientsCollection = db.getCollection("clients", Cliente.class);

            performDatabaseOperations(clientsCollection);
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos MongoDB: " + e.getMessage());
        }
    }

    private static MongoDatabase initializeDatabase(MongoClient dbClient) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        System.out.println("Base de datos inicializada con éxito.");
        return dbClient.getDatabase("garbel").withCodecRegistry(pojoCodecRegistry);
    }

    private static void performDatabaseOperations(MongoCollection<Cliente> clientsCollection) {
        ClienteCRUD clientCRUD = new ClienteCRUD();
        CuentaCRUD accountCRUD = new CuentaCRUD();

        // Ejemplo de operaciones CRUD
        Cliente client1 = new Cliente(50, "123A", "juan", "bu", "Española", "29292929", "hola@fffssf", "1234");
        Cliente client3 = new Cliente(60, "1234b", "pepe", "ba", "Española", "2929299", "hola@fffsf", "1234");
        Cliente client2 = new Cliente(80, "123456A", "maria", "be", "Española", "40404040", "hola@fffsd", "1234");
        Cliente client4 = new Cliente(70, "123456b", "ana", "bi", "Española", "505059", "hola@fffdsf", "1234");

        Cuenta account1 = new Cuenta("IBAN1", 1000.0, client1.getClientid());
        Cuenta account2 = new Cuenta("IBAN2", 2000.0, client2.getClientid());


        // Ejemplo de uso de métodos CRUD

        // Creación de clientes
        System.out.println("Creando clientes...");
        clientCRUD.createClient(client1, clientsCollection);
        clientCRUD.createClient(client3, clientsCollection);
        System.out.println("Clientes creados con éxito.");


        // Modificación de cliente
        System.out.println("\nModificando cliente...");
        clientCRUD.modificarClient(client1, clientsCollection);
        System.out.println("Cliente modificado con éxito.");



        clientCRUD.modificarClient(client1,clientsCollection);

        // Transacción
        System.out.println("\nRealizando transacción...");
        accountCRUD.transferencia(account1, account2, 500.0d);
        System.out.println("Transacción completada con éxito.");
        accountCRUD.mostrarAcconts();


        // Mostrar estado final
        System.out.println("\nEstado final de los clientes y cuentas:");

// Borrando clientes
        System.out.println("Borrando cliente con clientid: " + client1.getClientid() + "...");
        clientCRUD.borrarClient(client1, clientsCollection);
        System.out.println("Cliente borrado exitosamente.");

        System.out.println("Borrando cliente con clientid: " + client3.getClientid() + "...");
        clientCRUD.borrarClient(client3, clientsCollection);
        System.out.println("Cliente borrado exitosamente.");

// Borrando cuentas
        System.out.println("Borrando cuenta asociada al cliente con clientid: " + client1.getClientid() + "...");
        accountCRUD.deleteAccount(client1);
        System.out.println("Cuenta borrada exitosamente.");

        System.out.println("Borrando cuenta asociada al cliente con clientid: " + client3.getClientid() + "...");
        accountCRUD.deleteAccount(client3);
        System.out.println("Cuenta borrada exitosamente.");

// Mostrando los clientes y cuentas restantes
        System.out.println("\nMostrando los clientes restantes en la colección:");
        clientCRUD.mostrarClients(clientsCollection);

        System.out.println("\nMostrando las cuentas restantes:");
        accountCRUD.mostrarAcconts();

    }
}
























