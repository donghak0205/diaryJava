package fastcampus.aop.part2.chapter03_java;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DiaryActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        EditText detailEditText = findViewById(R.id.detailEditText);
        SharedPreferences sharedPreferences = getSharedPreferences("diary", MODE_PRIVATE);

        detailEditText.setText(sharedPreferences.getString("diary", ""));

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sharedPreferences.edit().putString("diary", detailEditText.getText().toString()).commit();
            }
        };

        detailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.postDelayed(runnable, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
