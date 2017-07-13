package com.formation.appli.bruxellesparcourbd.DB;

import android.util.Log;

import com.formation.appli.bruxellesparcourbd.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeremyvdm on 02/07/2017.
 */

public class UserDAO {

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    //region table and column name
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USERID = "_userId";
    public static final String COLUMN_LAST_NAME = "last name";
    public static final String COLUMN_FIRST_NAME = "first name";
    public static final String COLUMN_USER_NAME = "user name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    //endregion

    public static final String TAG = "error db";

    DatabaseReference myRef = mDatabase.getReference(TABLE_USER);

    public void addUserTodatabase(User user){
        myRef.child(COLUMN_USERID).setValue(user);
    }

    public void UpdateUserInDataBase(User user){
        String key = myRef.child(TABLE_USER).push().getKey();
        Map<String, Object> postValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+TABLE_USER+"/" + key, postValues);
        childUpdates.put("/user-posts/" + COLUMN_USERID + "/" + key, postValues);

        myRef.updateChildren(childUpdates);
    }

    public void removeUserFromDatabase(User user){

    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            User user = dataSnapshot.getValue(User.class);
            // ...
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };

    ////region SQLlite request
    //public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXIST " + UserDAO.TABLE_USER +" { /n" +
    //        UserDAO.COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, /n" +
    //        UserDAO.COLUMN_LAST_NAME + " STRING NOT NULL, /n" +
    //        UserDAO.COLUMN_FIRST_NAME + " STRING NOT NULL, /n" +
    //        UserDAO.COLUMN_USER_NAME + " STRING NOT NULL, /n" +
    //        UserDAO.COLUMN_EMAIL + " STRING NOT NULL, /n" +
    //        UserDAO.COLUMN_PASSWORD + " STRING NOT NULL, /n"+
    //        " }";
//
    //public static final String DELETE_USER_TABLE = "DROP TABLE IF EXSIST " + UserDAO.TABLE_USER;
    ////endregion
//
    ////region Accès DB
    //private DBHelper dbHepler;
    //private Context context;
    //private SQLiteDatabase db;
//
    //public UserDAO(Context context) {
    //    this.context = context;
    //}
//
    //public UserDAO OpenReadable(){
    //    dbHepler = new DBHelper(context);
    //    db = dbHepler.getReadableDatabase();
    //    return this;
    //}
//
    //public UserDAO openWritable(){
    //    dbHepler = new DBHelper(context);
    //    db = dbHepler.getReadableDatabase();
    //    return this;
    //}
//
    //public void close(){
    //    db.close();
    //    dbHepler.close();
    //}
    ////endregion
//
    ////region update table
    //public long inserUser(User u){
    //    ContentValues cv = userContentValues(u);
    //    long userId = db.insert(TABLE_USER,null,cv);
    //    return userId;
    //}
//
    //private ContentValues userContentValues(User u) {
    //    ContentValues cv = new ContentValues();
    //    cv.put(COLUMN_LAST_NAME, u.getLastName());
    //    cv.put(COLUMN_FIRST_NAME, u.getFirstName());
    //    cv.put(COLUMN_USER_NAME, u.getUserName());
    //    cv.put(COLUMN_EMAIL, u.getEmail());
    //    cv.put(COLUMN_PASSWORD,u.getPassword());
    //    return cv;
    //}
//
    //public void deleteUser(User u){
    //    String whereClause = COLUMN_USERID + " = " + u.get_userId();
    //    db.delete(TABLE_USER,whereClause,null);
    //}
//
    //public long update(User u){
    //    ContentValues cv = userContentValues(u);
    //    String whereClause = COLUMN_USERID + " = " + u.get_userId();
    //    long userId = db.insert(TABLE_USER,null,cv);
    //    return userId;
    //}
//
    //private User CursorToUser(Cursor c){
    //    int id = c.getInt(c.getColumnIndex(COLUMN_USERID));
    //    String lastName = c.getString(c.getColumnIndex(COLUMN_LAST_NAME));
    //    String firstName = c.getString(c.getColumnIndex(COLUMN_FIRST_NAME));
    //    String userName = c.getString(c.getColumnIndex(COLUMN_USER_NAME));
    //    String email = c.getString(c.getColumnIndex(COLUMN_EMAIL));
    //    String password = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
//
    //    return new User(id,lastName,firstName,userName,email,password);
    //}
//
    //public User getUserById(int id){
    //    String whereClause = COLUMN_USERID + " = " + id;
    //    Cursor c = db.query(TABLE_USER, null, whereClause, null, null, null, null);
//
    //    int count = c.getCount();
    //    if(count>0){
    //        c.moveToFirst();
    //        User u = CursorToUser(c);
    //        return u;
    //    }
    //    return null;
//
    //}
    ////endregion
//
}
