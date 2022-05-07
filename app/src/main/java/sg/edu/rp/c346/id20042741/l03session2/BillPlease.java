package sg.edu.rp.c346.id20042741.l03session2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.regex.Pattern;

public class BillPlease extends AppCompatActivity {

    EditText amtTxt;
    EditText paxTxt;
    EditText discTxt;
    ToggleButton svsToggle;
    ToggleButton gstToggle;
    RadioGroup paymentGrp;
    Button splitBtn;
    Button resetBtn;
    TextView billView;
    TextView splitView;
    double svsCharge = 0;
    double gstCharge = 0;
    EditText paynowNum;
    int selectedId;
    RadioButton cashRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_please);

        amtTxt = findViewById(R.id.amtTxtId);
        paxTxt = findViewById(R.id.paxTxtId);
        discTxt = findViewById(R.id.discTxtId);
        svsToggle = findViewById(R.id.btnSvsId);
        gstToggle = findViewById(R.id.btnGstId);
        paymentGrp = findViewById(R.id.paymentTypeId);
        splitBtn = findViewById(R.id.btnSplit);
        resetBtn = findViewById(R.id.btnReset);
        billView = findViewById(R.id.totalOutputId);
        splitView = findViewById(R.id.splitOutputId);
        paynowNum = findViewById(R.id.mobileNoTxt);
        cashRb = findViewById(R.id.rbCash);

        svsToggle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                svsCharge = svsToggle.isChecked() ? 0.07 : 0;
            }
        });

        gstToggle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                gstCharge = gstToggle.isChecked() ? 0.1 : 0;
            }
        });

        splitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                refreshDisplay();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

    }
    public void refreshDisplay() {
        double totalAmt = amtTxt.getText().toString().isEmpty() ? 0 : Double.parseDouble(amtTxt.getText().toString());
        int totalPax = paxTxt.getText().toString().isEmpty() ? 1 : Integer.parseInt(paxTxt.getText().toString());
        double totalDisc = discTxt.getText().toString().isEmpty() ? 0 : Double.parseDouble(discTxt.getText().toString()) / 100;
        String outputMsg = paynowNum.getText().toString().isEmpty() ? "in cash" : "via PayNow to" + paynowNum.getText().toString();

        String mobileNoPattern = ("(8|9)[0-9]{7}");
        String noPattern = ("^\\d*\\.?\\d*$");

        Boolean mobileNoValidation = Pattern.matches(mobileNoPattern, paynowNum.getText().toString());
        Boolean numValidation = Pattern.matches(noPattern, amtTxt.getText().toString());

        selectedId = paymentGrp.getCheckedRadioButtonId();

        double paymentDue = totalAmt * (1 - (svsCharge + gstCharge + totalDisc));
        double split = paymentDue / totalPax;

        if (totalAmt < 0 || !numValidation) {
            billView.setText("Please enter a valid amount!");
            billView.setTextColor(getResources().getColor(R.color.red));
            splitView.setText("");
        }

        else if (amtTxt.getText().toString().isEmpty()) {
            billView.setText("Please enter something!");
            billView.setTextColor(getResources().getColor(R.color.red));
            splitView.setText("");
        }

        else if (selectedId == R.id.rbPayNow && !mobileNoValidation) {
            billView.setText("Please enter number in 8/9 format followed by 7 numbers");
            billView.setTextColor(getResources().getColor(R.color.red));
            splitView.setText("");
        }

        else {
            billView.setTextColor(getResources().getColor(R.color.black));
            billView.setText(String.format("Total bill: %.2f", paymentDue));
            splitView.setText(String.format("Each pays: $%.2f  %s", split, outputMsg));
        }

    }
}