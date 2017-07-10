package mcssoft.com.todolist.database;

public class Schema {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TODOLIST";

    //<editor-fold defaultstate="collapsed" desc="Region: Table REF_ITEM">
    // A record in this table represent a single reference item.
    public static final String TABLE_REF_ITEM   = "REF_ITEM";
    public static final String REF_ITEM_ROWID   = "_id";
    public static final String REF_ITEM_CODE    = "REF_CODE";    // e.g. GENRL, FANDV etc.
    public static final String REF_ITEM_DESC    = "REF_DESC";    // e.g. GENRL, Fruit&Veg etc
    public static final String REF_ITEM_VALUE   = "REF_VALUE";   // e.g. Bread
    public static final String REF_ITEM_VAL_SEL = "REF_SELECT";  // Y or N, item selected in UI

    public static final String CREATE_TABLE_REF_ITEM = "CREATE TABLE "
            + TABLE_REF_ITEM   + " ("
            + REF_ITEM_ROWID   + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + REF_ITEM_CODE    + " TEXT NOT NULL, "
            + REF_ITEM_DESC    + " TEXT NOT NULL, "
            + REF_ITEM_VALUE   + " TEXT NOT NULL, "
            + REF_ITEM_VAL_SEL + " TEXT NOT NULL)";

    public static final String DROP_TABLE_REF_ITEM =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_REF_ITEM + ";";

    public static final String WHERE_REF_ITEM_CODE = REF_ITEM_CODE + "=?";

    public static final String WHERE_REF_ITEM_ROWID = REF_ITEM_ROWID + "=?";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Table SL">
    // A record in this table represents a single entry in a shopping list.
    public static final String TABLE_SL = "SL";
    public static final String SL_ROWID = "_id";
    public static final String SL_ID    = "SL_ID";    // list id.
    public static final String SL_TYPE  = "SL_TYPE";  // list type, e.g. Ggeneral.
    public static final String SL_VAL   = "SL_VAL";   // list value, e.g. Bread

    public static final String CREATE_TABLE_SL = "CREATE TABLE "
            + TABLE_SL + " ("
            + SL_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SL_ID    + " TEXT NOT NULL, "
            + SL_TYPE  + " TEXT NOT NULL, "
            + SL_VAL   + " TEXT NOT NULL)";

    public static final String DROP_TABLE_SL =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_SL + ";";

    public static final String WHERE_SL_ID = SL_ID + "=?";
    public static final String WHERE_SL_TYPE = SL_TYPE + "=?";
    //</editor-fold>
}
