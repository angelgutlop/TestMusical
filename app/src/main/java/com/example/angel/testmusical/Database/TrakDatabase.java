package com.example.angel.testmusical.Database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = TrakDatabase.VERSION, fileName = "tests.db")
public class TrakDatabase {

    public static final int VERSION = 1;


    @Table(TrakContract.class)
    public static final String traks = "traks";
}
