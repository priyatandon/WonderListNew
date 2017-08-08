package com.priyatandon.wonderlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemAdapter;
    ListView itemList;
    EditText addItemInput;
    int itemPosition;
    private static String itemPreEdit="";
    private final int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printArrayItems();
        itemList=(ListView)findViewById(R.id.itemList);
        itemList.setAdapter(itemAdapter);;


        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemPosition=i;

                Intent editIntent=new Intent(MainActivity.this,EditItemActivity.class);
                String itemToEdit=items.get(itemPosition);
                itemPreEdit=itemToEdit;
                editIntent.putExtra("itemToEdit",itemToEdit);
                startActivityForResult(editIntent,REQUEST_CODE);

            }
        });


        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemToDelete=items.get(i);
                items.remove(i);
                Toast.makeText(MainActivity.this,itemToDelete+" deleted",Toast.LENGTH_SHORT).show();
                itemAdapter.notifyDataSetChanged();
                writeItems();
                return true;

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode ==  RESULT_OK){
                if(data == null){
                    return;
                }
                String itemToSave=data.getStringExtra("itemToSave");
                items.set(itemPosition,itemToSave);
                Toast.makeText(this, itemToSave + " edited", Toast.LENGTH_SHORT).show();
                itemAdapter.notifyDataSetChanged();
            }
        }
    }

    public void printArrayItems(){
        readItems();
        itemAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
    }

    public void readItems() {
        File filesDir = getFilesDir();
        File itemFile = new File(filesDir, "itemlist.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(itemFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    public void writeItems(){
        File filesDir = getFilesDir();
        File itemFile = new File(filesDir, "itemlist.txt");
        try {
           FileUtils.writeLines(itemFile,items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddItem(View view) {
        addItemInput=(EditText)findViewById(R.id.addItemInput);
        String newItem=addItemInput.getText().toString();
        if(newItem.length()==0){
            Toast.makeText(this,"Please enter the item",Toast.LENGTH_SHORT).show();
        }
        else {
            itemAdapter.add(newItem);
            addItemInput.setText("");
            writeItems();
        }
    }
}
