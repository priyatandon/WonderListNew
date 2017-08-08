package com.priyatandon.wonderlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle editData=getIntent().getExtras();
        if(editData==null){
            return;
        }
        String editItem=editData.getString("itemToEdit");
        EditText editItemInput=(EditText)findViewById(R.id.editItemInput) ;
        editItemInput.setText(editItem);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;

    }

    public void onSave(View view) {
        Intent saveIntent=new Intent(EditItemActivity.this,MainActivity.class);
        EditText  editItemInput=(EditText)findViewById(R.id.editItemInput) ;
        String itemToSave=editItemInput.getText().toString();
        saveIntent.putExtra("itemToSave",itemToSave);
        setResult(RESULT_OK,saveIntent);
        finish();
    }

}
