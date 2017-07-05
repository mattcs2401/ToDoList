package mcssoft.com.todolist.database;

public class SchemaConstants {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TODOLIST";

    //<editor-fold defaultstate="collapsed" desc="Region: Table Shopping List">
    public static final String TABLE_SHOPPING_LIST = "SHOPPING_LIST";
    public static final String SHOPPING_LIST_ROWID = "_id";
    public static final String SHOPPING_LIST_TYPE = "SLTYPE";        // e.g. General, Fruit&Veg etc
    public static final String SHOPPING_LIST_VALUE = "SLVALUE";      // e.g. Bread
    public static final String SHOPPING_LIST_VALUE_SEL = "SLSELECT"; // Y or N, item selected in UI

    public static final String CREATE_TABLE_SHOPPING_LIST = "CREATE TABLE "
            + TABLE_SHOPPING_LIST     + " ("
            + SHOPPING_LIST_ROWID     + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SHOPPING_LIST_TYPE      + " TEXT NOT NULL, "
            + SHOPPING_LIST_VALUE     + " TEXT NOT NULL, "
            + SHOPPING_LIST_VALUE_SEL + " TEXT NOT NULL)";

    public  static final String DROP_TABLE_SHOPPING_LIST =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_SHOPPING_LIST + ";";

    public static final String WHERE_SHOPPING_LIST_TYPE = SHOPPING_LIST_TYPE + "=?";
    //</editor-fold>


}
//    public static final String CREATE_RACES_TABLE = "CREATE TABLE "
//            + RACES_TABLE     + " ("
//            + RACE_ROWID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + RACE_MEETING_ID + " TEXT NOT NULL, "
//            + RACE_NO         + " TEXT NOT NULL, "
//            + RACE_TIME       + " TEXT NOT NULL, "
//            + RACE_NAME       + " TEXT NOT NULL, "
//            + RACE_DIST       + " TEXT NOT NULL)";
