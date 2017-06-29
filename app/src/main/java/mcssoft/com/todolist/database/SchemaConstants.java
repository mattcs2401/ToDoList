package mcssoft.com.todolist.database;

public class SchemaConstants {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TODOLIST";

    //<editor-fold defaultstate="collapsed" desc="Region: Table Shopping List">
    public static final String TABLE_SHOPPING_LIST = "SHOPPING_LIST";
    public static final String SHOPPING_LIST_ROWID = "_id";
    public static final String SHOPPING_LIST_TYPE = "TYPE";     // e.g. General, Fruit&Veg etc
    public static final String SHOPPING_LIST_VALUE = "VALUE";   // e.g. Bread

    public static final String SHOPPING_LIST_TYPE_GEN = "General";        // food related
    public static final String SHOPPING_LIST_TYPE_FANDV = "Fruit&Veg";    // "    "
    public static final String SHOPPING_LIST_TYPE_MANDF = "Meat&Fish";    // "    "
    public static final String SHOPPING_LIST_TYPE_OTHER = "Other";        // non-food related.

    public static final String CREATE_TABLE_SHOPPING_LIST = "CREATE TABLE "
            + TABLE_SHOPPING_LIST + " ("
            + SHOPPING_LIST_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SHOPPING_LIST_TYPE  + " TEXT NOT NULL, "
            + SHOPPING_LIST_VALUE + " TEXT NOT NULL)";

    public  static final String DROP_TABLE_SHOPPING_LIST =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_SHOPPING_LIST + ";";
    //</editor-fold>


}
