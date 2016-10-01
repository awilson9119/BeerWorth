package aw.beerworth;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //variable declarations
    Button calcButton, baButton;
    EditText beerPrice, abv, numBottles, bottleVol;
    double dBeerPrice, dAbv, dBottleVol;
    int iNumBottles;
    TextView pricePerOz, errorMsg, prev1, prev2, prev3, prev4;

    String eMsg = "You messed up the input, dummy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find the elements
        beerPrice  = (EditText) findViewById(R.id.beerPrice);
        numBottles = (EditText) findViewById(R.id.numBottles);
        bottleVol  = (EditText) findViewById(R.id.bottleVol);
        abv        = (EditText) findViewById(R.id.abv);
        baButton   = (Button)   findViewById(R.id.baLinkButton);
        calcButton = (Button)   findViewById(R.id.calcButton);
        errorMsg   = (TextView) findViewById(R.id.errorMsg);
        pricePerOz = (TextView) findViewById(R.id.pricePerOz);
        prev1      = (TextView) findViewById(R.id.prev1);
        prev2      = (TextView) findViewById(R.id.prev2);
        prev3      = (TextView) findViewById(R.id.prev3);
        prev4      = (TextView) findViewById(R.id.prev4);

        //set button listeners
        calcButton.setOnClickListener(this);

        baButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.beeradvocate.com"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (inputsEmpty(beerPrice, numBottles, bottleVol, abv)) {
            errorMsg.setText(eMsg);
        }
        else {

            //cast input to correct types
            dBeerPrice  = parseDouble(beerPrice.getText().toString());
            iNumBottles = parseInt(numBottles.getText().toString());
            dBottleVol  = parseDouble(bottleVol.getText().toString());
            dAbv        = parseDouble(abv.getText().toString());

            if (inputsOk(dBeerPrice, iNumBottles, dBottleVol, dAbv)) {
                double result = calcPricePerOz(dBeerPrice, iNumBottles, dBottleVol, dAbv);

                //cast back to string and limit decimals
                String strResult = Double.toString(result);
                strResult = strResult.substring(0, Math.min(strResult.length(), 6));

                //send result to textview and move previous results down the list
                errorMsg.setText("");
                prev4.setText(prev3.getText().toString());
                prev3.setText(prev2.getText().toString());
                prev2.setText(prev1.getText().toString());
                prev1.setText(pricePerOz.getText().toString());
                pricePerOz.setText(strResult);
            }
            else {
                errorMsg.setText(eMsg);
            }
        }
    }

    //calculates price for one fl.oz. of alcohol
    public double calcPricePerOz(double dBeerPrice, int iNumBottles, double dBottleVol, double dAbv) {
        double decimalAbv = dAbv/100;
        double totAlc = dBottleVol * iNumBottles * decimalAbv;
        return dBeerPrice/totAlc;
    }

    //returns true if an input is empty
    public boolean inputsEmpty(EditText beerPrice, EditText numBottles, EditText bottleVol, EditText abv) {
        return  TextUtils.isEmpty(beerPrice.getText().toString()) ||
                TextUtils.isEmpty(numBottles.getText().toString()) ||
                TextUtils.isEmpty(bottleVol.getText().toString()) ||
                TextUtils.isEmpty(abv.getText().toString());
    }

    //returns true if the inputs are all positive
    public boolean inputsOk(double beerPrice, int numBottles, double bottleVol, double abv) {
        return (beerPrice > 0) && (numBottles > 0) && (bottleVol > 0) && (abv > 0);
    }

}