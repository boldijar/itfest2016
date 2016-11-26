package com.bolnizar.itfest.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.models.Room;
import com.bolnizar.itfest.data.models.School;
import com.bolnizar.itfest.events.AddEventPresenter;
import com.bolnizar.itfest.events.AddEventView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class AddEventToClassActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, AddEventView {

    public static final String ARG_CLASS_ID = "clasid";
    private static final String ARG_SCHOOL_ID = "schid";

    @BindView(R.id.add_event_date)
    EditText mDate;
    @BindView(R.id.add_event_name)
    EditText mName;
    @BindView(R.id.add_event_checkbox)
    CheckBox mRepetableCheckBox;
    @BindView(R.id.add_event_repeat_days)
    EditText mDays;
    @BindView(R.id.add_event_room)
    EditText mRoomEditText;

    private long mSelectedDate;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
    private AddEventPresenter mAddEventPresenter;
    private List<Room> mRooms;
    private Room mSeletedRoom;

    public static Intent createIntent(Context context, int classId, int schoolId) {
        Intent intent = new Intent(context, AddEventToClassActivity.class);
        intent.putExtra(ARG_CLASS_ID, classId);
        intent.putExtra(ARG_SCHOOL_ID, schoolId);
        return intent;
    }

    private int mClassId;
    private int mSchoolId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_to_class);
        setTitle(getString(R.string.addeventclass));
        mClassId = getIntent().getIntExtra(ARG_CLASS_ID, 0);
        mSchoolId = getIntent().getIntExtra(ARG_SCHOOL_ID, 0);
        ButterKnife.bind(this);
        mAddEventPresenter = new AddEventPresenter(this, this);
        mAddEventPresenter.loadRooms(mSchoolId);
    }

    @OnClick(R.id.add_event_date)
    void dateClick() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @OnClick(R.id.add_event_room)
    void roomClick() {
        if (mRooms == null) {
            Toast.makeText(this, R.string.rooms_not_loaded, Toast.LENGTH_SHORT).show();
            mAddEventPresenter.loadRooms(mSchoolId);
            return;
        }
        if (mRooms.size() == 0) {
            Toast.makeText(this, R.string.school_without_rooms, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        PopupMenu menu = new PopupMenu(this, mRoomEditText);
        for (Room room : mRooms) {
            menu.getMenu().add(room.name);
        }
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                for (Room room : mRooms) {
                    if (room.name.equals(item.getTitle().toString())) {
                        clickedRoom(room);
                    }
                }
                return false;
            }
        });
        menu.show();
    }

    private void clickedRoom(Room room) {
        mSeletedRoom = room;
        mRoomEditText.setText(room.name);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        mSelectedDate = calendar.getTimeInMillis();
        mDate.setText(mSimpleDateFormat.format(calendar.getTime()));
    }

    @OnCheckedChanged(R.id.add_event_checkbox)
    void repeatCheckChanged(boolean checked) {
        mDays.setEnabled(checked);
    }

    @Override
    public void showRooms(List<Room> rooms) {
        mRooms = rooms;
    }

    @Override
    public void showSuccess() {
        mName.setText(null);
    }

    @OnClick(R.id.add_event_save)
    void saveEvent() {
        if (mSelectedDate == 0) {
            showMessage(R.string.date_is_empty);
            return;
        }
        if (mName.getText().toString().trim().length() == 0) {
            showMessage(R.string.name_is_empty);
            return;
        }
        if (mSeletedRoom == null) {
            showMessage(R.string.name_is_empty);
            return;
        }
        Integer repetableIntervalInteger = null;
        if (mRepetableCheckBox.isChecked()) {
            int repetableInterval;
            try {
                repetableInterval = Integer.parseInt(mDays.getText().toString());
                if (repetableInterval <= 0) {
                    throw new NumberFormatException();
                }
                repetableIntervalInteger = repetableInterval;
            } catch (NumberFormatException e) {
                showMessage(R.string.repetable_days_invalid);
                return;
            }
        }
        mAddEventPresenter.addEvent(mName.getText().toString(), mSelectedDate, repetableIntervalInteger, mSeletedRoom.id, mClassId);

    }
}
