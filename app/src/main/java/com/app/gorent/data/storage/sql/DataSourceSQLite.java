package com.app.gorent.data.storage.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.app.gorent.R;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.model.Role;
import com.app.gorent.data.model.User;
import com.app.gorent.data.storage.Session;
import com.app.gorent.ui.activities.auth.LoggedInUserView;
import com.app.gorent.utils.result.AuthResult;
import com.app.gorent.utils.result.UserQueryResult;

public class DataSourceSQLite {

    private Context context;
    private SQLite sqlite;
    private SQLiteDatabase database;
    private static DataSourceSQLite instance;

    private DataSourceSQLite(Context context) {
        this.context = context;
        this.sqlite = new SQLite(context);
        Log.i("SQLite",
                "Opens the database connection " +
                        sqlite.getDatabaseName());
        this.database = sqlite.getWritableDatabase();
        //this.database.execSQL("delete from users");
    }

    public static DataSourceSQLite getInstance(Context context){
        if(instance==null){
            instance = new DataSourceSQLite(context);
        }
        return instance;
    }

    public void login(String email, String password, MutableLiveData<AuthResult> authResult){
        String loginStatement = String.format(StatementsSQL.login, email, password);
        Cursor cursor = database.rawQuery(loginStatement,null);
        User user = cursorToUser(cursor);
        if(user!=null){
            LoggedInUser loggedInUser = new LoggedInUser(user.getEmail()+"", user.getFull_name()+"");
            new Session(context).saveLoggedInUser(loggedInUser);
            authResult.setValue(new AuthResult(new LoggedInUserView(loggedInUser.getFull_name())));
        }else{
            authResult.setValue(new AuthResult(R.string.login_failed));
        }
    }

    public void signUp(User user, MutableLiveData<AuthResult> authResult){
        ContentValues cv = new ContentValues();
        cv.put("email", user.getEmail());
        cv.put("password", user.getPassword());
        cv.put("full_name", user.getFull_name());
        cv.put("role", user.getRole().toString());
        long result=-1;
        try{
            result = database.insertOrThrow(
                    "users",
                    null, cv);
        }catch(SQLException e){
            Log.e("Exception","SQLException"+String.valueOf(e.getMessage()));
            e.printStackTrace();
            authResult.setValue(new AuthResult(R.string.sign_up_failed));
            return;
        }
        if(result!=-1){
            LoggedInUser loggedInUser = new LoggedInUser(user.getEmail()+"", user.getFull_name()+"");
            new Session(context).saveLoggedInUser(loggedInUser);
            authResult.setValue(new AuthResult(new LoggedInUserView(loggedInUser.getFull_name())));
        }else{
            authResult.setValue(new AuthResult(R.string.sign_up_failed));
        }
    }

    public void findUserByEmail(String email, MutableLiveData<UserQueryResult> userQueryResult){
        String findUserStatement = String.format(StatementsSQL.findUserByEmail, email);
        Cursor cursor = database.rawQuery(findUserStatement,null);
        User user = cursorToUser(cursor);
        if(user!=null) {
            userQueryResult.setValue(new UserQueryResult(user));
        }else{
            userQueryResult.setValue(new UserQueryResult(R.string.error_user_not_found));
        }
    }

    private User cursorToUser(Cursor cursor){
        User user = null;
        if(cursor.moveToFirst()){
            do{
                user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setFull_name(cursor.getString(cursor.getColumnIndex("full_name")));
                user.setRole(Role.valueOf(cursor.getString(cursor.getColumnIndex("role"))));
                break;
            }while(cursor.moveToNext());
        }
        return user;
    }

    public void logout() {
        new Session(context).clear();
    }
}
