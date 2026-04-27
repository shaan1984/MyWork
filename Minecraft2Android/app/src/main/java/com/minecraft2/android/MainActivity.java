package com.minecraft2.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.minecraft2.android.save.SaveManager;

public class MainActivity extends Activity {

    private SaveManager saveManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveManager = new SaveManager(this);
        setupButtons();
    }

    private void setupButtons() {
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnSettings = findViewById(R.id.btnSettings);
        Button btnAbout = findViewById(R.id.btnAbout);
        Button btnQuit = findViewById(R.id.btnQuit);

        btnPlay.setOnClickListener(v -> showWorldSelectionDialog());
        btnSettings.setOnClickListener(v -> showSettingsDialog());
        btnAbout.setOnClickListener(v -> showAboutDialog());
        btnQuit.setOnClickListener(v -> finishAffinity());
    }

    private void showWorldSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select World");

        String[] worlds = saveManager.listWorlds();
        String[] options;
        if (worlds.length > 0) {
            options = new String[worlds.length + 1];
            System.arraycopy(worlds, 0, options, 0, worlds.length);
            options[worlds.length] = "+ New World";
        } else {
            options = new String[]{"+ New World"};
        }

        final String[] finalWorlds = worlds;
        builder.setItems(options, (dialog, which) -> {
            if (which == options.length - 1) {
                showNewWorldDialog();
            } else {
                launchGame(finalWorlds[which], -1);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showNewWorldDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New World");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 16, 48, 0);

        EditText etName = new EditText(this);
        etName.setHint("World Name");
        etName.setText("My World");
        layout.addView(etName);

        EditText etSeed = new EditText(this);
        etSeed.setHint("Seed (leave blank for random)");
        etSeed.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_SIGNED);
        layout.addView(etSeed);

        builder.setView(layout);
        builder.setPositiveButton("Create", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) name = "My World";
            long seed = etSeed.getText().toString().isEmpty()
                    ? System.currentTimeMillis()
                    : Long.parseLong(etSeed.getText().toString());
            launchGame(name, seed);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void launchGame(String worldName, long seed) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("world_name", worldName);
        intent.putExtra("seed", seed);
        startActivity(intent);
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Settings")
                .setMessage("Graphics: High\nSound: On\nRender Distance: 8 chunks")
                .setPositiveButton("OK", null).show();
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("About Minecraft 2")
                .setMessage(getString(R.string.about_text))
                .setPositiveButton("OK", null).show();
    }
}
