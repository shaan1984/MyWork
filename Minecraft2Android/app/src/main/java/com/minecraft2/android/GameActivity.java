package com.minecraft2.android;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.minecraft2.android.engine.GameEngine;
import com.minecraft2.android.engine.GameView;

public class GameActivity extends AppCompatActivity {

    private GameEngine gameEngine;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String worldName = getIntent().getStringExtra("world_name");
        long seed = getIntent().getLongExtra("seed", System.currentTimeMillis());

        gameEngine = new GameEngine(this, worldName, seed);
        gameView = new GameView(this, gameEngine);
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameEngine != null) gameEngine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameEngine != null) {
            gameEngine.pause();
            gameEngine.saveWorld();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameEngine != null) gameEngine.destroy();
    }

    @Override
    public void onBackPressed() {
        if (gameEngine != null && gameEngine.isRunning()) {
            showPauseDialog();
        } else {
            super.onBackPressed();
        }
    }

    private void showPauseDialog() {
        gameEngine.pause();
        new AlertDialog.Builder(this)
                .setTitle("Paused")
                .setItems(new String[]{"Resume", "Save & Quit"}, (dialog, which) -> {
                    if (which == 0) {
                        gameEngine.resume();
                    } else {
                        gameEngine.saveWorld();
                        finish();
                    }
                })
                .setOnCancelListener(d -> gameEngine.resume())
                .show();
    }
}
