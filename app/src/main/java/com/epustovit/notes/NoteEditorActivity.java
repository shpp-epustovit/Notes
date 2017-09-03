package com.epustovit.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    EditText editText;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editText = (EditText)findViewById(R.id.editText);

        /**
         * получение интента из первой активности
         */
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);          // задать id
        MainActivity.arrayAdapter.notifyDataSetChanged();   // обновить arrayAdapter

        if (noteId != -1){

            editText.setText(MainActivity.notes.get(noteId));
        }else { //add new note

            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;

        }

        /**
         * изменение текста
         */

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                MainActivity.notes.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                /**
                 * saving notes
                 */

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.epustovit.notes", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<String>(MainActivity.notes);

                sharedPreferences.edit().putStringSet("notes",set).apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
