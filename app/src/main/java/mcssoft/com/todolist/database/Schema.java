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
    public static final String WHERE_REF_ITEM_SEL = REF_ITEM_VAL_SEL + "=?";
    public static final String RAW_WHERE_REF_ITEM_SEL = " WHERE " + REF_ITEM_VAL_SEL + "=?";
    public static final String WHERE_REF_ITEM_ROWID = REF_ITEM_ROWID + "=?";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Table SLIST">
    // A record in this table represents a single entry of type Shopping List in a TODOLIST.
    public static final String TABLE_SLIST = "SLIST";
    public static final String SLIST_ROWID = "_id";
    public static final String SLIST_ID    = "SLIST_ID";   // id (compacted date YYYYMMDDHHMM).
    public static final String SLIST_NAME  = "SLIST_NAME"; // name.
    public static final String SLIST_DATE  = "SLIST_DATE"; // date created DD/MM/YYYY HH:MI

    public static final String CREATE_TABLE_SLIST = "CREATE TABLE "
            + TABLE_SLIST + " ("
            + SLIST_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SLIST_ID    + " TEXT NOT NULL, "
            + SLIST_NAME  + " TEXT, "
            + SLIST_DATE  + " TEXT NOT NULL)";

    public static final String DROP_TABLE_SLIST =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_SLIST + ";";

    public static final String WHERE_SLIST_ROWID = SLIST_ROWID + "=?";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Table GENERAL">
    public static final String TABLE_GENERAL = "GENERAL";
    public static final String GENERAL_ROWID = "_id";

    public static final String CREATE_TABLE_GENERAL = "CREATE TABLE "
            + TABLE_GENERAL + " ("
            + GENERAL_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ")";

    public static final String DROP_TABLE_GENERAL =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_GENERAL + ";";
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Region: Table SLIST_ITEM">
    // A record in this table represents a single entry in a Shopping List.
    // This is a linking table between tables SLIST and REF_ITEM.
    public static final String TABLE_SLIST_ITEM    = "SLIST_ITEM";
    public static final String SLIST_ITEM_ROWID    = "_id";
    public static final String SLIST_ITEM_SLIST_ID = "SLIST_ID";     // SLIST.ROWID
    public static final String SLIST_ITEM_REF_ID   = "REF_ID";       // REF_ITEM.ROWID

    public static final String CREATE_TABLE_SLIST_ITEM = "CREATE TABLE "
            + TABLE_SLIST_ITEM    + " ("
            + SLIST_ITEM_ROWID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SLIST_ITEM_SLIST_ID + " INTEGER NOT NULL, "
            + SLIST_ITEM_REF_ID   + " INTEGER NOT NULL)";

    public static final String DROP_TABLE_SLIST_ITEM =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_SLIST_ITEM + ";";
    //</editor-fold>
}
