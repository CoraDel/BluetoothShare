package com.example.cdelplac.testassets;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutocompleteClass {
    Context c;

    public AutocompleteClass(Context c) {
        this.c = c;
    }

    public ArrayAdapter<String> getAdpaterBd(List<String> list) {
        Set<String> setList = new HashSet<>();
        setList.addAll(list);
        list.clear();
        list.addAll(setList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(c, android.R.layout.select_dialog_item, list);
        return arrayAdapter;
    }

    public List<String> getAlldbList(String request) {
        List<String> list = new ArrayList<String>();
        DatabaseHelper db = new DatabaseHelper(c);
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(request, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return list;
    }
}
