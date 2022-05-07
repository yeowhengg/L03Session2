package sg.edu.rp.c346.id20042741.l03session2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

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

        svsToggle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                svsCharge = svsToggle.isChecked() ? 0.07 : 0;
                refreshDisplay();
            }
        });

        gstToggle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                gstCharge = gstToggle.isChecked() ? 0.1 : 0;
                refreshDisplay();
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
                amtTxt.setText("");
                paxTxt.setText("");
                discTxt.setText("");
                paymentGrp.clearCheck();
                svsToggle.setChecked(false);
                gstToggle.setChecked(false);
                billView.setText("");
                splitView.setText("");
            }
        });

    }
    public void refreshDisplay(){
        double totalAmt = Double.parseDouble(amtTxt.getText().toString());
        int totalPax = Integer.parseInt(paxTxt.getText().toString());
        double totalDisc = discTxt.getText().toString().isEmpty() ? 0 : Double.parseDouble(discTxt.getText().toString())/100;

        String regex = ("(8|9)[0-9]{7}");
        Boolean mobileNoValidation = Pattern.matches(regex, paynowNum.getText().toString());
        selectedId = paymentGrp.getCheckedRadioButtonId();

        double paymentDue = totalAmt * (1-(svsCharge + gstCharge + totalDisc));
        double split = paymentDue / totalPax;

        if(selectedId < 0){
            Log.println(Log.DEBUG,"debug","HELLO");
            billView.setText(String.format("Total bill: %.2f", paymentDue));
            splitView.setText(String.format("Each pays: $%.2f in cash",split));
        }
        else if((selectedId == R.id.rbPayNow) && mobileNoValidation){
            billView.setText(String.format("Total bill: %.2f", paymentDue));
            splitView.setText(String.format("Each pays: $%.2f via PayNow to %s",split, paynowNum.getText().toString()));
        }
        else{
            billView.setText(String.format("Total bill: %.2f", paymentDue));
            splitView.setText(String.format("Each pays: $%.2f in cash",split));
        }

    }
}