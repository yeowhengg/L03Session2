package sg.edu.rp.c346.id20042741.l03session2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class BillPlease extends AppCompatActivity {

    EditText amtTxt;
    EditText paxTxt;
    EditText discTxt;
    ToggleButton svsToggle;
    ToggleButton gstToggle;
    RadioGroup paymentGrp;
    RadioButton cashRb;
    RadioButton paynowRb;
    Button splitBtn;
    Button resetBtn;
    TextView billView;
    TextView splitView;

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
        cashRb = findViewById(R.id.rbCash);
        paynowRb = findViewById(R.id.rbPayNow);
        splitBtn = findViewById(R.id.btnSplit);
        resetBtn = findViewById(R.id.btnReset);
        billView = findViewById(R.id.totalOutputId);
        splitView = findViewById(R.id.splitOutputId);

    }
}