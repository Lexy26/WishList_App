package com.example.wishlistproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DbName = "DataB";
    public static String DbPath = "";
    Context mContext;

    public DatabaseHelper( Context context) {
        super(context, DbName, null, 1);
        mContext = context;
        DbPath = "/data/data/" + context.getPackageName() + "/databases/";
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table UTILISATEUR
        db.execSQL("CREATE TABLE if not exists UTILISATEUR (\n" +
                "    User             TEXT NOT NULL\n" +
                "                          PRIMARY KEY,\n" +
                "    Password         TEXT NOT NULL,\n" +
                "    Image            BLOB,\n" +
                "    Taille           TEXT,\n" +
                "    Nom_Prenom       TEXT NOT NULL,\n" +
                "    Adresse          TEXT NOT NULL,\n" +
                "    Couleur_App      TEXT NOT NULL,\n" +
                "    Anniversaire     DATE,\n" +
                "    Couleur_Favorite TEXT\n" +
                ");");
        //Create table WHISHLIST
        db.execSQL("CREATE TABLE if not exists WHISHLIST (\n" +
                "    IDList   INTEGER NOT NULL\n" +
                "                     PRIMARY KEY,\n" +
                "    Etat     TEXT    NOT NULL,\n" +
                "    Image    BLOB,\n" +
                "    ListName TEXT    NOT NULL\n" +
                ");");
        //Create table STATUE_PRODUIT
        db.execSQL("CREATE TABLE if not exists STATUE_PRODUIT (\n" +
                "    NrProduit INTEGER NOT NULL,\n" +
                "    IDList    INTEGER NOT NULL,\n" +
                "    Status    TEXT    NOT NULL,\n" +
                "    PRIMARY KEY (\n" +
                "        NrProduit,\n" +
                "        IDList\n" +
                "    ),\n" +
                "    FOREIGN KEY (\n" +
                "        NrProduit\n" +
                "    )\n" +
                "    REFERENCES PRODUIT (NrProduit),\n" +
                "    FOREIGN KEY (\n" +
                "        IDList\n" +
                "    )\n" +
                "    REFERENCES WHISHLIST (IDList) \n" +
                ");");
        //Create table SITE
        db.execSQL("CREATE TABLE if not exists SITE (\n" +
                "    NrProduit INTEGER NOT NULL,\n" +
                "    NomSite   TEXT    NOT NULL,\n" +
                "    Prix      INTEGER,\n" +
                "    PRIMARY KEY (\n" +
                "        NrProduit,\n" +
                "        NomSite\n" +
                "    ),\n" +
                "    FOREIGN KEY (\n" +
                "        NrProduit\n" +
                "    )\n" +
                "    REFERENCES PRODUIT (NrProduit) \n" +
                ");");
        //Create table SHARE
        db.execSQL("CREATE TABLE if not exists SHARE (\n" +
                "    User       TEXT    NOT NULL,\n" +
                "    FriendUser TEXT    NOT NULL,\n" +
                "    IDList     INTEGER NOT NULL,\n" +
                "    PRIMARY KEY (\n" +
                "        FriendUser,\n" +
                "        User\n" +
                "    ),\n" +
                "    FOREIGN KEY (\n" +
                "        FriendUser\n" +
                "    )\n" +
                "    REFERENCES UTILISATEUR (User),\n" +
                "    FOREIGN KEY (\n" +
                "        IDList\n" +
                "    )\n" +
                "    REFERENCES WHISHLIST (IDList),\n" +
                "    FOREIGN KEY (\n" +
                "        User\n" +
                "    )\n" +
                "    REFERENCES UTILISATEUR (User) \n" +
                ");");
        //Create table QUANTITE_PRODUIT
        db.execSQL("CREATE TABLE if not exists QUANTITE_PRODUIT (\n" +
                "    NbrQuantité INTEGER NOT NULL,\n" +
                "    NrProduit   INTEGER NOT NULL,\n" +
                "    IDList      INTEGER NOT NULL,\n" +
                "    PRIMARY KEY (\n" +
                "        NrProduit,\n" +
                "        IDList\n" +
                "    ),\n" +
                "    FOREIGN KEY (\n" +
                "        NrProduit\n" +
                "    )\n" +
                "    REFERENCES PRODUIT (NrProduit),\n" +
                "    FOREIGN KEY (\n" +
                "        IDList\n" +
                "    )\n" +
                "    REFERENCES WHISHLIST (IDList) \n" +
                ");\n");
        //Create table PRODUIT
        db.execSQL("CREATE TABLE if not exists PRODUIT (\n" +
                "    NrProduit INTEGER PRIMARY KEY\n" +
                "                      NOT NULL,\n" +
                "    NomProd   TEXT    NOT NULL,\n" +
                "    PrixMax   INTEGER,\n" +
                "    PrixMin   INTEGER,\n" +
                "    Image     BLOB,\n" +
                "    Couleur   TEXT,\n" +
                "    Taille    BLOB,\n" +
                "    Détail    BLOB\n" +
                ");");
        //Create table HOBBY
        db.execSQL("CREATE TABLE if not exists HOBBY (\n" +
                "    User    TEXT NOT NULL,\n" +
                "    IDHobby BLOB,\n" +
                "    FOREIGN KEY (\n" +
                "        User\n" +
                "    )\n" +
                "    REFERENCES UTILISATEUR (User),\n" +
                "    PRIMARY KEY (\n" +
                "        User,\n" +
                "        IDHobby\n" +
                "    )\n" +
                ");");
        //Create table FRIENDS
        db.execSQL("CREATE TABLE if not exists FRIENDS (\n" +
                "    User        TEXT NOT NULL,\n" +
                "    FriendUser  TEXT NOT NULL,\n" +
                "    EtatRequête TEXT NOT NULL,\n" +
                "    PRIMARY KEY (\n" +
                "        User,\n" +
                "        FriendUser\n" +
                "    ),\n" +
                "    FOREIGN KEY (\n" +
                "        FriendUser\n" +
                "    )\n" +
                "    REFERENCES UTILISATEUR (User),\n" +
                "    FOREIGN KEY (\n" +
                "        User\n" +
                "    )\n" +
                "    REFERENCES UTILISATEUR (User) \n" +
                ");");
        //Create table EVALUATION
        db.execSQL("CREATE TABLE if not exists EVALUATION (\n" +
                "    NrProduit    INTEGER NOT NULL,\n" +
                "    IDList       INTEGER NOT NULL,\n" +
                "    IDEvaluation INTEGER NOT NULL,\n" +
                "    PRIMARY KEY (\n" +
                "        NrProduit,\n" +
                "        IDList\n" +
                "    ),\n" +
                "    FOREIGN KEY (\n" +
                "        IDEvaluation\n" +
                "    )\n" +
                "    REFERENCES APPRECIATION (IDEvaluation) \n" +
                ");");
        //Create table CATEGORIE
        db.execSQL("CREATE TABLE if not exists CATEGORIE (\n" +
                "    NrProduit INTEGER NOT NULL,\n" +
                "    NomCat    TEXT    NOT NULL,\n" +
                "    PRIMARY KEY (\n" +
                "        NrProduit,\n" +
                "        NomCat\n" +
                "    ),\n" +
                "    FOREIGN KEY (\n" +
                "        NrProduit\n" +
                "    )\n" +
                "    REFERENCES PRODUIT (NrProduit) \n" +
                ");\n");
        //Create table APPRECIATION
        db.execSQL("CREATE TABLE if not exists APPRECIATION (\n" +
                "    IDEvaluation INTEGER PRIMARY KEY\n" +
                "                         NOT NULL,\n" +
                "    Etoile       INTEGER NOT NULL,\n" +
                "    Commentaire  TEXT\n" +
                ");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data
        Log.w("Provider database", "upgrading database from version "+ oldVersion + " to "+ newVersion+", which will destroy all data");
        db.execSQL("DROP TABLE IF EXISTS UTILISATEUR");
        db.execSQL("DROP TABLE IF EXISTS WHISHLIST");
        db.execSQL("DROP TABLE IF EXISTS STATUE_PRODUIT");
        db.execSQL("DROP TABLE IF EXISTS SITE");
        db.execSQL("DROP TABLE IF EXISTS SHARE");
        db.execSQL("DROP TABLE IF EXISTS QUANTITE_PRODUIT");
        db.execSQL("DROP TABLE IF EXISTS PRODUIT");
        db.execSQL("DROP TABLE IF EXISTS HOBBY");
        db.execSQL("DROP TABLE IF EXISTS FRIENDS");
        db.execSQL("DROP TABLE IF EXISTS EVALUATION");
        db.execSQL("DROP TABLE IF EXISTS CATEGORIE");
        db.execSQL("DROP TABLE IF EXISTS APPRECIATION");
        onCreate(db);
    }
    private boolean ExistDatabase(){
        // if there is interested database in the path given
        File mFile = new File(DbPath+DbName);
        return mFile.exists();
    }
    public void StartWork(){
        DbPath = mContext.getFilesDir().getParent()+"/databases/";
        if (!ExistDatabase()) {
            this.getWritableDatabase();
            CopyDatabase();
        }
    }

    public void CopyDatabase(){
            try {
                InputStream mInput = mContext.getAssets().open(DbName);
                OutputStream mOutput = new FileOutputStream(DbPath + DbName);
                int lenght;
                byte[] mBuffer = new byte[1024];
                while ((lenght = mInput.read(mBuffer))> 0){
                    mOutput.write(mBuffer,0, lenght);
                }
                mInput.close();
                mOutput.flush();
                mOutput.close();
            }catch (Exception e){
            }}

    public void CheckDatabase(){
        try{
            String path = DbPath + DbName;
            SQLiteDatabase.openDatabase(path, null, 0);
        }catch (Exception e) {
            this.getReadableDatabase();
            CopyDatabase();
        }
    }
    public void OpenDatabase(){
        String path = DbPath + DbName;
        SQLiteDatabase.openDatabase(path,null,0);

    }
}
