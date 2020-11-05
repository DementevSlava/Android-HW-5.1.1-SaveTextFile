package com.dementev.savetextfile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ExternalFiles externalFiles;
    // Генератор случайностей
    private Random random = new Random();
    // Наш адаптер
    private ItemsDataAdapter adapter;
    // Список картинок, которые мы будем брать для нашего списка
    private List<Drawable> images = new ArrayList<>();
    private List<String> word = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        ListView listView = findViewById(R.id.listView);


        setSupportActionBar(toolbar);

        fillImages();
        fillWord();


        // При тапе по кнопке добавим один новый элемент списка
        fab.setOnClickListener(view -> {
                    generateRandomItemData();
                    externalFiles.saveExternalFile(adapter.getAdapterStrings());
                }
        );
        externalFiles = new ExternalFiles(this, "save.txt");
        adapter = new ItemsDataAdapter(this, null, externalFiles);
        listView.setAdapter(adapter);

        generateLoadedItemData();

        listView.setOnItemClickListener((parent, view, position, id) ->
                showItemData(position)
        );

    }

    private void fillImages() {
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_report_image));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_add));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_agenda));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_camera));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_call));
    }

    private void fillWord() {
        word.add(getString(R.string.title1));
        word.add(getString(R.string.title2));
        word.add(getString(R.string.title3));
        word.add(getString(R.string.title4));
        word.add(getString(R.string.title5));
        word.add(getString(R.string.title6));
        word.add(getString(R.string.title7));
    }

    private void showItemData(int position) {
        ItemData itemData = adapter.getItem(position);
        Toast.makeText(MainActivity.this,
                "Title: " + itemData.getTitle() + "\n" +
                        "Subtitle: " + itemData.getSubtitle() + "\n",
                Toast.LENGTH_SHORT).show();
    }

    private void generateRandomItemData() {
        if (adapter.getCount() < word.size()) {
            adapter.addItem(new ItemData(
                    images.get(random.nextInt(images.size())),
                    word.get(adapter.getCount()),
                    "Разделы"));
        } else {
            Toast.makeText(this, "Больше нет записей", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateLoadedItemData() {
        List<String> list = externalFiles.loadExternalFile();
        if (list != null) {
            for (String s : list) {
                adapter.addItem(new ItemData(
                        images.get(random.nextInt(images.size())),
                        s,
                        "Разделы"));
            }
        }

    }
}