package kvm.turtles.android.android.hamming_code_generator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button_Generate);
        // TextView used to display message indicating generated Hamming Code
        final TextView textViewGeneratedMessage = (TextView)
                findViewById(R.id.textView_generatedHammingCode);
        // TextView used to display generated Hamming code
        final TextView textViewHammingCode = (TextView)
                findViewById(R.id.textView_HammingCode);
        final Context context = getApplicationContext();

        // OnClick to generate code
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View V) {
                EditText editTextSource = (EditText)findViewById(R.id.editText_sourceWord);
                CharSequence sourceChar = editTextSource.getText();

                int charLength = sourceChar.length();
                boolean validation = true;

                // validate that a source word was entered
                if(charLength <= 0) {
                    validation = false;
                    CharSequence toastBlank = "Please enter a source word";
                    final Toast blankToast = Toast.makeText(context, toastBlank, Toast.LENGTH_LONG);
                    blankToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    blankToast.show();
                } else {
                    // validate that only 0's and 1's were entered
                    for(int i = 0; i < charLength; i++) {
                        char sourceBit = sourceChar.charAt(i);
                        int charBit = sourceBit - '0';
                        if (charBit != 1 && charBit != 0) {
                            validation = false;
                            break;
                        }
                    }
                }

                // create hamming code if validation is true
                if(validation) {
                    int hammLength = 0;
                    int r, i, j, pow;

                    // calculate length of hamming code
                    for(r = 1; (charLength + r + 1) > Math.pow(2, r); r++) {
                    }
                    hammLength = r + charLength;

                    // create new array to hold Hamming code
                    int h[] = new int[hammLength];

                    // populate h[] with original code and 0's for check bits
                    for(pow = 0, i = 0, j = 0; j < hammLength; j++) {
                        if(Math.pow(2, pow) == (j + 1)) {
                            h[j] = 0;
                            pow++;
                        } else {
                            h[j] = sourceChar.charAt(i) - '0';
                            i++;
                        }
                    }
                    // Calculate check bits
                    int steps, sum;
                    for(pow = 0, j = 0; j < hammLength; j++) {
                        if(Math.pow(2, pow) == (j + 1)){
                            steps = 0;
                            sum = 0;
                            for (int k = j; k < hammLength; k++) {
                                if(steps <= j) {
                                    sum += h[k];
                                    steps++;
                                } else {
                                    k += j;
                                    steps = 0;
                                }
                            }
                            h[j] = sum % 2;
                            pow++;
                        }
                    }
                    // Show generated Hamming code
                    textViewHammingCode.setText(Arrays.toString(h));
                    textViewGeneratedMessage.setVisibility(View.VISIBLE);
                    textViewHammingCode.setVisibility(View.VISIBLE);


                } else {
                    textViewGeneratedMessage.setVisibility(View.INVISIBLE);
                    textViewHammingCode.setVisibility(View.INVISIBLE);
                    CharSequence toastTextInvalidEntry = "Please enter only 0's and 1's";
                    final Toast invalidEntryToast = Toast.makeText(context,
                            toastTextInvalidEntry, Toast.LENGTH_LONG);
                    invalidEntryToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    invalidEntryToast.show();
                }
            }
        });
    }
}
