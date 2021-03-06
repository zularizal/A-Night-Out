package sfotakos.anightout.newevent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sfotakos.anightout.R;
import sfotakos.anightout.databinding.ActivityNewEventBinding;
import sfotakos.anightout.home.HomeActivity;

public class NewEventActivity extends AppCompatActivity {

    public static final String HOME_ACTIVITY_PARENT = "home-activity";

    private ActivityNewEventBinding mBinding;

    private final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_event);

        setSupportActionBar(mBinding.newEventToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupEventDateField();
        setupSaveButton();
    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        return this.getNavigationUpIntent();
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        return this.getNavigationUpIntent();
    }

    // TODO Add custom view for date and time picking
    // Temporary implementation as seen on https://stackoverflow.com/a/19897981
    private void setupEventDateField() {
        updateEventDateField();

        final DatePickerDialog.OnDateSetListener date = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateEventDateField();
                    }

                };

        mBinding.newEventDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewEventActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateEventDateField() {
        String myFormat = "dd/MM/yyyy HH:mm:ss"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        mBinding.newEventDateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void setupSaveButton(){
        mBinding.newEventSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Saved!", Toast.LENGTH_LONG).show();
                onNavigateUp();
            }
        });
    }

    private Intent getNavigationUpIntent() {
        Intent navigationIntent = null;

        String action = getIntent().getAction();
        if (action != null) {
            switch (action) {
                case HOME_ACTIVITY_PARENT:
                    navigationIntent = new Intent(this, HomeActivity.class);
                    navigationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    break;
                default:
                    throw new InvalidParameterException("Unknown parent activity");
            }
        }
        return navigationIntent;
    }
}
