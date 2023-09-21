package com.example;

import com.example.serverservice.ServerStarter;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;

import java.io.IOException;

public class Application {



    public static void main(String[] args) throws IOException {
        Micronaut.run(Application.class, args);


        ApplicationContext applicationContext = ApplicationContext.run();
        ServerStarter serverStarter = applicationContext.getBean(ServerStarter.class);
        serverStarter.startService();
    }



}
