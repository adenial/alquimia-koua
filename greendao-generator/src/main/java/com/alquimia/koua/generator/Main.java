package com.alquimia.koua.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Main
{
    public static void main(String[] args)throws Exception
    {
        Schema schema = new Schema(1, "com.alquimia.koua.db");
        addMessage(schema);
        addCategory(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    private static void addCategory(Schema schema)
    {
        Entity category = schema.addEntity("Category");
        category.addIdProperty();
        category.addStringProperty("name").notNull();
    }

    private static void addMessage(Schema schema)
    {
        Entity message = schema.addEntity("KouaRecord");
        message.addIdProperty();
        message.addStringProperty("operation").notNull();
        message.addIntProperty("operationType").notNull();
        message.addDoubleProperty("amount");
        message.addIntProperty("authorization");
        message.addStringProperty("message");
        message.addStringProperty("date");
    }
}
