package mcssoft.com.todolist.database;

public class Schema {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TODOLIST";

    //<editor-fold defaultstate="collapsed" desc="Region: Table REF_ITEM">
    // A record in this table represent a single reference item.
    public static final String TABLE_REF_ITEM = "REF_ITEM";
    public static final String REF_ITEM_ROWID = "_id";
    public static final String REF_ITEM_ARCHV = "REF_ARCHV";   // archive flag.
    public static final String REF_ITEM_CODE  = "REF_CODE";    // e.g. FANDV.
    public static final String REF_ITEM_DESC  = "REF_DESC";    // e.g. Fruit&Veg.
    public static final String REF_ITEM_VALUE = "REF_VALUE";   // e.g. Tomato.
    public static final String REF_ITEM_SEL   = "REF_SEL";     // flag, value selected for a list.

    public static final String CREATE_TABLE_REF_ITEM = "CREATE TABLE "
            + TABLE_REF_ITEM + " ("
            + REF_ITEM_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + REF_ITEM_ARCHV + " TEXT NOT NULL, "
            + REF_ITEM_CODE  + " TEXT NOT NULL, "
            + REF_ITEM_DESC  + " TEXT NOT NULL, "
            + REF_ITEM_VALUE + " TEXT NOT NULL, "
            + REF_ITEM_SEL   + " TEXT NOT NULL)";

    public static final String DROP_TABLE_REF_ITEM =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_REF_ITEM + ";";

    public static final String WHERE_REF_ITEM_ALL = REF_ITEM_ARCHV + "=?";
    public static final String WHERE_REF_ITEM_ROWID = REF_ITEM_ROWID + "=? AND " + REF_ITEM_ARCHV + "=?";
    public static final String WHERE_REF_ITEM_SEL = REF_ITEM_SEL + "=?";
    public static final String WHERE_REF_ITEM_CODE = REF_ITEM_CODE + "=? AND " + REF_ITEM_ARCHV + "=?";
    public static final String WHERE_REF_ITEM_ARCHV = REF_ITEM_ARCHV + "=?";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Table SLIST">
    // A record in this table represents a single entry of type Shopping List in a TODOLIST.
    public static final String TABLE_SLIST = "SLIST";
    public static final String SLIST_ROWID = "_id";
    public static final String SLIST_ARCHV = "SLIST_ARCHV"; // archive flag.
    public static final String SLIST_ID    = "SLIST_ID";    // id (compacted date YYYYMMDDHHMM).
    public static final String SLIST_NAME  = "SLIST_NAME";  // name.
    public static final String SLIST_DATE  = "SLIST_DATE";  // date created DD/MM/YYYY HH:MI

    public static final String CREATE_TABLE_SLIST = "CREATE TABLE "
            + TABLE_SLIST + " ("
            + SLIST_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SLIST_ARCHV + " TEXT NOT NULL, "
            + SLIST_ID    + " TEXT NOT NULL, "
            + SLIST_NAME  + " TEXT, "
            + SLIST_DATE  + " TEXT NOT NULL)";

    public static final String DROP_TABLE_SLIST =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_SLIST + ";";

    public static final String WHERE_SLIST_ROWID = SLIST_ROWID + "=?";
    public static final String WHERE_SLIST_ARCHV = SLIST_ARCHV + "=?";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Table GENERAL">
    public static final String TABLE_GENERAL = "GENERAL";
    public static final String GENERAL_ROWID = "_id";
    public static final String GENERAL_ARCHV = "GEN_ARCHV";     // archive flag.
    public static final String GENERAL_ID    = "GENERAL_ID";    // id (compacted date YYYYMMDDHHMM).
    public static final String GENERAL_NAME  = "GENERAL_NAME";  // name.
    public static final String GENERAL_DATE  = "GENERAL_DATE";  // date created DD/MM/YYYY HH:MI


    public static final String CREATE_TABLE_GENERAL = "CREATE TABLE "
            + TABLE_GENERAL + " ("
            + GENERAL_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GENERAL_ARCHV + " TEXT NOT NULL, "
            + GENERAL_ID    + " TEXt NOT NULL, "
            + GENERAL_NAME  + " TEXT NOT NULL, "
            + GENERAL_DATE  + " TEXt NOT NULL)";

    public static final String DROP_TABLE_GENERAL =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_GENERAL + ";";

    public static final String WHERE_GENERAL_ROWID = GENERAL_ROWID + "=?";
    public static final String WHERE_GENERAL_ARCHV = GENERAL_ARCHV + "=?";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Table GEN_ITEM">
    public static final String TABLE_GEN_ITEM   = "GEN_ITEM";
    public static final String GEN_ITEM_ROWID   = "_id";
    public static final String GEN_ITEM_ARCHV   = "GEN_ARCHV";  // archive flag.
    public static final String GEN_ITEM_ID      = "GEN_ID";     // id (compacted date YYYYMMDDHHMM).
    public static final String GEN_ITEM_GENERAL = "GENERAL_ID"; // table GENERAL id.
    public static final String GEN_ITEM_SLIST   = "SLIST_ID";  //  table SLIST id.
    public static final String GEN_ITEM_TEXT    = "GEN_TEXT";  //  item's text.

    public static final String CREATE_TABLE_GEN_ITEM = "CREATE TABLE "
            + TABLE_GEN_ITEM   + " ("
            + GEN_ITEM_ROWID   + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GEN_ITEM_ARCHV   + " TEXT NOT NULL, "
            + GEN_ITEM_ID      + " TEXt NOT NULL, "
            + GEN_ITEM_GENERAL + " TEXT NOT NULL, "
            + GEN_ITEM_SLIST   + " TEXt, "
            + GEN_ITEM_TEXT    + " TEXT NOT NULL)";

    public static final String DROP_TABLE_GEN_ITEM =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_GEN_ITEM + ";";

    public static final String WHERE_GEN_ITEM_ROWID = GEN_ITEM_ROWID + "=?";
    public static final String WHERE_GEN_ITEM_ARCHV = GEN_ITEM_ARCHV + "=?";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Table SLIST_ITEM">
    // A record in this table represents a single entry in a Shopping List.
    // This is a linking table between tables SLIST and REF_ITEM.
    public static final String TABLE_SLIST_ITEM    = "SLIST_ITEM";
    public static final String SLIST_ITEM_ROWID    = "_id";
    public static final String SLIST_ITEM_ARCHV    = "ARCHV";        // archive flag.
    public static final String SLIST_ITEM_SLIST_ID = "SLIST_ID";     // SLIST.ROWID
    public static final String SLIST_ITEM_REF_ID   = "REF_ID";       // REF_ITEM.ROWID

    public static final String CREATE_TABLE_SLIST_ITEM = "CREATE TABLE "
            + TABLE_SLIST_ITEM    + " ("
            + SLIST_ITEM_ROWID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SLIST_ITEM_ARCHV    + " TEXT NOT NULL, "
            + SLIST_ITEM_SLIST_ID + " INTEGER NOT NULL, "
            + SLIST_ITEM_REF_ID   + " INTEGER NOT NULL)";

    public static final String DROP_TABLE_SLIST_ITEM =
            "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TABLE_SLIST_ITEM + ";";

    public static final String WHERE_SLIST_ITEM_ROWID = SLIST_ITEM_ROWID + "=?";
    public static final String WHERE_SLIST_ITEM__ARCHV = SLIST_ITEM_ARCHV + "=?";
    public static final String WHERE_SLIST_ITEM_SLIST_ID = SLIST_ID + "=?";
    //</editor-fold>

    // Raw query
    public static final String QUERY_RAW_GET_SHOPPING =
        "SELECT RI.REF_CODE, RI.REF_DESC, RI.REF_VALUE " +
        "FROM REF_ITEM RI, SLIST_ITEM SLI, SLIST SL " +
        "WHERE RI._ID=SLI.REF_ID AND SLI.SLIST_ID=SL._ID AND SL._ID=?";
}
