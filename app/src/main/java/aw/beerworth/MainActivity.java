package aw.beerworth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button calcButton;
    EditText beerPrice, abv, numBottles, bottleVol;
    double dBeerPrice, dAbv, dBottleVol;
    int iNumBottles;
    TextView pricePerOz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find the elements
        beerPrice  = (EditText) findViewById(R.id.beerPrice);
        numBottles = (EditText) findViewById(R.id.numBottles);
        bottleVol  = (EditText) findViewById(R.id.bottleVol);
        abv        = (EditText) findViewById(R.id.abv);
        calcButton = (Button)   findViewById(R.id.calcButton);
        pricePerOz = (TextView) findViewById(R.id.pricePerOz);

        //set button listener
        calcButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //cast input to correct types
        dBeerPrice  = parseDouble(beerPrice.getText().toString());
        iNumBottles = parseInt(numBottles.getText().toString());
        dBottleVol  = parseDouble(bottleVol.getText().toString());
        dAbv        = parseDouble(abv.getText().toString());

        double result = calcPricePerOz(dBeerPrice, iNumBottles, dBottleVol, dAbv);
        String strResult = Double.toString(result);
        strResult = strResult.substring(0, Math.min(strResult.length(), 7));
        pricePerOz.setText(strResult);

    }

    public double calcPricePerOz(double dBeerPrice, int iNumBottles, double dBottleVol, double dAbv) {
        double decAbv = dAbv/100;
        double totAlc = dBottleVol * iNumBottles * decAbv;
        double pricePerOz = dBeerPrice/totAlc;
        return pricePerOz;
    }
}