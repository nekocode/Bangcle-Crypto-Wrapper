package cn.nekocode.bangcle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.nekocode.bangcle.exported.Keys;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.textView)).setText(
                "KEY1=" + Keys.KEY1 + "\nKEY2=" + Keys.KEY2
        );
    }
}
