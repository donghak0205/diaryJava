package fastcampus.aop.part2.chapter03_java;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NumberPicker firstNumberPicker;
    private NumberPicker secondNumberPicker;
    private NumberPicker thirdNumberPicker;
    private AppCompatButton openButton;
    private AppCompatButton passChangeButton;
    private Boolean passwordChangMode = false;
    private SharedPreferences passwordPreferences;
    private String passwordFromUser;
    private AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNumberPicker = findViewById(R.id.firstNumberPicker);
        firstNumberPicker.setMinValue(0);
        firstNumberPicker.setMaxValue(9);

        secondNumberPicker = findViewById(R.id.secondNumberPicker);
        secondNumberPicker.setMinValue(0);
        secondNumberPicker.setMaxValue(9);

        thirdNumberPicker = findViewById(R.id.thirdNumberPicker);
        thirdNumberPicker.setMinValue(0);
        thirdNumberPicker.setMaxValue(9);

        openButton = findViewById(R.id.openButton);
        passChangeButton = findViewById(R.id.passChangeButton);
        alertBuilder = new AlertDialog.Builder(this);

        openButton.setOnClickListener(v -> {

            passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE);
            passwordFromUser = Integer.toString(firstNumberPicker.getValue()) + Integer.toString(secondNumberPicker.getValue()) + Integer.toString(thirdNumberPicker.getValue());

            Log.d("firstNumberPicker", passwordFromUser);
            if (passwordChangMode) {
                Toast.makeText(this, "비번 변경완료후 클릭해주세요!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                startActivity(new Intent(this, DiaryActivity.class));
            } else {
                showErrorAlertDialog();
            }
        });

        passChangeButton.setOnClickListener(v -> {
            passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE);
            passwordFromUser = Integer.toString(firstNumberPicker.getValue()) + Integer.toString(secondNumberPicker.getValue()) + Integer.toString(thirdNumberPicker.getValue());

            if (passwordChangMode) {

                SharedPreferences.Editor passwordEdit = passwordPreferences.edit();
                passwordEdit.putString("password", passwordFromUser);
                passwordEdit.commit();

                passChangeButton.setBackgroundColor(Color.BLACK);
                passwordChangMode = false;

            } else {
                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show();
                    passChangeButton.setBackgroundColor(Color.RED);

                    passwordChangMode = true;
                } else {
                    showErrorAlertDialog();
                }
            }
        });
    }

    private void showErrorAlertDialog() {
        alertBuilder
                .setTitle("실패!")
                .setMessage("비밀번호가 틀렸습니다.")
                .setPositiveButton("확인", null)
                .create()
                .show();
    }
}