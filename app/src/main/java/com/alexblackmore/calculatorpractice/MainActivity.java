package com.alexblackmore.calculatorpractice;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "testing.txt";

    Button button0Btn;
    Button button1Btn;
    Button button2Btn;
    Button button3Btn;
    Button button4Btn;
    Button button5Btn;
    Button button6Btn;
    Button button7Btn;
    Button button8Btn;
    Button button9Btn;
    Button buttonPlusBtn;
    Button buttonMinusBtn;
    Button buttonTimesBtn;
    Button buttonDivideBtn;
    Button buttonDecimalBtn;
    Button buttonEqualsBtn;
    Button buttonClearBtn;
    EditText inputQuestionET;
    TextView outputAnswerTV;
    ImageView menuIV;

    String outputAnswerStr;

    Button.OnClickListener mNumberListener;
    Button.OnClickListener mEqualsListener;
    Button.OnClickListener mClearListener;
    ImageView.OnClickListener mMenuListener;
    PopupMenu.OnMenuItemClickListener mMenuClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0Btn = findViewById(R.id.buttonZero);
        button1Btn = findViewById(R.id.buttonOne);
        button2Btn = findViewById(R.id.buttonTwo);
        button3Btn = findViewById(R.id.buttonThree);
        button4Btn = findViewById(R.id.buttonFour);
        button5Btn = findViewById(R.id.buttonFive);
        button6Btn = findViewById(R.id.buttonSix);
        button7Btn = findViewById(R.id.buttonSeven);
        button8Btn = findViewById(R.id.buttonEight);
        button9Btn = findViewById(R.id.buttonNine);
        buttonPlusBtn = findViewById(R.id.buttonPlus);
        buttonMinusBtn = findViewById(R.id.buttonMinus);
        buttonTimesBtn = findViewById(R.id.buttonTimes);
        buttonDivideBtn = findViewById(R.id.buttonDivide);
        buttonDecimalBtn = findViewById(R.id.buttonDecimal);
        buttonEqualsBtn = findViewById(R.id.buttonEquals);
        buttonClearBtn = findViewById(R.id.buttonClear);
        inputQuestionET = findViewById(R.id.inputQuestionWdg);
        outputAnswerTV = findViewById(R.id.outputAnswerWdg);
        menuIV = findViewById(R.id.imageMenu);

        mNumberListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonifiedParam = (Button) v;
                String mNumberValueStr = buttonifiedParam.getText().toString();

                if (mNumberValueStr.matches(".*[+\\-*/].*") && inputQuestionET.getText().toString().matches(".*[+\\-*/].*")) {
                    Toast.makeText(MainActivity.this, R.string.only_one_operator_msg, Toast.LENGTH_SHORT).show();
                } else if (mNumberValueStr.matches(".*[+\\-*/].*") && inputQuestionET.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.cant_start_operator_msg, Toast.LENGTH_SHORT).show();
                } else {
                    inputQuestionET.append(mNumberValueStr);
                }
            }
        };


        mClearListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputQuestionET.getText().clear();
            }
        };


        mEqualsListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputQuestionStr = inputQuestionET.getText().toString();

                if (inputQuestionStr.matches("[0-9]*[.]?[0-9]*[+\\-*\\/]{1}[0-9]*[.]?[0-9]*")) {
                    if (inputQuestionStr.contains("*")) {
                        String[] splitStrArray = inputQuestionStr.split("\\*");
                        double splitFirstHalfDbl = Double.parseDouble(splitStrArray[0]);
                        double splitSecondHalfDbl = Double.parseDouble(splitStrArray[1]);
                        double outputAnswerDbl = splitFirstHalfDbl * splitSecondHalfDbl;
                        outputAnswerStr = Double.toString(outputAnswerDbl);
                        outputAnswerTV.setText(outputAnswerStr);
                    } else if (inputQuestionStr.contains("+")) {
                        String[] splitStrArray = inputQuestionStr.split("\\+");
                        double splitFirstHalfDbl = Double.parseDouble(splitStrArray[0]);
                        double splitSecondHalfDbl = Double.parseDouble(splitStrArray[1]);
                        double outputAnswerDbl = splitFirstHalfDbl + splitSecondHalfDbl;
                        outputAnswerStr = Double.toString(outputAnswerDbl);
                        outputAnswerTV.setText(outputAnswerStr);
                    } else if (inputQuestionStr.contains("-")) {
                        String[] splitStrArray = inputQuestionStr.split("-");
                        double splitFirstHalfDbl = Double.parseDouble(splitStrArray[0]);
                        double splitSecondHalfDbl = Double.parseDouble(splitStrArray[1]);
                        double outputAnswerDbl = splitFirstHalfDbl - splitSecondHalfDbl;
                        outputAnswerStr = Double.toString(outputAnswerDbl);
                        outputAnswerTV.setText(outputAnswerStr);
                    } else if (inputQuestionStr.contains("/")) {
                        String[] splitStrArray = inputQuestionStr.split("/");
                        double splitFirstHalfDbl = Double.parseDouble(splitStrArray[0]);
                        double splitSecondHalfDbl = Double.parseDouble(splitStrArray[1]);
                        double outputAnswerDbl = splitFirstHalfDbl / splitSecondHalfDbl;
                        outputAnswerStr = Double.toString(outputAnswerDbl);
                        outputAnswerTV.setText(outputAnswerStr);
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.invalid_format, Toast.LENGTH_SHORT).show();
                }
            }
        };

        mMenuListener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        };

        mMenuClickListener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().toString().equals(getString(R.string.use_in_next_msg))) {
                    inputQuestionET.setText(outputAnswerStr);
                    inputQuestionET.setSelection(inputQuestionET.getText().length());
                } else if (item.getTitle().toString().equals(getString(R.string.save_msg))) {
                    saveNumber();
                } else if (item.getTitle().toString().equals(getString(R.string.clear_msg))) {
                    outputAnswerTV.setText("");
                }
                return true;
            }
        };


        button0Btn.setOnClickListener(mNumberListener);
        button1Btn.setOnClickListener(mNumberListener);
        button2Btn.setOnClickListener(mNumberListener);
        button3Btn.setOnClickListener(mNumberListener);
        button4Btn.setOnClickListener(mNumberListener);
        button5Btn.setOnClickListener(mNumberListener);
        button6Btn.setOnClickListener(mNumberListener);
        button7Btn.setOnClickListener(mNumberListener);
        button8Btn.setOnClickListener(mNumberListener);
        button9Btn.setOnClickListener(mNumberListener);
        buttonPlusBtn.setOnClickListener(mNumberListener);
        buttonMinusBtn.setOnClickListener(mNumberListener);
        buttonTimesBtn.setOnClickListener(mNumberListener);
        buttonDivideBtn.setOnClickListener(mNumberListener);
        buttonDecimalBtn.setOnClickListener(mNumberListener);

        buttonEqualsBtn.setOnClickListener(mEqualsListener);

        buttonClearBtn.setOnClickListener(mClearListener);

        menuIV.setOnClickListener(mMenuListener);

    }

    public void showPopupMenu(View v){
        PopupMenu mPopupMenu = new PopupMenu(MainActivity.this, menuIV);
        mPopupMenu.getMenuInflater().inflate(R.menu.popup_menu, mPopupMenu.getMenu());
        mPopupMenu.setOnMenuItemClickListener(mMenuClickListener);
        mPopupMenu.show();
    }

    public void saveNumber() {
        //Does it need View v?
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(outputAnswerStr.getBytes());
            Toast.makeText(this, getString(R.string.saved_to_msg) + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
