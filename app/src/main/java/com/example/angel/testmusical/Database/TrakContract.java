package com.example.angel.testmusical.Database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

public class TrakContract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    public static final String Id = "id";

    @DataType(DataType.Type.TEXT)
    public static final String compositorName = "compositorName";

    @DataType(DataType.Type.TEXT)
    public static final String genere = "genere";

    @DataType(DataType.Type.TEXT)
    public static final String assetName = "assetName";

    @DataType(DataType.Type.TEXT)
    public static final String albumArt = "albumArt";


}
